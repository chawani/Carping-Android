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
import com.tourkakao.carping.Post.DTO.PostListItem;
import com.tourkakao.carping.Post.PostDetailActivity;
import com.tourkakao.carping.Post.PostInfoActivity;
import com.tourkakao.carping.R;
import com.tourkakao.carping.databinding.PostTotalItemBinding;

import java.util.ArrayList;

public class PostTotalAdapter extends RecyclerView.Adapter<PostTotalAdapter.ViewHolder>{
    private Context context;
    private ArrayList<PostListItem> items;

    public interface OnLikeItemClickListener {
        void onItemClick(View v, int position) ;
    }
    private PostCategoryAdapter.OnLikeItemClickListener lListener = null ;
    public void setOnItemClickListener(PostCategoryAdapter.OnLikeItemClickListener listener) {
        this.lListener = listener ;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private PostTotalItemBinding binding;
        public ViewHolder(PostTotalItemBinding binding){
            super(binding.getRoot());
            this.binding=binding;
            binding.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(context, PostInfoActivity.class);
                    int pk=Integer.parseInt(binding.pk.getText().toString());
                    intent.putExtra("pk",pk);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
            binding.like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        if (lListener != null) {
                            lListener.onItemClick(view, position);
                        }
                    }
                }
            });
        }
        public void bind(PostListItem item){
            if (item.isIs_liked()) {
                Glide.with(context).load(R.drawable.is_pushed_like).into(binding.like);
            }else{
                Glide.with(context).load(R.drawable.like_mark_white).into(binding.like);
            }
            Glide.with(context).load(item.getThumbnail()).into(binding.image);
            binding.title.setText(item.getTitle());
            binding.pk.setText(Integer.toString(item.getId()));
            binding.star.setText("★ "+item.getTotal_star_avg());
            binding.image.setColorFilter(Color.parseColor("#75000000"));
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

    public int getId(int position) {
        return items.get(position).getId();
    }
    public boolean getLike(int position) {return items.get(position).isIs_liked();}
    public void setLike(int position){
        items.get(position).setIs_liked(true);
    }
    public void cancelLike(int position){
        items.get(position).setIs_liked(false);
    }
}