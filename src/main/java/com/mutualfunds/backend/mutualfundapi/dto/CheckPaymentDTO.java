package com.mutualfunds.backend.mutualfundapi.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckPaymentDTO {
    /*
     * 
     * "id": "4209d078-2384-4652-984d-1106342b25a6",
    "accountNumber": "11200222",
    "ifscCode": "UBIT22222",
    "amount": 500,
    "redirectUrl": "http://localhost:3000",
    "status": "Created",
    "createdAt": "2024-04-05T01:20:48Z",
    "utr": null
     */

     private String id;
     private String accountNumber;
     private String ifscCode;
     private Double amount;
     private String redirectUrl;
     private String status;
     private String createdAt;
     private String utr;
}
