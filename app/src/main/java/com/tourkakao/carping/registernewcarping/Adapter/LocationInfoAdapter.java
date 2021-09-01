package com.tourkakao.carping.registernewcarping.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tourkakao.carping.databinding.NewcarpingSearchInformationBinding;
import com.tourkakao.carping.newcarping.DataClass.Newcarping_Review;
import com.tourkakao.carping.registernewcarping.DataClass.CarpingSearchKeyword;

import java.util.ArrayList;

public class LocationInfoAdapter extends RecyclerView.Adapter {
    private Context context;
    private ArrayList<CarpingSearchKeyword.Place> places;
    private int width;
    NewcarpingSearchInformationBinding searchInformationBinding;
    public LocationInfoAdapter(Context context, ArrayList<CarpingSearchKeyword.Place> places, int width){
        this.context=context;
        this.places=places;
        this.width=width-20;
    }
    public class LocationInfoViewHolder extends RecyclerView.ViewHolder{
        private NewcarpingSearchInformationBinding binding;
        public LocationInfoViewHolder(NewcarpingSearchInformationBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
        public void setItem(CarpingSearchKeyword.Place place){
            binding.address.setText(place.getAddress_name());
            binding.cate.setText(place.getCategory_name());
            binding.name.setText(place.getPlace_name());
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        searchInformationBinding=NewcarpingSearchInformationBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new LocationInfoViewHolder(searchInformationBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        LocationInfoViewHolder vh=(LocationInfoViewHolder)holder;
        vh.binding.getRoot().setMinimumWidth(width);
        //vh.binding.getRoot().requestLayout();
        vh.setItem(places.get(position));
    }

    @Override
    public int getItemCount() {
        return places==null?0:places.size();
    }
    public CarpingSearchKeyword.Place getItem(int pos){
        return places.get(pos);
    }

    public void update_Item(ArrayList<CarpingSearchKeyword.Place> items){
        if (places!= null) {
            places=null;
        }
        places=items;
        notifyDataSetChanged();
    }
}
