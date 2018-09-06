package com.kdas.ddash.restaurant;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.kdas.ddash.LikedRestaurantsSharedPreference;
import com.kdas.ddash.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.MyViewHolder> {

    private List<Restaurant> restaurantList;
    private LikedRestaurantsSharedPreference sharedPreference;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, description, status;
        public ImageView imageView;
        public Button button;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            description = (TextView) view.findViewById(R.id.description);
            status = (TextView) view.findViewById(R.id.status);
            imageView = (ImageView)view.findViewById(R.id.imageView);
            button = (Button) view.findViewById(R.id.like_button);
        }
    }


    public RestaurantAdapter(List<Restaurant> restaurantList, LikedRestaurantsSharedPreference sharedPreference) {
        this.restaurantList = restaurantList;
        this.sharedPreference = sharedPreference;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.restaurant_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Restaurant restaurant = restaurantList.get(position);
        holder.name.setText(restaurant.getName());
        holder.description.setText(restaurant.getDescription());
        holder.status.setText(restaurant.getStatus());
        updateButtonColor(restaurant, holder.button);
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("tag", "button clicked");
                restaurant.isLike = !restaurant.isLike;
                saveLikeStatus(restaurant);
                updateButtonColor(restaurant, v);
            }
        });
        Picasso.get().load(restaurant.getCoverImageUrl()).into(holder.imageView);
    }

    private void saveLikeStatus(Restaurant restaurant) {
        if (restaurant.isLike) {
            sharedPreference.saveLikedResturant(restaurant.name);
        } else {
            sharedPreference.unLikeRestaurant(restaurant.name);
        }
    }

    private void updateButtonColor(Restaurant restaurant, View likeButton) {
        if (sharedPreference.isLikedRestaurant(restaurant.name)) {
            likeButton.setBackgroundColor(Color.GREEN);
        } else  {
            likeButton.setBackgroundColor(Color.RED);
        }
    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }

    public void appendData(List<Restaurant> result) {
        restaurantList.addAll(result);
    }

}
