package de.javamaps.items;

public class ComboItem {

    private long nodeId;
    private String text;

    public ComboItem(long id, String text) {
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