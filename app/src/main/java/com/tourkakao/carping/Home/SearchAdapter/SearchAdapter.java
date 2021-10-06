package com.tourkakao.carping.Home.SearchAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tourkakao.carping.Home.ThemeDataClass.Search;
import com.tourkakao.carping.R;
import com.tourkakao.carping.databinding.EachPopularItemBinding;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter {
    Context context;
    ArrayList<Search> searches;
    EachPopularItemBinding popularItemBinding;
    public SearchAdapter(Context context, ArrayList<Search> searches){
        this.context=context;
        this.searches=searches;
    }
    public class SearchViewHolder extends RecyclerView.ViewHolder{
        private EachPopularItemBinding binding;
        public SearchViewHolder(EachPopularItemBinding binding){
            super(binding.getRoot());
            this.binding=binding;
        }
        public void setItem(Search search, int pos){
            System.out.println(search.getName());
            binding.name.setText(search.getName());
            if(!search.getName().equals("인기 검색어가 없어요")) {
                if (pos == 0) {
                    Glide.with(context).load(R.drawable.first).into(binding.gradeImg);
                }else if(pos==1){
                    Glide.with(context).load(R.drawable.second).into(binding.gradeImg);
                }else if(pos==2){
                    Glide.with(context).load(R.drawable.third).into(binding.gradeImg);
                }else if(pos==3){
                    Glide.with(context).load(R.drawable.fourth).into(binding.gradeImg);
                }else if(pos==4){
                    Glide.with(context).load(R.drawable.fifth).into(binding.gradeImg);
                }
            }
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        popularItemBinding=EachPopularItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new SearchViewHolder(popularItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        SearchViewHolder vh=(SearchViewHolder)holder;
        vh.setItem(searches.get(position), position);
    }

    @Override
    public int getItemCount() {
        return searches==null?0:searches.size();
    }

    public void updateItem(ArrayList<Search> items){
        if(searches!=null){
            searches=null;
        }
        searches=items;
        notifyDataSetChanged();
    }
}
