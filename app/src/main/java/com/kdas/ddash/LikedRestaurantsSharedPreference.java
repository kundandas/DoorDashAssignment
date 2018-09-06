package com.kdas.ddash;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.Console;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class LikedRestaurantsSharedPreference {

    SharedPreferences sharedPreferences;
    private static final String KEY_LIKEDRESTAURANTS = "liked_Restaurants";

    Set<String> likerestaurantsCache = new HashSet<>();

    public LikedRestaurantsSharedPreference(Context context) {
        sharedPreferences = context.getSharedPreferences("likedRestaurants", Context.MODE_PRIVATE);
    }


    public void saveLikedResturant(String name) {
        //add to persistence
        likerestaurantsCache.add(name);
        sharedPreferences.edit().putStringSet(KEY_LIKEDRESTAURANTS, likerestaurantsCache).commit();

    }

    public void unLikeRestaurant(String name) {
        likerestaurantsCache.remove(name);
        sharedPreferences.edit().putStringSet(KEY_LIKEDRESTAURANTS, likerestaurantsCache).commit();
    }

    public boolean isLikedRestaurant(String name) {
        HashSet<String> emptySet = new HashSet<>();
        likerestaurantsCache = sharedPreferences.getStringSet(KEY_LIKEDRESTAURANTS, emptySet);
        Iterator<String> it = likerestaurantsCache.iterator();
        while(it.hasNext()){
            if (it.next().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

}
