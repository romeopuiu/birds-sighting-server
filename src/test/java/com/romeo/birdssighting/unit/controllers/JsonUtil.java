package com.romeo.birdssighting.unit.controllers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonUtil {

    /**
     * Method to convert an object to its JSON representation as a byte array
     */
    static byte[] toJson(Object object) throws IOException {
        // Create an instance of ObjectMapper to perform the conversion
        ObjectMapper mapper = new ObjectMapper();
        // Configure the mapper to exclude properties with null values from serialization
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        // Convert the object to its JSON representation and return the byte array
        return mapper.writeValueAsBytes(object);
    }
}
