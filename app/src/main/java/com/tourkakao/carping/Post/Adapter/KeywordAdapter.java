package com.tourkakao.carping.Post.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tourkakao.carping.Post.DTO.Review;
import com.tourkakao.carping.R;
import com.tourkakao.carping.databinding.ActivityPostSearchBinding;
import com.tourkakao.carping.databinding.PostReviewItemBinding;
import com.tourkakao.carping.databinding.PostSearchKeywordItemBinding;

import java.util.ArrayList;

public class KeywordAdapter extends RecyclerView.Adapter<KeywordAdapter.ViewHolder>{
    private Context context;
    private ArrayList<String> items;
    private String type="";
    private ActivityPostSearchBinding parentBinding;

    public interface OnItemClickListener {
        void onItemClick(View v, int position) ;
    }
    private KeywordAdapter.OnItemClickListener listener = null ;
    public void setOnItemClickListener(KeywordAdapter.OnItemClickListener listener) {
        this.listener = listener ;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private PostSearchKeywordItemBinding binding;
        public ViewHolder(PostSearchKeywordItemBinding binding){
            super(binding.getRoot());
            this.binding=binding;
        }
        public void bind(String item){
            if(type.equals("recent")) {
                Glide.with(context).load(R.drawable.cancel_img).into(binding.delete);
            }else{
                binding.delete.setVisibility(View.GONE);
            }
            binding.keyword.setText(item);
            binding.keyword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    parentBinding.searchText.setText(binding.keyword.getText());
                }
            });
            binding.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition() ;
                    if (pos != RecyclerView.NO_POSITION) {
                        if (listener != null) {
                            listener.onItemClick(view, pos);
                        }
                    }
                }
            });
        }
    }

    public KeywordAdapter(Context context,ActivityPostSearchBinding parentBinding,ArrayList<String> items,String type){
        this.context=context;
        this.parentBinding=parentBinding;
        this.items=items;
        this.type=type;
    }

    @NonNull
    @Override
    public KeywordAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new KeywordAdapter.ViewHolder(PostSearchKeywordItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull KeywordAdapter.ViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items==null?0:items.size();
    }

    public String getItem(int pos){
        return  items.get(pos);
    }
}