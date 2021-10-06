package com.tourkakao.carping.theme.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tourkakao.carping.Map.Adapter.MapSearchAdapter;
import com.tourkakao.carping.R;
import com.tourkakao.carping.databinding.EachThemeRecommendItemBinding;
import com.tourkakao.carping.theme.Dataclass.TourSearch;

import java.util.ArrayList;

public class RecommendAdapter extends RecyclerView.Adapter {
    Context context;
    EachThemeRecommendItemBinding itemBinding;
    ArrayList<TourSearch.Place> searches;
    public RecommendAdapter(Context context, ArrayList<TourSearch.Place> searches){
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
    public class Recommend_ViewHolder extends RecyclerView.ViewHolder{
        private EachThemeRecommendItemBinding binding;
        public Recommend_ViewHolder(EachThemeRecommendItemBinding binding){
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
        public void setItem(TourSearch.Place search){
            Glide.with(context).load(R.drawable.locate_img).into(binding.locateImg);
            binding.name.setText(search.getPlace_name());
            binding.address.setText(search.getAddress_name());
            float d=Float.parseFloat(search.getDistance());
            d=d/1000;
            binding.distance.setText(d+"km");
            if(search.getPhone().length()!=0) {
                binding.phone.setVisibility(View.VISIBLE);
                binding.phone.setText(search.getPhone());
            }else{
                binding.phone.setVisibility(View.GONE);
            }
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        itemBinding=EachThemeRecommendItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new Recommend_ViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Recommend_ViewHolder vh=(Recommend_ViewHolder)holder;
        vh.setItem(searches.get(position));
    }

    @Override
    public int getItemCount() {
        return searches==null?0:searches.size();
    }
    public void updateItem(ArrayList<TourSearch.Place> items){
        if(searches!=null){
            searches=null;
        }
        searches=items;
        notifyDataSetChanged();
    }
}
