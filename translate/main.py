from fastapi import FastAPI, HTTPException
from pydantic import BaseModel
from transformers import MarianMTModel, MarianTokenizer
import os
import uuid

app = FastAPI()

@app.on_event("startup")
async def startup_event():
    # Code to run at startup
    print("SUPA Server started!!!")
    downloadModel("ru", "en")
    downloadModel("en", "ru")

# Request model
class TranslationRequest(BaseModel):
    id: uuid.UUID
    message: str
    languageFrom: str
    languageTo: str

# Response model
class TranslationResponse(BaseModel):
    message: str

@app.post("/translate", response_model=TranslationResponse)
async def translate(request: TranslationRequest):
    if not request.message:
        raise HTTPException(status_code=400, detail="Message cannot be empty")
    
    # Mock translation logic
    translated_message = translate(request.message, request.languageFrom, request.languageTo)
    
    return TranslationResponse(message=translated_message)

# Run the application

def downloadModel(src_lang, tgt_lang):
    print("Start loading cache model" + src_lang + " to " + tgt_lang)
    model_name = f'Helsinki-NLP/opus-mt-{src_lang}-{tgt_lang}'

    # Указание директории для кэширования модели
    cache_dir = os.path.abspath('translation_models')

    # Загрузка токенизатора и модели с указанием папки кэша
    tokenizer = MarianTokenizer.from_pretrained(model_name, cache_dir=cache_dir)
    model = MarianMTModel.from_pretrained(model_name, cache_dir=cache_dir)
    print("End loading cache model")
# Установка переменных окружения
os.environ["HF_HUB_DISABLE_SYMLINKS_WARNING"] = "1"

def translate(text, src_lang, tgt_lang):
    print("Start loading cache model")
    # Имя модели на Hugging Face
    model_name = f'Helsinki-NLP/opus-mt-{src_lang}-{tgt_lang}'

    # Указание директории для кэширования модели
    cache_dir = os.path.abspath('translation_models')

    # Загрузка токенизатора и модели с указанием папки кэша
    tokenizer = MarianTokenizer.from_pretrained(model_name, cache_dir=cache_dir)
    model = MarianMTModel.from_pretrained(model_name, cache_dir=cache_dir)
    print("End loading cache model")
    # Перевод текста
    translated = model.generate(**tokenizer(text, return_tensors="pt", padding=True))
    translated_text = [tokenizer.decode(t, skip_special_tokens=True) for t in translated]
    return translated_text[0]

if __name__ == "__main__":
    downloadModel("ru", "en")
    import uvicorn
    uvicorn.run(app, host="0.0.0.0", port=8000)
