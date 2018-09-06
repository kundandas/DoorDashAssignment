package com.kdas.ddash;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kdas.ddash.network.NetworkServiceProvider;
import com.kdas.ddash.network.OkHTTPNetworkServiceProvider;
import com.kdas.ddash.restaurant.Restaurant;
import com.kdas.ddash.restaurant.RestaurantAdapter;
import com.kdas.ddash.restaurant.RestaurantDataRequest;
import com.kdas.ddash.restaurant.RestaurantListViewModel;
import com.kdas.ddash.restaurant.RestaurantListViewModelFactory;
import com.kdas.ddash.restaurant.RestaurantLiveDataContainer;
import com.kdas.ddash.restaurant.RestaurantRetriever;
import com.kdas.ddash.restaurant.ResultParser;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Restaurant> restaurantList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ViewGroup progressBar;
    private ViewGroup errorLayout;
    private TextView errorMessageTextView;
    private RestaurantAdapter mAdapter;
    private RestaurantListViewModelFactory restaurantListViewModelFactory;
    private RestaurantRetriever restaurantRetriever;
    private ResultParser resultParser;
    private NetworkServiceProvider networkServiceProvider;
    private static final Double LATITUDE = 37.422740;
    private static final Double LONGITUDE = -122.139956;
    int currentOffset = 0;
    int currentLimit = 50;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progress_layout);
        errorLayout = findViewById(R.id.error_layout);
        errorMessageTextView = findViewById(R.id.error_detail);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        LikedRestaurantsSharedPreference sharedPreference = new LikedRestaurantsSharedPreference(this);
        mAdapter = new RestaurantAdapter(restaurantList, sharedPreference);
        recyclerView.setAdapter(mAdapter);

        networkServiceProvider = new OkHTTPNetworkServiceProvider();
        resultParser = new ResultParser();
        restaurantRetriever = new RestaurantRetriever(networkServiceProvider, resultParser);
        restaurantListViewModelFactory = new RestaurantListViewModelFactory(restaurantRetriever);
        RestaurantListViewModel model = ViewModelProviders.of(this, restaurantListViewModelFactory).get(RestaurantListViewModel.class);
        RestaurantDataRequest restaurantDataRequest = new RestaurantDataRequest.Builder()
                .setLatitude(LATITUDE)
                .setLongitude(LONGITUDE)
                .setOffset(currentOffset)
                .setLimit(currentLimit)
                .build();
        model.getRestaurantList(restaurantDataRequest).observe(this, new Observer<RestaurantLiveDataContainer>() {
            @Override
            public void onChanged(@Nullable RestaurantLiveDataContainer restaurantLiveDataContainer) {
                if (restaurantLiveDataContainer.getStatus() == RestaurantLiveDataContainer.RestaurantLiveDataStatus.SUCCESS) {
                    mAdapter.appendData(restaurantLiveDataContainer.getRestaurantList());
                    mAdapter.notifyDataSetChanged();
                    hideProgress();
                } else {
                    showError(restaurantLiveDataContainer.getErrorModel());
                }
            }
        });
        showProgress();
    }

    private void showProgress() {
        recyclerView.setVisibility(View.GONE);
        errorLayout.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        recyclerView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        errorLayout.setVisibility(View.GONE);
    }

    private void showError(ErrorModel errorModel) {
        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        errorLayout.setVisibility(View.VISIBLE);
        errorMessageTextView.setText(errorModel.getErrorDisplayMessage(this));
    }

}
