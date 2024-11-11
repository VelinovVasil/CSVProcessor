package com.example.csvprocessor.service;

import com.example.csvprocessor.model.CsvRecord;
import com.example.csvprocessor.service.impl.CsvProcessingServiceImpl;
import com.example.csvprocessor.util.CsvReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CsvProcessingServiceTotalCostTest {

    @Mock
    private CsvReader mockCsvReader;

    @InjectMocks
    private CsvProcessingServiceImpl service;

    private CsvRecord record1;
    private CsvRecord record2;
    private Stream<CsvRecord> csvData;

    @BeforeEach
    public void setUp() {
        record1 = new CsvRecord();
        record1.setId("1");
        record1.setBillingAccountId("1");
        record1.setCost(new BigDecimal("10.00"));
        record1.setCurrency("USD");
        record1.setSkuId("skuid1");
        record1.setUsageStartTime(LocalDateTime.of(2024, 4, 23, 2, 31));
        record1.setLocationCountry("US");

        record2 = new CsvRecord();
        record2.setId("2");
        record2.setBillingAccountId("2");
        record2.setCost(new BigDecimal("20.00"));
        record2.setCurrency("USD");
        record2.setSkuId("skuid2");
        record2.setUsageStartTime(LocalDateTime.of(2024, 4, 23, 3, 0));
        record2.setLocationCountry("FI");

        List<CsvRecord> csvData = List.of(record1, record2);
        when(mockCsvReader.getCachedRecords()).thenReturn(csvData);
    }

    @Test
    public void testCalculateTotalCostNoFilters() {
        BigDecimal totalCost = service.calculateTotalCost(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());
        assertEquals(new BigDecimal("30.00"), totalCost);
    }

    @Test
    public void testCalculateTotalCostWithLocationFilter() {
        Optional<String> location = Optional.of("US");
        BigDecimal totalCost = service.calculateTotalCost(Optional.empty(), Optional.empty(), location, Optional.empty());
        assertEquals(new BigDecimal("10.00"), totalCost);
    }

    @Test
    public void testCalculateTotalCostWithDateRangeFilter() {
        Optional<LocalDateTime> startTime = Optional.of(LocalDateTime.of(2024, 4, 23, 2, 0));
        Optional<LocalDateTime> endTime = Optional.of(LocalDateTime.of(2024, 4, 23, 3, 0));
        BigDecimal totalCost = service.calculateTotalCost(startTime, endTime, Optional.empty(), Optional.empty());
        assertEquals(new BigDecimal("10.00"), totalCost);
    }

    @Test
    public void testCalculateTotalCostWithSkuIdFilter() {
        Optional<String> skuId = Optional.of("skuid1");
        BigDecimal totalCost = service.calculateTotalCost(Optional.empty(), Optional.empty(), Optional.empty(), skuId);
        assertEquals(new BigDecimal("10.00"), totalCost);
    }

    @Test
    public void testCalculateTotalCostWithNonMatchingFilters() {
        Optional<String> location = Optional.of("CA");
        Optional<String> skuId = Optional.of("skuid3");
        BigDecimal totalCost = service.calculateTotalCost(Optional.empty(), Optional.empty(), location, skuId);
        assertEquals(BigDecimal.ZERO, totalCost);
    }
}