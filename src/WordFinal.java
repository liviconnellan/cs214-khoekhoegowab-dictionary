/**
 * CS214 Project 2026
 * Electronic Khoekhoegowab Dictionary
 *
 * File: WordExpanded.java
 *
 * Student Number: 29004691
 * Name: Olivia Connellan
 *
 * Description:
 * Represents an expanded version of a word, including the resolved stem,
 * full word, and associated dialects.
 */

import java.util.ArrayList;
import java.util.List;

public class WordExpanded {
    private String stem;
    private String fullWord;
    private List<String> dialects = new ArrayList<>();

    public WordExpanded(String stem, String fullWord, List<String> dialects) {
        this.stem = stem;
        this.fullWord = fullWord;
        this.dialects = new ArrayList<>(dialects);
    }

    public String getStem() {
        return stem;
    }

    public void setStem(String stem) {
        this.stem = stem;
    }

    public String getFullWord() {
        return fullWord;
    }

    public void setFullWord(String fullWord) {
        this.fullWord = fullWord;
    }

    public List<String> getDialects() {
        return new ArrayList<>(dialects);
    }

    public void setDialects(List<String> dialects) {
        this.dialects = new ArrayList<>(dialects);
    }

    @Override
    public String toString() {
        return stem + " " + fullWord + " " + dialects;
    }  
}
