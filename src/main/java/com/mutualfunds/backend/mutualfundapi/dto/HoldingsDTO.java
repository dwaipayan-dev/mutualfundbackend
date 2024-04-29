package com.mutualfunds.backend.mutualfundapi.dto;
import java.util.Map;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class HoldingsDTO {
    private Map<String, Map<String, FundDTO>> strategyMap;
    private Double totalInvestedAmount;
    private Double totalMarketValue;
    private String message;
    private boolean success;

    public static HoldingsDTO genericFailureResponse(String errorMessage) {
        HoldingsDTO holdingsDTO = HoldingsDTO.builder()
        .message(errorMessage)
        .success(false)
        .build();
        return holdingsDTO;
    }
    
}
