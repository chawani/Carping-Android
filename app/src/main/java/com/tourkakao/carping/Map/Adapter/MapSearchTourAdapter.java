package com.tourkakao.carping.Map.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tourkakao.carping.Map.DataClass.MapSearchTour;
import com.tourkakao.carping.R;
import com.tourkakao.carping.databinding.EachMapItemBinding;

import java.util.ArrayList;

public class MapSearchTourAdapter extends RecyclerView.Adapter {
    Context context;
    EachMapItemBinding eachMapItemBinding;
    ArrayList<MapSearchTour> searches;
    public MapSearchTourAdapter(Context context, ArrayList<MapSearchTour> searches){
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
    public class MapSearchTourAdapter_ViewHolder extends RecyclerView.ViewHolder{
        private EachMapItemBinding binding;
        public MapSearchTourAdapter_ViewHolder(EachMapItemBinding binding){
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
        public void setItem(MapSearchTour tour){
            Glide.with(context).load(R.drawable.locate_img).into(binding.locateImg);
            binding.name.setText(tour.getName());
            binding.address.setText(tour.getAddress());
            binding.distance.setText(tour.getDistance() + "km");
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        eachMapItemBinding=EachMapItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MapSearchTourAdapter_ViewHolder(eachMapItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MapSearchTourAdapter_ViewHolder vh=(MapSearchTourAdapter_ViewHolder)holder;
        vh.setItem(searches.get(position));
    }

    @Override
    public int getItemCount() {
        return searches==null?0:searches.size();
    }
    public void updateItem(ArrayList<MapSearchTour> items){
        if(searches!=null){
            searches=null;
        }
        searches=items;
        notifyDataSetChanged();
    }
}
