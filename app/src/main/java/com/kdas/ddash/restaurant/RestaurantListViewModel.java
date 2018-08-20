package com.kdas.ddash.restaurant;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.kdas.ddash.ErrorModel;

import java.util.List;

public class RestaurantListViewModel extends ViewModel implements RestaurantRetriever.RequestCallback{
    private MutableLiveData<RestaurantLiveDataContainer> restaurantLiveDataContainerMutableLiveData;
    private RestaurantRetriever restaurantRetriever;

    public RestaurantListViewModel(RestaurantRetriever restaurantRetriever) {
        this.restaurantRetriever = restaurantRetriever;
    }

    public LiveData<RestaurantLiveDataContainer> getRestaurantList(RestaurantDataRequest restaurantDataRequest) {
        if (restaurantLiveDataContainerMutableLiveData == null) {
            restaurantLiveDataContainerMutableLiveData = new MutableLiveData<>();
            loadRestaurantData(restaurantDataRequest);
        }
        return restaurantLiveDataContainerMutableLiveData;
    }

    private void loadRestaurantData(RestaurantDataRequest restaurantDataRequest) {
        restaurantRetriever.requestRestaurant(restaurantDataRequest, this);
    }

    @Override
    public void onSuccess(List<Restaurant> result) {
        RestaurantLiveDataContainer restaurantLiveDataContainer = new RestaurantLiveDataContainer(RestaurantLiveDataContainer.RestaurantLiveDataStatus.SUCCESS, result);
        restaurantLiveDataContainerMutableLiveData.setValue(restaurantLiveDataContainer);
    }

    @Override
    public void onFailure(ErrorModel errorModel) {
        RestaurantLiveDataContainer restaurantLiveDataContainer = new RestaurantLiveDataContainer(RestaurantLiveDataContainer.RestaurantLiveDataStatus.ERROR, errorModel);
        restaurantLiveDataContainerMutableLiveData.setValue(restaurantLiveDataContainer);
    }


}
