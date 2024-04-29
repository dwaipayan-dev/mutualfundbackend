package com.mutualfunds.backend.mutualfundapi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateOrderDTO {
    /*
     * 
     * {
    "data": {
        "id": "11e5dee7-afac-428a-b163-15746ac4587f",
        "fund": "Arbitrage Fund 1",
        "amount": 500,
        "units": 0,
        "pricePerUnit": 0,
        "status": "",
        "paymentID": "3cd4267a-75f6-40f7-86dc-5ec4802e7ca9",
        "submittedAt": "",
        "succeededAt": "",
        "failedAt": ""
    },
    "success": true
}
     */

     private OrderDTO data;
     private boolean success;

}
