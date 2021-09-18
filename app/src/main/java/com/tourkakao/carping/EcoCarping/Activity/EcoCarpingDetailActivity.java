package com.tourkakao.carping.EcoCarping.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.tourkakao.carping.EcoCarping.Adapter.CommentAdapter;
import com.tourkakao.carping.EcoCarping.Adapter.DetailPagerAdapter;
import com.tourkakao.carping.EcoCarping.DTO.Comment;
import com.tourkakao.carping.EcoCarping.DTO.EcoPost;
import com.tourkakao.carping.EcoCarping.DTO.PostComment;
import com.tourkakao.carping.EcoCarping.ViewModel.EcoDetailViewModel;
import com.tourkakao.carping.R;
import com.tourkakao.carping.SharedPreferenceManager.SharedPreferenceManager;
import com.tourkakao.carping.databinding.ActivityEcoCarpingDetailBinding;

import java.util.ArrayList;

public class EcoCarpingDetailActivity extends AppCompatActivity {
    ActivityEcoCarpingDetailBinding binding;
    private EcoDetailViewModel ecoDetailViewModel;
    private int pk;
    private DetailPagerAdapter detailPagerAdapter;
    private CommentAdapter commentAdapter;
    private String postId;
    private int likeCount;
    private int current_user;
    private int commentCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityEcoCarpingDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ecoDetailViewModel =new ViewModelProvider(this).get(EcoDetailViewModel.class);
        ecoDetailViewModel.setContext(this);

        getDetailInfo();

        binding.commentView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        current_user=SharedPreferenceManager.getInstance(getApplicationContext()).getInt("id",0);

        ecoDetailViewModel.getPost().observe(this, new Observer<EcoPost>() {
            @Override
            public void onChanged(EcoPost ecoPost) {
                int id=(int)Double.parseDouble(ecoPost.getUser());
                if(current_user!=id){
                    binding.privateEditButton.setVisibility(View.GONE);
                    binding.privateDeleteButton.setVisibility(View.GONE);
                }
                binding.username.setText(ecoPost.getUsername());
                binding.hour.setText(ecoPost.getCreated_at());
                binding.title.setText(ecoPost.getTitle());
                binding.content.setText(ecoPost.getText());
                likeCount=(int)Double.parseDouble(ecoPost.getLike_count());
                commentCount=ecoPost.getComment().size();
                binding.likeComment.setText("좋아요"+Integer.toString(likeCount)+" · 댓글"+ecoPost.getComment().size());
                Glide.with(getApplicationContext())
                        .load(ecoPost.getProfile())
                        .transform(new CenterCrop(), new RoundedCorners(100))
                        .into(binding.profile);
                binding.location.setText(ecoPost.getPlace());
                if(String.valueOf(ecoPost.getIs_liked()).equals("true")){
                    Glide.with(getApplicationContext()).load(R.drawable.is_pushed_like).into(binding.likeMark);
                }else{
                    Glide.with(getApplicationContext()).load(R.drawable.like_mark).into(binding.likeMark);
                }
                postId=ecoPost.getId();
                binding.likeCount.setText(Integer.toString(likeCount));
            }
        });
        ecoDetailViewModel.getImages().observe(this, new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> strings) {
                detailPagerAdapter =new DetailPagerAdapter(getApplicationContext(),strings);
                binding.imageViewPager.setAdapter(detailPagerAdapter);
            }
        });
        ecoDetailViewModel.getTags().observe(this, new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> strings) {
                for(String tag:strings) {
                    TextView textView = new TextView(getApplicationContext());
                    LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.rightMargin= convertDp(5);
                    textView.setLayoutParams(params);
                    textView.setBackgroundResource(R.drawable.tag_design);
                    textView.setPadding(convertDp(10),convertDp(5),convertDp(10),convertDp(5));
                    textView.setTextColor(Color.parseColor("#5f51ef"));
                    textView.setText("#"+tag);
                    binding.tagArea.addView(textView);
                }
            }
        });
        ecoDetailViewModel.getComments().observe(this, new Observer<ArrayList<Comment>>() {
            @Override
            public void onChanged(ArrayList<Comment> comments) {
                if(comments!=null) {
                    commentAdapter =
                            new CommentAdapter(getApplicationContext(), comments,ecoDetailViewModel,EcoCarpingDetailActivity.this);
                    binding.commentView.setAdapter(commentAdapter);
                }
            }
        });
        binding.completeComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!binding.commentEditView.getText().toString().equals("")) {
                    setCommentMap();
                    commentCount=commentCount+1;
                    binding.likeComment.setText("좋아요"+likeCount+" · 댓글"+commentCount);
                }
            }
        });
        ecoDetailViewModel.getLikeStatus().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(s.equals("true")){
                    Glide.with(getApplicationContext()).load(R.drawable.is_pushed_like).into(binding.likeMark);
                }
                if(s.equals("false")){
                    Glide.with(getApplicationContext()).load(R.drawable.like_mark).into(binding.likeMark);
                }
            }
        });
        binding.likeMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ecoDetailViewModel.getLikeStatus().getValue().equals("true")){
                    ecoDetailViewModel.cancelLike();
                    likeCount=likeCount-1;
                    binding.likeCount.setText(Integer.toString(likeCount));
                    binding.likeComment.setText("좋아요"+likeCount+" · 댓글"+ecoDetailViewModel.getPost().getValue().getComment().size());
                }
                if(ecoDetailViewModel.getLikeStatus().getValue().equals("false")){
                    ecoDetailViewModel.pushLike();
                    likeCount=likeCount+1;
                    binding.likeCount.setText(Integer.toString(likeCount));
                    binding.likeComment.setText("좋아요"+likeCount+" · 댓글"+ecoDetailViewModel.getPost().getValue().getComment().size());
                }
            }
        });
        binding.privateEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), EcoCarpingEditActivity.class);
                intent.putExtra("pk",postId);
                startActivity(intent);
            }
        });
        binding.privateDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
    }

    void showDialog() {
        AlertDialog.Builder msgBuilder = new AlertDialog.Builder(this)
                .setTitle("삭제")
                .setMessage("삭제하시겠습니까?")
                .setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialogInterface, int i) {
                        ecoDetailViewModel.deletePost();
                        finish();
                    }
                })
                .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        AlertDialog msgDlg = msgBuilder.create();
        msgDlg.show();
    }


    public void getDetailInfo(){
        Intent intent=getIntent();
        pk=(int)Float.parseFloat(intent.getStringExtra("pk"));
        ecoDetailViewModel.setPk(pk);

        ecoDetailViewModel.loadDetail();
    }

    public void setCommentMap(){
        String userString= SharedPreferenceManager.getInstance(getApplicationContext()).getString("id","");
        int userInteger=(int)Double.parseDouble(userString);
        userString=Integer.toString(userInteger);
        int postIdInteger=(int)Double.parseDouble(postId);

        PostComment comment=new PostComment(userString,Integer.toString(postIdInteger),binding.commentEditView.getText().toString());
        ecoDetailViewModel.updateComments(comment);
        binding.likeComment.setText("좋아요"+likeCount+" · 댓글"+ecoDetailViewModel.getComments().getValue().size());
    }

    public int convertDp(int dp) {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, metrics);
    }
}