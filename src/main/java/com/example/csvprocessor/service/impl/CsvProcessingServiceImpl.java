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
}
