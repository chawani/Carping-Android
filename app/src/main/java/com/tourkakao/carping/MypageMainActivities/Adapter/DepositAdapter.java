package com.tourkakao.carping.MypageMainActivities.Adapter;

import android.content.Context;
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
import com.tourkakao.carping.MypageMainActivities.DTO.Post;
import com.tourkakao.carping.Post.DTO.PostListItem;
import com.tourkakao.carping.R;
import com.tourkakao.carping.databinding.DepositItemBinding;
import com.tourkakao.carping.databinding.MypagePostItemBinding;

import java.util.ArrayList;

public class DepositAdapter extends RecyclerView.Adapter<DepositAdapter.ViewHolder>{
    Context context;
    ArrayList<Post> items;

    public class ViewHolder extends RecyclerView.ViewHolder{
        private DepositItemBinding binding;
        public ViewHolder(DepositItemBinding binding){
            super(binding.getRoot());
            this.binding=binding;
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
        public void bind(Post item){
            Glide.with(context)
                    .load(item.getProfile())
                    .transform(new CenterCrop(), new RoundedCorners(100))
                    .into(binding.profile);
            binding.price.setText(item.getPoint()+"원");
            binding.title.setText(item.getTitle());
            binding.name.setText(item.getUsername());
            binding.date.setText(item.getPay_date());
        }
    }

    public DepositAdapter(Context context,ArrayList<Post> items){
        this.context=context;
        this.items=items;
    }

    @NonNull
    @Override
    public DepositAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DepositAdapter.ViewHolder(DepositItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DepositAdapter.ViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items==null?0:items.size();
    }
}
