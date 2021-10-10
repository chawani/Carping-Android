package com.tourkakao.carping.thisweekend.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tourkakao.carping.Home.ThemeDataClass.Thisweekend;
import com.tourkakao.carping.R;
import com.tourkakao.carping.databinding.EachTotalThisweekendBinding;

import java.util.ArrayList;


public class Total_ThisWeekend_Adapter extends RecyclerView.Adapter {
    Context context;
    EachTotalThisweekendBinding eachTotalThisweekendBinding;
    ArrayList<Thisweekend> thisweekends;
    public Total_ThisWeekend_Adapter(Context context, ArrayList<Thisweekend> thisweekends){
        this.context=context;
        this.thisweekends=thisweekends;
    }
    public interface OnSelectItemClickListener{
        void OnSelectItemClick(View v, int pos, int pk);
    }
    private OnSelectItemClickListener mListener=null;

    public void setOnSelectItemClickListener(OnSelectItemClickListener listener){
        this.mListener=listener;
    }
    public class Total_ThisWeekend_ViewHolder extends RecyclerView.ViewHolder{
        private EachTotalThisweekendBinding binding;
        public Total_ThisWeekend_ViewHolder(EachTotalThisweekendBinding binding){
            super(binding.getRoot());
            this.binding=binding;

            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos=getAdapterPosition();
                    int pk=thisweekends.get(pos).getPk();
                    if(pos!=RecyclerView.NO_POSITION){
                        mListener.OnSelectItemClick(v, pos, pk);
                    }
                }
            });
        }
        public void setItem(Thisweekend post){
            Glide.with(context).load(post.getImage()).into(binding.thisweekendBackgroundImg);
            binding.thisweekendTitle.setText(post.getTitle());
            String tags="";
            for(int i=0; i<post.getTags().size(); i++){
                tags+="#"+post.getTags().get(i)+" ";
            }
            Glide.with(context).load(R.drawable.group).into(binding.peopleImg);
            binding.thisweekendTags.setText(tags);
            binding.thisweekendPeople.setText(Integer.toString(post.getViews()));
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        eachTotalThisweekendBinding=EachTotalThisweekendBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new Total_ThisWeekend_ViewHolder(eachTotalThisweekendBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Total_ThisWeekend_ViewHolder vh=(Total_ThisWeekend_ViewHolder)holder;
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
