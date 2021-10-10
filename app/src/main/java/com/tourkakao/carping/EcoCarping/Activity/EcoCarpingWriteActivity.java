package com.tourkakao.carping.EcoCarping.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.Manifest;
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
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.tourkakao.carping.BuildConfig;
import com.tourkakao.carping.Gallerypermission.Gallery_setting;
import com.tourkakao.carping.NetworkwithToken.CommonClass;
import com.tourkakao.carping.NetworkwithToken.TotalApiClient;
import com.tourkakao.carping.R;
import com.tourkakao.carping.SharedPreferenceManager.SharedPreferenceManager;
import com.tourkakao.carping.databinding.ActivityEcoCarpingWriteBinding;
import com.tourkakao.carping.databinding.ImageItemBinding;
import com.tourkakao.carping.registernewcarping.Activity.RegisterActivity;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

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

public class EcoCarpingWriteActivity extends AppCompatActivity {
    ActivityEcoCarpingWriteBinding ecobinding;
    private static final int REQUEST_CODE = 0;
    private MutableLiveData<ArrayList<Uri>> imageList=new MutableLiveData<>();
    private ArrayList<Uri> uriList=new ArrayList<>();
    private ArrayList<String> uriList2=new ArrayList<>();
    private ArrayList<String> tagList=new ArrayList<>();
    private MutableLiveData<ArrayList<String>> tagListLiveData=new MutableLiveData<>();
    private String KAKAO_KEY="KakaoAK "+ BuildConfig.KAKAO_REST_API_KEY;
    private MapPOIItem marker;
    private MapView mapView;
    private String placeName;
    private MapPoint mapPoint;
    private boolean imgPossible=true;
    private Context context;
    private boolean placeCheck=false;
    private boolean imgCheck=false;
    private boolean titleCheck=false;
    private boolean contentCheck=false;
    private boolean tagCheck=false;
    private boolean trashCheck=false;
    private double x,y=0;
    private Toast myToast;
    private Gallery_setting gallery_setting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ecobinding=ActivityEcoCarpingWriteBinding.inflate(getLayoutInflater());
        setContentView(ecobinding.getRoot());
        context=getApplicationContext();
        gallery_setting=new Gallery_setting(this, EcoCarpingWriteActivity.this);

        initLayout();
        selectPlace();
        selectImage();
        tag();
        writeCheck();

