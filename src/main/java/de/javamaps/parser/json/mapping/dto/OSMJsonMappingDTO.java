package de.javamaps.parser.json.mapping.dto;

import java.util.Collection;

public class OSMJsonMappingDTO {
    private Collection<OSMElement> elements;

    public Collection<OSMElement> getElements() {
        return elements;
    }

    public void setElements(Collection<OSMElement> elements) {
        this.elements = elements;
    }
}
