package de.javamaps.parser.json;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.javamaps.ApplicationConstants;
import de.javamaps.GlobalApplicationStorage;
import de.javamaps.exceptions.DataParsingException;
import de.javamaps.items.Neighbor;
import de.javamaps.items.Vertex;
import de.javamaps.parser.json.mapping.dto.OSMElement;
import de.javamaps.parser.json.mapping.dto.OSMJsonMappingDTO;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.net.URL;
import java.util.*;

public class JsonParser {

    /**
     * @return map containing all vertexes with their id as key
     * @throws DataParsingException if reading the data fails
     */
    public Map<String, Vertex> getAllMapVertexes() throws DataParsingException {
        Map<String, Vertex> vertexIndex;

        if (validPreprocessedDataExists()) {
            vertexIndex = getExistingData();
        } else {
            vertexIndex = parseVertexesFromFile();
        }
        linkNeighborVertexes(vertexIndex);
        return vertexIndex;
    }

    /**
     * Links the vertex objects in the neighbors lists instead of just having their id
     *
     * @param vertexIndex map containing all vertexes with their id as key
     */
    private void linkNeighborVertexes(Map<String, Vertex> vertexIndex) {
        Collection<Vertex> allVertexes = vertexIndex.values();

        for (Vertex vertex : allVertexes) {
            for (Neighbor neighbor : vertex.getNeighbors()) {
                String neighborId = neighbor.getID();
                Vertex neighborVertex = vertexIndex.get(neighborId);
                neighbor.setVertex(neighborVertex);
            }
        }
    }

    private Map<String, Vertex> getExistingData() throws DataParsingException {
        try {
            InputStream existingDataStream = new FileInputStream(ApplicationConstants.Parser.PREPROCESSED_VERTEXES_PATH);
            Object existingData = new ObjectInputStream(existingDataStream).readObject();
            if (!(existingData instanceof List<?>)) {
                throw new DataParsingException("Wrong format for existing data");
            }
            Map<String, Vertex> vertexIndex = new HashMap<>();
            for (Object vertexObject : (List<?>) existingData) {
                if (vertexObject instanceof Vertex vertex) {
                    vertexIndex.put(vertex.getId(), vertex);
                }
            }
            return vertexIndex;
        } catch (IOException | ClassNotFoundException e) {
            throw new DataParsingException(e);
        }
    }

    private Map<String, Vertex> parseVertexesFromFile() throws DataParsingException {
        File dataFile = getFileFromResourcePath(ApplicationConstants.Parser.JSON_FILE_PATH);
        OSMJsonMappingDTO jsonMapping = convertJsonFileToJavaObject(dataFile);

        Map<String, Vertex> vertexIndex = readVertexesFromMapping(jsonMapping);

        if(!ApplicationConstants.Parser.FORCE_PARSING){
            storeParsedVertexesAsPreprocessedData(vertexIndex.values());
        }

        return vertexIndex;
    }

    private void storeParsedVertexesAsPreprocessedData(Collection<Vertex> vertexes) throws DataParsingException {
        try {
            //convert collection to a serializable collection implementation
            vertexes = new ArrayList<>(vertexes);
            FileOutputStream fileOut = new FileOutputStream(ApplicationConstants.Parser.PREPROCESSED_VERTEXES_PATH);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(vertexes);
            objectOut.close();

        } catch (IOException ex) {
            //TODO real logging
            ex.printStackTrace();
        }
    }

    private Map<String, Vertex> readVertexesFromMapping(OSMJsonMappingDTO jsonMapping) {
        Map<String, Vertex> vertexIndex = new HashMap<>();

        jsonMapping.getElements().stream()
                .filter(element -> StringUtils.equals(ApplicationConstants.Parser.OSM.ELEMENT_TYPE_NODE, element.getType()))
                .forEach(element -> vertexIndex.put(element.getId(), createVertexForElement(element)));

        jsonMapping.getElements().stream()
                .filter(element -> StringUtils.equals(ApplicationConstants.Parser.OSM.ELEMENT_TYPE_WAY, element.getType()))
                .forEach(element -> writeNeighborData(element, vertexIndex));

        return vertexIndex;
    }

    private void writeNeighborData(OSMElement element, Map<String, Vertex> vertexIndex) {
        Vertex lastVertex = null;
        for(String currentNodeId: element.getNodes()){
            Vertex currentVertex = vertexIndex.get(currentNodeId);
            if(lastVertex != null){
                lastVertex.addNeighbor(currentNodeId);
                currentVertex.addNeighbor(lastVertex.getId());
            }
            lastVertex = currentVertex;
        }
    }

    private Vertex createVertexForElement(OSMElement element) {
        String name;
        if(element.getAdditionalAttributes() != null){
            name = element.getAdditionalAttributes().get(ApplicationConstants.Parser.OSM.AdditionalAttributes.NAME);
        }
        else{
            name = null;
        }
        return new Vertex(name, element.getId(), element.getLatitude(), element.getLongitude());
    }

    private OSMJsonMappingDTO convertJsonFileToJavaObject(File dataFile) throws DataParsingException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper = objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return objectMapper.readValue(dataFile, OSMJsonMappingDTO.class);
        } catch (IOException e) {
            throw new DataParsingException(e);
        }
    }

    /**
     * @return true, if the use of preprocessed data is enabled and a preprocessed data exists, that is newer than the raw data
     */
    private boolean validPreprocessedDataExists() throws DataParsingException {
        //Check if using preprocessed data is disabled
        if (ApplicationConstants.Parser.FORCE_PARSING) {
            return false;
        }

        File preprocessedData = new File(ApplicationConstants.Parser.PREPROCESSED_VERTEXES_PATH);
        if (!preprocessedData.exists()) {
            return false;
        }

        File rawData = getFileFromResourcePath(ApplicationConstants.Parser.JSON_FILE_PATH);
        return FileUtils.isFileNewer(preprocessedData, rawData);
    }

    private File getFileFromResourcePath(String filePath) throws DataParsingException {
        URL fileURL = this.getClass().getResource(filePath);
        if (fileURL == null) {
            throw new DataParsingException("Failed to get Resource: " + filePath);
        }
        return new File(fileURL.getPath());
    }
}
