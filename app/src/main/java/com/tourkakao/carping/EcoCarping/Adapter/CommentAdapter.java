package com.tourkakao.carping.EcoCarping.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.tourkakao.carping.EcoCarping.DTO.Comment;
import com.tourkakao.carping.Home.EcoFragmentAdapter.EcoReviewAdapter;
import com.tourkakao.carping.databinding.CommentItemBinding;
import com.tourkakao.carping.databinding.EcoCarpingDetailImageBinding;

import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context context;
    private ArrayList<Comment> comments;

    public class ViewHolder extends RecyclerView.ViewHolder{
        private CommentItemBinding binding;
        public ViewHolder(CommentItemBinding binding){
            super(binding.getRoot());
            this.binding=binding;
        }
        public void bind(Comment comment){
            Glide.with(context).load(comment.getBadge()).into(binding.badge);
            Glide.with(context)
                    .load(comment.getProfile())
                    .transform(new CenterCrop(), new RoundedCorners(100))
                    .into(binding.image);
            binding.level.setText(comment.getLevel());
            binding.name.setText(comment.getUsername());
            binding.time.setText(comment.getCreated_at());
            binding.content.setText(comment.getText());
        }
    }

    public CommentAdapter(Context context, ArrayList<Comment> comments){
        this.context=context;
        this.comments=comments;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(CommentItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder vh=(CommentAdapter.ViewHolder)holder;
        vh.bind(comments.get(position));
    }

    @Override
    public int getItemCount() {
        return comments==null?0:comments.size();
    }
}
