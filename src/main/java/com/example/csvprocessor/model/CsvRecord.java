package com.example.csvprocessor.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CsvRecord {
    private String id;
    private String billingAccountId;
    private double cost;
    private double costAtList;
    private String costType;
    private String creditsAmount;
    private String creditsFullName;
    private String creditsId;
    private String creditsName;
    private String creditsType;
    private String currency;
    private double currencyConversionRate;
    private String invoiceMonth;
    private String labels;
    private String locationCountry;
    private String locationLocation;
    private String locationRegion;
    private String locationZone;
    private double priceEffectivePrice;
    private double pricePricingUnitQuantity;
    private double priceTierStartAmount;
    private String priceUnit;
    private String projectId;
    private String resourceGlobalName;
    private String resourceName;
    private String serviceDescription;
    private String serviceId;
    private String skuDescription;
    private String skuId;
    private String systemLabelsKey;
    private String systemLabelsValue;
    private String transactionType;
    private double usageAmount;
    private double usageAmountInPricingUnits;
    private String usagePricingUnit;
    private String usageUnit;
    private LocalDateTime usageEndTime;
    private LocalDateTime usageStartTime;
}
