package com.tourkakao.carping.Home.ThemeFragmentAdapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.tourkakao.carping.Home.ThemeDataClass.Popular;
import com.tourkakao.carping.NetworkwithToken.TotalApiClient;
import com.tourkakao.carping.R;
import com.tourkakao.carping.databinding.EachPopularBinding;

import java.util.ArrayList;

public class PopularCarpingPlace_Adapter extends RecyclerView.Adapter {
    Context context;
    ArrayList<Popular> populars;
    EachPopularBinding eachPopularBinding;
    public PopularCarpingPlace_Adapter(Context context, ArrayList<Popular> populars){
        this.context=context;
        this.populars=populars;
    }
    public interface OnSelectItemClickListener{
        void OnSelectItemClick(View v, int pos, int pk);
    }
    private OnSelectItemClickListener mListener=null;

    public void setOnSelectItemCLickListener(OnSelectItemClickListener listener){
        this.mListener=listener;
    }
    public class PopularCarpingPlace_ViewHolder extends RecyclerView.ViewHolder{
        EachPopularBinding binding;
        public PopularCarpingPlace_ViewHolder(EachPopularBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos=getAdapterPosition();
                    int pk=populars.get(pos).getId();
                    if(pos!=RecyclerView.NO_POSITION){
                        if(mListener!=null){
                            mListener.OnSelectItemClick(v, pos, pk);
                        }
                    }
                }
            });
        }
        public void setItem(Popular popular, int pos){
            if(popular.getImage()==null){
                Glide.with(context).load(R.drawable.thema_no_img).transform(new RoundedCorners(10)).into(binding.pimage);
            }else{
                Glide.with(context).load(popular.getImage()).transform(new RoundedCorners(10)).into(binding.pimage);
            }
            if(pos==0){
                Glide.with(context).load(R.drawable.first).into(binding.gradeImg);
            }else if(pos==1){
                Glide.with(context).load(R.drawable.second).into(binding.gradeImg);
            }else if(pos==2){
                Glide.with(context).load(R.drawable.third).into(binding.gradeImg);
            }
            binding.pname.setText(popular.getName());
            binding.paddress.setText(popular.getAddress());
            binding.psearchCnt.setText(popular.getSearch_count()+"");
            if(popular.isIs_bookmarked()==false){
                Glide.with(context).load(R.drawable.bookmark_img).into(binding.pbookmark);
            }else{
                Glide.with(context).load(R.drawable.mybookmark_img).into(binding.pbookmark);
            }
            String[] tags=popular.getType().split(",");
            if(binding.typeLayout.getChildAt(0)!=null){
                binding.typeLayout.removeAllViews();
            }
            for(int i=0; i<tags.length; i++){
                TextView textView=new TextView(context);
                textView.setText(tags[i]);
                textView.setTextSize(9.0f);
                textView.setTextColor(Color.parseColor("#a4a4a4"));
                textView.setPadding(5, 2, 3, 5);
                textView.setBackgroundColor(Color.parseColor("#f2f2f2"));
                binding.typeLayout.addView(textView);
            }
            if(tags.length==0){
                binding.typeLayout.setVisibility(View.GONE);
            }
            binding.pbookmark.setOnClickListener(v -> {
                if(popular.isIs_bookmarked()){
                    TotalApiClient.getApiService(context).release_theme_bookmark(popular.getId()).subscribe();
                    Glide.with(context).load(R.drawable.bookmark_img).into(binding.pbookmark);
                    populars.get(pos).setIs_bookmarked(false);
                }else{
                    TotalApiClient.getApiService(context).set_theme_bookmark(popular.getId()).subscribe();
                    Glide.with(context).load(R.drawable.mybookmark_img).into(binding.pbookmark);
                    populars.get(pos).setIs_bookmarked(true);
                }
            });
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        eachPopularBinding=EachPopularBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new PopularCarpingPlace_ViewHolder(eachPopularBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        PopularCarpingPlace_ViewHolder vh=(PopularCarpingPlace_ViewHolder)holder;
        vh.setItem(populars.get(position), position);
    }

    @Override
    public int getItemCount() {
        return populars==null?0:populars.size();
    }
    public void update_Item(ArrayList<Popular> items){
        if(this.populars!=null){
            this.populars=null;
        }
        this.populars=items;
        notifyDataSetChanged();
    }
}
