/**
 * CS214 Project 2026
 * Electronic Khoekhoegowab Dictionary
 *
 * File: Head.java
 *
 * Student Number: 29004691
 * Name: Olivia Connellan
 *
 * Description:
 * Represents the head section of an entry, containing one or more WordSpec
 * objects extracted from the text inside curly braces.
 */

import java.util.ArrayList;
import java.util.List;

public class Head {
    private final String rawHeadText; 
    private List<WordSpec> wordSpecs = new ArrayList<>();
    private WordSpec firstWord;

    public Head(String rawHeadText) {
        this.rawHeadText = rawHeadText;
    }
    
    public void setWordSpecs(List<WordSpec> wordSpecs) {
        this.wordSpecs = wordSpecs;
    }

    public void setFirstWord(WordSpec word) {
        this.firstWord = word;
    }

    public WordSpec getFirstWord() {
        return firstWord;
    }
    
    public List<WordSpec> getWordSpecs() {
        return new ArrayList<>(wordSpecs);
    }

    @Override
    public String toString() {
        return "words: " + wordSpecs;
    }
}
