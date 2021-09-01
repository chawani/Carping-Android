package com.tourkakao.carping.EcoCarping.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tourkakao.carping.EcoCarping.DTO.ResultSearchKeyword;
import com.tourkakao.carping.databinding.LocationInfomationBinding;

import java.util.ArrayList;

public class LocationInfoAdapter extends RecyclerView.Adapter<LocationInfoAdapter.ViewHolder>{
    private Context context;
    private ArrayList<ResultSearchKeyword.Place> places;
    public class ViewHolder extends RecyclerView.ViewHolder{
        private LocationInfomationBinding binding;
        public ViewHolder(LocationInfomationBinding binding){
            super(binding.getRoot());
            this.binding=binding;
        }
        public void bind(ResultSearchKeyword.Place place){
            binding.address.setText(place.getAddress_name());
            binding.cate.setText(place.getCategory_name());
            binding.name.setText(place.getPlace_name());
        }
    }

    public LocationInfoAdapter(Context context, ArrayList<ResultSearchKeyword.Place> places){
        this.context=context;
        this.places=places;
    }

    public ResultSearchKeyword.Place getItem(int i) {
        return places.get(i);
    }

    @NonNull
    @Override
    public LocationInfoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LocationInfoAdapter.ViewHolder(LocationInfomationBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LocationInfoAdapter.ViewHolder holder, int position) {
        holder.bind(places.get(position));
    }

    @Override
    public int getItemCount() {
        return places==null?0:places.size();
    }
}
