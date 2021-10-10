package com.tourkakao.carping.sharedetail.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Context;
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
import com.tourkakao.carping.databinding.ActivityFixShareBinding;
import com.tourkakao.carping.databinding.EachImageBinding;
import com.tourkakao.carping.newcarping.Activity.Fix_newcarpingActivity;
import com.tourkakao.carping.registernewcarping.Activity.TagsActivity;
import com.tourkakao.carping.registernewshare.Activity.LocateSelectActivity;
import com.tourkakao.carping.sharedetail.viewmodel.ShareFixViewModel;

public class FixShareActivity extends AppCompatActivity {
    ActivityFixShareBinding fixShareBinding;
    Context context;
    int postpk;
    ShareFixViewModel fixViewModel;
    Gallery_setting gallery_setting;
    TextView addtag;
    int image_count=0;
    boolean send_image_ok=true;
    boolean send_text_ok=true;
    boolean send_title_ok=true;
    boolean send_locate_ok=true;
    boolean change_locate=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fixShareBinding=ActivityFixShareBinding.inflate(getLayoutInflater());
        setContentView(fixShareBinding.getRoot());
        context=this;
        gallery_setting=new Gallery_setting(context, FixShareActivity.this);
        postpk=getIntent().getIntExtra("pk", 0);

        fixViewModel=new ViewModelProvider(this).get(ShareFixViewModel.class);
        fixViewModel.setContext(context);
        fixViewModel.setPostpk(postpk);

