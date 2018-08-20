package com.kdas.ddash.restaurant;

import com.kdas.ddash.ErrorModel;

import java.util.List;

public class RestaurantLiveDataContainer {

    public enum RestaurantLiveDataStatus {
        SUCCESS,
        ERROR
    }

    public RestaurantLiveDataContainer(RestaurantLiveDataStatus status, List<Restaurant> result) {
        this.status = status;
        this.result = result;
    }

    public RestaurantLiveDataContainer(RestaurantLiveDataStatus status, ErrorModel errorModel) {
        this.status = status;
        this.errorModel = errorModel;
    }

    List<Restaurant> result;
    RestaurantLiveDataStatus status;
    ErrorModel errorModel;

    public List<Restaurant> getRestaurantList() {
        return result;
    }

    public RestaurantLiveDataStatus getStatus() {
        return status;
    }

    public ErrorModel getErrorModel() {
        return errorModel;
    }
}
