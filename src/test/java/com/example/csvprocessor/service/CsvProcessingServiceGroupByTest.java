package com.example.csvprocessor.service;

import com.example.csvprocessor.dto.GroupedCostResult;
import com.example.csvprocessor.model.CsvRecord;
import com.example.csvprocessor.service.impl.CsvProcessingServiceImpl;
import com.example.csvprocessor.util.CsvReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class CsvProcessingServiceGroupByTest {

    @Mock
    private CsvReader mockCsvReader;

    @InjectMocks
    private CsvProcessingServiceImpl csvProcessingService;

    private CsvRecord record1;
    private CsvRecord record2;
    private CsvRecord record3;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        record1 = new CsvRecord();
        record1.setCost(new BigDecimal("10.00"));
        record1.setUsageStartTime(LocalDateTime.of(2024, 1, 1, 0, 0));
        record1.setLocationCountry("US");
        record1.setServiceId("logging");

        record2 = new CsvRecord();
        record2.setCost(new BigDecimal("20.00"));
        record2.setUsageStartTime(LocalDateTime.of(2024, 1, 1, 0, 0));
        record2.setLocationCountry("US");
        record2.setServiceId("compute");

        record3 = new CsvRecord();
        record3.setCost(new BigDecimal("15.00"));
        record3.setUsageStartTime(LocalDateTime.of(2024, 1, 2, 0, 0));
        record3.setLocationCountry("BG");
        record3.setServiceId("compute");

        when(mockCsvReader.getCachedRecords()).thenReturn(List.of(record1, record2, record3));
    }

    @Test
    public void testCalculateGroupedCost_ByCountryOnly() {
        List<GroupedCostResult> results = csvProcessingService.calculateGroupedCost(false, true, false);

        assertEquals(2, results.size());
        assertTrue(results.stream().anyMatch(result -> result.getCriteria().equals(Map.of("country", "US"))));
        assertTrue(results.stream().anyMatch(result -> result.getCriteria().equals(Map.of("country", "BG"))));
    }

    @Test
    public void testCalculateGroupedCost_NoGrouping() {
        List<GroupedCostResult> results = csvProcessingService.calculateGroupedCost(false, false, false);

        assertEquals(1, results.size());
        assertTrue(results.stream().anyMatch(result -> result.getCriteria().equals(Map.of())));
    }

    @Test
    public void testCalculateGroupedCost_ByDateCountryService() {
        List<GroupedCostResult> results = csvProcessingService.calculateGroupedCost(true, true, true);

        assertEquals(3, results.size());
    }

    @Test
    public void testCalculateGroupedCost_ByDateOnly() {
        List<GroupedCostResult> results = csvProcessingService.calculateGroupedCost(true, false, false);

        assertEquals(2, results.size());
    }
}
