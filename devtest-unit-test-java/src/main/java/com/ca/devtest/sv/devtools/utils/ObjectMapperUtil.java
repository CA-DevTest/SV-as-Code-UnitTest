package com.ca.devtest.sv.devtools.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;

public class ObjectMapperUtil {

    /***
     * Convert given string into object of give class type
     *
     * @param json - json string to convert
     * @param tClass - target object class type
     * @return - returns object of given class type
     * @param <T>
     * @throws Exception
     */
    public static <T>T json2Object(String json, Class<T> tClass) throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, tClass);
    }

    /***
     * Converts given object into json string
     *
     * @param object - object which should be converted to json string
     * @return - returns json representation of given object
     * @param <T>
     * @throws Exception
     */
    public static <T> String objectToJSON(T object) throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }

    /***
     * Writes java object to given file
     *
     * @param file - Name of the file where content should be written
     * @param object - object which should be written to the file
     * @param <T>
     * @throws Exception
     */
    public static<T> void objectToFile( File file, T object) throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(file, object);
    }

    /***
     * Reads a file into object
     *
     * @param file - Name of the file from which content should be read
     * @param tClass - target object class type
     * @return 
     * @param <T>
     * @throws Exception
     */
    public static<T> T fileToObject(File file, Class<T> tClass) throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(file, tClass);
    }
}
