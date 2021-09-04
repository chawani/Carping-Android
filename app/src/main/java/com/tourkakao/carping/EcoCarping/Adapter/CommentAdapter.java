package com.tourkakao.carping.EcoCarping.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.google.gson.Gson;
import com.tourkakao.carping.EcoCarping.Activity.EcoCarpingDetailActivity;
import com.tourkakao.carping.EcoCarping.DTO.Comment;
import com.tourkakao.carping.EcoCarping.DTO.PostComment;
import com.tourkakao.carping.EcoCarping.ViewModel.EcoDetailViewModel;
import com.tourkakao.carping.Home.EcoFragmentAdapter.EcoReviewAdapter;
import com.tourkakao.carping.NetworkwithToken.CommonClass;
import com.tourkakao.carping.NetworkwithToken.TotalApiClient;
import com.tourkakao.carping.SharedPreferenceManager.SharedPreferenceManager;
import com.tourkakao.carping.databinding.CommentItemBinding;
import com.tourkakao.carping.databinding.EcoCarpingDetailImageBinding;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context context;
    private ArrayList<Comment> comments;
    private EcoDetailViewModel viewModel;
    private EcoCarpingDetailActivity activity;
    private String current_user;

    public class ViewHolder extends RecyclerView.ViewHolder{
        private CommentItemBinding binding;
        public ViewHolder(CommentItemBinding binding){
            super(binding.getRoot());
            this.binding=binding;

            binding.privateDeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pk=(int)Double.parseDouble(binding.pk.getText().toString());
                    showDialog(pk);
                }
            });
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
            binding.pk.setText(comment.getId());
            int user=(int)Double.parseDouble(comment.getUser());
            if(!Integer.toString(user).equals(current_user))
                binding.privateDeleteButton.setVisibility(View.GONE);
        }
    }

    public CommentAdapter(Context context, ArrayList<Comment> comments, EcoDetailViewModel viewModel,EcoCarpingDetailActivity activity){
        this.context=context;
        this.comments=comments;
        this.viewModel=viewModel;
        this.activity=activity;
        current_user=SharedPreferenceManager.getInstance(context).getString("id","");
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

    void showDialog(int pk) {
        AlertDialog.Builder msgBuilder = new AlertDialog.Builder(activity)
                .setTitle("삭제")
                .setMessage("삭제하시겠습니까?")
                .setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialogInterface, int i) {
                        viewModel.deleteComment(pk);
                        Intent intent=activity.getIntent();
                        activity.finish();
                        activity.startActivity(intent);
                    }
                })
                .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        AlertDialog msgDlg = msgBuilder.create();
        msgDlg.show();
    }
}
