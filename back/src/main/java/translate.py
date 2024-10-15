from transformers import MarianMTModel, MarianTokenizer
import os

# Установка переменных окружения
os.environ["HF_HOME"] = "C:/alternative_cache_dir"
os.environ["HF_HUB_DISABLE_SYMLINKS_WARNING"] = "1"

def translate(text, src_lang, tgt_lang):
    model_name = f'Helsinki-NLP/opus-mt-{src_lang}-{tgt_lang}'

    # Загрузка токенизатора и модели с указанием папки кэша
    tokenizer = MarianTokenizer.from_pretrained(model_name, cache_dir="C:/alternative_cache_dir")
    model = MarianMTModel.from_pretrained(model_name, cache_dir="C:/alternative_cache_dir")

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