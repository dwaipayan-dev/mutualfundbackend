package com.mutualfunds.backend.mutualfundapi.daos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateOrderDAO {
    /*
     * 
     * {
    "fund": "Arbitrage Fund 1",
    "amount": 500,
    "paymentID": "3b84fe7b-f044-4277-91bf-712194201b34"
}
     */

    private String fund;
    private Double amount;
    private String paymentID;
}
