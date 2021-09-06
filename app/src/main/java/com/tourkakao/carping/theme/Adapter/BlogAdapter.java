package com.tourkakao.carping.theme.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.tourkakao.carping.R;
import com.tourkakao.carping.databinding.EachThemedetailDaumBinding;
import com.tourkakao.carping.theme.Dataclass.DaumBlog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class BlogAdapter extends RecyclerView.Adapter {
    Context context;
    EachThemedetailDaumBinding daumBinding;
    ArrayList<DaumBlog.Blog> blogs;
    public BlogAdapter(Context context, ArrayList<DaumBlog.Blog> blogs){
        this.context=context;
        this.blogs=blogs;
    }
    public interface OnSelectItemClickListener{
        void OnSelectItemClick(View v, int pos);
    }
    private OnSelectItemClickListener mListener=null;
    public void setOnSelectItemClickListener(OnSelectItemClickListener listener){
        this.mListener=listener;
    }
    public class Blog_ViewHolder extends RecyclerView.ViewHolder{
        private EachThemedetailDaumBinding binding;
        public Blog_ViewHolder(EachThemedetailDaumBinding binding){
            super(binding.getRoot());
            this.binding=binding;

            binding.getRoot().setOnClickListener(v -> {
                int pos=getAdapterPosition();
                if(pos!=RecyclerView.NO_POSITION){
                    mListener.OnSelectItemClick(v, pos);
                }
            });
        }
        public void setItem(DaumBlog.Blog blog){
            if(blog.getThumbnail()!=null && blog.getThumbnail()!="" && !blog.getThumbnail().isEmpty()){
                Glide.with(context).load(blog.getThumbnail()).transform(new RoundedCorners(10)).into(binding.blogThumbnail);
            }else{
                Glide.with(context).load(R.drawable.thema_no_img).into(binding.blogThumbnail);
            }
            blog.setTitle(blog.getTitle().replaceAll("<b>", ""));
            blog.setTitle(blog.getTitle().replaceAll("</b>", ""));
            blog.setContents(blog.getContents().replaceAll("<b>", ""));
            blog.setContents(blog.getContents().replaceAll("</b>", ""));
            int idx=blog.getDatetime().indexOf("T");
            if(idx!=-1){
                blog.setDatetime(blog.getDatetime().substring(0, idx));
            }
            binding.date.setText(blog.getDatetime());
            binding.title.setText(blog.getTitle());
            if(blog.getContents().length()<20){
                binding.content.setText(blog.getContents());
            }else{
                String text=blog.getContents().substring(0, 20);
                text+="...";
                binding.content.setText(text);
            }
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        daumBinding=EachThemedetailDaumBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new Blog_ViewHolder(daumBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Blog_ViewHolder vh=(Blog_ViewHolder)holder;
        vh.setItem(blogs.get(position));
    }

    @Override
    public int getItemCount() {
        return blogs==null?0:blogs.size();
    }
}
