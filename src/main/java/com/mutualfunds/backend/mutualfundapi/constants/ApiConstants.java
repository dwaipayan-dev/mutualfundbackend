package com.mutualfunds.backend.mutualfundapi.constants;

import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.io.IOException;

@Slf4j
public class ApiConstants {

    private ApiConstants(){

    }

    public static final OkHttpClient API_CLIENT = new OkHttpClient().newBuilder().build();
    public static final MediaType JSON_MEDIA = MediaType.parse("application/json");
    
    public static RequestBody getRequestBody(String requestJson) {
        return RequestBody.create(JSON_MEDIA, requestJson);
    }

    public static String getResponseBody(Request request) throws IOException {
        try(Response response = ApiConstants.API_CLIENT.newCall(request).execute()) {
            if (response.code() != 200) {
                if (response.code() == 400) {
                    log.error("Bad Request Error");
                    throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
                } else {
                    log.error("Server Error");
                    throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
            return response.body().string();
        }
    }
}
