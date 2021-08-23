package com.tourkakao.carping.newcarping.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.RatingBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tourkakao.carping.Gallerypermission.Gallery_setting;
import com.tourkakao.carping.Home.MainActivity;
import com.tourkakao.carping.R;
import com.tourkakao.carping.databinding.ActivityWriteNewcarpingReviewBinding;

public class Write_newcarping_reviewActivity extends AppCompatActivity {
    private ActivityWriteNewcarpingReviewBinding reviewBinding;
    Gallery_setting gallery_setting;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reviewBinding=ActivityWriteNewcarpingReviewBinding.inflate(getLayoutInflater());
        setContentView(reviewBinding.getRoot());
        context=this;
        gallery_setting=new Gallery_setting(context, Write_newcarping_reviewActivity.this);

        Glide.with(context).load(R.drawable.picture_btn_img).into(reviewBinding.pictureBtn);
        reviewBinding.title.setText(getIntent().getStringExtra("title"));

        setting_ratingbar_and_related_text();
        setting_review_edittext();
        setting_getting_review_image();
    }

    public void setting_review_edittext(){
        reviewBinding.reviewEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                reviewBinding.textCount.setText(s.toString().length() + "/300");
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
    public void setting_ratingbar_and_related_text(){
        reviewBinding.totalstar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if(rating==0){
                  reviewBinding.starMent.setText("이 캠핑장을 평가해주세요!");
                  reviewBinding.starMent.setTextColor(Color.LTGRAY);
                } else if(rating<=1){
                    reviewBinding.starMent.setText("안좋았어요");
                    reviewBinding.starMent.setTextColor(Color.BLACK);
                }else if(rating<=2){
                    reviewBinding.starMent.setText("나쁘지 않아요");
                    reviewBinding.starMent.setTextColor(Color.BLACK);
                }else if(rating<=3){
                    reviewBinding.starMent.setText("보통이에요");
                    reviewBinding.starMent.setTextColor(Color.BLACK);
                }else if(rating<=4){
                    reviewBinding.starMent.setText("맘에 들어요!");
                    reviewBinding.starMent.setTextColor(Color.BLACK);
                }else{
                    reviewBinding.starMent.setText("최고예요!");
                    reviewBinding.starMent.setTextColor(Color.BLACK);
                }
            }
        });
    }
    public void setting_getting_review_image(){
        reviewBinding.pictureBtn.setOnClickListener(v -> {
            if(Build.VERSION.SDK_INT>=23){
                int permission_read=context.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
                int permission_write=context.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if(permission_read== PackageManager.PERMISSION_DENIED || permission_write==PackageManager.PERMISSION_DENIED){
                    gallery_setting.check_gallery_permission();
                }else{
                    Intent galleryintent=new Intent();
                    galleryintent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                    startActivityForResult(galleryintent, gallery_setting.GALLERY_CODE);
                }
            }else{
                Intent galleryintent=new Intent();
                galleryintent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(galleryintent, gallery_setting.GALLERY_CODE);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==gallery_setting.PERMISSION_GALLERY_REQUESTCODE){
            if(grantResults.length==gallery_setting.REQUIRED_PERMISSIONS.length){
                boolean check_result=true;
                for(int result: grantResults){
                    if(result== PackageManager.PERMISSION_DENIED){
                        check_result=false;
                        break;
                    }
                }
                if(check_result){
                    Toast.makeText(context, "갤러리 접근 권한이 설정되었습니다.", Toast.LENGTH_SHORT).show();
                }else{
                    AlertDialog.Builder builder=new AlertDialog.Builder(context);
                    builder.setTitle("갤러리 접근 권한 설정 알림")
                            .setMessage("서비스 사용을 위해서는 갤러리 접근 권한 설정이 필요합니다. [설정]->[앱]에서 갤러리 접근 권한을 승인해주세요")
                            .setCancelable(false)
                            .setNegativeButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    builder.create().show();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if(requestCode==gallery_setting.GALLERY_CODE){
                if(data.getData()!=null){
                    System.out.println(data.getData());
                }
            }
        }
    }
}