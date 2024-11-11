package com.example.csvprocessor.service;

import com.example.csvprocessor.model.CsvRecord;
import com.example.csvprocessor.service.impl.CsvProcessingServiceImpl;
import com.example.csvprocessor.util.CsvReader;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CsvProcessingServiceSearchByLabelAndCountryTest {

    @Mock
    private CsvReader mockCsvReader;

    @Mock
    private ObjectMapper mockObjectMapper;

    @InjectMocks
    private CsvProcessingServiceImpl csvProcessingService;

    private CsvRecord record1;
    private CsvRecord record2;
    private CsvRecord record3;
    private List<CsvRecord> csvData;

    @BeforeEach
    public void setUp() {
        record1 = new CsvRecord();
        record1.setId("1");
        record1.setBillingAccountId("1");
        record1.setCost(new BigDecimal("10.00"));
        record1.setCurrency("USD");
        record1.setServiceId("logging");
        record1.setUsageStartTime(LocalDateTime.of(2024, 1, 1, 0, 0));
        record1.setLocationCountry("US");
        record1.setLabels("{\"team\": [\"zettahost\"]}");

        record2 = new CsvRecord();
        record2.setId("2");
        record2.setBillingAccountId("2");
        record2.setCost(new BigDecimal("20.00"));
        record2.setCurrency("USD");
        record2.setServiceId("logging");
        record2.setUsageStartTime(LocalDateTime.of(2024, 1, 1, 0, 0));
        record2.setLocationCountry("BG");
        record2.setLabels("{\"team\": [\"zetta\"]}");

        record3 = new CsvRecord();
        record3.setId("3");
        record3.setBillingAccountId("3");
        record3.setCost(new BigDecimal("15.00"));
        record3.setCurrency("USD");
        record3.setServiceId("compute");
        record3.setUsageStartTime(LocalDateTime.of(2024, 1, 2, 0, 0));
        record3.setLocationCountry("US");
        record3.setLabels("{\"team\": [\"zetta\"]}");

        csvData = List.of(record1, record2, record3);
        when(mockCsvReader.getCachedRecords()).thenReturn(csvData);
    }

    @Test
    public void testSearchByLabelAndCountryWithNoMatchingLabel() throws JsonProcessingException {
        Optional<String> labelKeyValue = Optional.of("team:nonexistent");
        Optional<String> country = Optional.of("US");
        int pageNumber = 1;
        int pageSize = 2;

        when(mockObjectMapper.readValue(anyString(), eq(Map.class))).thenReturn(Map.of("team", List.of("zetta")));

        List<CsvRecord> result = csvProcessingService.searchByLabelAndCountry(labelKeyValue, country, pageNumber, pageSize);
        assertEquals(0, result.size());
    }

    @Test
    public void testSearchByLabelAndCountryWithNoMatchingCountry() throws JsonProcessingException {
        Optional<String> labelKeyValue = Optional.of("team:zettahost");
        Optional<String> country = Optional.of("BG");
        int pageNumber = 1;
        int pageSize = 2;

        when(mockObjectMapper.readValue(anyString(), eq(Map.class))).thenReturn(Map.of("team", List.of("zettahost")));

        List<CsvRecord> result = csvProcessingService.searchByLabelAndCountry(labelKeyValue, country, pageNumber, pageSize);
        assertEquals(0, result.size());
    }

    @Test
    public void testSearchByLabelAndCountryWithPagination() throws JsonProcessingException {
        Optional<String> labelKeyValue = Optional.of("team:zetta");
        Optional<String> country = Optional.of("US");
        int pageNumber = 1;
        int pageSize = 1;

        when(mockObjectMapper.readValue(anyString(), eq(Map.class))).thenReturn(Map.of("team", List.of("zetta")));

        List<CsvRecord> result = csvProcessingService.searchByLabelAndCountry(labelKeyValue, country, pageNumber, pageSize);
        assertEquals(1, result.size());
        CsvRecord resultItem = result.get(0);
        assertEquals("US", resultItem.getLocationCountry());
        assertTrue(resultItem.getLabels().contains("team"));
        assertTrue(resultItem.getLabels().contains("zetta"));

        pageNumber = 2;
        result = csvProcessingService.searchByLabelAndCountry(labelKeyValue, country, pageNumber, pageSize);
        assertEquals(1, result.size());
        resultItem = result.get(0);
        assertEquals("US", resultItem.getLocationCountry());
        assertTrue(resultItem.getLabels().contains("team"));
        assertTrue(resultItem.getLabels().contains("zetta"));
    }

    @Test
    public void testSearchByLabelAndCountryWithNoResults() throws JsonProcessingException {
        Optional<String> labelKeyValue = Optional.of("team:nonexistent");
        Optional<String> country = Optional.of("CN");
        int pageNumber = 1;
        int pageSize = 2;

        when(mockObjectMapper.readValue(anyString(), eq(Map.class))).thenReturn(Map.of("team", List.of("zetta")));

        List<CsvRecord> result = csvProcessingService.searchByLabelAndCountry(labelKeyValue, country, pageNumber, pageSize);
        assertEquals(0, result.size());
    }
}
