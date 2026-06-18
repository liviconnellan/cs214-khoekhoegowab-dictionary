/**
 * CS214 Project 2026
 * Electronic Khoekhoegowab Dictionary
 *
 * File: DefinitionChunk.java
 *
 * Student Number: 29004691
 * Name: Olivia Connellan
 *
 * Description:
 * Represents a single definition within an entry. Stores the definition text,
 * associated dialects, and flags for handling tilde expansion.
 */

import java.util.ArrayList;
import java.util.List;

public class DefinitionChunk {                   
    private String definition;  
    private boolean hasSingleTilde;   
    private boolean hasDoubleTilde;             
    private List<String> dialects = new ArrayList<>();

    public DefinitionChunk() {
        
    }

    public DefinitionChunk(String definition, List<String> dialects) {
        this.definition = definition;
        this.dialects = new ArrayList<>(dialects);
    }

    public void setDefinition(String definition) {
        if (definition == null) {
            this.definition = "";
        } else {
            this.definition = definition;
        }
    }

    public void setHasSingleTilde(boolean hasSingleTilde) {
        this.hasSingleTilde =  hasSingleTilde;
    }
    
    public void setHasDoubleTilde(boolean hasDoubleTilde) {
        this.hasDoubleTilde =  hasDoubleTilde;
    }

     public boolean getHasSingleTilde() {
        return hasSingleTilde;
    }

    public boolean getHasDoubleTilde() {
        return hasDoubleTilde;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDialects(List<String> dialects) {
        this.dialects =  new ArrayList<>(dialects);
    }

    public List<String> getDialects() {
        return new ArrayList<>(dialects);
    }

    @Override
    public String toString() {
        return definition;
    }
    
}
