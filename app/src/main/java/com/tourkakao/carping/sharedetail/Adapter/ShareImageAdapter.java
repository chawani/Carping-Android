package com.tourkakao.carping.sharedetail.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tourkakao.carping.databinding.EachShareImageBinding;

import java.util.ArrayList;

public class ShareImageAdapter extends RecyclerView.Adapter<ShareImageAdapter.ShareImageViewHolder> {
    private Context context;
    private ArrayList<String> images;
    public class ShareImageViewHolder extends RecyclerView.ViewHolder{
        private EachShareImageBinding binding;
        public ShareImageViewHolder(EachShareImageBinding binding){
            super(binding.getRoot());
            this.binding=binding;
        }
        public void setItem(String image){
            System.out.println(image+"---------------------shareimageadapter");
            Glide.with(context).load(image).into(binding.shareImg);
        }
    }
    public ShareImageAdapter(Context context, ArrayList<String> images){
        this.context=context;
        this.images=images;
    }

    @NonNull
    @Override
    public ShareImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ShareImageAdapter.ShareImageViewHolder(EachShareImageBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ShareImageViewHolder holder, int position) {
        holder.setItem(images.get(position));
    }

    @Override
    public int getItemCount() {
        return images==null?0:images.size();
    }



}
