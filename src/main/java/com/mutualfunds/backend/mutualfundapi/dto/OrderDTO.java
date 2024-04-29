package com.mutualfunds.backend.mutualfundapi.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderDTO {
    /*
     * 
     * {
    "id": "26bda99a-79d0-4563-9d8f-3b47da6554f3",
    "fund": "Arbitrage Fund 1",
    "amount": 500,
    "units": 25.993197491666407,
    "pricePerUnit": 19.235801988589643,
    "status": "Succeeded",
    "paymentID": "3cd4267a-75f6-40f7-86dc-5ec4802e7ca9",
    "submittedAt": "2024-04-05T02:39:28Z",
    "succeededAt": "2024-04-05T02:39:33Z",
    "failedAt": null
}
     */

     private String id;
     private String fund;
     private Double amount;
     private Double units;
     private Double pricePerUnit;
     private String status;
     private String paymentID;
     private String submittedAt;
     private String succeededAt;
     private String failedAt;

}
