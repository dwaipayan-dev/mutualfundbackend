package com.mutualfunds.backend.mutualfundapi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FundDTO {
    private String name;
    private int count;
    private int failedCount;
    private Double investedValue;
    private Double marketValue;

}
