package com.tourkakao.carping.Home.ThemeFragmentAdapter;

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
import com.tourkakao.carping.Home.ThemeDataClass.AZPost;
import com.tourkakao.carping.Home.ThemeDataClass.Thisweekend;
import com.tourkakao.carping.R;
import com.tourkakao.carping.databinding.EachAzBinding;

import java.util.ArrayList;

public class Az_Adapter extends RecyclerView.Adapter {
    Context context;
    EachAzBinding eachAzBinding;
    ArrayList<AZPost> azposts;
    public Az_Adapter(Context context, ArrayList<AZPost> azposts){
        this.context=context;
        this.azposts=azposts;
    }
    public interface OnSelectItemClickListener{
        void OnSelectItemClick(View v, int pos, int pk);
    }
    private OnSelectItemClickListener mListener=null;

    public void setOnSelectItemCLickListener(OnSelectItemClickListener listener){
        this.mListener=listener;
    }


    public class Az_ViewHolder extends RecyclerView.ViewHolder{
        private EachAzBinding binding;
        public Az_ViewHolder(EachAzBinding binding){
            super(binding.getRoot());
            this.binding=binding;

            binding.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos=getAdapterPosition();
                    int pk=(int)Double.parseDouble(azposts.get(pos).getId());
                    if(pos!=RecyclerView.NO_POSITION){
                        if(mListener!=null){
                            mListener.OnSelectItemClick(v, pos, pk);
                        }
                    }
                }
            });
        }
        public void setItem(AZPost post){
            Glide.with(context).load(post.getUser_profile())
                    .transform(new CenterCrop(), new RoundedCorners(100))
                    .into(binding.azProfileImg);
            Glide.with(context).load(post.getThumbnail()).into(binding.azBackgroundImg);
            if(post.getPay_type().equals("0.0")){
                Glide.with(context).load(R.drawable.free_mark).into(binding.premiumImage);
            }
            if(post.getPay_type().equals("1.0")){
                Glide.with(context).load(R.drawable.premium_mark).into(binding.premiumImage);
            }
            binding.azBackgroundImg.setColorFilter(Color.parseColor("#3E3E3E"), PorterDuff.Mode.MULTIPLY);
            binding.azTitleText.setText(post.getTitle());
            binding.star.setText("★ "+post.getTotal_star_avg());
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        eachAzBinding=EachAzBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new Az_ViewHolder(eachAzBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Az_ViewHolder vh=(Az_ViewHolder)holder;
        vh.setItem(azposts.get(position));
    }

    @Override
    public int getItemCount() {
        return azposts==null?0:azposts.size();
    }

    public void update_Item(ArrayList<AZPost> items){
        if(azposts!=null){
            azposts=null;
        }
        azposts=items;
        notifyDataSetChanged();
    }
}
