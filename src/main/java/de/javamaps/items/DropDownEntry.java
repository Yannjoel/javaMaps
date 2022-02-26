package de.javamaps.items;

public class DropDownEntry {

    private final long nodeId;
    private final String text;

    public DropDownEntry(long id, String text) {
        this.nodeId = id;
        this.text = text;
    }

    public long getId() {
        return nodeId;
    }

    public String getText() {
        return text;
    }   

    public String toString(){
        return this.text;
    }
}