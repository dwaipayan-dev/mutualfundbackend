package com.mutualfunds.backend.mutualfundapi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentResponseDTO {

    private String paymentLink;

    private boolean success;

    private String message;

    public static PaymentResponseDTO genericFailureResponse(String errorMessage){
        PaymentResponseDTO paymentResponse = new PaymentResponseDTO();
        paymentResponse.setMessage("Unable to fetch response due to " + errorMessage);
        paymentResponse.setSuccess(false);
        return paymentResponse;
    }
}
