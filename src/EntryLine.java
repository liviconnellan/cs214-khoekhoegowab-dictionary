/**
 * CS214 Project 2026
 * Electronic Khoekhoegowab Dictionary
 *
 * File: EntryBlock.java
 *
 * Student Number: 29004691
 * Name: Olivia Connellan
 * *
 * Description:
 * Represents a single block within an entry, consisting of a Head (word
 * section) and a Tail (definition and metadata section).
 */
public class EntryBlock {
    private Head head;  
    private Tail tail;   
    private final String rawHeadText;
    private final String rawTailText;

    /**
     * Constructs an EntryBlock using the raw head and tail text.
     *
     * @param rawHeadText the original text inside the curly braces representing the head section
     * @param rawTailText the original text following the head, containing definitions and metadata
     */
    public EntryBlock(String rawHeadText, String rawTailText) {
        this.rawHeadText = rawHeadText;
        this.rawTailText = rawTailText;
    }

    public void setHead(Head head) {
        this.head = head;
    }

    public void setTail(Tail tail) {
        this.tail = tail;
    }

    public Head getHead() {
        return head;
    }

    public Tail getTail() {
        return tail;
    }

    public String getRawHead() {
        return rawHeadText;
    }

    public String getRawTail() {
        return rawTailText;
    }

    @Override
    public String toString() {
        return "Head: " + rawHeadText + " Tail: " + rawTailText;

    }

}
