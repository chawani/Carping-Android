package com.tourkakao.carping.Home.ThemeFragmentAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Az_Adapter extends RecyclerView.Adapter {
    Context context;
    public Az_Adapter(Context context){
        this.context=context;
    }
    public interface OnSelectItemClickListener{
        void OnSelectItemClick(View v, int pos);
    }
    private OnSelectItemClickListener mListener=null;

    public void setOnSelectItemCLickListener(OnSelectItemClickListener listener){
        this.mListener=listener;
    }
    public class Az_ViewHolder extends RecyclerView.ViewHolder{

        public Az_ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
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
        public void setItem(){}
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v=inflater.inflate(null, parent, false);
        return new Az_ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Az_ViewHolder vh=(Az_ViewHolder)holder;
        vh.setItem();
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
