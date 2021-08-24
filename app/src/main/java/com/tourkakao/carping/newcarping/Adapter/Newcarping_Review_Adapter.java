package com.tourkakao.carping.newcarping.Adapter;

import android.content.Context;
import android.util.AndroidException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tourkakao.carping.NetworkwithToken.TotalApiClient;
import com.tourkakao.carping.R;
import com.tourkakao.carping.databinding.EachNewcarpingReviewBinding;
import com.tourkakao.carping.newcarping.DataClass.Newcarping_Review;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Newcarping_Review_Adapter extends RecyclerView.Adapter {
    Context context;
    EachNewcarpingReviewBinding eachNewcarpingReviewBinding;
    ArrayList<Newcarping_Review> reviews;
    public Newcarping_Review_Adapter(Context context, ArrayList<Newcarping_Review> reviews){
        this.context=context;
        this.reviews=reviews;
    }
    public class Newcarping_Review_Viewholder extends RecyclerView.ViewHolder{
        private EachNewcarpingReviewBinding binding;
        public Newcarping_Review_Viewholder(EachNewcarpingReviewBinding binding){
            super(binding.getRoot());
            this.binding=binding;

        }
        public void setItem(Newcarping_Review review){
            Glide.with(context).load(review.getImage()).into(binding.image);
            Glide.with(context).load(review.getReview_profile()).circleCrop().into(binding.profile);
            binding.date.setText(review.getCreated_at());
            binding.like.setText(Integer.toString(review.getLike_count()));
            binding.ratingstar.setRating(review.getTotal_star());
            binding.userId.setText(review.getUsername());
            binding.review.setText(review.getText());
            if(review.getCheck_like()==1){
                Glide.with(context).load(R.drawable.like).into(binding.likeImg);
            }else{
                Glide.with(context).load(R.drawable.nolike).into(binding.likeImg);
            }
            binding.likeImg.setOnClickListener(v -> {
                if(review.getCheck_like()==0) {
                    TotalApiClient.getApiService(context).set_like(review.getId())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    result -> {
                                        review.setCheck_like(1);
                                        review.setLike_count(review.getLike_count()+1);
                                        binding.like.setText(Integer.toString(review.getLike_count()));
                                        Glide.with(context).load(R.drawable.like).into(binding.likeImg);
                                    },
                                    error -> { }
                            );
                }else{
                    TotalApiClient.getApiService(context).release_like(review.getId())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    result -> {
                                        review.setCheck_like(0);
                                        review.setLike_count(review.getLike_count()-1);
                                        binding.like.setText(Integer.toString(review.getLike_count()));
                                        Glide.with(context).load(R.drawable.nolike).into(binding.likeImg);
                                    },
                                    error -> { }
                            );
                }
            });
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        eachNewcarpingReviewBinding=EachNewcarpingReviewBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new Newcarping_Review_Viewholder(eachNewcarpingReviewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Newcarping_Review_Viewholder vh=(Newcarping_Review_Viewholder)holder;
        vh.setItem(reviews.get(position));
    }

    @Override
    public int getItemCount() {
        return reviews==null?0:reviews.size();
    }
    public void update_Item(ArrayList<Newcarping_Review> items){
        if (reviews != null) {
            reviews=null;
        }
        reviews=items;
        notifyDataSetChanged();
    }

}
