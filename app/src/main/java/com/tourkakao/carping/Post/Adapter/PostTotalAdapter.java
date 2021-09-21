package com.tourkakao.carping.Post.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.tourkakao.carping.EcoCarping.Activity.EcoCarpingDetailActivity;
import com.tourkakao.carping.EcoCarping.Adapter.EcoTotalReviewAdapter;
import com.tourkakao.carping.Home.EcoDataClass.EcoReview;
import com.tourkakao.carping.Post.DTO.PostListItem;
import com.tourkakao.carping.Post.PostDetailActivity;
import com.tourkakao.carping.R;
import com.tourkakao.carping.databinding.EcoCarpingTotalListItemBinding;
import com.tourkakao.carping.databinding.PostTotalItemBinding;

import java.util.ArrayList;

public class PostTotalAdapter extends RecyclerView.Adapter<PostTotalAdapter.ViewHolder>{
    private Context context;
    private ArrayList<PostListItem> items;

    public class ViewHolder extends RecyclerView.ViewHolder{
        private PostTotalItemBinding binding;
        public ViewHolder(PostTotalItemBinding binding){
            super(binding.getRoot());
            this.binding=binding;
            binding.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(context, PostDetailActivity.class);
                    intent.putExtra("pk",binding.pk.getText().toString());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
        }
        public void bind(PostListItem items){
            binding.title.setText(items.getTitle());
            Glide.with(context).load(R.drawable.like_mark).into(binding.like);
        }
    }

    public PostTotalAdapter(Context context,ArrayList<PostListItem> items){
        this.context=context;
        this.items=items;
    }

    @NonNull
    @Override
    public PostTotalAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PostTotalAdapter.ViewHolder(PostTotalItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PostTotalAdapter.ViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items==null?0:items.size();
    }
}