package com.example.exchange.dto.apiexchangerate;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import lombok.Data;

@Data
public class AvailableCurrency {
    @JsonProperty("supported_codes")
    private ArrayList<ArrayList<String>> supportedCurrency;
}
