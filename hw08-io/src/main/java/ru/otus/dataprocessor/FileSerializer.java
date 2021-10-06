package ru.otus.dataprocessor;

import com.fasterxml.jackson.databind.json.JsonMapper;

import java.io.*;
import java.util.Map;

public class FileSerializer implements Serializer {

    private final String fileName;

    public FileSerializer(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void serialize(Map<String, Double> data) {
        //формирует результирующий json и сохраняет его в файл
        try {
            var result = new JsonMapper().writeValueAsString(data);
            try (var bw = new BufferedWriter(new FileWriter(fileName))) {
                bw.write(result);
            }
        } catch (Exception ex) {
            throw new MyIOException(ex.getMessage());
        }
    }
}
