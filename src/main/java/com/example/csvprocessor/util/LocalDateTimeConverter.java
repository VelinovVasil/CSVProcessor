package com.example.csvprocessor.util;

import com.opencsv.bean.AbstractBeanField;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeConverter extends AbstractBeanField<LocalDateTime, String> {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    protected LocalDateTime convert(String value) {
        try {

            if (value != null && value.endsWith(" UTC")) {
                value = value.substring(0, value.length() - 4);
            }

            return LocalDateTime.parse(value, formatter);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}



