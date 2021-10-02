package com.tourkakao.carping.MypageMainActivities.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.tourkakao.carping.MypageMainActivities.DTO.Autocamp;
import com.tourkakao.carping.MypageMainActivities.DTO.MyCarpingPost;
import com.tourkakao.carping.databinding.MypageCarpingApiListItemBinding;

import java.util.ArrayList;

public class ScrapAutoAdapter extends RecyclerView.Adapter<ScrapAutoAdapter.ViewHolder>{
    Context context;
    ArrayList<MyCarpingPost> autocamps;

    public interface OnSelectItemClickListener{
        void OnSelectItemClick(View v, int pos, int pk);
    }
    private ScrapAutoAdapter.OnSelectItemClickListener mListener=null;

    public void setOnSelectItemCLickListener(ScrapAutoAdapter.OnSelectItemClickListener listener){
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
                    int pk=autocamps.get(pos).getId();
                    if(pos!=RecyclerView.NO_POSITION){
                        if(mListener!=null){
                            mListener.OnSelectItemClick(v, pos, pk);
                        }
                    }
                }
            });
        }
        public void bind(MyCarpingPost autocamp){
            Glide.with(context)
                    .load(autocamp.getImage1())
                    .transform(new CenterCrop(), new RoundedCorners(30))
                    .into(binding.image);
            binding.title.setText(autocamp.getTitle());
            binding.bookmarkCount.setText("스크랩 "+autocamp.getBookmark_count());
        }
    }

    public ScrapAutoAdapter(Context context,ArrayList<MyCarpingPost> autocamps){
        this.context=context;
        this.autocamps=autocamps;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ScrapAutoAdapter.ViewHolder(MypageCarpingApiListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(autocamps.get(position));
    }

    @Override
    public int getItemCount() {
        return autocamps==null?0:autocamps.size();
    }
}
