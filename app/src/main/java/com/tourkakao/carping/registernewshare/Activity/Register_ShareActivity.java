package com.tourkakao.carping.registernewshare.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.tourkakao.carping.Gallerypermission.Gallery_setting;
import com.tourkakao.carping.R;
import com.tourkakao.carping.SharedPreferenceManager.SharedPreferenceManager;
import com.tourkakao.carping.databinding.ActivityRegisterShareBinding;
import com.tourkakao.carping.databinding.EachImageBinding;
import com.tourkakao.carping.registernewcarping.Activity.TagsActivity;
import com.tourkakao.carping.registernewshare.viewmodel.RegisterShareViewmodel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register_ShareActivity extends AppCompatActivity {
    ActivityRegisterShareBinding shareBinding;
    Context context;
    RegisterShareViewmodel registerShareViewmodel;
    Gallery_setting gallery_setting;
    //Pattern https = Pattern.compile("^(https?):\\/\\/([^:\\/\\s]+)(:([^\\/]*))?((\\/[^\\s/\\/]+)*)?\\/?([^#\\s\\?]*)(\\?([^#\\s]*))?(#(\\w*))?$");
    //Pattern http = Pattern.compile("^(http?):\\/\\/([^:\\/\\s]+)(:([^\\/]*))?((\\/[^\\s/\\/]+)*)?\\/?([^#\\s\\?]*)(\\?([^#\\s]*))?(#(\\w*))?$");
    boolean send_image_ok=false;
    boolean send_text_ok=false;
    boolean send_locate_ok=false;
    boolean send_title_ok=false;
    boolean send_chat_ok=false;
    int image_count=0;
    private TextView addtag=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shareBinding=ActivityRegisterShareBinding.inflate(getLayoutInflater());
        setContentView(shareBinding.getRoot());
        context=this;
        gallery_setting=new Gallery_setting(context, Register_ShareActivity.this);

        registerShareViewmodel=new ViewModelProvider(this).get(RegisterShareViewmodel.class);
        registerShareViewmodel.setContext(context);
        registerShareViewmodel.setUserpk(SharedPreferenceManager.getInstance(context).getInt("id", 0));

        setting_title();
        setting_locate_btn();
        setting_body();
        setting_chat();
        setting_getting_review_image();
        setting_sending_button();
        setting_initial_tags();
        setting_remove_tags();
        starting_observe_send_ok();
    }
    public void setting_title(){
        shareBinding.titleEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                registerShareViewmodel.s_title=s.toString();
                if(s.toString().length()<=0){
                    send_title_ok=false;
                }else{
                    send_title_ok=true;
                }
                changeing_register_button();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    public void setting_locate_btn(){
        shareBinding.locateTextview.setOnClickListener(v -> {
            Intent intent=new Intent(context, LocateSelectActivity.class);
            startActivityForResult(intent, 1002);
        });
        shareBinding.reLocateTextview.setOnClickListener(v -> {
            Intent intent=new Intent(context, LocateSelectActivity.class);
            startActivityForResult(intent, 1002);
        });
    }
    public void setting_body(){
        shareBinding.reviewEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                registerShareViewmodel.s_body=s.toString();
                if(s.toString().length()<=0){
                    send_text_ok=false;
                }else{
                    send_text_ok=true;
                }
                shareBinding.textCount.setText(s.toString().length()+"/500");
                changeing_register_button();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    public void setting_chat(){
        shareBinding.chatEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                registerShareViewmodel.s_chat=s.toString();
                if(s.toString().length()<=0){
                    send_chat_ok=false;
                }else{
                    send_chat_ok=true;
                }
                changeing_register_button();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    public void setting_getting_review_image(){
        shareBinding.addImage.setOnClickListener(v -> {
            if(image_count<4) {
                if (Build.VERSION.SDK_INT >= 23) {
                    int permission_read = context.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
                    int permission_write = context.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    if (permission_read == PackageManager.PERMISSION_DENIED || permission_write == PackageManager.PERMISSION_DENIED) {
                        gallery_setting.check_gallery_permission();
                    } else {
                        Intent galleryintent = new Intent(Intent.ACTION_GET_CONTENT);
                        galleryintent.setType("image/*");
                        startActivityForResult(galleryintent, gallery_setting.GALLERY_CODE);
                    }
                } else {
                    Intent galleryintent = new Intent(Intent.ACTION_GET_CONTENT);
                    galleryintent.setType("image/*");
                    startActivityForResult(galleryintent, gallery_setting.GALLERY_CODE);
                }
            }else{
                Toast.makeText(context, "4장까지 첨부 가능해요", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void changeing_register_button(){
        if(send_image_ok && send_text_ok && send_locate_ok && send_title_ok && send_chat_ok){
            shareBinding.registerBtn.setBackgroundColor(Color.BLACK);
        }else{
            shareBinding.registerBtn.setBackgroundColor(Color.parseColor("#A4A4A4"));
        }
    }
    public void setting_sending_button(){
        shareBinding.registerBtn.setOnClickListener(v -> {
            if(send_image_ok && send_locate_ok && send_text_ok && send_title_ok && send_chat_ok){
                registerShareViewmodel.sending_newshare();
            }else if(!send_locate_ok){
                Toast.makeText(context, "동네를 선택해주세요!", Toast.LENGTH_SHORT).show();
                return;
            }else if(!send_image_ok){
                Toast.makeText(context, "사진을 업로드해주세요!", Toast.LENGTH_SHORT).show();
                return;
            }else if(!send_title_ok){
                Toast.makeText(context, "제목을 적어주세요!", Toast.LENGTH_SHORT).show();
                return;
            }else if(!send_text_ok){
                Toast.makeText(context, "내용을 적어주세요!", Toast.LENGTH_SHORT).show();
                return;
            }else if(!send_chat_ok){
                Toast.makeText(context, "오픈카톡방 주소를 적어주세요!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void setting_initial_tags(){
        addtag=new TextView(context);
        addtag.setText("+");
        addtag.setClickable(true);
        addtag.setBackgroundResource(R.drawable.purple_border_round);
        addtag.setPadding(60, 30, 60, 30);
        addtag.setOnClickListener(v -> {
            Intent intent=new Intent(context, TagsActivity.class);
            startActivityForResult(intent, 1003);
        });
        shareBinding.tagLayout.addView(addtag);
    }
    public void setting_remove_tags(){
        shareBinding.tagsRemove.setOnClickListener(v -> {
            shareBinding.tagLayout.removeAllViews();
            registerShareViewmodel.s_tags.clear();
            setting_initial_tags();
        });
    }
    public void starting_observe_send_ok(){
        registerShareViewmodel.send_ok.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if(integer==1){
                    Intent intent=new Intent();
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if(requestCode==gallery_setting.GALLERY_CODE){
                if(data.getData()!=null) {
                    int idx=0;
                    for(int i=0; i<4; i++){
                        if(registerShareViewmodel.s_uri.get(i)==null){
                            registerShareViewmodel.s_uri.set(i, data.getData());
                            idx=i;
                            break;
                        }
                    }
                    image_count++;
                    shareBinding.imageCnt.setText(image_count+"/4");
                    EachImageBinding binding=EachImageBinding.inflate(getLayoutInflater());
                    ImageView addimage=binding.addEachImage;
                    ImageView cancelimage=binding.cancelImage;
                    cancelimage.setClickable(true);
                    Glide.with(context).load(data.getData()).transform(new CenterCrop(), new RoundedCorners(30)).into(addimage);
                    Glide.with(context).load(R.drawable.cancel_image).transform(new CenterCrop(), new RoundedCorners(30)).into(cancelimage);
                    int finalIdx = idx;
                    cancelimage.setOnClickListener(v -> {
                        shareBinding.imageAreaLayout.removeView(binding.getRoot());
                        registerShareViewmodel.s_uri.set(finalIdx, null);
                        image_count--;
                        shareBinding.imageCnt.setText(image_count+"/4");
                        if(image_count==0){
                            send_image_ok=false;
                        }
                        changeing_register_button();
                    });
                    shareBinding.imageAreaLayout.addView(binding.getRoot());
                    send_image_ok=true;
                    changeing_register_button();
                }
            }else if(requestCode==1002){
                if(data.getStringExtra("locate")!=null){
                    send_locate_ok=true;
                    registerShareViewmodel.s_locate=data.getStringExtra("locate");
                    registerShareViewmodel.s_locate_id=data.getIntExtra("locate_id", -1);
                    System.out.println(registerShareViewmodel.s_locate_id);
                    shareBinding.locateTextview.setText(registerShareViewmodel.s_locate);
                    shareBinding.locateTextview.setClickable(false);
                    shareBinding.reLocateTextview.setVisibility(View.VISIBLE);
                }
            }else if(requestCode==1003){
                String tag=data.getStringExtra("tag");
                registerShareViewmodel.s_tags.add(tag);
                TextView newtag=new TextView(context);
                newtag.setText("#"+tag);
                newtag.setBackgroundResource(R.drawable.purple_border_round);
                newtag.setTextColor(Color.parseColor("#9F81F7"));
                newtag.setPadding(60, 30, 60, 30);
                shareBinding.tagLayout.addView(newtag);
            }
        }
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
}