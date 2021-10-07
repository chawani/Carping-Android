package com.tourkakao.carping.newcarping.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AndroidException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tourkakao.carping.NetworkwithToken.TotalApiClient;
import com.tourkakao.carping.R;
import com.tourkakao.carping.databinding.EachNewcarpingReviewBinding;
import com.tourkakao.carping.newcarping.Activity.Fix_newcarping_reviewActivity;
import com.tourkakao.carping.newcarping.DataClass.Newcarping_Review;
import com.tourkakao.carping.newcarping.Fragment.ReviewFragment;
import com.tourkakao.carping.newcarping.viewmodel.EachNewCarpingViewModel;
import com.tourkakao.carping.theme.Adapter.ThemeAdapter;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Newcarping_Review_Adapter extends RecyclerView.Adapter {
    Context context;
    EachNewcarpingReviewBinding eachNewcarpingReviewBinding;
    ArrayList<Newcarping_Review> reviews;
    private int userpk;
    private int postpk;
    public Newcarping_Review_Adapter(Context context, ArrayList<Newcarping_Review> reviews, int userpk, int pk){
        this.context=context;
        this.reviews=reviews;
        this.userpk=userpk;
        this.postpk=pk;
    }
    public interface OnSelectItemClickListener{
        void OnSelectItemClick(View v, int pos, int pk);
    }
    private OnSelectItemClickListener mListener=null;

    public void setOnSelectItemClickListener(OnSelectItemClickListener listener){
        this.mListener=listener;
    }
    public class Newcarping_Review_Viewholder extends RecyclerView.ViewHolder{
        private EachNewcarpingReviewBinding binding;
        public Newcarping_Review_Viewholder(EachNewcarpingReviewBinding binding){
            super(binding.getRoot());
            this.binding=binding;
        }
        public void setItem(Newcarping_Review review, int pos){
            Glide.with(context).load(review.getImage()).into(binding.image);
            Glide.with(context).load(review.getReview_profile()).circleCrop().into(binding.profile);
            binding.date.setText(review.getCreated_at());
            binding.like.setText(Integer.toString(review.getLike_count()));
            binding.ratingstar.setRating(review.getTotal_star());
            binding.userId.setText(review.getUsername());
            binding.review.setText(review.getText());
            if(review.isCheck_like()){
                Glide.with(context).load(R.drawable.like).into(binding.likeImg);
            }else{
                Glide.with(context).load(R.drawable.nolike).into(binding.likeImg);
            }
            if(userpk==review.getUser()){
                binding.fix.setVisibility(View.VISIBLE);
                binding.fix.setOnClickListener(v -> {
                    Intent intent=new Intent(context, Fix_newcarping_reviewActivity.class);
                    intent.putExtra("total_star", review.getTotal_star());
                    intent.putExtra("star1", review.getStar1());
                    intent.putExtra("star2", review.getStar2());
                    intent.putExtra("star3", review.getStar3());
                    intent.putExtra("star4", review.getStar4());
                    intent.putExtra("image", review.getImage());
                    intent.putExtra("text", review.getText());
                    intent.putExtra("userpk", userpk);
                    intent.putExtra("postpk", postpk);
                    intent.putExtra("id", review.getId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                });
                binding.delete.setVisibility(View.VISIBLE);
                binding.delete.setOnClickListener(v -> {
                    mListener.OnSelectItemClick(v, pos, reviews.get(pos).getId());
                });
            }else{
                binding.fix.setVisibility(View.GONE);
                binding.delete.setVisibility(View.GONE);
            }
            binding.likeImg.setOnClickListener(v -> {
                if(!review.isCheck_like()) {
                    TotalApiClient.getApiService(context).set_like(review.getId())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    result -> {
                                        review.setCheck_like(true);
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
                                        review.setCheck_like(false);
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
        vh.setItem(reviews.get(position), position);
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
