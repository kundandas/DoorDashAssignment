package com.kdas.ddash.restaurant;

import android.os.AsyncTask;

import com.kdas.ddash.DoorDashErrorModel;
import com.kdas.ddash.ErrorModel;
import com.kdas.ddash.R;
import com.kdas.ddash.network.NetworkResponse;
import com.kdas.ddash.network.NetworkServiceProvider;

import java.lang.ref.WeakReference;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class RestaurantRetriever{

    private static final String URL = "https://api.doordash.com/v2/restaurant/?";
    private NetworkServiceProvider networkServiceProvider;
    private ResultParser parser;

    public interface RequestCallback {
        void onSuccess(List<Restaurant> result);
        void onFailure(ErrorModel errorModel);
    }

    public RestaurantRetriever(NetworkServiceProvider networkServiceProvider, ResultParser parser) {
        this.networkServiceProvider = networkServiceProvider;
        this.parser = parser;
    }

    public void requestRestaurant(RestaurantDataRequest restaurantDataRequest, RequestCallback callback) {

        String url = makeUrl(restaurantDataRequest);
        RestaurantRetrieverTask restaurantRetrieverTask = new RestaurantRetrieverTask(url, callback);
        restaurantRetrieverTask.execute();
    }

    private  String makeUrl(RestaurantDataRequest restaurantDataRequest) {
        String formattedUrl = URL +
                "lat=" + restaurantDataRequest.getLatitude() +
                "&lng=" + restaurantDataRequest.getLongitude() +
                "&offset=" + restaurantDataRequest.getOffset() +
                "&limit=" + restaurantDataRequest.getLimit();
        return formattedUrl;
    }

    private class RestaurantRetrieverTask extends AsyncTask<Void, Void, Boolean> {

        String url;
        WeakReference<RequestCallback> callback;
        List<Restaurant> result;
        ErrorModel errorModel;

        public RestaurantRetrieverTask(String url, RequestCallback callback) {
            this.url = url;
            this.callback = new WeakReference<>(callback);
        }

        protected Boolean doInBackground(Void... params) {
            NetworkResponse response = networkServiceProvider.makeGETRequest(url);
            if (response.getException() != null) {
                errorModel = new DoorDashErrorModel(R.string.error_exception, new String[]{response.getException().getMessage()});
                return false;
            }
            if (response.getResponseCode() == HttpsURLConnection.HTTP_OK) {
                result = parser.parseResult(response.getResponseData());
                if (result == null) {
                    errorModel = new DoorDashErrorModel(R.string.error_parsing);
                    return false;
                }
            } else {
                errorModel = new DoorDashErrorModel(R.string.error_network, new String[]{String.valueOf(response.getResponseCode())});
                return false;
            }
            return true;
        }

        protected void onPostExecute(Boolean isSuccess) {

            if (callback.get() != null) {
                if (isSuccess) {
                    callback.get().onSuccess(result);
                } else {
                    callback.get().onFailure(errorModel);
                }
            }
        }
    }
}
