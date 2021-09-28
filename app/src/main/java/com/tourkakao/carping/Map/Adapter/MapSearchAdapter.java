package com.tourkakao.carping.Map.Adapter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tourkakao.carping.Map.DataClass.MapSearch;
import com.tourkakao.carping.R;
import com.tourkakao.carping.databinding.EachMapItemBinding;

import java.util.ArrayList;

public class MapSearchAdapter extends RecyclerView.Adapter {
    Context context;
    EachMapItemBinding eachMapItemBinding;
    ArrayList<MapSearch.Place> searches;
    public MapSearchAdapter(Context context, ArrayList<MapSearch.Place> searches){
        this.context=context;
        this.searches=searches;
    }

    public interface OnSelectItemClickListener{
        void OnSelectItemClick(View v, int pos);
    }
    private OnSelectItemClickListener mListener=null;
    public void setOnSelectItemCLickListener(OnSelectItemClickListener listener){
        this.mListener=listener;
    }
    public class MapSearch_ViewHolder extends RecyclerView.ViewHolder{
        private EachMapItemBinding binding;
        public MapSearch_ViewHolder(EachMapItemBinding binding){
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
        public void setItem(MapSearch.Place search){
            Glide.with(context).load(R.drawable.locate_img).into(binding.locateImg);
            binding.name.setText(search.getPlace_name());
            binding.address.setText(search.getAddress_name());
            float d=Float.parseFloat(search.getDistance());
            d=d/1000;
            binding.distance.setText(d + "km");
        }

    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        eachMapItemBinding=EachMapItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MapSearch_ViewHolder(eachMapItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MapSearch_ViewHolder vh=(MapSearch_ViewHolder)holder;
        vh.setItem(searches.get(position));
    }

    @Override
    public int getItemCount() {
        return searches==null?0:searches.size();
    }


}
