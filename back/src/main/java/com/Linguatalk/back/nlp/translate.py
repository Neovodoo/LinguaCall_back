from transformers import MarianMTModel, MarianTokenizer
import os

# Установка переменных окружения
os.environ["HF_HUB_DISABLE_SYMLINKS_WARNING"] = "1"

def translate(text, src_lang, tgt_lang):

    # Имя модели на Hugging Face
    model_name = f'Helsinki-NLP/opus-mt-{src_lang}-{tgt_lang}'

    # Указание директории для кэширования модели
    cache_dir = os.path.abspath('translation_models')


    # Загрузка токенизатора и модели с указанием папки кэша
    tokenizer = MarianTokenizer.from_pretrained(model_name, cache_dir=cache_dir)
    model = MarianMTModel.from_pretrained(model_name, cache_dir=cache_dir)


    # Перевод текста
    translated = model.generate(**tokenizer(text, return_tensors="pt", padding=True))
    translated_text = [tokenizer.decode(t, skip_special_tokens=True) for t in translated]
    return translated_text[0]

if __name__ == "__main__":
    import sys
    text = sys.argv[1]
    src_lang = sys.argv[2]
    tgt_lang = sys.argv[3]
    try:
        result = translate(text, src_lang, tgt_lang)
        print(result)
    except Exception as e:
        print(f"Error during translation: {e}")
