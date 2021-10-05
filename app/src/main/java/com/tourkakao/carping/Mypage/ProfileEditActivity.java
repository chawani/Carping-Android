package com.tourkakao.carping.Mypage;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.google.gson.Gson;
import com.tourkakao.carping.Home.HomeViewModel.MypageViewModel;
import com.tourkakao.carping.Mypage.DTO.Profile;
import com.tourkakao.carping.NetworkwithToken.CommonClass;
import com.tourkakao.carping.NetworkwithToken.TotalApiClient;
import com.tourkakao.carping.R;
import com.tourkakao.carping.SharedPreferenceManager.SharedPreferenceManager;
import com.tourkakao.carping.databinding.ActivityProfileEditBinding;

import java.io.File;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ProfileEditActivity extends AppCompatActivity {
    private ActivityProfileEditBinding binding;
    private Context context;
    private MypageViewModel myViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityProfileEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        myViewModel =new ViewModelProvider(this).get(MypageViewModel.class);
        myViewModel.setContext(context);

        context=getApplicationContext();
        settingImg();
        settingInfo();

        binding.personalInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, PersonalInformationActivity.class);
                startActivity(intent);
            }
        });

        binding.phoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, PhoneCertificationActivity.class);
                startActivity(intent);
            }
        });

        binding.editImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 0);
            }
        });
    }

    public void updateProfileImg(List datas){
        Gson gson=new Gson();
        String totalString=gson.toJson(datas.get(0));
        Profile profile=gson.fromJson(totalString,Profile.class);
        Glide.with(context)
                .load(profile.getImage())
                .transform(new CenterCrop(), new RoundedCorners(100))
                .into(binding.profile);
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==0&&resultCode==RESULT_OK){
            int userId= SharedPreferenceManager.getInstance(context).getInt("id",0);
            Uri uri = data.getData();
            String path=getPath(uri);
            File file = new File(path);
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part image = MultipartBody.Part.createFormData("image", file.getName(), requestBody);
            TotalApiClient.getMypageApiService(context).postProfileImg(Integer.toString(userId),image)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableSingleObserver<CommonClass>() {
                        @Override
                        public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull CommonClass commonClass) {
                            if(commonClass.getCode()==200) {
                                updateProfileImg(commonClass.getData());
                            }
                            else {
                                System.out.println("요청실패:"+commonClass.getCode()+commonClass.getError_message());
                            }
                        }

                        @Override
                        public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                        }
                    });
        }
    }

    public void settingImg(){
        Glide.with(context).load(R.drawable.right_arrow).into(binding.arrow1);
        Glide.with(context).load(R.drawable.right_arrow).into(binding.arrow2);
        Glide.with(context).load(R.drawable.right_arrow).into(binding.arrow3);
    }
    public void settingInfo(){
        myViewModel.getProfileMutableLiveData().observe(this, new Observer<Profile>() {
            @Override
            public void onChanged(Profile profile) {
                Glide.with(context)
                        .load(profile.getImage())
                        .into(binding.profile);
                Glide.with(context).load(R.drawable.profile_img_edit_button).into(binding.editImg);
                Glide.with(context).load(profile.getBadge()).into(binding.badge);
                int levelInteger=(int)Double.parseDouble(profile.getLevel());
                binding.level.setText("LV."+Integer.toString(levelInteger));
                binding.username.setText(profile.getUsername());
                if(profile.getPhone()!=null) {
                    binding.phoneCertificationText.setText(profile.getPhone()+" 인증완료");
                }
            }
        });

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

    @Override
    public void onResume() {
        super.onResume();
        myViewModel.loadProfile();
    }
}