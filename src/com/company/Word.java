package com.company;

/**
 * Created by anranw on 2/26/17.
 */
public class Word{
    private String word;
    private POSTag pos;

    public String getWord() {
        return word;
    }

    public POSTag getPosTag() {
        return pos;
    }

    public SCTag getSCTag() {
        return sc;
    }

    public NERTag getNerTag() {
        return ner;
    }

    private SCTag sc;
    private NERTag ner;

    @Override
    public String toString() {
        return "Word{" +
                "word='" + word + '\'' +
                ", pos=" + pos +
                ", sc=" + sc +
                ", ner=" + ner +
                '}';
    }

    public Word(String word, POSTag pos, SCTag sc, NERTag ner){
        this.word=word;
        this.pos=pos;
        this.sc=sc;
        this.ner=ner;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Word word1 = (Word) o;

        if (!word.equals(word1.word)) return false;
        if (!pos.equals(word1.pos)) return false;
        if (!sc.equals(word1.sc)) return false;
        return ner.equals(word1.ner);

    }

    @Override
    public int hashCode() {
        int result = word.hashCode();
        result = 31 * result + pos.hashCode();
        result = 31 * result + sc.hashCode();
        result = 31 * result + ner.hashCode();
        return result;
    }
}
