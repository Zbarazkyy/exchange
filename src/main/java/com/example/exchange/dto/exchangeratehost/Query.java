package com.example.exchange.dto.exchangeratehost;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Query {
    private String from;
    @JsonProperty("to")
    private String myto;
    private int amount;
}
