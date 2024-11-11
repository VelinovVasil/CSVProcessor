package com.example.csvprocessor.service;

import com.example.csvprocessor.dto.GroupedCostResult;
import com.example.csvprocessor.model.CsvRecord;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CsvProcessingService {

    BigDecimal calculateTotalCost(Optional<LocalDateTime> startTime,
                                  Optional<LocalDateTime> endTime,
                                  Optional<String> location,
                                  Optional<String> skuId);

    List<GroupedCostResult> calculateGroupedCost(boolean date,
                                                 boolean country,
                                                 boolean service);

    List<CsvRecord> searchByLabelAndCountry(Optional<String> labelKeyValue,
                                            Optional<String> country,
                                            int pageSize,
                                            int pageNumber);
}
