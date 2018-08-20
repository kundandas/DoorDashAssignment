package com.kdas.ddash.network;

public class RestaurantDataNetworkResponse implements NetworkResponse {

    private String response;
    private int responseCode;
    private Exception exception;

    public RestaurantDataNetworkResponse(int responseCode, String response) {
        this.responseCode = responseCode;
        this.response = response;
    }

    public RestaurantDataNetworkResponse(Exception exception) {
        this.exception = exception;
    }

    @Override
    public int getResponseCode() {
        return responseCode;
    }

    @Override
    public String getResponseData() {
        return response;
    }

    @Override
    public Exception getException() {
        return exception;
    }
}
