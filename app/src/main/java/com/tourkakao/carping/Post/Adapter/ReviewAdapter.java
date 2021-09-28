package com.tourkakao.carping.Post.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tourkakao.carping.Post.DTO.PostListItem;
import com.tourkakao.carping.Post.DTO.Review;
import com.tourkakao.carping.Post.PostDetailActivity;
import com.tourkakao.carping.R;
import com.tourkakao.carping.databinding.PostReviewItemBinding;
import com.tourkakao.carping.databinding.PostTotalItemBinding;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder>{
    private Context context;
    private ArrayList<Review> items;

    public class ViewHolder extends RecyclerView.ViewHolder{
        private PostReviewItemBinding binding;
        public ViewHolder(PostReviewItemBinding binding){
            super(binding.getRoot());
            this.binding=binding;
        }
        public void bind(Review item){
            Glide.with(context).load(item.getProfile()).into(binding.profile);
            binding.star.setRating(item.getTotal_star());
            binding.user.setText(item.getUsername());
            binding.date.setText(item.getCreated_at());
            binding.content.setText(item.getText());
            if(item.isIs_liked()) {
                Glide.with(context).load(R.drawable.nolike).into(binding.likeImg);
            }else{
                Glide.with(context).load(R.drawable.like).into(binding.likeImg);
            }
            binding.likeCount.setText(item.getLike_count());
        }
    }

    public ReviewAdapter(Context context,ArrayList<Review> items){
        this.context=context;
        this.items=items;
    }

    @NonNull
    @Override
    public ReviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ReviewAdapter.ViewHolder(PostReviewItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewAdapter.ViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items==null?0:items.size();
    }
}