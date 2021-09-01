package com.tourkakao.carping.EcoCarping.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.tourkakao.carping.EcoCarping.Activity.EcoCarpingDetailActivity;
import com.tourkakao.carping.EcoCarping.DTO.ResultSearchKeyword;
import com.tourkakao.carping.Home.EcoDataClass.EcoReview;
import com.tourkakao.carping.R;
import com.tourkakao.carping.databinding.EcoCarpingDetailImageBinding;
import com.tourkakao.carping.databinding.EcoCarpingTotalListItemBinding;
import com.tourkakao.carping.databinding.LocationInfomationBinding;

import java.util.ArrayList;

public class DetailPagerAdapter extends RecyclerView.Adapter<DetailPagerAdapter.ViewHolder>{
    private Context context;
    private ArrayList<String> images;
    public class ViewHolder extends RecyclerView.ViewHolder{
        private EcoCarpingDetailImageBinding binding;
        public ViewHolder(EcoCarpingDetailImageBinding binding){
            super(binding.getRoot());
            this.binding=binding;
        }
        public void bind(String image){
            Glide.with(context).load(image).into(binding.image);
        }
    }

    public DetailPagerAdapter(Context context, ArrayList<String> images){
        this.context=context;
        this.images=images;
    }

    public String getItem(int i) {
        return images.get(i);
    }

    @NonNull
    @Override
    public DetailPagerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DetailPagerAdapter.ViewHolder(EcoCarpingDetailImageBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DetailPagerAdapter.ViewHolder holder, int position) {
        holder.bind(images.get(position));
    }

    @Override
    public int getItemCount() {
        return images==null?0:images.size();
    }
}
