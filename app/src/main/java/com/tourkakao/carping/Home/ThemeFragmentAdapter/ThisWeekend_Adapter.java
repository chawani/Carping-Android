package com.tourkakao.carping.Home.ThemeFragmentAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tourkakao.carping.Home.ThemeDataClass.Thisweekend;
import com.tourkakao.carping.databinding.EachThisweekendBinding;

import java.util.ArrayList;

public class ThisWeekend_Adapter extends RecyclerView.Adapter {
    Context context;
    EachThisweekendBinding eachThisweekendBinding;
    ArrayList<Thisweekend> thisweekends;
    public ThisWeekend_Adapter(Context context, ArrayList<Thisweekend> thisweekends){
        this.context=context;
        this.thisweekends=thisweekends;
    }
    public interface OnSelectItemClickListener{
        void OnSelectItemClick(View v, int pos, int pk);
    }
    private OnSelectItemClickListener mListener=null;

    public void setOnSelectItemCLickListener(OnSelectItemClickListener listener){
        this.mListener=listener;
    }
    public class ThisWeekend_ViewHolder extends RecyclerView.ViewHolder{
        private EachThisweekendBinding binding;
        public ThisWeekend_ViewHolder(EachThisweekendBinding binding){
            super(binding.getRoot());
            this.binding=binding;

            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos=getAdapterPosition();
                    int pk=thisweekends.get(pos).getPk();
                    if(pos!=RecyclerView.NO_POSITION){
                        if(mListener!=null){
                            mListener.OnSelectItemClick(v, pos, pk);
                        }
                    }
                }
            });
        }
        public void setItem(Thisweekend post){
            Glide.with(context).load(post.getImage()).into(binding.thisweekendBackgroundImg);
            binding.thisweekendTitle.setText(post.getTitle());
            String tags="";
            for(int i=0; i<post.getTags().size(); i++){
                tags+="#"+post.getTags().get(i);
            }
            binding.thisweekendTags.setText(tags);
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        eachThisweekendBinding=EachThisweekendBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ThisWeekend_ViewHolder(eachThisweekendBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ThisWeekend_ViewHolder vh=(ThisWeekend_ViewHolder)holder;
        vh.setItem(thisweekends.get(position));
    }

    @Override
    public int getItemCount() {
        return thisweekends==null?0:thisweekends.size();
    }

    public void update_Item(ArrayList<Thisweekend> items){
        if(thisweekends!=null){
            thisweekends=null;
        }
        thisweekends=items;
        notifyDataSetChanged();
    }
}
