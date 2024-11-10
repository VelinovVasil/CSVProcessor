package com.example.csvprocessor.controller;

import com.example.csvprocessor.dto.GroupedCostResult;
import com.example.csvprocessor.model.CsvRecord;
import com.example.csvprocessor.service.CsvProcessingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/csv")
public class CsvProcessingController {

    private final CsvProcessingService csvProcessingService;


    public CsvProcessingController(CsvProcessingService csvProcessingService) {
        this.csvProcessingService = csvProcessingService;
    }

    @GetMapping("/total-cost")
    public ResponseEntity<BigDecimal> getTotalCost(
            @RequestParam Optional<LocalDateTime> startTime,
            @RequestParam Optional<LocalDateTime> endTime,
            @RequestParam Optional<String> location,
            @RequestParam Optional<String> skuId) {

        BigDecimal totalCost = csvProcessingService.calculateTotalCost(startTime, endTime, location, skuId);
        return ResponseEntity.ok(totalCost);
    }

    @GetMapping("/grouped-cost")
    public ResponseEntity<List<GroupedCostResult>> getGroupedCost(
            @RequestParam boolean date,
            @RequestParam boolean country,
            @RequestParam boolean service) {

        List<GroupedCostResult> groupedCost = csvProcessingService.calculateGroupedCost(date, country, service);
        return ResponseEntity.ok(groupedCost);
    }

    @GetMapping("/search")
    public ResponseEntity<List<CsvRecord>> searchByLabelAndCountry(
            @RequestParam Optional<String> labelKeyValue,
            @RequestParam Optional<String> country,
            @RequestParam int pageSize,
            @RequestParam int pageNumber) {

        List<CsvRecord> results = csvProcessingService.searchByLabelAndCountry(labelKeyValue, country, pageSize, pageNumber);
        return ResponseEntity.ok(results);
    }
}
