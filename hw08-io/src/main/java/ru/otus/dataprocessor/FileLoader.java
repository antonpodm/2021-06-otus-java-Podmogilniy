package ru.otus.dataprocessor;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import ru.otus.model.Measurement;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.List;

public class FileLoader implements Loader {

    private final String fileName;

    public FileLoader(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<Measurement> load() {
        try {
            var resource = getClass().getClassLoader().getResource(fileName);
            if (resource == null) {
                throw new FileProcessException("file not found");
            }
            var gsonFileAsString = Files.readString(new File(resource.toURI()).toPath());
            return new Gson().fromJson(gsonFileAsString, new TypeToken<List<Measurement>>() {}.getType());
        } catch (IOException| URISyntaxException ex) {
            throw new MyIOException(ex.getMessage());
        }
    }
}
