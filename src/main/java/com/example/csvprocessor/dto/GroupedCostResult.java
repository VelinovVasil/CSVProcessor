package com.example.csvprocessor.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Map;

@Getter
@Setter
@ToString
public class GroupedCostResult {
    private Map<String, Object> criteria;
    private BigDecimal cost;
}
