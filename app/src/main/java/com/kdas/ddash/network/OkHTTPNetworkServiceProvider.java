package com.kdas.ddash.network;

import android.util.Log;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHTTPNetworkServiceProvider implements NetworkServiceProvider {

    private static final String TAG = "OkHTTPNetworkProvider";
    OkHttpClient client;

    public OkHTTPNetworkServiceProvider() {
        client = new OkHttpClient();
    }

    @Override
    public NetworkResponse makeGETRequest(String url) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            Response response = client.newCall(request).execute();
            return new RestaurantDataNetworkResponse(response.code(), response.body().string());
        } catch (IOException e) {
            Log.e(TAG, "IOException in fetching data from server", e);
            return new RestaurantDataNetworkResponse(e);
        }
    }
}
