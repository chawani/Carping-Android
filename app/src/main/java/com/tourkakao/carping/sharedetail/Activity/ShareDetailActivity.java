package com.tourkakao.carping.sharedetail.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
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
    InputMethodManager keyboard;
    int postpk;
    int userpk;
    int like=0;
    int comment=0;
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
        keyboard=(InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);

        detailViewModel=new ViewModelProvider(this).get(ShareDetailViewModel.class);
        detailViewModel.setContext(context);
        detailViewModel.get_share_detail(postpk);
        detailViewModel.setUserpk(userpk);


        Glide.with(context).load(R.drawable.share_ask_img).into(shareDetailBinding.askBtn);
        setting_comment();
        setting_comment_btn();
        starting_observe_images();
        starting_observe_tags();
        starting_observe_detail();
        setting_like_btn();
        setting_ask_url_btn();
        starting_observe_setting();
        setting_back_btn();
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
                if(shareDetailBinding.tagArea.getChildAt(0)!=null){
                    shareDetailBinding.tagArea.removeAllViews();
                }
                for(String t:strings){
                    TextView tag=new TextView(context);
                    tag.setText("#"+t);
                    LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.rightMargin= convertDp(5);
                    tag.setLayoutParams(params);
                    tag.setBackgroundResource(R.drawable.purple_border_round);
                    tag.setPadding(convertDp(10),convertDp(5),convertDp(10),convertDp(5));
                    tag.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
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
                        registerForContextMenu(v);
                        v.showContextMenu();
                        unregisterForContextMenu(v);
                    });
                    isshare=shareDetail.isIs_shared();
                    firstshare=isshare;
                }
                shareDetailBinding.username.setText(shareDetail.getUsername());
                shareDetailBinding.hour.setText(shareDetail.getCreated_at());
                shareDetailBinding.title.setText(shareDetail.getTitle());
                shareDetailBinding.content.setText(shareDetail.getText());
                like=shareDetail.getLike_count();
                comment=shareDetail.getComment().size();
                shareDetailBinding.likeComment.setText("좋아요"+like+" · 댓글"+comment);
                chat_url=shareDetail.getChat_addr();
                Glide.with(context).load(shareDetail.getProfile()).transform(new CenterCrop(), new RoundedCorners(100)).into(shareDetailBinding.profile);
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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater=getMenuInflater();
        if(isshare==true){
            inflater.inflate(R.menu.detail_share_yes_menu, menu);
        }else{
            inflater.inflate(R.menu.detail_share_not_menu, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.edit:
                SharedPreferenceManager.getInstance(getApplicationContext()).setInt("change_isshare", 1);
                Intent fixintent=new Intent(context, FixShareActivity.class);
                fixintent.putExtra("pk", postpk);
                startActivityForResult(fixintent, 1001);
                break;
            case R.id.delete:
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setTitle("무료나눔 삭제")
                        .setMessage("무료나눔을 삭제할까요?")
                        .setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("네", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferenceManager.getInstance(getApplicationContext()).setInt("change_isshare", 1);
                                detailViewModel.share_delete(postpk);
                            }
                        }).create().show();
                break;
            case R.id.clear:
                SharedPreferenceManager.getInstance(getApplicationContext()).setInt("change_isshare", 1);
                if(isshare==true){
                    detailViewModel.share_cancel_complete(postpk);
                }else{
                    detailViewModel.share_complete(postpk);
                }
                break;
        }
        return true;
    }

    public void setting_like_btn(){
        shareDetailBinding.likeMark.setOnClickListener(v -> {
            SharedPreferenceManager.getInstance(getApplicationContext()).setInt("change_isshare", 1);
            if(islike==true){
                islike=false;
                like--;
                shareDetailBinding.likeCount.setText(Integer.toString(like));
                Glide.with(context).load(R.drawable.like_mark).into(shareDetailBinding.likeMark);
                detailViewModel.share_release_like(postpk);
                shareDetailBinding.likeComment.setText("좋아요"+like+" · 댓글"+comment);
            }else{
                islike=true;
                like++;
                shareDetailBinding.likeCount.setText(Integer.toString(like));
                Glide.with(context).load(R.drawable.is_pushed_like).into(shareDetailBinding.likeMark);
                detailViewModel.share_like(postpk);
                shareDetailBinding.likeComment.setText("좋아요"+like+" · 댓글"+comment);
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
                    SharedPreferenceManager.getInstance(context).setInt("change_isshare", 1);
                    finish();
                }
            }
        });
        detailViewModel.share_complete.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if(integer==0){
                    isshare=false;

                }else if(integer==1){
                    isshare=true;
                }
            }
        });
    }
    public void setting_comment(){
        shareDetailBinding.commentView.setLayoutManager(new LinearLayoutManager(context));
        shareDetailBinding.commentView.setAdapter(detailViewModel.setting_comment_adapter());

        shareDetailBinding.completeComment.setOnClickListener(v -> {
            String text=shareDetailBinding.commentEditView.getText().toString();
            detailViewModel.send_comment(postpk, text);
            shareDetailBinding.commentEditView.setText("");
            keyboard.hideSoftInputFromWindow(shareDetailBinding.commentEditView.getWindowToken(), 0);
            comment++;
            shareDetailBinding.likeComment.setText("좋아요"+like+" · 댓글"+comment);
        });

    }
    public void setting_comment_btn(){
        shareDetailBinding.commentEditView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().length()>0){
                    shareDetailBinding.completeComment.setBackgroundColor(Color.parseColor("#5f51ef"));
                    shareDetailBinding.completeComment.setTextColor(Color.parseColor("#FFFFFF"));
                }else{
                    shareDetailBinding.completeComment.setBackgroundColor(Color.parseColor("#22222222"));
                    shareDetailBinding.completeComment.setTextColor(Color.parseColor("#999999"));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    public void setting_back_btn(){
        shareDetailBinding.back.setOnClickListener(v -> {
            finish();
        });
    }
    @Override
    public void onBackPressed() {
        finish();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if(requestCode==1001){
                detailViewModel.get_share_detail(postpk);
            }
        }
    }

    public int convertDp(int dp) {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, metrics);
    }
}