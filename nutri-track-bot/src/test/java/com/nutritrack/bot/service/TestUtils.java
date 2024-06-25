package com.nutritrack.bot.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.*;

public class TestUtils {

    public static InputStream replacePlaceholderInJsonFile(String filePath, String replacement) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        // Load the JSON file
        File jsonFile = new File(filePath);
        JsonNode rootNode = objectMapper.readTree(jsonFile);

        // Modify the 'body' element
        String bodyContent = rootNode.get("body").asText();
        bodyContent = bodyContent.replace("%s", replacement);
        ((ObjectNode) rootNode).put("body", bodyContent);

        // Convert the modified JSON to an InputStream
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(baos, rootNode);
        byte[] jsonBytes = baos.toByteArray();

        return new ByteArrayInputStream(jsonBytes);
    }

}
