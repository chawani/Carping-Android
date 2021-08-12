package com.tourkakao.carping.Home.ThemeFragmentAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tourkakao.carping.Home.ThemeDataClass.AZPost;
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
        void OnSelectItemClick(View v, int pos);
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

            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos=getAdapterPosition();
                    if(pos!=RecyclerView.NO_POSITION){
                        if(mListener!=null){
                            mListener.OnSelectItemClick(v, pos);
                        }
                    }
                }
            });
        }
        public void setItem(AZPost post){
            Glide.with(context).load(post.getProfile()).circleCrop().into(binding.azProfileImg);
            Glide.with(context).load(post.getImage()).into(binding.azBackgroundImg);
            if(post.getIsprimeum()==0){
                binding.premiumText.setVisibility(View.GONE);
            }
            binding.azTitleText.setText(post.getTitle());
            binding.azStarnumberText.setText("("+post.getStar_number()+")");
            binding.azRatingstar.setRating(post.getStar_score());
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
}
