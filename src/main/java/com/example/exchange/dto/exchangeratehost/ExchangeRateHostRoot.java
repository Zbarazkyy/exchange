package com.example.exchange.dto.exchangeratehost;

import lombok.Data;

@Data
public class ExchangeRateHostRoot {
    private boolean success;
    private Query query;
    private Info info;
    private boolean historical;
    private String date;
    private double result;
}
