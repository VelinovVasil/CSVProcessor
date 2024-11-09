package com.example.csvprocessor.util;

import com.example.csvprocessor.model.CsvRecord;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

@Service
public class CsvReader {

//    @Value("${csv.file.path}")
    private String filePath = "src/main/resources/static/costs_export.csv";

    public Stream<CsvRecord> readCsv() {
        try {
            FileReader reader = new FileReader(filePath);
            CsvToBean<CsvRecord> csvToBean = new CsvToBeanBuilder<CsvRecord>(reader)
                    .withType(CsvRecord.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            return csvToBean.stream().onClose(() -> {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
            return Stream.empty();
        }
    }
}