package com.example.csvprocessor.service.impl;

import com.example.csvprocessor.dto.GroupedCostResult;
import com.example.csvprocessor.model.CsvRecord;
import com.example.csvprocessor.service.CsvProcessingService;
import com.example.csvprocessor.util.CsvReader;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.example.csvprocessor.util.CsvRecordValidator.*;

@Service
public class CsvProcessingServiceImpl implements CsvProcessingService {

    private final CsvReader csvReader;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public CsvProcessingServiceImpl(CsvReader csvReader) {
        this.csvReader = csvReader;
    }

    @Override
    public BigDecimal calculateTotalCost(Optional<LocalDateTime> startTime, Optional<LocalDateTime> endTime, Optional<String> location, Optional<String> skuId) {
        return this.csvReader.getCachedRecords().parallelStream()
                .filter(r -> startTime.map(st -> isValidUsageStartTime(r) && r.getUsageStartTime().isAfter(st)).orElse(true))
                .filter(r -> endTime.map(et -> isValidUsageStartTime(r) && r.getUsageStartTime().isBefore(et)).orElse(true))
                .filter(r -> location.map(loc -> loc != null && loc.equals(r.getLocationCountry())).orElse(true))
                .filter(r -> skuId.map(id -> id != null && id.equals(r.getSkuId())).orElse(true))
                .map(r -> r.getCost())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public List<GroupedCostResult> calculateGroupedCost(boolean date, boolean country, boolean service) {
        Stream<CsvRecord> records = csvReader.getCachedRecords().parallelStream();

        Map<Map<String, Object>, BigDecimal> groupedCosts = records
                .collect(Collectors.groupingBy(record -> {
                    isValidUsageStartTime(record);
                    isValidLocationCountry(record);
                    isValidServiceId(record);

                    Map<String, Object> groupKey = new HashMap<>();
                    if (date) groupKey.put("date", record.getUsageStartTime().toLocalDate());
                    if (country) groupKey.put("country", record.getLocationCountry());
                    if (service) groupKey.put("service", record.getServiceId());
                    return groupKey;
                }, Collectors.reducing(BigDecimal.ZERO, CsvRecord::getCost, BigDecimal::add)));

        return groupedCosts.entrySet().stream()
                .map(entry -> {
                    GroupedCostResult result = new GroupedCostResult();
                    result.setCriteria(entry.getKey());
                    result.setCost(entry.getValue());
                    return result;
                })
                .collect(Collectors.toList());
    }


    @Override
    public List<CsvRecord> searchByLabelAndCountry(Optional<String> labelKeyValue,
                                                   Optional<String> country,
                                                   int pageNumber,
                                                   int pageSize) {

        Stream<CsvRecord> records = this.csvReader.getCachedRecords().parallelStream();

        if (country.isPresent()) {
            records = records.filter(r -> isValidLocationCountry(r) && country.get().equals(r.getLocationCountry()));
        }

        if (labelKeyValue.isPresent()) {
            String[] keyValue = labelKeyValue.get().split(":");

            if (keyValue.length == 2) {
                String key = keyValue[0];
                String value = keyValue[1];

                records = records.filter(r -> {
                    try {
                        areValidLabels(r);
                        String labelsJson = r.getLabels();

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
