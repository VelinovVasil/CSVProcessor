package com.example.csvprocessor.service.impl;

import com.example.csvprocessor.model.CsvRecord;
import com.example.csvprocessor.service.CsvProcessingService;
import com.example.csvprocessor.util.CsvReader;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CsvProcessingServiceImpl implements CsvProcessingService {

    private final CsvReader csvReader;

    public CsvProcessingServiceImpl(CsvReader csvReader) {
        this.csvReader = csvReader;
    }


    @Override
    public List<Map<String, Object>> calculateGroupedCost(List<String> groupByFields) {
        return null;
    }

    @Override
    public BigDecimal calculateTotalCost(Optional<LocalDateTime> startTime, Optional<LocalDateTime> endTime, Optional<String> location, Optional<String> skuId) {
        return this.csvReader.readCsv()
                .filter(r -> startTime.map(st -> r.getUsageStartTime().isAfter(st)).orElse(true))
                .filter(r -> endTime.map(et -> r.getUsageStartTime().isBefore(et)).orElse(true))
                .filter(r -> location.map(loc -> loc.equals(r.getLocationCountry())).orElse(true))
                .filter(r -> skuId.map(id -> id.equals(r.getSkuId())).orElse(true))
                .map(r -> r.getCost())
                .reduce(BigDecimal.ZERO, (total, cost) -> total.add(cost));
    }

    @Override
    public List<CsvRecord> searchByLabelAndCountry(String labelKey, String labelValue, Optional<String> country, int pageSize, int pageNumber) {
        return null;
    }



    @Override
    public List<CsvRecord> searchByLabelAndCountry(Optional<String> labelKeyValue,
                                                   Optional<String> country,
                                                   int pageNumber,
                                                   int pageSize) {


        Stream<CsvRecord> records = this.csvReader.readCsv();


        if (country.isPresent()) {
            records = records.filter(r -> country.get().equals(r.getLocationCountry()));
        }


        if (labelKeyValue.isPresent()) {
            String[] keyValue = labelKeyValue.get().split(":");

            if (keyValue.length == 2) {
                String key = keyValue[0];
                String value = keyValue[1];

                records = records.filter(r -> {
                    try {
                        String labelsJson = r.getLabels();

                        // ToDo: figure out why the injected ObjectMapper dependency does not work
                        ObjectMapper objectMapper = new ObjectMapper();
                        JsonNode labelsNode = objectMapper.readTree(labelsJson);

                        JsonNode teamNode = labelsNode.get(key);
                        if (teamNode != null && teamNode.isArray()) {
                            for (JsonNode valueNode : teamNode) {
                                if (valueNode.asText().equals(value)) {
                                    return true;
                                }
                            }
                        }

                        return false;
                    } catch (JsonProcessingException e) {
                        System.err.println("Error deserializing labels: " + e.getMessage());
                        return false;
                    }
                });
            }
        }

        int skip = (pageNumber - 1) * pageSize;

        List<CsvRecord> paginatedResults = records
                .skip(skip)
                .limit(pageSize)
                .collect(Collectors.toList());

        return paginatedResults;
    }


}
