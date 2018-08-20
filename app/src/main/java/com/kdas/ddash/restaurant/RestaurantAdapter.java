package com.kdas.ddash.restaurant;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kdas.ddash.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.MyViewHolder> {

    private List<Restaurant> restaurantList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, description, status;
        public ImageView imageView;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            description = (TextView) view.findViewById(R.id.description);
            status = (TextView) view.findViewById(R.id.status);
            imageView = (ImageView)view.findViewById(R.id.imageView);
        }
    }


    public RestaurantAdapter(List<Restaurant> restaurantList) {
        this.restaurantList = restaurantList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.restaurant_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Restaurant restaurant = restaurantList.get(position);
        holder.name.setText(restaurant.getName());
        holder.description.setText(restaurant.getDescription());
        holder.status.setText(restaurant.getStatus());
        Picasso.get().load(restaurant.getCoverImageUrl()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }

    public void appendData(List<Restaurant> result) {
        restaurantList.addAll(result);
    }

}
