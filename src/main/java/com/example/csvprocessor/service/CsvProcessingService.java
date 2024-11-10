package com.example.csvprocessor.service;

import com.example.csvprocessor.model.CsvRecord;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CsvProcessingService {

    List<Map<String, Object>> calculateGroupedCost(List<String> groupByFields);

    BigDecimal calculateTotalCost(Optional<LocalDateTime> startTime,
                                  Optional<LocalDateTime> endTime,
                                  Optional<String> location,
                                  Optional<String> skuId);

    List<CsvRecord> searchByLabelAndCountry(String labelKey,
                                            String labelValue,
                                            Optional<String> country,
                                            int pageSize,
                                            int pageNumber);
}
