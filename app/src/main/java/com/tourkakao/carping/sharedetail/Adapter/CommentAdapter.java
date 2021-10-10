package com.tourkakao.carping.sharedetail.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.tourkakao.carping.NetworkwithToken.TotalApiClient;
import com.tourkakao.carping.databinding.CommentItemBinding;
import com.tourkakao.carping.databinding.CommentWithDoubleItemBinding;
import com.tourkakao.carping.sharedetail.DataClass.Comment;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CommentAdapter extends RecyclerView.Adapter {
    private Context context;
    ArrayList<Comment> comments;
    CommentItemBinding commentItemBinding;
    public int userpk;
    public CommentAdapter(Context context, ArrayList<Comment> comments, int userpk){
        this.context=context;
        this.comments=comments;
        this.userpk=userpk;
    }
    public class CommentViewHolder extends RecyclerView.ViewHolder{
        CommentItemBinding binding;
        public CommentViewHolder(CommentItemBinding binding){
            super(binding.getRoot());
            this.binding=binding;
        }
        public void setItem(Comment comment, int pos){
            binding.content.setText(comment.getText());
            binding.level.setText("LV."+(int)Float.parseFloat(comment.getLevel()));
            binding.name.setText(comment.getUsername());
            Glide.with(context).load(comment.getBadge()).transform(new RoundedCorners(100)).into(binding.badge);
            Glide.with(context).load(comment.getProfile()).transform(new RoundedCorners(100)).into(binding.image);
            binding.time.setText(comment.getCreated_at());
            if(comment.getUser()==userpk){
                binding.privateDeleteButton.setVisibility(View.VISIBLE);
                binding.privateDeleteButton.setOnClickListener(v -> {
                    AlertDialog.Builder builder=new AlertDialog.Builder(context);
                    builder.setTitle("댓글 삭제")
                            .setMessage("댓글을 삭제할까요?")
                            .setPositiveButton("네", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    TotalApiClient.getCommunityApiService(context).delete_share_comment(comment.getId())
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(
                                                    res -> {
                                                        if(res.isSuccess()){
                                                            comments.remove(pos);
                                                            updateItem(comments);
                                                        }else{
                                                            System.out.println(res.getError_message());
                                                        }
                                                    },
                                                    error -> {
                                                        System.out.println(error+"error");
                                                    }
                                            );
                                }
                            })
                            .setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).create().show();

                });
            }else{
                binding.privateDeleteButton.setVisibility(View.GONE);
            }
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        commentItemBinding=CommentItemBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new CommentViewHolder(commentItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        CommentViewHolder vh=(CommentViewHolder)holder;
        vh.setItem(comments.get(position), position);
    }

    @Override
    public int getItemCount() {
        return comments==null?0:comments.size();
    }

    public void updateItem(ArrayList<Comment> item){
        if(comments!=null){
            comments=null;
        }
        comments=item;
        notifyDataSetChanged();
    }
}
