package com.company;

import java.util.List;

/**
 * Created by anranw on 2/26/17.
 */
public class Sentence{
    private List<Word> words;
    public Sentence(List<Word> words){
        this.words=words;
    }

    public List<Word> getWords() {
        return words;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sentence sentence = (Sentence) o;

        return words.equals(sentence.words);

    }

    @Override
    public int hashCode() {
        return words.hashCode();
    }

    @Override
    public String toString() {
        return "Sentence{" +
                "words=" + words +
                '}';
    }

    public void setWords(List<Word> words) {

        this.words = words;
    }
}

