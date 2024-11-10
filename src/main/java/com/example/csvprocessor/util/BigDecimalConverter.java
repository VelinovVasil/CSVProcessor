package com.example.csvprocessor.util;

import com.opencsv.bean.AbstractBeanField;
import java.math.BigDecimal;

public class BigDecimalConverter extends AbstractBeanField<BigDecimal, String> {
    @Override
    protected BigDecimal convert(String value) {
        try {
            return new BigDecimal(value);
        } catch (NumberFormatException e) {
            return BigDecimal.ZERO;
        }
    }
}

