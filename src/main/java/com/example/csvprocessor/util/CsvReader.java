package com.example.csvprocessor.util;

import com.example.csvprocessor.model.CsvRecord;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class CsvReader {

    private List<CsvRecord> cachedRecords = new ArrayList<>();

    // @Value("${csv.file.path}")
    private String filePath = "src/main/resources/static/costs_export.csv";

    @PostConstruct
    private void init() {
        loadCsvData();
    }

    private void loadCsvData() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            CsvToBean<CsvRecord> csvToBean = new CsvToBeanBuilder<CsvRecord>(bufferedReader)
                    .withType(CsvRecord.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            cachedRecords = csvToBean.parse();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<CsvRecord> getCachedRecords() {
        return Collections.unmodifiableList(cachedRecords);
    }
}