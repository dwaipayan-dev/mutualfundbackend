package com.mutualfunds.backend.mutualfundapi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MarketValueDTO {
    /*
     * 
     * {
    "name": "Arbitrage Fund 1",
    "marketValue": 10.265595957950804
}
     */
    private String name;
    private Double marketValue;
     
}
