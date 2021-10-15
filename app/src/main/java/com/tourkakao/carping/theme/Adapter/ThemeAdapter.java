package com.tourkakao.carping.theme.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.tourkakao.carping.NetworkwithToken.TotalApiClient;
import com.tourkakao.carping.R;
import com.tourkakao.carping.theme.Dataclass.Theme;
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
        public void setItem(Theme theme, int pos){
            if(theme.getImage()!=null) {
                Glide.with(context).load(theme.getImage()).transform(new RoundedCorners(10)).into(binding.image);
            }else{
                Glide.with(context).load(R.drawable.thema_no_img).into(binding.image);
            }
            Glide.with(context).load(R.drawable.locate_img).into(binding.locateImg);
            if(theme.isCheck_bookmark()){
                Glide.with(context).load(R.drawable.mybookmark_img).into(binding.bookmark);
            }else {
                Glide.with(context).load(R.drawable.bookmark_img).into(binding.bookmark);
            }
            binding.bookmark.setOnClickListener(v -> {
                if(theme.isCheck_bookmark()){
                    TotalApiClient.getApiService(context).release_theme_bookmark(theme.getId()).subscribe();
                    Glide.with(context).load(R.drawable.bookmark_img).into(binding.bookmark);
                    themes.get(pos).setCheck_bookmark(false);
                }else{
                    TotalApiClient.getApiService(context).set_theme_bookmark(theme.getId()).subscribe();
                    Glide.with(context).load(R.drawable.mybookmark_img).into(binding.bookmark);
                    themes.get(pos).setCheck_bookmark(true);
                }
            });
            binding.address.setText(theme.getAddress());
            binding.distance.setText(theme.getDistance()+"km");
            binding.name.setText(theme.getName());
            if(theme.getPhone()!=null) {
                binding.number.setText(theme.getPhone());
            }else{
                binding.number.setText("번호없음");
            }
            String[] cat=theme.getType().split(",");
            binding.categoryLayout.removeAllViews();
            for(int i=0; i<cat.length; i++){
                TextView textView=new TextView(context);
                textView.setText(cat[i]);
                textView.setTextSize(12);
                textView.setPadding(15, 5, 15, 5);
                textView.setBackgroundResource(R.drawable.border_round_tag);
                textView.setTextColor(Color.parseColor("#a1a1a1"));
                LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.rightMargin=15;
                textView.setLayoutParams(params);
                binding.categoryLayout.addView(textView);
            }
            binding.scrab.setText("스크랩 "+theme.getScrap_cnt());
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        themeBinding=EachThemeBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new Theme_ViewHolder(themeBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Theme_ViewHolder vh=(Theme_ViewHolder)holder;
        vh.setItem(themes.get(position), position);
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
