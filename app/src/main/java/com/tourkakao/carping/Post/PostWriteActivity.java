package com.tourkakao.carping.Post;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tourkakao.carping.EcoCarping.Activity.EcoCarpingWriteActivity;
import com.tourkakao.carping.Gallerypermission.Gallery_setting;
import com.tourkakao.carping.NetworkwithToken.CommonClass;
import com.tourkakao.carping.NetworkwithToken.TotalApiClient;
import com.tourkakao.carping.R;
import com.tourkakao.carping.SharedPreferenceManager.SharedPreferenceManager;
import com.tourkakao.carping.databinding.ActivityPostRegisterBinding;
import com.tourkakao.carping.databinding.ActivityPostWriteBinding;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class PostWriteActivity extends AppCompatActivity {
    private ActivityPostWriteBinding binding;
    private Context context;
    private MutableLiveData<Integer> addCount=new MutableLiveData<>();
    private boolean thumbnailCheck,titleCheck=false;
    private boolean[] subtitleCheck={false,false,false,false,false};
    private boolean content1Check,content2Check,content3Check,content4Check,content5Check=false;
    private boolean image1Check,image2Check,image3Check,image4Check,image5Check=false;
    private Toast myToast;
    private String[] uriList={"","","","","",""};
    private HashMap<String, String> map=new HashMap<>();
    private Gallery_setting gallery_setting;
    public static Activity postWriteActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityPostWriteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        context=getApplicationContext();
        gallery_setting=new Gallery_setting(this, PostWriteActivity.this);
        postWriteActivity=PostWriteActivity.this;

        initLayout();
        addImage();
        checkChangeText();

        binding.add1.setOnClickListener(addListener);
        binding.add2.setOnClickListener(addListener);
        binding.add3.setOnClickListener(addListener);
        binding.add4.setOnClickListener(addListener);
        binding.add5.setOnClickListener(addListener);
        addCount.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if(integer==1){
                    binding.contentArea2.setVisibility(View.VISIBLE);
                    scrollDown();
                    changeButtonColor(checkAll());
                }
                if(integer==2){
                    binding.contentArea3.setVisibility(View.VISIBLE);
                    scrollDown();
                    changeButtonColor(checkAll());
                }
                if(integer==3){
                    binding.contentArea4.setVisibility(View.VISIBLE);
                    scrollDown();
                    changeButtonColor(checkAll());
                }
                if(integer==4){
                    binding.contentArea5.setVisibility(View.VISIBLE);
                    scrollDown();
                    changeButtonColor(checkAll());
                }
                if(integer>4){
                    myToast = Toast.makeText(getApplicationContext(),"더 이상 추가 할 수 없습니다", Toast.LENGTH_SHORT);
                    myToast.show();
                    return;
                }
            }
        });
        binding.completionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!thumbnailCheck){
                    myToast = Toast.makeText(getApplicationContext(),"썸네일을 입력해주세요", Toast.LENGTH_SHORT);
                    myToast.show();
                    return;
                }
                if(!titleCheck){
                    myToast = Toast.makeText(getApplicationContext(),"제목을 입력해주세요", Toast.LENGTH_SHORT);
                    myToast.show();
                    return;
                }
                boolean subCheck=true;
                for(int i=0;i<=addCount.getValue();i++){
                    if(!subtitleCheck[i])
                        subCheck=false;
                }
                if(!subCheck){
                    myToast = Toast.makeText(getApplicationContext(),"소제목을 입력해주세요", Toast.LENGTH_SHORT);
                    myToast.show();
                    return;
                }
                if(!checkAll()){
                    myToast = Toast.makeText(getApplicationContext(),"소제목에 대한 내용이나 사진을 입력해주세요", Toast.LENGTH_SHORT);
                    myToast.show();
                    return;
                }
                if(!subtitleCheck[2]){
                    myToast = Toast.makeText(getApplicationContext(),"소제목을 최소 3개 입력해주세요", Toast.LENGTH_SHORT);
                    myToast.show();
                    return;
                }
                settingPostData();
                Intent intent=new Intent(context, PostSettingActivity.class);
                intent.putExtra("map",map);
                intent.putExtra("image",uriList);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @androidx.annotation.NonNull String[] permissions, @androidx.annotation.NonNull int[] grantResults) {
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

    public boolean checkAll(){
        boolean checkTotal=true;
        if(subtitleCheck[0]) {
            if (!(content1Check || image1Check)) {
                checkTotal=false;
            }
        }
        if(subtitleCheck[1]) {
            if (!(content2Check || image2Check)) {
                checkTotal=false;
            }
        }
        if(subtitleCheck[2]) {
            if (!(content3Check || image3Check)) {
                checkTotal=false;
            }
        }
        if(subtitleCheck[3]) {
            if (!(content4Check || image4Check)) {
                checkTotal=false;
            }
        }
        if(subtitleCheck[4]) {
            if (!(content5Check || image5Check)) {
                checkTotal=false;
            }
        }
        if(!titleCheck||!thumbnailCheck)
            checkTotal=false;
        for(int i=0;i<=addCount.getValue();i++){
            if(!subtitleCheck[i])
                checkTotal=false;
        }
        return checkTotal;
    }

    public void changeButtonColor(boolean check){
        boolean allContentCheck=check;
        for(int i=0;i<=addCount.getValue();i++){
            if(!subtitleCheck[i])
                allContentCheck=false;
        }
        if(allContentCheck&&subtitleCheck[2])
            binding.completionButton.setBackgroundColor(Color.BLACK);
        else
            binding.completionButton.setBackgroundColor(Color.parseColor("#999999"));
    }

    public void scrollDown(){
        binding.scrollView.post(new Runnable() {
            @Override
            public void run() {
                binding.scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }

    void checkChangeText(){
        binding.content1.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 입력되는 텍스트에 변화가 있을 때 호출된다.
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // 입력이 끝났을 때 호출된다.
                if(binding.content1.getText().toString().length()==0){
                    content1Check=false;
                }
                else{
                    content1Check=true;
                }
                changeButtonColor(checkAll());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // 입력하기 전에 호출된다.
            }
        });
        binding.content2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(binding.content2.getText().toString().length()==0){
                    content2Check=false;
                }
                else{
                    content2Check=true;
                }
                changeButtonColor(checkAll());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.content3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(binding.content3.getText().toString().length()==0){
                    content3Check=false;
                }
                else{
                    content3Check=true;
                }
                changeButtonColor(checkAll());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.content4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(binding.content4.getText().toString().length()==0){
                    content4Check=false;
                }
                else{
                    content4Check=true;
                }
                changeButtonColor(checkAll());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.content5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(binding.content5.getText().toString().length()==0){
                    content5Check=false;
                }
                else{
                    content5Check=true;
                }
                changeButtonColor(checkAll());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.subheading1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(binding.subheading1.getText().toString().length()==0){
                    subtitleCheck[0]=false;
                }
                else{
                    subtitleCheck[0]=true;
                }
                changeButtonColor(checkAll());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.subheading2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(binding.subheading2.getText().toString().length()==0){
                    subtitleCheck[1]=false;
                }
                else{
                    subtitleCheck[1]=true;
                }
                changeButtonColor(checkAll());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.subheading3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(binding.subheading3.getText().toString().length()==0){
                    subtitleCheck[2]=false;
                }
                else{
                    subtitleCheck[2]=true;
                }
                changeButtonColor(checkAll());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.subheading4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(binding.subheading4.getText().toString().length()==0){
                    subtitleCheck[3]=false;
                }
                else{
                    subtitleCheck[3]=true;
                }
                changeButtonColor(checkAll());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.subheading5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(binding.subheading5.getText().toString().length()==0){
                    subtitleCheck[4]=false;
                }
                else{
                    subtitleCheck[4]=true;
                }
                changeButtonColor(checkAll());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(binding.title.getText().toString().length()==0){
                    titleCheck=false;
                }
                else{
                    titleCheck=true;
                }
                changeButtonColor(checkAll());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    View.OnClickListener addListener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(addCount.getValue()==0&&binding.subheading1.getText().length()==0){
                myToast = Toast.makeText(getApplicationContext(),"내용을 채우고 문단을 추가해주세요", Toast.LENGTH_SHORT);
                myToast.show();
                return;
            }
            if(addCount.getValue()==1&&binding.subheading2.getText().length()==0){
                myToast = Toast.makeText(getApplicationContext(),"내용을 채우고 문단을 추가해주세요", Toast.LENGTH_SHORT);
                myToast.show();
                return;
            }
            if(addCount.getValue()==2&&binding.subheading3.getText().length()==0){
                myToast = Toast.makeText(getApplicationContext(),"내용을 채우고 문단을 추가해주세요", Toast.LENGTH_SHORT);
                myToast.show();
                return;
            }
            if(addCount.getValue()==3&&binding.subheading4.getText().length()==0){
                myToast = Toast.makeText(getApplicationContext(),"내용을 채우고 문단을 추가해주세요", Toast.LENGTH_SHORT);
                myToast.show();
                return;
            }
            addCount.setValue(addCount.getValue()+1);
        }
    };
    View.OnClickListener image1Listener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (Build.VERSION.SDK_INT >= 23) {
                int permission_read = context.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
                int permission_write = context.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (permission_read == PackageManager.PERMISSION_DENIED || permission_write == PackageManager.PERMISSION_DENIED) {
                    gallery_setting.check_gallery_permission();
                } else {
                    Intent galleryintent = new Intent(Intent.ACTION_GET_CONTENT);
                    galleryintent.setType("image/*");
                    startActivityForResult(galleryintent, 1);
                }
            } else {
                Intent galleryintent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryintent.setType("image/*");
                startActivityForResult(galleryintent, 1);
            }
        }
    };
    View.OnClickListener image2Listener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (Build.VERSION.SDK_INT >= 23) {
                int permission_read = context.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
                int permission_write = context.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (permission_read == PackageManager.PERMISSION_DENIED || permission_write == PackageManager.PERMISSION_DENIED) {
                    gallery_setting.check_gallery_permission();
                } else {
                    Intent galleryintent = new Intent(Intent.ACTION_GET_CONTENT);
                    galleryintent.setType("image/*");
                    startActivityForResult(galleryintent, 2);
                }
            } else {
                Intent galleryintent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryintent.setType("image/*");
                startActivityForResult(galleryintent, 2);
            }
        }
    };
    View.OnClickListener image3Listener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (Build.VERSION.SDK_INT >= 23) {
                int permission_read = context.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
                int permission_write = context.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (permission_read == PackageManager.PERMISSION_DENIED || permission_write == PackageManager.PERMISSION_DENIED) {
                    gallery_setting.check_gallery_permission();
                } else {
                    Intent galleryintent = new Intent(Intent.ACTION_GET_CONTENT);
                    galleryintent.setType("image/*");
                    startActivityForResult(galleryintent, 3);
                }
            } else {
                Intent galleryintent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryintent.setType("image/*");
                startActivityForResult(galleryintent, 3);
            }
        }
    };
    View.OnClickListener image4Listener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (Build.VERSION.SDK_INT >= 23) {
                int permission_read = context.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
                int permission_write = context.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (permission_read == PackageManager.PERMISSION_DENIED || permission_write == PackageManager.PERMISSION_DENIED) {
                    gallery_setting.check_gallery_permission();
                } else {
                    Intent galleryintent = new Intent(Intent.ACTION_GET_CONTENT);
                    galleryintent.setType("image/*");
                    startActivityForResult(galleryintent, 4);
                }
            } else {
                Intent galleryintent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryintent.setType("image/*");
                startActivityForResult(galleryintent, 4);
            }
        }
    };
    View.OnClickListener image5Listener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (Build.VERSION.SDK_INT >= 23) {
                int permission_read = context.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
                int permission_write = context.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (permission_read == PackageManager.PERMISSION_DENIED || permission_write == PackageManager.PERMISSION_DENIED) {
                    gallery_setting.check_gallery_permission();
                } else {
                    Intent galleryintent = new Intent(Intent.ACTION_GET_CONTENT);
                    galleryintent.setType("image/*");
                    startActivityForResult(galleryintent, 5);
                }
            } else {
                Intent galleryintent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryintent.setType("image/*");
                startActivityForResult(galleryintent, 5);
            }
        }
    };

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==0&&resultCode==RESULT_OK){
            thumbnailCheck=true;
            Uri uri = data.getData();
            uriList[0]=getPath(uri);
            Glide.with(context).load(uri).into(binding.thumbnail);
            changeButtonColor(checkAll());
        }
        if(requestCode==1&&resultCode==RESULT_OK){
            image1Check=true;
            Uri uri = data.getData();
            uriList[1]=getPath(uri);
            binding.image1.setVisibility(View.GONE);
            Glide.with(context).load(uri).into(binding.imageChange1);
            changeButtonColor(checkAll());
        }
        if(requestCode==2&&resultCode==RESULT_OK){
            image2Check=true;
            Uri uri = data.getData();
            uriList[2]=getPath(uri);
            binding.image2.setVisibility(View.GONE);
            Glide.with(context).load(uri).into(binding.imageChange2);
            changeButtonColor(checkAll());
        }
        if(requestCode==3&&resultCode==RESULT_OK){
            image3Check=true;
            Uri uri = data.getData();
            uriList[3]=getPath(uri);
            binding.image3.setVisibility(View.GONE);
            Glide.with(context).load(uri).into(binding.imageChange3);
            changeButtonColor(checkAll());
        }
        if(requestCode==4&&resultCode==RESULT_OK){
            image4Check=true;
            Uri uri = data.getData();
            uriList[4]=getPath(uri);
            binding.image4.setVisibility(View.GONE);
            Glide.with(context).load(uri).into(binding.imageChange4);
            changeButtonColor(checkAll());
        }
        if(requestCode==5&&resultCode==RESULT_OK){
            image5Check=true;
            Uri uri = data.getData();
            uriList[5]=getPath(uri);
            binding.image5.setVisibility(View.GONE);
            Glide.with(context).load(uri).into(binding.imageChange5);
            changeButtonColor(checkAll());
        }
    }

    public void settingPostData(){
        map.put("author_comment", getIntent().getStringExtra("channel"));
        map.put("kakao_openchat_url", getIntent().getStringExtra("openchat"));
        map.put("title", binding.title.getText().toString());
        map.put("sub_title1",binding.subheading1.getText().toString());
        map.put("text1", binding.content1.getText().toString());
        map.put("sub_title2", binding.subheading2.getText().toString());
        map.put("text2", binding.content2.getText().toString());
        map.put("sub_title3", binding.subheading3.getText().toString());
        map.put("text3", binding.content3.getText().toString());
        map.put("sub_title4", binding.subheading4.getText().toString());
        map.put("text4", binding.content4.getText().toString());
        map.put("sub_title5",binding.subheading5.getText().toString());
        map.put("text5", binding.content5.getText().toString());
    }

    public void initLayout(){
        Glide.with(context).load(R.drawable.thumbnail_empty).into(binding.thumbnail);
        Glide.with(context).load(R.drawable.add_image).into(binding.addContentImg1);
        Glide.with(context).load(R.drawable.post_photo_empty).into(binding.image1);
        Glide.with(context).load(R.drawable.add_image).into(binding.addContentImg2);
        Glide.with(context).load(R.drawable.post_photo_empty).into(binding.image2);
        Glide.with(context).load(R.drawable.add_image).into(binding.addContentImg3);
        Glide.with(context).load(R.drawable.post_photo_empty).into(binding.image3);
        Glide.with(context).load(R.drawable.add_image).into(binding.addContentImg4);
        Glide.with(context).load(R.drawable.post_photo_empty).into(binding.image4);
        Glide.with(context).load(R.drawable.add_image).into(binding.addContentImg5);
        Glide.with(context).load(R.drawable.post_photo_empty).into(binding.image5);
        binding.contentArea2.setVisibility(View.GONE);
        binding.contentArea3.setVisibility(View.GONE);
        binding.contentArea4.setVisibility(View.GONE);
        binding.contentArea5.setVisibility(View.GONE);
        addCount.setValue(0);
        Glide.with(context).load(R.drawable.back).into(binding.back);
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void addImage(){
        binding.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= 23) {
                    int permission_read = context.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
                    int permission_write = context.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    if (permission_read == PackageManager.PERMISSION_DENIED || permission_write == PackageManager.PERMISSION_DENIED) {
                        gallery_setting.check_gallery_permission();
                    } else {
                        Intent galleryintent = new Intent(Intent.ACTION_GET_CONTENT);
                        galleryintent.setType("image/*");
                        startActivityForResult(galleryintent, 0);
                    }
                } else {
                    Intent galleryintent = new Intent(Intent.ACTION_GET_CONTENT);
                    galleryintent.setType("image/*");
                    startActivityForResult(galleryintent, 0);
                }
            }
        });
        binding.image1.setOnClickListener(image1Listener);
        binding.imageChange1.setOnClickListener(image1Listener);
        binding.image2.setOnClickListener(image2Listener);
        binding.imageChange2.setOnClickListener(image2Listener);
        binding.image3.setOnClickListener(image3Listener);
        binding.imageChange3.setOnClickListener(image3Listener);
        binding.image4.setOnClickListener(image4Listener);
        binding.imageChange4.setOnClickListener(image4Listener);
        binding.image5.setOnClickListener(image5Listener);
        binding.imageChange5.setOnClickListener(image5Listener);
    }

    public String getPath(Uri uri){
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
                if (isExternalStorageDocument(uri)) {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];
                    if ("primary".equalsIgnoreCase(type)) {
                        return Environment.getExternalStorageDirectory() + "/" + split[1];
                    }
                }
                else if (isDownloadsDocument(uri)) {
                    final String id = DocumentsContract.getDocumentId(uri);
                    final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                    return getDataColumn(context, contentUri, null, null);
                }
                else if (isMediaDocument(uri)) {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];
                    Uri contentUri = null;
                    if ("image".equals(type)) {
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    } else if ("video".equals(type)) {
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                    } else if ("audio".equals(type)) {
                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                    }
                    final String selection = "_id=?";
                    final String[] selectionArgs = new String[]{
                            split[1]
                    };
                    return getDataColumn(context, contentUri, selection, selectionArgs);
                }
            }
            else if ("content".equalsIgnoreCase(uri.getScheme())) {
                return getDataColumn(context, uri, null, null);
            }
            else if ("file".equalsIgnoreCase(uri.getScheme())) {
                return uri.getPath();
            }
        }
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }
}