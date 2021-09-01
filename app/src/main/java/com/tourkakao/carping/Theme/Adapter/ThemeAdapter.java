package com.tourkakao.carping.Theme.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tourkakao.carping.Theme.Dataclass.Theme;
import com.tourkakao.carping.databinding.EachThemeBinding;

import java.util.ArrayList;

public class ThemeAdapter extends RecyclerView.Adapter {
    Context context;
    EachThemeBinding themeBinding;
    ArrayList<Theme> themes;
    public ThemeAdapter(Context context, ArrayList<Theme> themes){
        this.context=context;
        this.themes=themes;
    }
    public interface OnSelectItemClickListener{
        void OnSelectItemClick(View v, int pos, int pk);
    }
    private OnSelectItemClickListener mListener=null;

    public void setOnSelectItemClickListener(OnSelectItemClickListener listener){
        this.mListener=listener;
    }
    public class Theme_ViewHolder extends RecyclerView.ViewHolder{
        private EachThemeBinding binding;
        public Theme_ViewHolder(EachThemeBinding binding){
            super(binding.getRoot());
            this.binding=binding;

            binding.getRoot().setOnClickListener(v -> {
                int pos=getAdapterPosition();
                int pk=themes.get(pos).getId();
                if(pos!=RecyclerView.NO_POSITION){
                    mListener.OnSelectItemClick(v, pos, pk);
                }
            });
        }
        public void setItem(Theme theme){
            Glide.with(context).load(theme.getImage()).into(binding.image);
            binding.address.setText(theme.getAddress());
            binding.distance.setText(theme.getDistance());
            binding.name.setText(theme.getName());
            binding.number.setText(theme.getPhone());
            String[] cat=theme.getType().split(",");
            for(int i=0; i<cat.length; i++){
                TextView textView=new TextView(context);
                textView.setText(cat[i]);
                textView.setPadding(20, 10, 20, 10);
                textView.setBackgroundColor(Color.LTGRAY);
                binding.categoryLayout.addView(textView);
            }
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        themeBinding=EachThemeBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new Theme_ViewHolder(themeBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Theme_ViewHolder vh=(Theme_ViewHolder)holder;
        vh.setItem(themes.get(position));
    }

    @Override
    public int getItemCount() {
        return themes==null?0:themes.size();
    }
    public void update_Item(ArrayList<Theme> items){
        if(themes!=null){
            themes=null;
        }
        themes=items;
        notifyDataSetChanged();
    }
}
