package com.tourkakao.carping.Map.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tourkakao.carping.Map.DataClass.MapSearchFromApi;
import com.tourkakao.carping.R;
import com.tourkakao.carping.databinding.EachMapItemBinding;

import java.util.ArrayList;

public class MapSearchFromApiAdapter extends RecyclerView.Adapter {
    Context context;
    EachMapItemBinding eachMapItemBinding;
    ArrayList<MapSearchFromApi> searches;
    public MapSearchFromApiAdapter(Context context, ArrayList<MapSearchFromApi> searches){
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
    public class MapSearchFromApi_ViewHolder extends RecyclerView.ViewHolder{
        private EachMapItemBinding binding;
        public MapSearchFromApi_ViewHolder(EachMapItemBinding binding){
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
        public void setItem(MapSearchFromApi search){
            Glide.with(context).load(R.drawable.locate_img).into(binding.locateImg);
            binding.name.setText(search.getTitle());
            binding.address.setText(search.getAddress());
            binding.distance.setText(search.getDistance() + "km");
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        eachMapItemBinding=EachMapItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MapSearchFromApi_ViewHolder(eachMapItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MapSearchFromApi_ViewHolder vh=(MapSearchFromApi_ViewHolder)holder;
        vh.setItem(searches.get(position));
    }

    @Override
    public int getItemCount() {
        return searches==null?0:searches.size();
    }
    public void updateItem(ArrayList<MapSearchFromApi> items){
        if(searches!=null){
            searches=null;
        }
        searches=items;
        notifyDataSetChanged();
    }
}
