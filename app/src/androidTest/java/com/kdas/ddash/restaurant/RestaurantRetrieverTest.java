package com.kdas.ddash.restaurant;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.kdas.ddash.ErrorModel;
import com.kdas.ddash.network.NetworkResponse;
import com.kdas.ddash.network.NetworkServiceProvider;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;

import java.util.List;

@RunWith(JUnit4.class)
public class RestaurantRetrieverTest {

    private static final int MOCK_OFFSET = 0;
    private static final int MOCK_LIMIT = 50;
    private static final Double MOCK_LONGITUDE = 0.0;
    private static final Double MOCK_LATITUDE = 0.0;

    RestaurantRetriever restaurantRetriever;
    NetworkServiceProvider networkServiceProvider;
    ResultParser resultParser;
    RestaurantDataRequest restaurantDataRequest;
    RestaurantRetriever.RequestCallback requestCallback;
    NetworkResponse networkResponse;

    @Before
    public void setup() throws Exception {
        networkServiceProvider = Mockito.mock(NetworkServiceProvider.class);
        resultParser = Mockito.mock(ResultParser.class);
        restaurantDataRequest = Mockito.mock(RestaurantDataRequest.class);
//        requestCallback = PowerMockito.mock(RestaurantRetriever.RequestCallback.class);
        restaurantRetriever = new RestaurantRetriever(networkServiceProvider, resultParser);
    }

    @Test
    public void testExceptionInFetchingData() throws Exception {
        networkResponse = Mockito.mock(NetworkResponse.class);
        mockRestaurantDataRequest();
        Mockito.when(networkServiceProvider.makeGETRequest(anyString())).thenReturn(networkResponse);
        Mockito.when(networkResponse.getException()).thenReturn(new Exception("this is mock"));

        restaurantRetriever.requestRestaurant(restaurantDataRequest, new RestaurantRetriever.RequestCallback() {
            @Override
            public void onSuccess(List<Restaurant> result) {
                Assert.fail("Failure expected");
            }

            @Override
            public void onFailure(ErrorModel errorModel) {
                Assert.assertTrue(errorModel.getErrorDisplayMessage(InstrumentationRegistry.getTargetContext()).contains("this is mock"));
            }
        });
        Thread.sleep(2000);
    }

    private void mockRestaurantDataRequest() {
        Mockito.when(restaurantDataRequest.getLatitude()).thenReturn(MOCK_LATITUDE);
        Mockito.when(restaurantDataRequest.getLongitude()).thenReturn(MOCK_LONGITUDE);
        Mockito.when(restaurantDataRequest.getLimit()).thenReturn(MOCK_LIMIT);
        Mockito.when(restaurantDataRequest.getOffset()).thenReturn(MOCK_OFFSET);

    }

}
