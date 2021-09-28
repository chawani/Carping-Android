package com.tourkakao.carping.sharedetail.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tourkakao.carping.databinding.CommentWithDoubleItemBinding;
import com.tourkakao.carping.sharedetail.DataClass.Comment;

import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter {
    private Context context;
    ArrayList<Comment> comments;
    CommentWithDoubleItemBinding commentWithDoubleItemBinding;
    public CommentAdapter(Context context, ArrayList<Comment> comments){
        this.context=context;
        this.comments=comments;
    }
    public class CommentViewHolder extends RecyclerView.ViewHolder{
        CommentWithDoubleItemBinding binding;
        public CommentViewHolder(CommentWithDoubleItemBinding binding){
            super(binding.getRoot());
            this.binding=binding;
        }
        public void setItem(Comment comment){
            binding.content.setText(comment.getText());
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        commentWithDoubleItemBinding=CommentWithDoubleItemBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new CommentViewHolder(commentWithDoubleItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        CommentViewHolder vh=(CommentViewHolder)holder;
        vh.setItem(comments.get(position));
    }

    @Override
    public int getItemCount() {
        return comments==null?0:comments.size();
    }
}
