package com.tourkakao.carping.Post.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.tourkakao.carping.Post.DTO.PostListItem;
import com.tourkakao.carping.Post.PostInfoActivity;
import com.tourkakao.carping.R;
import com.tourkakao.carping.databinding.PostCategoryItemBinding;
import com.tourkakao.carping.databinding.PostTotalItemBinding;

import java.util.ArrayList;

public class PostCategoryAdapter extends RecyclerView.Adapter<PostCategoryAdapter.ViewHolder>{
    private Context context;
    private ArrayList<PostListItem> items;

    public interface OnLikeItemClickListener {
        void onItemClick(View v, int position) ;
    }
    private PostCategoryAdapter.OnLikeItemClickListener lListener = null ;
    public void setOnItemClickListener(PostCategoryAdapter.OnLikeItemClickListener listener) {
        this.lListener = listener ;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private PostCategoryItemBinding binding;
        public ViewHolder(PostCategoryItemBinding binding){
            super(binding.getRoot());
            this.binding=binding;
            binding.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(context, PostInfoActivity.class);
                    int pk=Integer.parseInt(binding.pk.getText().toString());
                    intent.putExtra("pk",pk);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
        }
        public void bind(PostListItem item){
            Glide.with(context).load(item.getThumbnail())
                    .transform(new CenterCrop(), new RoundedCorners(30))
                    .into(binding.image);
            if (item.isIs_liked()) {
                Glide.with(context).load(R.drawable.is_pushed_like).into(binding.like);
            }else{
                Glide.with(context).load(R.drawable.like_mark_white).into(binding.like);
            }
            if(item.getPay_type()==0) {
                binding.point.setText("");
                Glide.with(context).load(R.drawable.free_mark).into(binding.premiumImage);
            }else{
                binding.point.setText(Integer.toString(item.getPoint())+"원");
                Glide.with(context).load(R.drawable.premium_mark).into(binding.premiumImage);
            }
            binding.title.setText(item.getTitle());
            binding.star.setText("★ "+item.getTotal_star_avg());
            binding.name.setText(item.getAuthor());
            binding.pk.setText(Integer.toString(item.getId()));
            binding.image.setColorFilter(Color.parseColor("#75000000"));

            binding.like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        if (lListener != null) {
                            lListener.onItemClick(view, position);
                            if (item.isIs_liked()) {
                                item.setIs_liked(false);
                            }else{
                                item.setIs_liked(true);
                            }
                        }
                    }
                }
            });
        }
    }

    public PostCategoryAdapter(Context context,ArrayList<PostListItem> items){
        this.context=context;
        this.items=items;
    }

    @NonNull
    @Override
    public PostCategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PostCategoryAdapter.ViewHolder(PostCategoryItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PostCategoryAdapter.ViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items==null?0:items.size();
    }

    public boolean getLike(int position) {return items.get(position).isIs_liked();}
    public int getId(int position) {
        return items.get(position).getId();
    }
}


//public class PostCategoryAdapter extends BaseAdapter {
//    private ArrayList<PostListItem> items;
//    private Context context;
//
//    public interface OnLikeItemClickListener {
//        void onItemClick(View v, int position) ;
//    }
//    private PostCategoryAdapter.OnLikeItemClickListener lListener = null ;
//    public void setOnItemClickListener(PostCategoryAdapter.OnLikeItemClickListener listener) {
//        this.lListener = listener ;
//    }
//
//    public PostCategoryAdapter(Context context,ArrayList<PostListItem> items){
//        this.context=context;
//        this.items=items;
//    }
//
//    @Override
//    public int getCount() {
//        return items.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return items.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        PostCategoryItemBinding binding;
//        if(convertView == null) {
//            binding = PostCategoryItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
//            convertView = binding.getRoot();
//        }
//        else {
//            binding = (PostCategoryItemBinding) convertView.getTag();
//        }
//
//        PostListItem item=(PostListItem)getItem(position);
//        Glide.with(context).load(item.getThumbnail())
//                .transform(new CenterCrop(), new RoundedCorners(30))
//                .into(binding.image);
//        if (item.isIs_liked()) {
//            Glide.with(context).load(R.drawable.is_pushed_like).into(binding.like);
//        }else{
//            Glide.with(context).load(R.drawable.like_mark_white).into(binding.like);
//        }
//        if(item.getPay_type()==0) {
//            Glide.with(context).load(R.drawable.free_mark).into(binding.premiumImage);
//            binding.point.setVisibility(View.GONE);
//        }else{
//            binding.point.setText(Integer.toString(item.getPoint())+"원");
//            Glide.with(context).load(R.drawable.premium_mark).into(binding.premiumImage);
//        }
//        binding.title.setText(item.getTitle());
//        binding.star.setText("★ "+item.getTotal_star_avg());
//        binding.name.setText(item.getAuthor());
//        binding.pk.setText(Integer.toString(item.getId()));
//        binding.image.setColorFilter(Color.parseColor("#75000000"));
//
//        binding.like.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (position != RecyclerView.NO_POSITION) {
//                    if (lListener != null) {
//                        lListener.onItemClick(view, position);
//                        if (item.isIs_liked()) {
//                            item.setIs_liked(false);
//                        }else{
//                            item.setIs_liked(true);
//                        }
//                    }
//                }
//            }
//        });
//
//        convertView.setTag(binding);
//        return convertView;
//    }
//
//    public boolean getLike(int position) {return items.get(position).isIs_liked();}
//    public int getId(int position) {
//        return items.get(position).getId();
//    }
//}