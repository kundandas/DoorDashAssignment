package com.kdas.ddash.restaurant;

import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

public class RestaurantListViewModelFactory implements ViewModelProvider.Factory {

    RestaurantRetriever restaurantRetriever;

    public RestaurantListViewModelFactory(RestaurantRetriever restaurantRetriever) {
        this.restaurantRetriever = restaurantRetriever;
    }


    @NonNull
    @Override
    public RestaurantListViewModel create(@NonNull Class modelClass) {
        return new RestaurantListViewModel(restaurantRetriever);
    }
}
