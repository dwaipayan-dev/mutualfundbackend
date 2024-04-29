package com.mutualfunds.backend.mutualfundapi.daos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PaymentDAO {

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long productId;

    private Integer amount;

    private String accountNumber;

    private String ifscCode;

    private String redirectUrl;
}
