package com.tourkakao.carping.registernewshare.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tourkakao.carping.databinding.EachLocateBinding;
import com.tourkakao.carping.theme.Adapter.BlogAdapter;

import java.util.ArrayList;

public class LocateAdapter extends RecyclerView.Adapter {
    Context context;
    ArrayList<String> locates;
    EachLocateBinding locateBinding;
    public LocateAdapter(Context context, ArrayList<String> locates){
        this.context=context;
        this.locates=locates;
    }
    public interface OnSelectItemClickListener{
        void OnSelectItemClick(View v, int pos);
    }
    private OnSelectItemClickListener mListener=null;
    public void setOnSelectItemClickListener(OnSelectItemClickListener listener){
        this.mListener=listener;
    }
    public class Locate_ViewHoler extends RecyclerView.ViewHolder{
        private EachLocateBinding binding;
        public Locate_ViewHoler(EachLocateBinding binding){
            super(binding.getRoot());
            this.binding=binding;
            binding.getRoot().setOnClickListener(v -> {
                int pos=getAdapterPosition();
                if(pos!=RecyclerView.NO_POSITION){
                    mListener.OnSelectItemClick(v, pos);
                }
            });
        }
        public void setItem(String locate){
            binding.eachLocate.setText(locate);
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        locateBinding=EachLocateBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new Locate_ViewHoler(locateBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Locate_ViewHoler vh=(Locate_ViewHoler)holder;
        vh.setItem(locates.get(position));
    }

    @Override
    public int getItemCount() {
        return locates==null?0:locates.size();
    }
    public void updateItem(ArrayList<String> items){
        if(locates!=null){
            locates=null;
        }
        locates=items;
        notifyDataSetChanged();
    }
}