        starting_observe_getting_detail();
        setting_add_tags();
        fixViewModel.getting_share();
        setting_remove_tags();
        setting_locate_btn();
        setting_title();
        setting_text();
        setting_getting_image();
        setting_cancel_btn();
        setting_editing_button();
        starting_observe_edit_ok();
    }
    public void starting_observe_getting_detail(){
        fixViewModel.get_clear.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if(integer==1){
                    fixShareBinding.locateTextview.setText(fixViewModel.sregion);
                    fixShareBinding.titleEdittext.setText(fixViewModel.stitle);
                    fixShareBinding.reviewEdittext.setText(fixViewModel.stext);
                    fixShareBinding.textCount.setText(fixViewModel.stext.length()+"/500");
                    fixShareBinding.chatEdittext.setText(fixViewModel.schat_addr);
                    setting_initial_tags();
                    if(fixViewModel.simage1!=null){
                        image_count++;
                    }
                    if(fixViewModel.simage2!=null){
                        image_count++;
                    }
                    if(fixViewModel.simage3!=null){
                        image_count++;
                    }
                    if(fixViewModel.simage4!=null){
                        image_count++;
                    }
                    setting_initial_images();
                }
            }
        });
    }
    public void setting_remove_tags(){
        fixShareBinding.tagsRemove.setOnClickListener(v ->{
            fixShareBinding.tagLayout.removeAllViews();
            fixViewModel.stags.clear();
            setting_add_tags();
        });
    }
    public void setting_locate_btn(){
        fixShareBinding.locateTextview.setOnClickListener(v -> {
            Intent intent=new Intent(context, LocateSelectActivity.class);
            startActivityForResult(intent, 1002);
        });
        fixShareBinding.reLocateTextview.setOnClickListener(v -> {
            Intent intent=new Intent(context, LocateSelectActivity.class);
            startActivityForResult(intent, 1002);
        });
    }
    public void setting_add_tags(){
        addtag=new TextView(context);
        addtag.setText("+");
        addtag.setClickable(true);
        addtag.setBackgroundResource(R.drawable.purple_border_round);
        addtag.setPadding(60, 30, 60, 30);
        addtag.setOnClickListener(v -> {
            Intent intent=new Intent(context, TagsActivity.class);
            startActivityForResult(intent, 1001);
        });
        fixShareBinding.tagLayout.addView(addtag);
    }
    public void setting_initial_tags(){
        for(int i=0; i<fixViewModel.stags.size(); i++){
            TextView newtag=new TextView(context);
            newtag.setText("#"+fixViewModel.stags.get(i));
            newtag.setBackgroundResource(R.drawable.purple_border_round);
            newtag.setTextColor(Color.parseColor("#9F81F7"));
            newtag.setPadding(60, 30, 60, 30);
            fixShareBinding.tagLayout.addView(newtag);
        }
    }
    public void setting_initial_images(){
        Glide.with(context).load(R.drawable.locate_img).into(fixShareBinding.locateImg);
        Glide.with(context).load(R.drawable.picture_btn_img).into(fixShareBinding.addImage2);
        fixShareBinding.yesImageLayout.setVisibility(View.VISIBLE);
        fixShareBinding.noImageLayout.setVisibility(View.GONE);
        fixShareBinding.imageCnt.setText(image_count+"/4");
        for(int i=0; i<fixViewModel.f_images.size(); i++){
            if(fixViewModel.f_images.get(i)==null){
                break;
            }
            EachImageBinding binding=EachImageBinding.inflate(getLayoutInflater());
            ImageView addimage=binding.addEachImage;
            ImageView cancelimage=binding.cancelImage;
            cancelimage.setClickable(true);
            Glide.with(context).load(fixViewModel.f_images.get(i)).transform(new CenterCrop(), new RoundedCorners(30)).into(addimage);
            Glide.with(context).load(R.drawable.cancel_image).transform(new CenterCrop(), new RoundedCorners(30)).into(cancelimage);
            int finalI = i;
            cancelimage.setOnClickListener(v -> {
                fixShareBinding.imageAreaLayout.removeView(binding.getRoot());
                fixViewModel.f_images.set(finalI, null);
                image_count--;
                fixShareBinding.imageCnt.setText(image_count+"/4");
                if(image_count==0){
                    fixShareBinding.yesImageLayout.setVisibility(View.GONE);
                    fixShareBinding.noImageLayout.setVisibility(View.VISIBLE);
                    send_image_ok=false;
                }
                changeing_register_button();
            });
            fixShareBinding.imageAreaLayout.addView(binding.getRoot());
            send_image_ok=true;
            changeing_register_button();
        }
    }

    public void changeing_register_button(){
        if(send_image_ok && send_locate_ok && send_text_ok && send_title_ok){
            fixShareBinding.registerBtn.setBackgroundColor(Color.BLACK);
        }else{
            fixShareBinding.registerBtn.setBackgroundColor(Color.parseColor("#A4A4A4"));
        }
    }
    public void setting_title(){
        fixShareBinding.titleEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                fixViewModel.stitle=s.toString();
                if(s.toString().length()<=0){
                    send_title_ok=false;
                }else{
                    send_title_ok=true;
                }
                changeing_register_button();
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });
    }
    public void setting_text(){
        fixShareBinding.reviewEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                fixViewModel.stext=s.toString();
                if(s.toString().length()<=0){
                    send_text_ok=false;
                }else{
                    send_text_ok=true;
                }
                fixShareBinding.textCount.setText(s.toString().length()+"/500");
                changeing_register_button();
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });
    }
    public void setting_getting_image() {
        fixShareBinding.addImage.setOnClickListener(v -> {
            if (image_count < 4) {
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
            } else {
                Toast.makeText(context, "4장까지 첨부 가능해요", Toast.LENGTH_SHORT).show();
            }
        });
        fixShareBinding.addImage2.setOnClickListener(v -> {
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
        });
    }
    public void setting_cancel_btn(){
        fixShareBinding.cancel.setOnClickListener(v -> {
            finish();
        });
    }
    public void setting_editing_button(){
        fixShareBinding.registerBtn.setOnClickListener(v -> {
            if(send_title_ok && send_text_ok && send_locate_ok && send_image_ok){
                fixViewModel.edit_share(change_locate);
            }else if(!send_locate_ok){
                Toast.makeText(context, "나눔 위치를 선택해주세요!", Toast.LENGTH_SHORT).show();
            }else if(!send_image_ok){
                Toast.makeText(context, "사진을 업로드해주세요!", Toast.LENGTH_SHORT).show();
            }else if(!send_title_ok){
                Toast.makeText(context, "제목을 적어주세요!", Toast.LENGTH_SHORT).show();
            }else if(!send_text_ok){
                Toast.makeText(context, "나눔내용을 적어주세요!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void starting_observe_edit_ok(){
        fixViewModel.share_edit_ok.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if(integer==1){
                    Toast.makeText(context, "수정되었어요!", Toast.LENGTH_SHORT).show();
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
            if(requestCode==1001){
                String tag=data.getStringExtra("tag");
                fixViewModel.stags.add(tag);
                TextView newtag = new TextView(context);
                newtag.setText("#" + tag);
                newtag.setBackgroundResource(R.drawable.purple_border_round);
                newtag.setTextColor(Color.parseColor("#5f51ef"));
                newtag.setPadding(60, 30, 60, 30);
                fixShareBinding.tagLayout.addView(newtag);
            }else if(requestCode==gallery_setting.GALLERY_CODE){
                if(data.getData()!=null){
                    int idx=0;
                    for(int i=0; i<4; i++){
                        if(fixViewModel.f_images.get(i)==null){
                            fixViewModel.f_uri.set(i, data.getData());
                            fixViewModel.f_images.set(i, "imageok");
                            idx=i;
                            break;
                        }
                    }
                    fixShareBinding.noImageLayout.setVisibility(View.GONE);
                    fixShareBinding.yesImageLayout.setVisibility(View.VISIBLE);
                    image_count++;
                    fixShareBinding.imageCnt.setText(image_count+"/4");
                    EachImageBinding binding=EachImageBinding.inflate(getLayoutInflater());
                    ImageView addimage=binding.addEachImage;
                    ImageView cancelimage=binding.cancelImage;
                    cancelimage.setClickable(true);
                    Glide.with(context).load(data.getData()).transform(new CenterCrop(), new RoundedCorners(30)).into(addimage);
                    Glide.with(context).load(R.drawable.cancel_image).transform(new CenterCrop(), new RoundedCorners(30)).into(cancelimage);
                    int finalIdx=idx;
                    cancelimage.setOnClickListener(v -> {
                        fixShareBinding.imageAreaLayout.removeView(binding.getRoot());
                        fixViewModel.f_uri.set(finalIdx, null);
                        fixViewModel.f_images.set(finalIdx, null);
                        image_count--;
                        fixShareBinding.imageCnt.setText(image_count+"/4");
                        if(image_count==0){
                            fixShareBinding.noImageLayout.setVisibility(View.VISIBLE);
                            fixShareBinding.yesImageLayout.setVisibility(View.GONE);
                            send_image_ok=false;
                        }
                        changeing_register_button();
                    });
                    fixShareBinding.imageAreaLayout.addView(binding.getRoot());
                    send_image_ok=true;
                    changeing_register_button();
                }
            }else if(requestCode==1002){
                if(data.getStringExtra("locate")!=null){
                    send_locate_ok=true;
                    change_locate=true;
                    fixViewModel.sregion=data.getStringExtra("locate");
                    fixViewModel.slocate_id=data.getIntExtra("locate_id", -1);
                    System.out.println(fixViewModel.slocate_id+"--------------locate fix");
                    fixShareBinding.locateTextview.setText(fixViewModel.sregion);
                    fixShareBinding.locateTextview.setClickable(false);
                    fixShareBinding.reLocateTextview.setVisibility(View.VISIBLE);
                }
            }
        }
    }
}