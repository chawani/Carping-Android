package com.tourkakao.carping.newcarping.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tourkakao.carping.databinding.EachNewcarpingReviewImageBinding;

import java.util.ArrayList;

public class Newcarping_Review_Image_Adapter extends RecyclerView.Adapter {
    Context context;
    ArrayList<String> images;
    EachNewcarpingReviewImageBinding eachNewcarpingReviewImageBinding;
    public Newcarping_Review_Image_Adapter(Context context, ArrayList<String> images){
        this.context=context;
        this.images=images;
    }
    public class Newcarping_Review_Image_Viewholder extends RecyclerView.ViewHolder{
        private EachNewcarpingReviewImageBinding binding;
        public Newcarping_Review_Image_Viewholder(EachNewcarpingReviewImageBinding binding){
            super(binding.getRoot());
            this.binding=binding;
        }
        public void setItem(String image){
            Glide.with(context).load(image).into(binding.newcarpingReviewImg);
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        eachNewcarpingReviewImageBinding=EachNewcarpingReviewImageBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new Newcarping_Review_Image_Viewholder(eachNewcarpingReviewImageBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Newcarping_Review_Image_Viewholder vh=(Newcarping_Review_Image_Viewholder)holder;
        vh.setItem(images.get(position));
    }

    @Override
    public int getItemCount() {
        return images==null?0:images.size();
    }
    public void update_Item(ArrayList<String> images){
        if(this.images!=null){
            this.images=null;
        }
        this.images=images;
        notifyDataSetChanged();
    }
}
