package com.tourkakao.carping.Home.ShareFragmentAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.tourkakao.carping.Home.ShareDataClass.Share;
import com.tourkakao.carping.R;
import com.tourkakao.carping.databinding.EachShareBinding;

import java.util.ArrayList;

public class ShareAdapter extends RecyclerView.Adapter {
    Context context;
    EachShareBinding eachShareBinding;
    ArrayList<Share> shares;
    public ShareAdapter(Context context, ArrayList<Share> shares){
        this.context=context;
        this.shares=shares;
    }
    public interface OnSelectItemClickListener{
        void OnSelectItemClick(View v, int pos, int pk);
    }
    private OnSelectItemClickListener mListener=null;
    public void setOnSelectItemClickListener(OnSelectItemClickListener listener){
        this.mListener=listener;
    }
    public class Share_ViewHolder extends RecyclerView.ViewHolder{
        private EachShareBinding binding;
        public Share_ViewHolder(EachShareBinding binding){
            super(binding.getRoot());
            this.binding=binding;
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos=getAdapterPosition();
                    int pk=shares.get(pos).getPk();
                    if(pos!=RecyclerView.NO_POSITION){
                        if(mListener!=null){
                            mListener.OnSelectItemClick(v, pos, pk);
                        }
                    }
                }
            });
        }
        public void setItem(Share share){
            Glide.with(context).load(share.getImage())
                    .transform(new CenterCrop(), new RoundedCorners(50))
                    .into(binding.image);
            Glide.with(context).load(R.drawable.heart).into(binding.heart);
            if(share.getName().length()<=16) {
                binding.name.setText(share.getName());
            }else{
                binding.name.setText(share.getName().substring(0, 17)+"..");
            }
            if(share.getBody().length()<=20) {
                binding.body.setText(share.getBody());
            }else{
                binding.body.setText(share.getBody().substring(0, 21)+"..");
            }
            if(share.isIs_shared()==true){
                binding.shareComplete.setVisibility(View.VISIBLE);
            }else{
                binding.shareComplete.setVisibility(View.GONE);
            }
            binding.locate.setText(share.getLocate());
            binding.time.setText(share.getTime());
            binding.heartCount.setText(share.getHeart()+"");
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        eachShareBinding=EachShareBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new Share_ViewHolder(eachShareBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Share_ViewHolder vh=(Share_ViewHolder)holder;
        vh.setItem(shares.get(position));
    }

    @Override
    public int getItemCount() {
        return shares==null?0:shares.size();
    }
    public void update_Item(ArrayList<Share> items){
        if(shares!=null){
            shares=null;
        }
        shares=items;
        notifyDataSetChanged();
    }
}
