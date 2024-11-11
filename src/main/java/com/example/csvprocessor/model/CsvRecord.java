package com.example.csvprocessor.model;

import com.example.csvprocessor.util.BigDecimalConverter;
import com.example.csvprocessor.util.LocalDateTimeConverter;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CsvRecord {

    @CsvBindByName(column = "_id")
    private String id;

    @CsvBindByName(column = "billing_account_id")
    private String billingAccountId;

    @CsvCustomBindByName(column = "cost", converter = BigDecimalConverter.class)
    private BigDecimal cost;

    @CsvCustomBindByName(column = "cost_at_list", converter = BigDecimalConverter.class)
    private BigDecimal costAtList;

    @CsvBindByName(column = "cost_type")
    private String costType;

    @CsvBindByName(column = "credits.amount")
    private String creditsAmount;

    @CsvBindByName(column = "credits.full_name")
    private String creditsFullName;

    @CsvBindByName(column = "credits.id")
    private String creditsId;

    @CsvBindByName(column = "credits.name")
    private String creditsName;

    @CsvBindByName(column = "credits.type")
    private String creditsType;

    @CsvBindByName(column = "currency")
    private String currency;

    @CsvCustomBindByName(column = "currency_conversion_rate", converter = BigDecimalConverter.class)
    private BigDecimal currencyConversionRate;

    @CsvBindByName(column = "invoice.month")
    private String invoiceMonth;

    @CsvBindByName(column = "location.country")
    private String locationCountry;

    @CsvBindByName(column = "location.location")
    private String locationLocation;

    @CsvBindByName(column = "location.region")
    private String locationRegion;

    @CsvBindByName(column = "location.zone")
    private String locationZone;

    @CsvCustomBindByName(column = "price.effective_price", converter = BigDecimalConverter.class)
    private BigDecimal priceEffectivePrice;

    @CsvCustomBindByName(column = "price.pricing_unit_quantity", converter = BigDecimalConverter.class)
    private BigDecimal pricePricingUnitQuantity;

    @CsvCustomBindByName(column = "price.tier_start_amount", converter = BigDecimalConverter.class)
    private BigDecimal priceTierStartAmount;

    @CsvBindByName(column = "price.unit")
    private String priceUnit;

    @CsvBindByName(column = "project.id")
    private String projectId;

    @CsvBindByName(column = "resource.global_name")
    private String resourceGlobalName;

    @CsvBindByName(column = "resource.name")
    private String resourceName;

    @CsvBindByName(column = "service.description")
    private String serviceDescription;

    @CsvBindByName(column = "service.id")
    private String serviceId;

    @CsvBindByName(column = "sku.description")
    private String skuDescription;

    @CsvBindByName(column = "sku.id")
    private String skuId;

    @CsvBindByName(column = "system_labels.key")
    private String systemLabelsKey;

    @CsvBindByName(column = "system_labels.value")
    private String systemLabelsValue;

    @CsvBindByName(column = "transaction_type")
    private String transactionType;

    @CsvCustomBindByName(column = "usage.amount", converter = BigDecimalConverter.class)
    private BigDecimal usageAmount;

    @CsvCustomBindByName(column = "usage.amount_in_pricing_units", converter = BigDecimalConverter.class)
    private BigDecimal usageAmountInPricingUnits;

    @CsvBindByName(column = "usage.pricing_unit")
    private String usagePricingUnit;

    @CsvBindByName(column = "usage.unit")
    private String usageUnit;

    @CsvCustomBindByName(column = "usage_end_time", converter = LocalDateTimeConverter.class)
    private LocalDateTime usageEndTime;

    @CsvCustomBindByName(column = "usage_start_time", converter = LocalDateTimeConverter.class)
    private LocalDateTime usageStartTime;

    @CsvBindByName(column = "labels")
    private String labels;

}
