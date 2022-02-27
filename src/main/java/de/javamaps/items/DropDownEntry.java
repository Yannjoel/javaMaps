package de.javamaps.items;

public class DropDownEntry {

    private final String nodeId;
    private final String text;

    public DropDownEntry(String id, String text) {
        this.nodeId = id;
        this.text = text;
    }

    public String getId() {
        return nodeId;
    }

    public String getText() {
        return text;
    }   

    public String toString(){
        return this.text;
    }
}