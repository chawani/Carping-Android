package com.tourkakao.carping.Post.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.tourkakao.carping.Post.DTO.PostListItem;
import com.tourkakao.carping.Post.PostDetailActivity;
import com.tourkakao.carping.Post.PremiumPostActivity;
import com.tourkakao.carping.R;
import com.tourkakao.carping.databinding.PostCategoryItemBinding;
import com.tourkakao.carping.databinding.PostTotalItemBinding;

import java.util.ArrayList;

public class PostCategoryAdapter extends BaseAdapter {
    private ArrayList<PostListItem> items;
    private Context context;

    public PostCategoryAdapter(Context context,ArrayList<PostListItem> items){
        this.context=context;
        this.items=items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PostCategoryItemBinding binding;
        if(convertView == null) {
            binding = PostCategoryItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            convertView = binding.getRoot();
        }
        else {
            binding = (PostCategoryItemBinding) convertView.getTag();
        }

        PostListItem item=(PostListItem)getItem(position);
        Glide.with(context).load(item.getThumbnail())
                .transform(new CenterCrop(), new RoundedCorners(30))
                .into(binding.image);
        Glide.with(context).load(R.drawable.like_mark).into(binding.like);
        if(item.getPoint().equals("0.0")) {
            Glide.with(context).load(R.drawable.free_mark).into(binding.premiumImage);
        }else{
            Glide.with(context).load(R.drawable.premium_mark).into(binding.premiumImage);
        }
        binding.title.setText(item.getTitle());
        binding.star.setText("★ "+item.getTotal_star_avg());
        binding.name.setText(item.getAuthor());
        binding.pk.setText(item.getId());
        if(!item.getPoint().equals("0.0")){
            int point=(int)Double.parseDouble(item.getPoint());
            binding.point.setText(Integer.toString(point)+"원");
        }

        binding.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, PremiumPostActivity.class);
                intent.putExtra("pk",binding.pk.getText().toString());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        convertView.setTag(binding);
        return convertView;
    }
}