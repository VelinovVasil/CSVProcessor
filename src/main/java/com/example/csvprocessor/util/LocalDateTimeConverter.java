package com.example.csvprocessor.util;

import com.opencsv.bean.AbstractBeanField;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeConverter extends AbstractBeanField<LocalDateTime, String> {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    @Override
    protected LocalDateTime convert(String value) {
        try {
            return LocalDateTime.parse(value, formatter);
        } catch (Exception e) {
            return null;
        }
    }
}

