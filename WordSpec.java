/**
 * CS214 Project 2026
 * Electronic Khoekhoegowab Dictionary
 *
 * File: WordFinal.java
 *
 * Student Number: 29004691
 * Name: Olivia Connellan
 *
 * Description:
 * Represents the final dictionary entry used for output. Contains the word,
 * dialects, parts of speech, origins, and definition in the required format.
 */

import java.util.ArrayList;
import java.util.List;

public class WordFinal {
    private String word;
    private List<String> dialects = new ArrayList<>();
    private List<String> partsOfSpeech = new ArrayList<>();
    private List<String> origins = new ArrayList<>();
    private String definition;

    public WordFinal() {
        this.word = "";
        this.definition = "";

    }
    public WordFinal(String word, 
        List<String> dialects, 
        List<String> partsOfSpeech, 
        List<String> origins, 
        String definition) {

        if (word == null) {
            this.word = "";
        } else {
            this.word = word;
        }
        
        if (definition == null) {
            this.definition = "";
        } else {
            this.definition = definition;
        }
        this.dialects = new ArrayList<>(dialects);
        this.partsOfSpeech = new ArrayList<>(partsOfSpeech);
        this.origins = new ArrayList<>(origins);
    }

    private String listFormat(List<String> list) {
        String out = "";
        if (list.size() == 0) {
            return out;
        }
        out += list.get(0);
        
        if (list.size() > 1) {
            for (int i = 1; i < list.size(); i++) {
                out += ";" + list.get(i);
            }
        }
        return out;
    }

    public String getWord() {
        return word;
    }

    public String getDefinition() {
        return definition;
    }

    public List<String> getDialects() {
        return dialects;
    }

    public List<String> getOrigins() {
        return origins;
    }

    public List<String> getPartsOfSpeech() {
        return partsOfSpeech;
    }

    /**
     * Returns the final output in the exact required format:
     * <word>#<dialects>#<pos>#<origins>#<definition>
     */
    @Override
    public String toString() {
        String out = word + "#" 
        + listFormat(dialects) + "#" 
        + listFormat(partsOfSpeech) + "#" 
        + listFormat(origins) + "#" 
        + definition;

        return out;
    }
}
