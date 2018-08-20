package com.kdas.ddash.restaurant;

import android.support.annotation.NonNull;

public class RestaurantDataRequest {

    private Double latitude;

    private Double longitude;

    private int offset = 0;

    private int limit = 50;

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public int getOffset() {
        return offset;
    }

    public int getLimit() {
        return limit;
    }

    public static class Builder {

        RestaurantDataRequest restaurantDataRequest = new RestaurantDataRequest();

        public Builder setLatitude(@NonNull Double latitude) {
            restaurantDataRequest.latitude = latitude;
            return this;
        }

        public Builder setLongitude(@NonNull Double longitude) {
            restaurantDataRequest.longitude = longitude;
            return this;
        }

        public Builder setOffset(int offset) {
            restaurantDataRequest.offset = offset;
            return this;
        }

        public Builder setLimit(int limit) {
            restaurantDataRequest.limit = limit;
            return this;
        }

        public RestaurantDataRequest build() {
            if (restaurantDataRequest.latitude < -90.0 || restaurantDataRequest.latitude > 90.0) {
                throw new IllegalArgumentException("Invalid latitude value. Must be between -90 to 90");
            }
            if (restaurantDataRequest.longitude < -180.0 || restaurantDataRequest.longitude > 180.0) {
                throw new IllegalArgumentException("Invalid longitude value. Must be between -180 to 180");
            }
            if (restaurantDataRequest.offset < 0) {
                throw new IllegalArgumentException("Offset value must be positive");
            }
            if (restaurantDataRequest.limit < 1 || restaurantDataRequest.limit > 50) {
                throw new IllegalArgumentException("Result limit must be valid and between 1 to 50");
            }
            return restaurantDataRequest;
        }

    }
}
