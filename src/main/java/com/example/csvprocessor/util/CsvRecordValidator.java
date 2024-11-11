package com.example.csvprocessor.util;

import com.example.csvprocessor.model.CsvRecord;
import com.example.csvprocessor.service.impl.CsvProcessingServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CsvRecordValidator {

    private static final Logger logger = LoggerFactory.getLogger(CsvRecordValidator.class);

    public static boolean isValidUsageStartTime(CsvRecord record) {
        if (record.getUsageStartTime() == null) {
            logger.warn("Record has a missing usageStartTime: " + record.toString());
            return false;
        }
        return true;
    }

    public static boolean isValidCost(CsvRecord record) {
        if (record.getCost() == null) {
            logger.warn("Record has a missing cost: " + record.toString());
            return false;
        }
        return true;
    }

    public static boolean isValidSkuId(CsvRecord record) {
        if (record.getSkuId() == null) {
            logger.warn("Record has a missing skuId: " + record.toString());
            return false;
        }
        return true;
    }

    public static boolean isValidLocationCountry(CsvRecord record) {
        if (record.getLocationCountry() == null) {
            logger.warn("Record has a missing locationCountry: " + record.toString());
            return false;
        }
        return true;
    }

    public static boolean isValidServiceId(CsvRecord record) {
        if (record.getServiceId() == null) {
            logger.warn("Record has a missing serviceId: " + record.toString());
            return false;
        }
        return true;
    }

    public static boolean areValidLabels(CsvRecord record) {
        if (record.getLabels() == null) {
            logger.warn("Record has missing labels: " + record.toString());
            return false;
        }
        return true;
    }
}
