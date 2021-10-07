package com.tourkakao.carping.newcarping.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.RatingBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tourkakao.carping.Gallerypermission.Gallery_setting;
import com.tourkakao.carping.R;
import com.tourkakao.carping.SharedPreferenceManager.SharedPreferenceManager;
import com.tourkakao.carping.databinding.ActivityFixNewcarpingReviewBinding;
import com.tourkakao.carping.newcarping.viewmodel.EachNewCarpingViewModel;

public class Fix_newcarping_reviewActivity extends AppCompatActivity {
    private ActivityFixNewcarpingReviewBinding fixBinding;
    Context context;
    Gallery_setting gallery_setting;
    Uri currentImageUri=null;
    boolean send_image_ok=true;
    boolean send_text_ok=true;
    EachNewCarpingViewModel eachNewCarpingViewModel;
    int userpk, postpk, id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fixBinding=ActivityFixNewcarpingReviewBinding.inflate(getLayoutInflater());
        setContentView(fixBinding.getRoot());
        context=this;
        gallery_setting=new Gallery_setting(context, Fix_newcarping_reviewActivity.this);

        eachNewCarpingViewModel=new ViewModelProvider(this).get(EachNewCarpingViewModel.class);
        eachNewCarpingViewModel.setContext(context);

        setting_inital();
        setting_ratingbar_and_related_text();
        setting_review_edittext();
        setting_getting_review_image();
        setting_sending_button();
        setting_cancel_button();
        starting_observe_send_review_ok();
    }
    public void setting_inital(){
        Intent intent=getIntent();
        userpk=intent.getIntExtra("userpk", 0);
        postpk=intent.getIntExtra("postpk", 0);
        id=intent.getIntExtra("id", 0);
        eachNewCarpingViewModel.fix_text=intent.getStringExtra("text");
        eachNewCarpingViewModel.fix_star1=intent.getFloatExtra("star1", 0);
        eachNewCarpingViewModel.fix_star2=intent.getFloatExtra("star2", 0);
        eachNewCarpingViewModel.fix_star3=intent.getFloatExtra("star3", 0);
        eachNewCarpingViewModel.fix_star4=intent.getFloatExtra("star4", 0);
        eachNewCarpingViewModel.fix_totalstar=intent.getFloatExtra("total_star", 0);

        fixBinding.star1.setRating(eachNewCarpingViewModel.fix_star1);
        fixBinding.star2.setRating(eachNewCarpingViewModel.fix_star2);
        fixBinding.star3.setRating(eachNewCarpingViewModel.fix_star3);
        fixBinding.star4.setRating(eachNewCarpingViewModel.fix_star4);
        fixBinding.totalstar.setRating(eachNewCarpingViewModel.fix_totalstar);
        fixBinding.reviewEdittext.setText(eachNewCarpingViewModel.fix_text);
        fixBinding.textCount.setText(fixBinding.reviewEdittext.getText().length()+"/300");
        Glide.with(context).load(R.drawable.picture_btn_img).into(fixBinding.pictureBtn);
        fixBinding.pictureBtn.setVisibility(View.GONE);
        fixBinding.selectPictureLayout.setVisibility(View.VISIBLE);
        Glide.with(context).load(intent.getStringExtra("image")).into(fixBinding.selectPictureBtn);
        fixBinding.reviewBtn.setBackgroundColor(Color.BLACK);

    }
    public void setting_review_edittext(){
        fixBinding.reviewEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().length()<=0){
                    send_text_ok=false;
                }else{
                    send_text_ok=true;
                }
                fixBinding.textCount.setText(s.toString().length() + "/300");
                if(send_image_ok && send_text_ok){
                    fixBinding.reviewBtn.setBackgroundColor(Color.BLACK);
                }else{
                    fixBinding.reviewBtn.setBackgroundColor(Color.parseColor("#A4A4A4"));
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
    public void setting_ratingbar_and_related_text(){
        fixBinding.totalstar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if(rating==0){
                    fixBinding.starMent.setText("이 캠핑장을 평가해주세요!");
                    fixBinding.starMent.setTextColor(Color.LTGRAY);
                } else if(rating<=1){
                    fixBinding.starMent.setText("안좋았어요");
                    fixBinding.starMent.setTextColor(Color.BLACK);
                }else if(rating<=2){
                    fixBinding.starMent.setText("나쁘지 않아요");
                    fixBinding.starMent.setTextColor(Color.BLACK);
                }else if(rating<=3){
                    fixBinding.starMent.setText("보통이에요");
                    fixBinding.starMent.setTextColor(Color.BLACK);
                }else if(rating<=4){
                    fixBinding.starMent.setText("맘에 들어요!");
                    fixBinding.starMent.setTextColor(Color.BLACK);
                }else{
                    fixBinding.starMent.setText("최고예요!");
                    fixBinding.starMent.setTextColor(Color.BLACK);
                }
            }
        });
    }
    public void setting_getting_review_image(){
        fixBinding.pictureBtn.setOnClickListener(v -> {
            if(Build.VERSION.SDK_INT>=23){
                int permission_read=context.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
                int permission_write=context.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if(permission_read== PackageManager.PERMISSION_DENIED || permission_write==PackageManager.PERMISSION_DENIED){
                    gallery_setting.check_gallery_permission();
                }else{
                    /*Intent galleryintent=new Intent(Intent.ACTION_GET_CONTENT);
                    galleryintent.setType("image/*");
                    startActivityForResult(galleryintent, gallery_setting.GALLERY_CODE);*/
                    Intent intent=new Intent();
                    intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(intent, gallery_setting.GALLERY_CODE);
                }
            }else{
                Intent galleryintent=new Intent(Intent.ACTION_GET_CONTENT);
                galleryintent.setType("image/*");
                startActivityForResult(galleryintent, gallery_setting.GALLERY_CODE);
            }
        });
        fixBinding.selectPictureBtn.setOnClickListener(v -> {
            if(Build.VERSION.SDK_INT>=23){
                int permission_read=context.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
                int permission_write=context.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if(permission_read== PackageManager.PERMISSION_DENIED || permission_write==PackageManager.PERMISSION_DENIED){
                    gallery_setting.check_gallery_permission();
                }else{
                    Intent galleryintent=new Intent(Intent.ACTION_GET_CONTENT);
                    galleryintent.setType("image/*");
                    startActivityForResult(galleryintent, gallery_setting.GALLERY_CODE);
                    /*Intent intent=new Intent();
                    intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(intent, gallery_setting.GALLERY_CODE);*/
                }
            }else{
                Intent galleryintent=new Intent(Intent.ACTION_GET_CONTENT);
                galleryintent.setType("image/*");
                startActivityForResult(galleryintent, gallery_setting.GALLERY_CODE);
            }
        });
        fixBinding.selectPictureBtn.setOnLongClickListener(v -> {
            AlertDialog.Builder builder=new AlertDialog.Builder(context);
            builder.setTitle("이미지 삭제")
                    .setMessage("업로드된 이미지를 삭제할까요?")
                    .setCancelable(false)
                    .setPositiveButton("네", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Glide.with(context).load((Uri)null).into(fixBinding.selectPictureBtn);
                            fixBinding.pictureBtn.setVisibility(View.VISIBLE);
                            fixBinding.selectPictureLayout.setVisibility(View.GONE);
                            send_image_ok=false;
                            fixBinding.reviewBtn.setBackgroundColor(Color.parseColor("#A4A4A4"));
                        }
                    })
                    .setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).create().show();
            return true;
        });
    }

    public void setting_sending_button(){
        fixBinding.reviewBtn.setOnClickListener(v -> {
            if(send_image_ok && send_text_ok){
                eachNewCarpingViewModel.fix_text=fixBinding.reviewEdittext.getText().toString();
                eachNewCarpingViewModel.fix_totalstar=fixBinding.totalstar.getRating();
                eachNewCarpingViewModel.fix_star1=fixBinding.star1.getRating();
                eachNewCarpingViewModel.fix_star2=fixBinding.star2.getRating();
                eachNewCarpingViewModel.fix_star3=fixBinding.star3.getRating();
                eachNewCarpingViewModel.fix_star4=fixBinding.star4.getRating();
                eachNewCarpingViewModel.fix_uri=currentImageUri;
                eachNewCarpingViewModel.fixing_newcarping_review(id, userpk, postpk);
            }else if(send_image_ok && !send_text_ok){
                Toast.makeText(context, "리뷰를 작성해주세요!", Toast.LENGTH_SHORT).show();
                return;
            }else if(!send_image_ok && send_text_ok){
                Toast.makeText(context, "리뷰사진을 업로드해주세요!", Toast.LENGTH_SHORT).show();
                return;
            }else if(!send_image_ok && !send_text_ok){
                Toast.makeText(context, "리뷰 및 사진을 업로드해주세요!", Toast.LENGTH_SHORT).show();
                return;
            }
        });
    }
    public void setting_cancel_button(){
        fixBinding.cancel.setOnClickListener(v -> {
            finish();
        });
    }
    public void starting_observe_send_review_ok(){
        eachNewCarpingViewModel.fix_send_ok.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if(integer==1){
                    SharedPreferenceManager.getInstance(context).setInt("newcarping_review_fix", 1);
                    finish();
                }else if(integer==-1){
                    Toast.makeText(context, "10M 이하의 이미지로 선택해주세요!", Toast.LENGTH_SHORT).show();
                }
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
                currentImageUri=data.getData();
                if(currentImageUri!=null){
                    fixBinding.pictureBtn.setVisibility(View.GONE);
                    fixBinding.selectPictureLayout.setVisibility(View.VISIBLE);
                    Glide.with(context).load(currentImageUri).into(fixBinding.selectPictureBtn);
                    send_image_ok=true;
                    if(send_image_ok && send_text_ok) {
                        fixBinding.reviewBtn.setBackgroundColor(Color.BLACK);
                    }else{
                        fixBinding.reviewBtn.setBackgroundColor(Color.parseColor("#A4A4A4"));
                    }
                }
            }
        }
    }
}