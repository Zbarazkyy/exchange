package com.example.exchange.dto.apiexchangerate;

import lombok.Data;

@Data
public class ExchangeRateApiRoot {
    private String result;
    private String documentation;
    private String terms_of_use;
    private int time_last_update_unix;
    private String time_last_update_utc;
    private int time_next_update_unix;
    private String time_next_update_utc;
    private String base_code;
    private ConversionRates conversion_rates;
}
