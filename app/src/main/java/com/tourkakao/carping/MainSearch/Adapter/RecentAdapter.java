package com.tourkakao.carping.MainSearch.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tourkakao.carping.NetworkwithToken.TotalApiClient;
import com.tourkakao.carping.databinding.EachRecentSearchBinding;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RecentAdapter extends RecyclerView.Adapter {
    Context context;
    ArrayList<String> recents;
    EachRecentSearchBinding eachRecentSearchBinding;
    public RecentAdapter(Context context, ArrayList<String> recents){
        this.context=context;
        this.recents=recents;
    }
    public interface OnSelectItemClickListener{
        void OnSelectItemClick(View v, int pos, String word);
    }
    private OnSelectItemClickListener mListener=null;
    public void setOnSelectItemCLickListener(OnSelectItemClickListener listener){
        this.mListener=listener;
    }
    public class Recent_ViewHodler extends RecyclerView.ViewHolder{
        private EachRecentSearchBinding binding;
        public Recent_ViewHodler(EachRecentSearchBinding binding){
            super(binding.getRoot());
            this.binding=binding;
            binding.getRoot().setOnClickListener(v-> {
                int pos=getAdapterPosition();
                if(pos!=RecyclerView.NO_POSITION){
                    if(mListener!=null){
                        mListener.OnSelectItemClick(v, pos, binding.recentText.getText().toString());
                    }
                }
            });
        }
        public void setItem(String str, int pos){
            binding.recentText.setText(str);
            binding.delete.setOnClickListener(v -> {
                String keyword=recents.get(pos);
                recents.remove(pos);
                update_with_delete(recents, keyword);
            });
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        eachRecentSearchBinding=EachRecentSearchBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new Recent_ViewHodler(eachRecentSearchBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Recent_ViewHodler vh=(Recent_ViewHodler)holder;
        vh.setItem(recents.get(position), position);
    }

    @Override
    public int getItemCount() {
        return recents==null?0:recents.size();
    }

    public void updateItem(ArrayList<String> items){
        if(recents!=null){
            recents=null;
        }
        recents=items;
        notifyDataSetChanged();
    }
    public void update_with_delete(ArrayList<String> items, String keyword){
        if(recents!=null){
            recents=null;
        }
        recents=items;
        notifyDataSetChanged();
        TotalApiClient.getApiService(context).delete_search_keyword(keyword, "main")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }
}
