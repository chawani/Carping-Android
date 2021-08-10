package com.tourkakao.carping.Home.ThemeFragmentAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PopularCarpingPlace_Adapter extends RecyclerView.Adapter {
    Context context;
    public PopularCarpingPlace_Adapter(Context context){
        this.context=context;
    }

    public class PopularCarpingPlace_ViewHolder extends RecyclerView.ViewHolder{

        public PopularCarpingPlace_ViewHolder(@NonNull View itemView) {
            super(itemView);

        }
        public void setItem(){}
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v=inflater.inflate(null, parent, false);
        return new PopularCarpingPlace_ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        PopularCarpingPlace_ViewHolder vh=(PopularCarpingPlace_ViewHolder)holder;
        vh.setItem();
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
