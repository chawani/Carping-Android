package com.tourkakao.carping.MainSearch.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tourkakao.carping.MainSearch.DataClass.MainSearch;
import com.tourkakao.carping.Map.Adapter.MapSearchTourAdapter;
import com.tourkakao.carping.R;
import com.tourkakao.carping.databinding.EachMapItemBinding;

import java.util.ArrayList;

public class MainSearchAdapter extends RecyclerView.Adapter {
    Context context;
    EachMapItemBinding eachbinding;
    ArrayList<MainSearch> searches;
    public MainSearchAdapter(Context context, ArrayList<MainSearch> searches){
        this.context=context;
        this.searches=searches;
    }
    public interface OnSelectItemClickListener{
        void OnSelectItemClick(View v, int pos, int pk);
    }
    private OnSelectItemClickListener mListener=null;
    public void setOnSelectItemCLickListener(OnSelectItemClickListener listener){
        this.mListener=listener;
    }
    public class MainSearchAdapter_ViewHolder extends RecyclerView.ViewHolder{
        private EachMapItemBinding binding;
        public MainSearchAdapter_ViewHolder(EachMapItemBinding binding){
            super(binding.getRoot());
            this.binding=binding;
            binding.getRoot().setOnClickListener(v -> {
                int pos=getAdapterPosition();
                if(pos!=RecyclerView.NO_POSITION){
                    if(mListener!=null){
                        mListener.OnSelectItemClick(v, pos, searches.get(pos).getPk());
                    }
                }
            });
        }
        public void setItem(MainSearch search){
            Glide.with(context).load(R.drawable.locate_img).into(binding.locateImg);
            binding.name.setText(search.getName());
            binding.address.setText(search.getAddress());
            binding.distance.setText(search.getDistance() + "km");
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        eachbinding=EachMapItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MainSearchAdapter_ViewHolder(eachbinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MainSearchAdapter_ViewHolder vh=(MainSearchAdapter_ViewHolder)holder;
        vh.setItem(searches.get(position));
    }

    @Override
    public int getItemCount() {
        return searches==null?0:searches.size();
    }
    public void updateItem(ArrayList<MainSearch> items){
        if(searches!=null){
            searches=null;
        }
        searches=items;
        notifyDataSetChanged();
    }
}
