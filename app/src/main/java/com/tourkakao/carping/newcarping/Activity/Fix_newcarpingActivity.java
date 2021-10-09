package com.tourkakao.carping.newcarping.Activity;

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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.tourkakao.carping.Gallerypermission.Gallery_setting;
import com.tourkakao.carping.R;
import com.tourkakao.carping.SharedPreferenceManager.SharedPreferenceManager;
import com.tourkakao.carping.databinding.ActivityFixNewcarpingBinding;
import com.tourkakao.carping.databinding.EachImageBinding;
import com.tourkakao.carping.newcarping.viewmodel.FixNewCarpingViewModel;
import com.tourkakao.carping.registernewcarping.Activity.TagsActivity;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.util.ArrayList;

public class Fix_newcarpingActivity extends AppCompatActivity {
    ActivityFixNewcarpingBinding fixNewcarpingBinding;
    Gallery_setting gallery_setting;
    FixNewCarpingViewModel viewModel;
    Context context;
    MapView mapView;
    int pk;
    private TextView addtag=null;
    int image_count=0;
    boolean send_image_ok=true;
    boolean send_text_ok=true;
    boolean send_title_ok=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fixNewcarpingBinding=ActivityFixNewcarpingBinding.inflate(getLayoutInflater());
        setContentView(fixNewcarpingBinding.getRoot());
        context=this;
        gallery_setting=new Gallery_setting(context, Fix_newcarpingActivity.this);

