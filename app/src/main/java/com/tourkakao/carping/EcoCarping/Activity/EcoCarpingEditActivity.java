package com.tourkakao.carping.EcoCarping.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
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
import com.tourkakao.carping.EcoCarping.DTO.EcoPost;
import com.tourkakao.carping.EcoCarping.ViewModel.EcoDetailViewModel;
import com.tourkakao.carping.NetworkwithToken.CommonClass;
import com.tourkakao.carping.NetworkwithToken.TotalApiClient;
import com.tourkakao.carping.R;
import com.tourkakao.carping.SharedPreferenceManager.SharedPreferenceManager;
import com.tourkakao.carping.databinding.ActivityEcoCarpingWriteBinding;
import com.tourkakao.carping.databinding.ImageItemBinding;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class EcoCarpingEditActivity extends AppCompatActivity {
    ActivityEcoCarpingWriteBinding ecobinding;
    private EcoDetailViewModel ecoDetailViewModel;
    private int pk;
    private String placeName;
    private MapPOIItem marker;
    private MapView mapView;
    private MapPoint mapPoint;
    private String KAKAO_KEY="KakaoAK "+ BuildConfig.KAKAO_REST_API_KEY;
    private ArrayList<String> tags=new ArrayList<>();
    private MutableLiveData<ArrayList<String>> tagsLiveData=new MutableLiveData<>();
    private ArrayList<String> images=new ArrayList<>();
    private MutableLiveData<ArrayList<String>> imagesLiveData=new MutableLiveData<>();
    private Context context;
    private boolean imgCheck=false;
    private boolean titleCheck=false;
    private boolean contentCheck=false;
    private boolean tagCheck=false;
    private ArrayList<Integer> is_null=new ArrayList<>();
    private int[] initialImgCheck={0,0,0,0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ecobinding= ActivityEcoCarpingWriteBinding.inflate(getLayoutInflater());
        setContentView(ecobinding.getRoot());

        ecoDetailViewModel =new ViewModelProvider(this).get(EcoDetailViewModel.class);
        ecoDetailViewModel.setContext(this);

        context=getApplicationContext();

        settingLayout();
        settingToolbar();
        getDetailInfo();
        selectImage();
        selectPlace();
        writeCheck();
        tag();

        ecobinding.completionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!checkAll()){
                    Toast myToast = Toast.makeText(getApplicationContext(),"모두 입력해주세요", Toast.LENGTH_SHORT);
                    myToast.show();
                    return;
                }
                post();
                Toast myToast = Toast.makeText(getApplicationContext(),"수정 완료", Toast.LENGTH_SHORT);
                myToast.show();
                finish();
            }
        });
    }

    public void selectPlace(){
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
        ecoDetailViewModel.getImages().observe(this, new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> strings) {
                for(int i=0;i<strings.size();i++){
                    initialImgCheck[i]=1;
                }
                images=strings;
                imagesLiveData.setValue(images);
            }
        });
        ecobinding.addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                //intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 0);
            }
        });
        imagesLiveData.observe(this, imgObserver);
    }

    public void tag(){
        ecoDetailViewModel.getTags().observe(this, new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> strings) {
                tags=strings;
                tagsLiveData.setValue(strings);
            }
        });
        ecobinding.tagAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), TagPageActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        ecobinding.deleteTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ecobinding.tagArea.removeAllViews();
                tags.clear();
                tagsLiveData.setValue(tags);
            }
        });
        tagsLiveData.observe(this,tagObserver);
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==2&&resultCode==2){
            placeName=data.getStringExtra("place");
            ecobinding.locationText.setText(placeName);
            settingMapView(data.getDoubleExtra("x",0),data.getDoubleExtra("y",0));
        }
        if(requestCode==0&&resultCode==RESULT_OK){
            Uri uri = data.getData();
            String path=getPath(uri);
            images.add(path);
            imagesLiveData.setValue(images);
        }
        if(requestCode==1&&resultCode==1){
            String tag = data.getStringExtra("tag");
            tags.add(tag);
            tagsLiveData.setValue(tags);
        }
    }

    public void settingToolbar(){
        ecobinding.toolbarText.setText("수정하기");
        Toolbar toolbar=ecobinding.toolbar;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    public void settingLayout(){
        Glide.with(this).load(R.drawable.write_add_img_button).into(ecobinding.addImage);
        ecobinding.insertImage.setVisibility(View.GONE);
        ecobinding.scrollview.setVisibility(View.VISIBLE);
        ecobinding.radioGroupText.setVisibility(View.GONE);
        ecobinding.radioGroup.setVisibility(View.GONE);
        ecobinding.reselect.setVisibility(View.VISIBLE);
        ecobinding.searchBar.setVisibility(View.GONE);
        ecobinding.mapView.setVisibility(View.VISIBLE);
        ecoDetailViewModel.getPost().observe(this, new Observer<EcoPost>() {
            @Override
            public void onChanged(EcoPost post) {
                ecobinding.locationText.setText(post.getPlace());
                ecobinding.title.setText(post.getTitle());
                ecobinding.content.setText(post.getText());
                settingMapView(Double.parseDouble(post.getLongitude()),Double.parseDouble(post.getLatitude()));
            }
        });
    }

    public Observer<ArrayList<String>> tagObserver=new Observer<ArrayList<String>>() {
        @Override
        public void onChanged(ArrayList<String> strings) {
            if(strings.size()==0) {
                ecobinding.deleteTag.setVisibility(View.GONE);
                tagCheck = false;
                changeButtonColor(checkAll());
                return;
            }
            else{
                ecobinding.deleteTag.setVisibility(View.VISIBLE);
                tagCheck=true;
                changeButtonColor(checkAll());
            }
            ecobinding.tagArea.removeAllViews();
            for(String tag:strings){
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
    };

    public void getDetailInfo(){
        Intent intent=getIntent();
        pk=(int)Double.parseDouble(intent.getStringExtra("pk"));
        ecoDetailViewModel.setPk(pk);

        ecoDetailViewModel.loadDetail();
    }

    public void settingMapView(Double x,Double y){
        mapView=new MapView(this);
        mapView.setDaumMapApiKey(KAKAO_KEY);
        marker = new MapPOIItem();
        mapPoint= MapPoint.mapPointWithGeoCoord(y,x);
        ecobinding.mapView.addView(mapView);
        mapView.setMapCenterPoint(mapPoint,true);
        marker.setItemName(placeName);
        marker.setMapPoint(mapPoint);
        marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);
        mapView.addPOIItem(marker);
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
                // 입력되는 텍스트에 변화가 있을 때 호출된다.
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
    }

    public Observer<ArrayList<String>> imgObserver=new Observer<ArrayList<String>>() {
        @Override
        public void onChanged(ArrayList<String> strings) {
            ecobinding.imageCount.setText(strings.size()+"/4");
            if (strings.size()==0){
                imgCheck=false;
                changeButtonColor(checkAll());
            }
            else{
                imgCheck=true;
                changeButtonColor(checkAll());
            }
            ecobinding.imageArea.removeAllViews();
            for(int i=0;i<strings.size();i++) {
                int index=i;
                ImageItemBinding imageItemBinding=ImageItemBinding.inflate(getLayoutInflater());
                ImageView iv = imageItemBinding.addImage;
                ImageView iv2 = imageItemBinding.minusImage;

                Glide.with(getApplicationContext()).load(strings.get(i))
                        .transform(new CenterCrop(), new RoundedCorners(30))
                        .into(iv);

                Glide.with(getApplicationContext()).load(R.drawable.cancel_image)
                        .transform(new CenterCrop(), new RoundedCorners(30))
                        .into(iv2);

                iv2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(initialImgCheck[index]==1){
                            initialImgCheck[index]=0;
                            is_null.add(index+1);
                        }
                        imageItemBinding.getRoot().removeView(view);
                        imageItemBinding.getRoot().removeView(iv);
                        deleteURI(index);
                    }
                });
                ecobinding.imageArea.addView(imageItemBinding.getRoot());
            }
        }
    };

    public boolean checkAll(){
        if(imgCheck&&titleCheck&&contentCheck&&tagCheck)
            return true;
        return false;
    }

    public void changeButtonColor(boolean check){
        if(check)
            ecobinding.completionButton.setBackgroundColor(Color.BLACK);
        else
            ecobinding.completionButton.setBackgroundColor(Color.parseColor("#999999"));
    }

    public void post(){
        HashMap<String, RequestBody> map=new HashMap<>();
        int userPk= SharedPreferenceManager.getInstance(getApplicationContext()).getInt("id",0);
        String tagString="[";
        for(int i=0;i<tags.size();i++){
            String tag='"'+tags.get(i).replace("#","")+'"';
            tagString=tagString+tag;
            if(i!=tags.size()-1)
                tagString=tagString+",";
        }
        tagString=tagString+"]";

        RequestBody user = RequestBody.create(MediaType.parse("text/plain"),Integer.toString(userPk));
        RequestBody latitude = RequestBody.create(MediaType.parse("text/plain"),String.valueOf(mapPoint.getMapPointGeoCoord().latitude));
        RequestBody longitude = RequestBody.create(MediaType.parse("text/plain"),String.valueOf(mapPoint.getMapPointGeoCoord().longitude));
        RequestBody title = RequestBody.create(MediaType.parse("text/plain"),ecobinding.title.getText().toString());
        RequestBody place=RequestBody.create(MediaType.parse("text/plain"),ecobinding.locationText.getText().toString());
        RequestBody text = RequestBody.create(MediaType.parse("text/plain"),ecobinding.content.getText().toString());
        RequestBody tags = RequestBody.create(MediaType.parse("text/plain"), tagString);
        map.put("user", user);
        map.put("latitude", latitude);
        map.put("longitude", longitude);
        map.put("place",place);
        map.put("title", title);
        map.put("text", text);
        map.put("tags", tags);

        MultipartBody.Part image1=null;
        MultipartBody.Part image2=null;
        MultipartBody.Part image3=null;
        MultipartBody.Part image4=null;

        if(images.size()>=1) {
                File file = new File(images.get(0));
                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                image1 = MultipartBody.Part.createFormData("image1", file.getName(), requestBody);
        }
        if(images.size()>=2) {
                File file = new File(images.get(1));
                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                image2 = MultipartBody.Part.createFormData("image2", file.getName(), requestBody);
        }
        if(images.size()>=3) {
                File file = new File(images.get(2));
                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                image3 = MultipartBody.Part.createFormData("image3", file.getName(), requestBody);
        }
        if(images.size()>=4) {
                File file = new File(images.get(3));
                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                image4 = MultipartBody.Part.createFormData("image4", file.getName(), requestBody);
        }

        for(int i:is_null) {
            System.out.println("지울것" +i);
        }

        TotalApiClient.getEcoApiService(getApplicationContext()).editPost(pk,image1,image2,image3,image4,map,is_null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<CommonClass>() {
                    @Override
                    public void onSuccess(@NonNull CommonClass commonClass) {
                        if(commonClass.getCode()==200) {
                            System.out.println(commonClass.getCode() + commonClass.getError_message());
                            System.out.println("post 성공");
                        }
                        else{
                            System.out.println("post 실패"+commonClass.getError_message());
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        System.out.println("오류"+e.getMessage());
                    }
                });
    }

    public void deleteURI(int i){
        images.remove(i);
        imagesLiveData.setValue(images);
    }

    public int convertDp(int dp) {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, metrics);
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
}