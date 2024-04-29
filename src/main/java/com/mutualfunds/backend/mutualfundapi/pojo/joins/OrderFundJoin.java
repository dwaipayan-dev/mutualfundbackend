package com.mutualfunds.backend.mutualfundapi.pojo.joins;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrderFundJoin {

    private Double investedValue;
    private String fundName;
    private String fundStrategy;
    private Double units;
    private String status;
    
}
