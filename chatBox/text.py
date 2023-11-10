import spacy
import pt_core_news_sm


nlp = spacy.load("pt_core_news_sm")
nlp = pt_core_news_sm.load()
doc = nlp("Esta é uma frase.")
print([(w.text, w.pos_) for w in doc])