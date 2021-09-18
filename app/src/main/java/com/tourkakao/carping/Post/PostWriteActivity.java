package com.tourkakao.carping.Post;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tourkakao.carping.NetworkwithToken.CommonClass;
import com.tourkakao.carping.NetworkwithToken.TotalApiClient;
import com.tourkakao.carping.R;
import com.tourkakao.carping.SharedPreferenceManager.SharedPreferenceManager;
import com.tourkakao.carping.databinding.ActivityPostRegisterBinding;
import com.tourkakao.carping.databinding.ActivityPostWriteBinding;

import java.io.File;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class PostWriteActivity extends AppCompatActivity {
    private ActivityPostWriteBinding binding;
    private Context context;
    private MutableLiveData<Integer> addCount=new MutableLiveData<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityPostWriteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        context=getApplicationContext();

        initLayout();
        addImage();

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
                }
                if(integer==2){
                    binding.contentArea3.setVisibility(View.VISIBLE);
                }
                if(integer==3){
                    binding.contentArea4.setVisibility(View.VISIBLE);
                }
                if(integer==4){
                    binding.contentArea5.setVisibility(View.VISIBLE);
                }
                if(integer>4){
                    Toast myToast = Toast.makeText(getApplicationContext(),"더 이상 추가 할 수 없습니다", Toast.LENGTH_SHORT);
                    myToast.show();
                    return;
                }
            }
        });
        binding.completionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, PostSettingActivity.class);
                startActivity(intent);
            }
        });
    }

    View.OnClickListener addListener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            addCount.setValue(addCount.getValue()+1);
        }
    };
    View.OnClickListener image1Listener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, 1);
        }
    };
    View.OnClickListener image2Listener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, 2);
        }
    };
    View.OnClickListener image3Listener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, 3);
        }
    };
    View.OnClickListener image4Listener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, 4);
        }
    };
    View.OnClickListener image5Listener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, 5);
        }
    };

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==0&&resultCode==RESULT_OK){
            Uri uri = data.getData();
            Glide.with(context).load(uri).into(binding.thumbnail);
        }
        if(requestCode==1&&resultCode==RESULT_OK){
            Uri uri = data.getData();
            binding.image1.setVisibility(View.GONE);
            Glide.with(context).load(uri).into(binding.imageChange1);
        }
        if(requestCode==2&&resultCode==RESULT_OK){
            Uri uri = data.getData();
            binding.image2.setVisibility(View.GONE);
            Glide.with(context).load(uri).into(binding.imageChange2);
        }
        if(requestCode==3&&resultCode==RESULT_OK){
            Uri uri = data.getData();
            binding.image3.setVisibility(View.GONE);
            Glide.with(context).load(uri).into(binding.imageChange3);
        }
        if(requestCode==4&&resultCode==RESULT_OK){
            Uri uri = data.getData();
            binding.image4.setVisibility(View.GONE);
            Glide.with(context).load(uri).into(binding.imageChange4);
        }
        if(requestCode==5&&resultCode==RESULT_OK){
            Uri uri = data.getData();
            binding.image5.setVisibility(View.GONE);
            Glide.with(context).load(uri).into(binding.imageChange5);
        }
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
    }

    public void addImage(){
        binding.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 0);
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