        ecobinding.completionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!checkAll()){
                    myToast = Toast.makeText(getApplicationContext(),"모두 입력해주세요", Toast.LENGTH_SHORT);
                    myToast.show();
                    return;
                }
                post();
            }
        });
    }

    public void initLayout(){
        Glide.with(getApplicationContext()).load(R.drawable.locate_img).into(ecobinding.locateImg);
        Glide.with(getApplicationContext()).load(R.drawable.cancel_img).into(ecobinding.back);
        ecobinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Glide.with(this).load(R.drawable.write_add_img_button).into(ecobinding.addImage);
        ecobinding.locationText.setText("위치");
        tagListLiveData.setValue(tagList);
        if(uriList.size()==0){
            ecobinding.scrollview.setVisibility(View.GONE);
        }
        ecobinding.mapView.setVisibility(View.GONE);
        ecobinding.reselect.setVisibility(View.GONE);
        ecobinding.scrollview.setHorizontalScrollBarEnabled(false);
    }

    public void selectPlace(){
        ecobinding.searchBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LocationSearchActivity.class);
                startActivityForResult(intent, 2);
            }
        });
        ecobinding.reselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ecobinding.mapView.removeView(mapView);
                Intent intent = new Intent(getApplicationContext(), LocationSearchActivity.class);
                startActivityForResult(intent, 2);
            }
        });
    }

    public void selectImage(){
        ecobinding.insertImage.setOnClickListener(listener);
        ecobinding.addImage.setOnClickListener(listener);
        imageList.observe(this, imgObserver);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE){
            if(resultCode==RESULT_OK) {
                try {
                    Uri uri = data.getData();
                    String path=getPath(uri);
                    uriList.add(uri);
                    uriList2.add(path);
                    imageList.setValue(uriList);
                    if(uriList2.size()>=4){
                        imgPossible=false;
                    }
                } catch (Exception e) {

                }
            }else if(resultCode==RESULT_CANCELED){
            }
        }
        if(requestCode==1){
            if(resultCode==1) {
                String tag = data.getStringExtra("tag");
                tagList.add(tag);
                tagListLiveData.setValue(tagList);
                TextView textView = new TextView(getApplicationContext());
                LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.leftMargin= convertDp(10);
                textView.setTextColor(Color.parseColor("#5f51ef"));
                textView.setLayoutParams(params);
                textView.setText(tag);
                textView.setBackgroundResource(R.drawable.tag_design);
                textView.setPadding(convertDp(20),convertDp(10),convertDp(20),convertDp(10));
                ecobinding.tagArea.addView(textView);
            }
        }
        if(requestCode==2&&resultCode==2){
            placeCheck=true;
            changeButtonColor(checkAll());
            ecobinding.reselect.setVisibility(View.VISIBLE);
            ecobinding.searchBar.setVisibility(View.GONE);
            ecobinding.mapView.setVisibility(View.VISIBLE);
            placeName=data.getStringExtra("place");
            ecobinding.locationText.setText(placeName);
            x=data.getDoubleExtra("x",0);
            y=data.getDoubleExtra("y",0);
            settingMapView(x,y);
        }
        if(requestCode==2&&resultCode==3){
            if(!(x==0&&y==0)) {
                settingMapView(x, y);
            }
        }
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

    public View.OnClickListener listener=new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            if(!imgPossible){
                return;
            }

            if (Build.VERSION.SDK_INT >= 23) {
                int permission_read = context.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
                int permission_write = context.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (permission_read == PackageManager.PERMISSION_DENIED || permission_write == PackageManager.PERMISSION_DENIED) {
                    gallery_setting.check_gallery_permission();
                } else {
                    Intent galleryintent = new Intent(Intent.ACTION_GET_CONTENT);
                    galleryintent.setType("image/*");
                    startActivityForResult(galleryintent, REQUEST_CODE);
                }
            } else {
                Intent galleryintent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryintent.setType("image/*");
                startActivityForResult(galleryintent, REQUEST_CODE);
            }
        }
    };

    Observer<ArrayList<Uri>> imgObserver =new Observer<ArrayList<Uri>>() {
        @Override
        public void onChanged(ArrayList<Uri> uris) {
            ecobinding.imageCount.setText(uris.size()+"/4");
            if(uris.size()==0){
                ecobinding.insertImage.setVisibility(View.VISIBLE);
                ecobinding.scrollview.setVisibility(View.GONE);
                imgCheck=false;
                changeButtonColor(checkAll());
            }
            else{
                ecobinding.insertImage.setVisibility(View.GONE);
                ecobinding.scrollview.setVisibility(View.VISIBLE);
                imgCheck=true;
                changeButtonColor(checkAll());
                ecobinding.imageArea.removeAllViews();
                for(int i=0;i<uris.size();i++) {
                    int index=i;
                    ImageItemBinding imageItemBinding=ImageItemBinding.inflate(getLayoutInflater());
                    ImageView iv = imageItemBinding.addImage;
                    ImageView iv2 = imageItemBinding.minusImage;

                    Glide.with(getApplicationContext()).load(uris.get(i))
                            .transform(new CenterCrop(), new RoundedCorners(30))
                            .into(iv);

                    Glide.with(getApplicationContext()).load(R.drawable.cancel_image)
                            .transform(new CenterCrop(), new RoundedCorners(30))
                            .into(iv2);

                    iv2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            imageItemBinding.getRoot().removeView(view);
                            imageItemBinding.getRoot().removeView(iv);
                            deleteURI(index);
                        }
                    });

                    ecobinding.imageArea.addView(imageItemBinding.getRoot());
                }
            }
        }
    };

    public void deleteURI(int i){
        uriList.remove(i);
        uriList2.remove(i);
        imageList.setValue(uriList);
    }

    public void tag(){
        ecobinding.tagAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), TagPageActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        tagListLiveData.observe(this, new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> strings) {
                if(strings.size()==0){
                    ecobinding.deleteTag.setVisibility(View.GONE);
                    tagCheck=false;
                    changeButtonColor(checkAll());
                }
                else{
                    ecobinding.deleteTag.setVisibility(View.VISIBLE);
                    tagCheck=true;
                    changeButtonColor(checkAll());
                }
            }
        });
        ecobinding.deleteTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ecobinding.tagArea.removeAllViews();
                tagList.clear();
                tagListLiveData.setValue(tagList);
            }
        });
    }

    public void writeCheck(){
        ecobinding.title.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 입력되는 텍스트에 변화가 있을 때 호출된다.
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // 입력이 끝났을 때 호출된다.
                if(ecobinding.title.getText().toString().length()==0){
                    titleCheck=false;
                }
                else{
                    titleCheck=true;
                }
                changeButtonColor(checkAll());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // 입력하기 전에 호출된다.
            }
        });
        ecobinding.content.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ecobinding.textLength.setText(s.toString().length()+"/500");
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // 입력이 끝났을 때 호출된다.
                if(ecobinding.content.getText().toString().length()==0){
                    contentCheck=false;
                }
                else{
                    contentCheck=true;
                }
                changeButtonColor(checkAll());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // 입력하기 전에 호출된다.
            }
        });
        ecobinding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                trashCheck=true;
                changeButtonColor(checkAll());
            }
        });
    }

    public boolean checkAll(){
        if(placeCheck&&imgCheck&&titleCheck&&contentCheck&&tagCheck&&trashCheck)
            return true;
        return false;
    }

    public void changeButtonColor(boolean check){
        if(check)
            ecobinding.completionButton.setBackgroundColor(Color.BLACK);
        else
            ecobinding.completionButton.setBackgroundColor(Color.parseColor("#999999"));
    }

    public void settingMapView(Double x,Double y){
        mapView=new MapView(this);
        mapView.setDaumMapApiKey(KAKAO_KEY);
        marker = new MapPOIItem();
        mapPoint=MapPoint.mapPointWithGeoCoord(y,x);
        ecobinding.mapView.addView(mapView);
        mapView.setMapCenterPoint(mapPoint,true);
        marker.setItemName(placeName);
        marker.setMapPoint(mapPoint);
        marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
        mapView.addPOIItem(marker);
    }

    public void post(){
        HashMap<String, RequestBody> map=new HashMap<>();
        int userId=SharedPreferenceManager.getInstance(getApplicationContext()).getInt("id",0);
        RadioGroup rdgGroup = ecobinding.radioGroup;
        RadioButton rdoButton = findViewById( rdgGroup.getCheckedRadioButtonId() );
        String strPgmId = rdoButton.getText().toString();
        String tagString="[";
        for(int i=0;i<tagList.size();i++){
            String tag='"'+tagList.get(i).replace("#","")+'"';
            tagString=tagString+tag;
            if(i!=tagList.size()-1)
                tagString=tagString+",";
        }
        tagString=tagString+"]";

        RequestBody user = RequestBody.create(MediaType.parse("text/plain"),Integer.toString(userId));
        RequestBody latitude = RequestBody.create(MediaType.parse("text/plain"),String.valueOf(mapPoint.getMapPointGeoCoord().latitude));
        RequestBody longitude = RequestBody.create(MediaType.parse("text/plain"),String.valueOf(mapPoint.getMapPointGeoCoord().longitude));
        RequestBody title = RequestBody.create(MediaType.parse("text/plain"),ecobinding.title.getText().toString());
        RequestBody place=RequestBody.create(MediaType.parse("text/plain"),ecobinding.locationText.getText().toString());
        RequestBody text = RequestBody.create(MediaType.parse("text/plain"),ecobinding.content.getText().toString());
        RequestBody trash = RequestBody.create(MediaType.parse("text/plain"),strPgmId);
        RequestBody tags = RequestBody.create(MediaType.parse("text/plain"), tagString);
        map.put("user", user);
        map.put("latitude", latitude);
        map.put("longitude", longitude);
        map.put("place",place);
        map.put("title", title);
        map.put("text", text);
        map.put("trash", trash);
        map.put("tags", tags);

        MultipartBody.Part image1=null;
        MultipartBody.Part image2=null;
        MultipartBody.Part image3=null;
        MultipartBody.Part image4=null;

        int count=uriList2.size();
        if(count>=1) {
            File file = new File(uriList2.get(0));
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            image1 = MultipartBody.Part.createFormData("image1", file.getName(), requestBody);
        }
        if(count>=2) {
            File file = new File(uriList2.get(1));
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            image2 = MultipartBody.Part.createFormData("image2", file.getName(), requestBody);
        }
        if(count>=3) {
            File file = new File(uriList2.get(2));
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            image3=MultipartBody.Part.createFormData("image3", file.getName(), requestBody);
        }
        if(count>=4) {
            File file = new File(uriList2.get(3));
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            image4 = MultipartBody.Part.createFormData("image4", file.getName(), requestBody);
        }

        TotalApiClient.getEcoApiService(getApplicationContext()).postReview(image1,image2,image3,image4, map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<CommonClass>() {
                    @Override
                    public void onSuccess(@NonNull CommonClass commonClass) {
                        if(commonClass.getCode()==200) {
                            myToast = Toast.makeText(getApplicationContext(),"작성 완료", Toast.LENGTH_SHORT);
                            myToast.show();
                            finish();
                        }
                        else{
                            myToast = Toast.makeText(getApplicationContext(),"작성 실패. 문의해주세요", Toast.LENGTH_SHORT);
                            myToast.show();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                    }
                });
    }

    public String getPath(Uri uri){
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
// DocumentProvider
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
// ExternalStorageProvider
                if (isExternalStorageDocument(uri)) {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];
                    if ("primary".equalsIgnoreCase(type)) {
                        return Environment.getExternalStorageDirectory() + "/" + split[1];
                    }
                }
// DownloadsProvider
                else if (isDownloadsDocument(uri)) {
                    final String id = DocumentsContract.getDocumentId(uri);
                    final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                    return getDataColumn(context, contentUri, null, null);
                }
// MediaProvider
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
// MediaStore (and general)
            else if ("content".equalsIgnoreCase(uri.getScheme())) {
                return getDataColumn(context, uri, null, null);
            }
// File
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

    public int convertDp(int dp) {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, metrics);
    }

}