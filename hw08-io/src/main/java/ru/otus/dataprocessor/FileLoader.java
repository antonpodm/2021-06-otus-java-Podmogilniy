package ru.otus.dataprocessor;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import ru.otus.model.Measurement;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileLoader implements Loader {

    private final String fileName;

    public FileLoader(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<Measurement> load() {
        try {
            var url = getClass().getClassLoader().getResource(fileName);
            var path = url.getPath();
            try (var bis = new BufferedInputStream(new FileInputStream(path))) {
                var root = new ObjectMapper().readTree(bis);
                var result = new ArrayList<Measurement>();
                if (root.isArray()) {
                    var array = (ArrayNode) root;
                    array.forEach(node -> {
                        var measurement = makeMeasurement(node);
                        if (measurement != null) {
                            result.add(measurement);
                        }
                    });
                } else if (root.isObject()) {
                    var measurement = makeMeasurement(root);
                    if (measurement != null) {
                        result.add(measurement);
                    }
                }
                return result;
            }
            //читает файл, парсит и возвращает результат
        } catch (FileNotFoundException ex) {
            throw new MyIOException("Файл не найден");
        } catch (IOException ex) {
            throw new MyIOException(ex.getMessage());
        }
    }

    private Measurement makeMeasurement(JsonNode node) {
        if (node.has("name") && node.has("value")) {
            var name = node.get("name").asText();
            var value = node.get("value").asDouble();
            return new Measurement(name, value);
        }
        return null;
    }
}
