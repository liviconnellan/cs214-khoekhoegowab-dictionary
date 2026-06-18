/**
 * CS214 Project 2026
 * Electronic Khoekhoegowab Dictionary
 *
 * File: Tail.java
 *
 * Student Number: 29004691
 * Name: Olivia Connellan
 *
 * Description:
 * Represents the tail section of an entry. Stores parts of speech, dialects,
 * origins, and definitions associated with the entry.
 */

import java.util.ArrayList;
import java.util.List;

public class Tail {

    private final String rawTailText;
    private List<String> dialects = new ArrayList<>();
    private List<String> partsOfSpeech = new ArrayList<>();
    private List<String> origins = new ArrayList<>();
    private List<DefinitionChunk> definitions = new ArrayList<>();

    public Tail(String rawTailText) {
        this.rawTailText = rawTailText;
    }

    public void setDefinitions(List<DefinitionChunk> definitions) {
        this.definitions = new ArrayList<>(definitions);
    }

    public List<DefinitionChunk> getDefinitions() {
        return new ArrayList<>(definitions);
    }

    public void setDialects(List<String> dialects) {
        this.dialects = new ArrayList<>(dialects);
    }

    public List<String> getDialects() {
        return new ArrayList<>(dialects);
    }

    public void setOrigins(List<String> origins) {
        this.origins = new ArrayList<>(origins);
    }

    public List<String> getOrigins() {
        return new ArrayList<>(origins);
    }

    public void setPartsOfSpeech(List<String> partsOfSpeech) {
        this.partsOfSpeech = new ArrayList<>(partsOfSpeech);

    }

    public List<String> getPartsOfSpeech() {
        return partsOfSpeech;
    }

    @Override
    public String toString() {
        return rawTailText;
    }
}
