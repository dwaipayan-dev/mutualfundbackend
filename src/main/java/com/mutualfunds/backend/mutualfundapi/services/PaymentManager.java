package com.mutualfunds.backend.mutualfundapi.services;

import java.io.IOException;

import com.mutualfunds.backend.mutualfundapi.constants.AppProperties;
import com.mutualfunds.backend.mutualfundapi.constants.JsonConstants;
import com.mutualfunds.backend.mutualfundapi.constants.RequestType;
import com.mutualfunds.backend.mutualfundapi.daos.PaymentDAO;
import com.mutualfunds.backend.mutualfundapi.dto.CheckPaymentDTO;
import com.mutualfunds.backend.mutualfundapi.dto.PaymentResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import com.mutualfunds.backend.mutualfundapi.constants.ApiConstants;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import okhttp3.RequestBody;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentManager {

    private final AppProperties appProperties;

    public PaymentResponseDTO createPaymentCall(PaymentDAO paymentInfo) throws IOException, HttpClientErrorException, HttpServerErrorException {
        String url = appProperties.getUrlPay() + "/payment";
        RequestBody body = ApiConstants.getRequestBody(JsonConstants.OBJECT_MAPPER.writeValueAsString(paymentInfo));
        Request request = new Request.Builder()
                .url(url)
                .method("POST", body).addHeader("Content-Type", "application/json").build();
        return JsonConstants.OBJECT_MAPPER.readValue(ApiConstants.getResponseBody(request),
                PaymentResponseDTO.class);
        
    }

    public CheckPaymentDTO getPaymentCall(String paymentId) throws IOException, HttpClientErrorException, HttpServerErrorException {
        String url = appProperties.getUrlPay() + "/payment/" + paymentId;
        // RequestBody body = ApiConstants.getRequestBody(null);
        Request request = new Request.Builder().url(url).get().build();
        return JsonConstants.OBJECT_MAPPER.readValue(ApiConstants.getResponseBody(request), CheckPaymentDTO.class);
    }
}
