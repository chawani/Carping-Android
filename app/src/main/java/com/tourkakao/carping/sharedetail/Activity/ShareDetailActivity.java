package com.tourkakao.carping.sharedetail.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.tourkakao.carping.R;
import com.tourkakao.carping.SharedPreferenceManager.SharedPreferenceManager;
import com.tourkakao.carping.databinding.ActivityShareDetailBinding;
import com.tourkakao.carping.sharedetail.Adapter.ShareImageAdapter;
import com.tourkakao.carping.sharedetail.DataClass.ShareDetail;
import com.tourkakao.carping.sharedetail.viewmodel.ShareDetailViewModel;

import java.util.ArrayList;

public class ShareDetailActivity extends AppCompatActivity {
    ActivityShareDetailBinding shareDetailBinding;
    Context context;
    ShareDetailViewModel detailViewModel;
    ShareImageAdapter shareImageAdapter;
    String chat_url=null;
    int postpk;
    int userpk;
    int like=0;
    boolean islike=false;
    boolean isshare=false;
    boolean firstshare=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shareDetailBinding=ActivityShareDetailBinding.inflate(getLayoutInflater());
        setContentView(shareDetailBinding.getRoot());
        context=this;
        postpk=getIntent().getIntExtra("pk", 0);
        userpk= SharedPreferenceManager.getInstance(context).getInt("id", 0);

        detailViewModel=new ViewModelProvider(this).get(ShareDetailViewModel.class);
        detailViewModel.setContext(context);
        detailViewModel.get_share_detail(postpk);


        Glide.with(context).load(R.drawable.share_ask_img).into(shareDetailBinding.askBtn);
        starting_observe_images();
        starting_observe_tags();
        starting_observe_detail();
        setting_like_btn();
        setting_ask_url_btn();
        starting_observe_setting();
    }
    public void starting_observe_images(){
        detailViewModel.getImages().observe(this, new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> strings) {
                shareImageAdapter=new ShareImageAdapter(context, strings);
                shareDetailBinding.imageViewPager.setAdapter(shareImageAdapter);
            }
        });
    }
    public void starting_observe_tags(){
        detailViewModel.getTags().observe(this, new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> strings) {
                for(String t:strings){
                    TextView tag=new TextView(context);
                    tag.setText("#"+t);
                    tag.setBackgroundResource(R.drawable.purple_border_round);
                    tag.setPadding(60, 30, 60, 30);
                    tag.setTextColor(Color.parseColor("#5f51ef"));
                    shareDetailBinding.tagArea.addView(tag);
                }
            }
        });
    }
    public void starting_observe_detail(){
        detailViewModel.getDetail().observe(this, new Observer<ShareDetail>() {
            @Override
            public void onChanged(ShareDetail shareDetail) {
                if(userpk==shareDetail.getUser()){
                    Glide.with(context).load(R.drawable.list_show_btn).into(shareDetailBinding.listBtn);
                    shareDetailBinding.listBtn.setOnClickListener(v -> {
                        shareDetailBinding.listLayout.setVisibility(View.VISIBLE);
                    });
                    shareDetailBinding.shareDelete.setOnClickListener(v -> {
                        detailViewModel.share_delete(postpk);
                        shareDetailBinding.listLayout.setVisibility(View.GONE);
                    });
                    isshare=shareDetail.isIs_shared();
                    firstshare=isshare;
                    shareDetailBinding.shareComplete.setOnClickListener(v -> {
                        if(isshare==true){
                            detailViewModel.share_cancel_complete(postpk);
                        }else{
                            detailViewModel.share_complete(postpk);
                        }
                        shareDetailBinding.listLayout.setVisibility(View.GONE);
                    });
                    shareDetailBinding.toolbar.setOnClickListener(v -> {
                        shareDetailBinding.listLayout.setVisibility(View.GONE);
                    });
                    shareDetailBinding.middleLayout.setOnClickListener(v -> {
                        shareDetailBinding.listLayout.setVisibility(View.GONE);
                    });
                    shareDetailBinding.imageViewPager.setOnClickListener(v -> {
                        shareDetailBinding.listLayout.setVisibility(View.GONE);
                    });
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        shareDetailBinding.scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                            @Override
                            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                                shareDetailBinding.listLayout.setVisibility(View.GONE);
                            }
                        });
                    }
                }
                shareDetailBinding.username.setText(shareDetail.getUsername());
                shareDetailBinding.hour.setText(shareDetail.getCreated_at());
                shareDetailBinding.title.setText(shareDetail.getTitle());
                shareDetailBinding.content.setText(shareDetail.getText());
                like=shareDetail.getLike_count();
                shareDetailBinding.likeComment.setText("좋아요"+like+" · 댓글"+"0");
                chat_url=shareDetail.getChat_addr();
                Glide.with(context).load(shareDetail.getProfile()).transform(new RoundedCorners(100)).into(shareDetailBinding.profile);
                shareDetailBinding.location.setText(shareDetail.getRegion());
                islike=shareDetail.isIs_liked();
                if(islike==true){
                    Glide.with(context).load(R.drawable.is_pushed_like).into(shareDetailBinding.likeMark);
                }else{
                    Glide.with(context).load(R.drawable.like_mark).into(shareDetailBinding.likeMark);
                }
                shareDetailBinding.likeCount.setText(Integer.toString(like));
            }
        });
    }


    public void setting_like_btn(){
        shareDetailBinding.likeMark.setOnClickListener(v -> {
            if(islike==true){
                islike=false;
                like--;
                shareDetailBinding.likeCount.setText(Integer.toString(like));
                Glide.with(context).load(R.drawable.like_mark).into(shareDetailBinding.likeMark);
                detailViewModel.share_release_like(postpk);
            }else{
                islike=true;
                like++;
                shareDetailBinding.likeCount.setText(Integer.toString(like));
                Glide.with(context).load(R.drawable.is_pushed_like).into(shareDetailBinding.likeMark);
                detailViewModel.share_like(postpk);
            }
        });
    }
    public void setting_ask_url_btn(){
        shareDetailBinding.askBtn.setOnClickListener(v -> {
            Intent askintent=new Intent(Intent.ACTION_VIEW, Uri.parse(chat_url));
            startActivity(askintent);
        });
    }
    public void starting_observe_setting(){
        detailViewModel.share_delete.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if(integer==1){
                    Toast.makeText(context, "무료나눔이 삭제되었어요!", Toast.LENGTH_SHORT).show();
                    SharedPreferenceManager.getInstance(context).setInt("share_delete", 1);
                    finish();
                }
            }
        });
        detailViewModel.share_complete.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if(integer==0){
                    isshare=false;
                    shareDetailBinding.shareComplete.setText("거래완료");

                }else if(integer==1){
                    isshare=true;
                    shareDetailBinding.shareComplete.setText("거래취소");
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent();
        if(firstshare!=isshare){
            SharedPreferenceManager.getInstance(context).setInt("change_isshare", 1);
        }else{
            SharedPreferenceManager.getInstance(context).setInt("change_isshare", 0);
        }
        setResult(RESULT_OK, intent);
        finish();

    }
}