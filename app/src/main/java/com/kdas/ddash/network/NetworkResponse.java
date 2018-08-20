package com.kdas.ddash.network;

public interface NetworkResponse {

    int getResponseCode();

    String getResponseData();

    Exception getException();
}
