package com.tourkakao.carping.sharedetail.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tourkakao.carping.databinding.CommentWithDoubleItemBinding;
import com.tourkakao.carping.sharedetail.DataClass.Comment;

import java.util.ArrayList;

public class ReCommentAdapter extends RecyclerView.Adapter {
    private Context context;
    CommentWithDoubleItemBinding commentWithDoubleItemBinding;
    ArrayList<Comment> comments;
    public ReCommentAdapter(Context context, ArrayList<Comment> comments){
        this.context=context;
        this.comments=comments;
    }
    public class RecommentViewHolder extends RecyclerView.ViewHolder{
        CommentWithDoubleItemBinding binding;
        public RecommentViewHolder(CommentWithDoubleItemBinding binding){
            super(binding.getRoot());
            this.binding=binding;
        }
        public void setItem(Comment comment){
            binding.addCommentBtn.setVisibility(View.GONE);
            binding.content.setText(comment.getText());
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        commentWithDoubleItemBinding=CommentWithDoubleItemBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new RecommentViewHolder(commentWithDoubleItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        RecommentViewHolder vh=(RecommentViewHolder)holder;
        vh.setItem(comments.get(position));
    }

    @Override
    public int getItemCount() {
        return comments==null?0:comments.size();
    }
}
