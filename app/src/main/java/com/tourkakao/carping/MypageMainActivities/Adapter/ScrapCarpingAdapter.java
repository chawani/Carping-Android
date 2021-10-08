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
import com.tourkakao.carping.MypageMainActivities.DTO.Campsite;
import com.tourkakao.carping.R;
import com.tourkakao.carping.databinding.MypageCarpingListItemBinding;

import java.util.ArrayList;

public class ScrapCarpingAdapter extends RecyclerView.Adapter<ScrapCarpingAdapter.ViewHolder>{
    Context context;
    ArrayList<Campsite> campsites;

    public interface OnSelectItemClickListener{
        void OnSelectItemClick(View v, int pos, int pk);
    }
    private ScrapCarpingAdapter.OnSelectItemClickListener mListener=null;

    public void setOnSelectItemCLickListener(ScrapCarpingAdapter.OnSelectItemClickListener listener){
        this.mListener=listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private MypageCarpingListItemBinding binding;
        public ViewHolder(MypageCarpingListItemBinding binding){
            super(binding.getRoot());
            this.binding=binding;
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos=getAdapterPosition();
                    int pk=campsites.get(pos).getId();
                    if(pos!=RecyclerView.NO_POSITION){
                        if(mListener!=null){
                            mListener.OnSelectItemClick(v, pos, pk);
                        }
                    }
                }
            });
        }
        public void bind(Campsite campsite){
            if(campsite.getImage()==null){
                Glide.with(context)
                        .load(R.drawable.mypage_no_img)
                        .transform(new CenterCrop(), new RoundedCorners(30))
                        .into(binding.image);
            }else {
                Glide.with(context)
                        .load(campsite.getImage())
                        .transform(new CenterCrop(), new RoundedCorners(30))
                        .into(binding.image);
            }
            binding.name.setText(campsite.getName());
            binding.address.setText(campsite.getAddress());
            Glide.with(context).load(R.drawable.locate_img).into(binding.locateImg);
            binding.distance.setText(campsite.getDistance()+"km");
            binding.bookmarkCount.setText("스크랩 "+campsite.getBookmark_count());
        }
    }

    public ScrapCarpingAdapter(Context context,ArrayList<Campsite> campsites){
        this.context=context;
        this.campsites=campsites;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(MypageCarpingListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(campsites.get(position));
    }

    @Override
    public int getItemCount() {
        return campsites==null?0:campsites.size();
    }

    public String getName(int pos){
        return campsites.get(pos).getName();
    }
}
