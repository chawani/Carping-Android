package com.tourkakao.carping.Post.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.tourkakao.carping.Post.DTO.PostListItem;
import com.tourkakao.carping.Post.DTO.Review;
import com.tourkakao.carping.Post.PostDetailActivity;
import com.tourkakao.carping.R;
import com.tourkakao.carping.SharedPreferenceManager.SharedPreferenceManager;
import com.tourkakao.carping.databinding.PostReviewItemBinding;
import com.tourkakao.carping.databinding.PostTotalItemBinding;

import java.util.ArrayList;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder>{
    private Context context;
    private List<Review> items;
    private int current_user;

    public interface OnLikeItemClickListener {
        void onItemClick(View v, int position) ;
    }
    public interface OnDeleteItemClickListener {
        void onItemClick(View v, int position) ;
    }
    private OnDeleteItemClickListener dListener = null ;
    private OnLikeItemClickListener lListener = null ;
    public void setOnItemClickListener(OnDeleteItemClickListener listener) {
        this.dListener = listener ;
    }
    public void setOnItemLikeClickListener(OnLikeItemClickListener listener) {
        this.lListener = listener ;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private PostReviewItemBinding binding;
        public ViewHolder(PostReviewItemBinding binding){
            super(binding.getRoot());
            this.binding=binding;
            binding.privateDeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition() ;
                    if (pos != RecyclerView.NO_POSITION) {
                        if (dListener != null) {
                            dListener.onItemClick(v, pos) ;
                        }
                    }
                }
            });
            binding.likeArea.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition() ;
                    if (pos != RecyclerView.NO_POSITION) {
                        if (lListener != null) {
                            lListener.onItemClick(v, pos) ;
                        }
                    }
                }
            });
        }
        public void bind(Review item){
            Glide.with(context).load(item.getProfile())
                    .transform(new CenterCrop(), new RoundedCorners(100))
                    .into(binding.profile);
            binding.star.setRating(item.getTotal_star());
            binding.user.setText(item.getUsername());
            binding.date.setText(item.getCreated_at());
            binding.content.setText(item.getText());
            if(!item.isIs_liked()) {
                Glide.with(context).load(R.drawable.nolike).into(binding.likeImg);
                binding.likeCount.setTextColor(Color.parseColor("#bab9ba"));
            }else{
                Glide.with(context).load(R.drawable.like).into(binding.likeImg);
                binding.likeCount.setTextColor(Color.parseColor("#5f51ef"));
            }
            binding.likeCount.setText(Integer.toString(item.getLike_count()));

            if(item.getUser()!=current_user)
                binding.privateDeleteButton.setVisibility(View.GONE);
        }
    }

    public ReviewAdapter(Context context, List<Review> items){
        this.context=context;
        this.items=items;
        current_user= SharedPreferenceManager.getInstance(context).getInt("id",0);
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

    public int getId(int position) {
        return items.get(position).getId();
    }
    public boolean getLike(int position) {return items.get(position).isIs_liked();}

    public void setLike(int position){
        items.get(position).setIs_liked(true);
        items.get(position).setLike_count(items.get(position).getLike_count()+1);
    }

    public void cancelLike(int position){
        items.get(position).setIs_liked(false);
        items.get(position).setLike_count(items.get(position).getLike_count()-1);
    }
}