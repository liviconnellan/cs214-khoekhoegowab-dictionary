/**
 * CS214 Project 2026
 * Electronic Khoekhoegowab Dictionary
 *
 * File: EntryLine.java
 *
 * Student Number: 29004691
 * Name: Olivia Connellan
 *
 * Description:
 * Represents a full input line from the dictionary. Stores multiple EntryBlock
 * objects and keeps track of the first word specification for tilde expansion.
 */

import java.util.ArrayList;
import java.util.List;

public class EntryLine {
    private final String rawLine;
    private List<EntryBlock> entryBlocks = new ArrayList<>();
    private WordSpec firstWordSpec;
    private List<String> firstStemOptions = new ArrayList<>();
    private String firstStemLongest;

    /**
     * Constructs an EntryLine using the raw input line and its parsed blocks.
     *
     * @param rawLine the original input line from the dictionary
     * @param blocks the parsed entry blocks found in the line
     */
    public EntryLine(String rawLine, List<EntryBlock> blocks) {
        this.rawLine = rawLine;
        this.entryBlocks = new ArrayList<>(blocks);
    }

    public String getRawLine() {
        return rawLine;
    }

    public List<EntryBlock> getEntryBlocks() {
        return new ArrayList<>(entryBlocks);
    }

    public WordSpec getFirstWordSpec() {
        return firstWordSpec;
    }

    public List<String> getFirstStemOptions() {
        return firstStemOptions;
    }

    public void setFirstWordSpec(WordSpec firstWordSpec) {
        this.firstWordSpec = firstWordSpec;
    }

    public void setFirstStemOptions(List<String> firstStemOptions) {
        this.firstStemOptions = new ArrayList<>(firstStemOptions);
    }

    public String getFirstStemLongest() {
        return firstStemLongest;
    }

    public void setFirstStemLongest(String firstStemLongest) {
        this.firstStemLongest = firstStemLongest;
    }
}
