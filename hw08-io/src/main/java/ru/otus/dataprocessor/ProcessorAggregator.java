package ru.otus.dataprocessor;

import ru.otus.model.Measurement;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ProcessorAggregator implements Processor {

    @Override
    public Map<String, Double> process(List<Measurement> data) {
        //группирует выходящий список по name, при этом суммирует поля value
        var result = new TreeMap<String, Double>();

        data.forEach(measurement -> {
            result.computeIfPresent(measurement.getName(), (key, val) -> val + measurement.getValue());
            result.putIfAbsent(measurement.getName(), measurement.getValue());
        });

        return result;
    }
}
