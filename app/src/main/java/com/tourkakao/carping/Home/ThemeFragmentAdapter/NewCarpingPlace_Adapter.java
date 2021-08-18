package com.tourkakao.carping.Home.ThemeFragmentAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tourkakao.carping.Home.ThemeDataClass.NewCapringPlace;
import com.tourkakao.carping.databinding.EachNewcarpingPlaceBinding;

import java.util.ArrayList;

public class NewCarpingPlace_Adapter extends RecyclerView.Adapter {
    Context context;
    EachNewcarpingPlaceBinding eachNewcarpingPlaceBinding;
    ArrayList<NewCapringPlace> places;

    public NewCarpingPlace_Adapter(Context context, ArrayList<NewCapringPlace> places){
        this.context=context;
        this.places=places;
    }
    public interface OnSelectItemClickListener{
        void OnSelectItemClick(View v, int pos, int pk);
    }
    private OnSelectItemClickListener mListener=null;

    public void setOnSelectItemCLickListener(OnSelectItemClickListener listener){
        this.mListener=listener;
    }
    public class NewCarpingPlace_ViewHolder extends RecyclerView.ViewHolder{
        private EachNewcarpingPlaceBinding binding;
        public NewCarpingPlace_ViewHolder(EachNewcarpingPlaceBinding binding) {
            super(binding.getRoot());
            this.binding=binding;

            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos=getAdapterPosition();
                    int pk=places.get(pos).getPk();
                    if(pos!=RecyclerView.NO_POSITION){
                        if(mListener!=null){
                            mListener.OnSelectItemClick(v, pos, pk);
                        }
                    }
                }
            });
        }
        public void setItem(NewCapringPlace place){
            Glide.with(context).load(place.getImage()).into(binding.newcarpingPlaceImg);
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        eachNewcarpingPlaceBinding=EachNewcarpingPlaceBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new NewCarpingPlace_ViewHolder(eachNewcarpingPlaceBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        NewCarpingPlace_ViewHolder vh=(NewCarpingPlace_ViewHolder)holder;
        vh.setItem(places.get(position));
    }

    @Override
    public int getItemCount() {
        return places==null?0:places.size();
    }

    public void update_Item(ArrayList<NewCapringPlace> places){
        if(this.places!=null){
            this.places=null;
        }
        this.places=places;
        notifyDataSetChanged();
    }
}
