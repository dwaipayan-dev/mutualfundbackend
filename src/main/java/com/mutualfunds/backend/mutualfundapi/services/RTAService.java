package com.mutualfunds.backend.mutualfundapi.services;

import java.io.IOException;

import com.mutualfunds.backend.mutualfundapi.constants.AppProperties;
import com.mutualfunds.backend.mutualfundapi.constants.RequestType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import com.mutualfunds.backend.mutualfundapi.constants.ApiConstants;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;

@Service
@RequiredArgsConstructor
@Slf4j
public class RTAService {

    private final AppProperties appProperties;

    public String createOrder(String jsonBody) throws IOException, HttpClientErrorException, HttpServerErrorException{
        Request request = new Request.Builder()
        .url(appProperties.getUrlRta() + "/order")
        .method(RequestType.POST.getName(), ApiConstants.getRequestBody(jsonBody))
        .addHeader("Content-Type", "application/json")
        .build();
        return ApiConstants.getResponseBody(request);
    }

    public String fetchOrder(String orderId) throws IOException, HttpClientErrorException, HttpServerErrorException{
        Request request = new Request.Builder()
        .url(appProperties.getUrlRta() + "/order/" + orderId)
        .get()
        .addHeader("Content-Type", "application/json")
        .build();
        return ApiConstants.getResponseBody(request);
    }

    public String fetchFundMarketValue(String fundName) throws IOException, HttpClientErrorException, HttpServerErrorException{
        String url = appProperties.getUrlRta() + "/market-value/" + fundName;
        Request request = new Request.Builder()
        .url(url)
        .get()
        .addHeader("Content-Type", "application/json")
        .build();
        return ApiConstants.getResponseBody(request);
    }
}
