package com.tourkakao.carping.MypageMainActivities.Adapter;

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
import com.tourkakao.carping.Home.ThemeFragmentAdapter.NewCarpingPlace_Adapter;
import com.tourkakao.carping.MypageMainActivities.DTO.MyCarpingPost;
import com.tourkakao.carping.R;
import com.tourkakao.carping.databinding.EcoCarpingTotalListItemBinding;
import com.tourkakao.carping.databinding.MypageCarpingApiListItemBinding;
import com.tourkakao.carping.databinding.MypageCarpingListItemBinding;

import java.util.ArrayList;

public class MyCarpingAdapter extends RecyclerView.Adapter<MyCarpingAdapter.ViewHolder>{
    Context context;
    ArrayList<MyCarpingPost> myCarpingPosts;

    public interface OnSelectItemClickListener{
        void OnSelectItemClick(View v, int pos, int pk);
    }
    private MyCarpingAdapter.OnSelectItemClickListener mListener=null;

    public void setOnSelectItemCLickListener(MyCarpingAdapter.OnSelectItemClickListener listener){
        this.mListener=listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private MypageCarpingApiListItemBinding binding;
        public ViewHolder(MypageCarpingApiListItemBinding binding){
            super(binding.getRoot());
            this.binding=binding;
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos=getAdapterPosition();
                    int pk=myCarpingPosts.get(pos).getId();
                    if(pos!=RecyclerView.NO_POSITION){
                        if(mListener!=null){
                            mListener.OnSelectItemClick(v, pos, pk);
                        }
                    }
                }
            });
        }
        public void bind(MyCarpingPost myCarpingPost){
            Glide.with(context)
                    .load(myCarpingPost.getImage1())
                    .transform(new CenterCrop(), new RoundedCorners(30))
                    .into(binding.image);
            binding.title.setText(myCarpingPost.getTitle());
            binding.bookmarkCount.setText("스크랩 "+myCarpingPost.getBookmark_count());
        }
    }

    public MyCarpingAdapter(Context context,ArrayList<MyCarpingPost> myCarpingPosts){
        this.context=context;
        this.myCarpingPosts=myCarpingPosts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(MypageCarpingApiListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(myCarpingPosts.get(position));
    }

    @Override
    public int getItemCount() {
        return myCarpingPosts==null?0:myCarpingPosts.size();
    }
}