        viewModel=new ViewModelProvider(this).get(FixNewCarpingViewModel.class);
        viewModel.setContext(context);
        initialize();
        setting_locate();
        setting_add_tags();
        setting_initial_tags();
        setting_initial_images();
        setting_getting_review_image();
        setting_remove_tags();
        setting_title();
        setting_review_edittext();
        starting_observe_edit_ok();
        setting_editing_button();
    }
    public void setting_locate(){
        mapView=new MapView(this);
        fixNewcarpingBinding.mapView.addView(mapView);
        MapPoint mapPoint=MapPoint.mapPointWithGeoCoord(viewModel.lat, viewModel.lon);
        mapView.setMapCenterPointAndZoomLevel(mapPoint, 4, true);
        MapPOIItem marker=new MapPOIItem();
        marker.setItemName("위치");
        marker.setMapPoint(mapPoint);
        marker.setMarkerType(MapPOIItem.MarkerType.CustomImage);
        marker.setCustomImageResourceId(R.drawable.nowmarker);
        marker.setCustomImageAutoscale(false);
        mapView.addPOIItem(marker);
    }
    public void initialize(){
        Intent intent=getIntent();
        viewModel.userpk=intent.getIntExtra("userpk", 0);
        viewModel.pk=intent.getIntExtra("postpk", 0);
        pk=intent.getIntExtra("postpk", 0);
        viewModel.lat=intent.getDoubleExtra("lat", 0.0f);
        viewModel.lon=intent.getDoubleExtra("lon", 0.0f);
        viewModel.f_title=intent.getStringExtra("title");
        viewModel.f_review=intent.getStringExtra("review");
        viewModel.f_tags=intent.getStringArrayListExtra("tags");
        fixNewcarpingBinding.titleEdittext.setText(viewModel.f_title);
        fixNewcarpingBinding.reviewEdittext.setText(viewModel.f_review);
        fixNewcarpingBinding.textCount.setText(viewModel.f_review.length()+"/100");
        if(intent.getStringExtra("image1")!=null) {
            image_count++;
        }
        if(intent.getStringExtra("image2")!=null) {
            image_count++;
        }
        if(intent.getStringExtra("image3")!=null) {
            image_count++;
        }
        if(intent.getStringExtra("image4")!=null) {
            image_count++;
        }
        viewModel.initial_images.add(intent.getStringExtra("image1"));
        viewModel.initial_images.add(intent.getStringExtra("image2"));
        viewModel.initial_images.add(intent.getStringExtra("image3"));
        viewModel.initial_images.add(intent.getStringExtra("image4"));
        viewModel.f_images.add(intent.getStringExtra("image1"));
        viewModel.f_images.add(intent.getStringExtra("image2"));
        viewModel.f_images.add(intent.getStringExtra("image3"));
        viewModel.f_images.add(intent.getStringExtra("image4"));
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
        fixNewcarpingBinding.tagLayout.addView(addtag);
    }
    public void setting_initial_images(){
        fixNewcarpingBinding.imageCnt.setText(image_count+"/4");
        for(int i=0; i<viewModel.f_images.size(); i++){
            if(viewModel.f_images.get(i)==null){
                break;
            }
            EachImageBinding binding=EachImageBinding.inflate(getLayoutInflater());
            ImageView addimage=binding.addEachImage;
            ImageView cancelimage=binding.cancelImage;
            cancelimage.setClickable(true);
            Glide.with(context).load(viewModel.f_images.get(i)).transform(new CenterCrop(), new RoundedCorners(30)).into(addimage);
            Glide.with(context).load(R.drawable.cancel_image).transform(new CenterCrop(), new RoundedCorners(30)).into(cancelimage);
            int finalI = i;
            cancelimage.setOnClickListener(v -> {
                fixNewcarpingBinding.imageAreaLayout.removeView(binding.getRoot());
                viewModel.f_images.set(finalI, null);
                image_count--;
                fixNewcarpingBinding.imageCnt.setText(image_count+"/4");
                if(image_count==0){
                    send_image_ok=false;
                }
                changeing_register_button();
            });
            fixNewcarpingBinding.imageAreaLayout.addView(binding.getRoot());
            send_image_ok=true;
            changeing_register_button();
        }
    }
    public void setting_getting_review_image(){
        fixNewcarpingBinding.addImage.setOnClickListener(v -> {
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
    public void setting_remove_tags(){
        fixNewcarpingBinding.tagsRemove.setOnClickListener(v-> {
            fixNewcarpingBinding.tagLayout.removeAllViews();
            viewModel.f_tags.clear();
            setting_add_tags();
        });
    }
    public void setting_initial_tags(){
        for(int i=0; i<viewModel.f_tags.size(); i++){
            TextView newtag=new TextView(context);
            newtag.setText("#"+viewModel.f_tags.get(i));
            newtag.setBackgroundResource(R.drawable.purple_border_round);
            newtag.setTextColor(Color.parseColor("#9F81F7"));
            newtag.setPadding(60, 30, 60, 30);
            fixNewcarpingBinding.tagLayout.addView(newtag);
        }
    }

    public void setting_title(){
        fixNewcarpingBinding.titleEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.f_title=s.toString();
                if(s.toString().length()<=0){
                    send_title_ok=false;
                }else{
                    send_text_ok=true;
                }
                changeing_register_button();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    public void setting_review_edittext(){
        fixNewcarpingBinding.reviewEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.f_review=s.toString();
                if(s.toString().length()<=0){
                    send_text_ok=false;
                }else{
                    send_text_ok=true;
                }
                fixNewcarpingBinding.textCount.setText(s.toString().length()+"/100");
                changeing_register_button();
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
    public void changeing_register_button(){
        if(send_image_ok && send_text_ok && send_title_ok){
            fixNewcarpingBinding.fix.setBackgroundColor(Color.BLACK);
        }else{
            fixNewcarpingBinding.fix.setBackgroundColor(Color.parseColor("#A4A4A4"));
        }
    }
    public void starting_observe_edit_ok(){
        viewModel.newcarping_edit_ok.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if(integer==1){
                    SharedPreferenceManager.getInstance(getApplicationContext()).setInt("newcarping", 1);
                    fixNewcarpingBinding.mapView.removeView(mapView);
                    mapView=null;
                    Toast.makeText(context, "수정되었어요!", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent();
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }
    public void setting_editing_button(){
        fixNewcarpingBinding.fix.setOnClickListener(v -> {
            if(send_image_ok && send_title_ok && send_text_ok){
                viewModel.edit_newcarping();
            }else if(!send_image_ok){
                Toast.makeText(context, "사진을 업로드해주세요!", Toast.LENGTH_SHORT).show();
                return;
            }else if(!send_title_ok){
                Toast.makeText(context, "차박지 이름을 적어주세요!", Toast.LENGTH_SHORT).show();
                return;
            }else if(!send_text_ok){
                Toast.makeText(context, "내용을 적어주세요!", Toast.LENGTH_SHORT).show();
                return;
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if(requestCode==1001){
                String tag = data.getStringExtra("tag");
                viewModel.f_tags.add(tag);
                TextView newtag = new TextView(context);
                newtag.setText("#" + tag);
                newtag.setBackgroundResource(R.drawable.purple_border_round);
                newtag.setTextColor(Color.parseColor("#9F81F7"));
                newtag.setPadding(60, 30, 60, 30);
                fixNewcarpingBinding.tagLayout.addView(newtag);
            }else if(requestCode==gallery_setting.GALLERY_CODE){
                if(data.getData()!=null){
                    int idx=0;
                    for(int i=0; i<4; i++){
                        if(viewModel.f_images.get(i)==null){
                            viewModel.f_uri.set(i, data.getData());
                            viewModel.f_images.set(i, "imageok");
                            idx=i;
                            break;
                        }
                    }
                    image_count++;
                    fixNewcarpingBinding.imageCnt.setText(image_count+"/4");
                    EachImageBinding binding=EachImageBinding.inflate(getLayoutInflater());
                    ImageView addimage=binding.addEachImage;
                    ImageView cancelimage=binding.cancelImage;
                    cancelimage.setClickable(true);
                    Glide.with(context).load(data.getData()).transform(new CenterCrop(), new RoundedCorners(30)).into(addimage);
                    Glide.with(context).load(R.drawable.cancel_image).transform(new CenterCrop(), new RoundedCorners(30)).into(cancelimage);
                    int finalIdx=idx;
                    cancelimage.setOnClickListener(v -> {
                        fixNewcarpingBinding.imageAreaLayout.removeView(binding.getRoot());
                        viewModel.f_uri.set(finalIdx, null);
                        viewModel.f_images.set(finalIdx, null);
                        image_count--;
                        fixNewcarpingBinding.imageCnt.setText(image_count+"/4");
                        if(image_count==0){
                            send_image_ok=false;
                        }
                        changeing_register_button();
                    });
                    fixNewcarpingBinding.imageAreaLayout.addView(binding.getRoot());
                    send_image_ok=true;
                    changeing_register_button();
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        fixNewcarpingBinding.mapView.removeView(mapView);
        mapView=null;
    }
}