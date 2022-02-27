package de.javamaps.parser.json.mapping.dto;

import com.fasterxml.jackson.annotation.JsonSetter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class OSMElement {
    private String type;
    private String id;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Map<String, String> additionalAttributes;
    private List<String> nodes;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    @JsonSetter("lat")
    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    @JsonSetter("lon")
    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public Map<String, String> getAdditionalAttributes() {
        return additionalAttributes;
    }

    @JsonSetter("tags")
    public void setAdditionalAttributes(Map<String, String> additionalAttributes) {
        this.additionalAttributes = additionalAttributes;
    }

    public List<String> getNodes() {
        return nodes;
    }

    public void setNodes(List<String> nodes) {
        this.nodes = nodes;
    }
}
