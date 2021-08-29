package com.tourkakao.carping.EcoCarping;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.loader.content.CursorLoader;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.FileUtils;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.tourkakao.carping.BuildConfig;
import com.tourkakao.carping.NetworkwithToken.CommonClass;
import com.tourkakao.carping.NetworkwithToken.EcoInterface;
import com.tourkakao.carping.NetworkwithToken.TotalApiClient;
import com.tourkakao.carping.R;
import com.tourkakao.carping.SharedPreferenceManager.SharedPreferenceManager;
import com.tourkakao.carping.databinding.ActivityEcoCarpingWriteBinding;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EcoCarpingWriteActivity extends AppCompatActivity {
    ActivityEcoCarpingWriteBinding ecobinding;
    private static final int REQUEST_CODE = 0;
    private MutableLiveData<ArrayList<Uri>> imageList=new MutableLiveData<>();
    private ArrayList<Uri> uriList=new ArrayList<>();
    private ArrayList<String> uriList2=new ArrayList<>();
    private ArrayList<String> tagList=new ArrayList<>();
    private String KAKAO_KEY="KakaoAK "+ BuildConfig.KAKAO_REST_API_KEY;
    private MapPOIItem marker;
    private MapView mapView;
    private String placeName;
    private int eight_six;
    private int one_six;
    private int one_zero;
    private int two_zero;
    private MapPoint mapPoint;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ecobinding=ActivityEcoCarpingWriteBinding.inflate(getLayoutInflater());
        setContentView(ecobinding.getRoot());

        settingToolbar();
        eight_six = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,86,getResources().getDisplayMetrics());
        one_six = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,16,getResources().getDisplayMetrics());
        one_zero =(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,10,getResources().getDisplayMetrics());
        two_zero=(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,20,getResources().getDisplayMetrics());
        ecobinding.locationText.setText("위치");

        ecobinding.searchBar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId==EditorInfo.IME_ACTION_SEARCH) {
                    Intent intent = new Intent(getApplicationContext(), LocationSearchActivity.class);
                    intent.putExtra("검색어", ecobinding.searchBar.getText().toString());
                    startActivityForResult(intent, 2);
                    return true;
                }
                return false;
            }
        });

        ecobinding.insertImage.setOnClickListener(listener);
        ecobinding.addImage.setOnClickListener(listener);

        if(uriList.size()==0){
            ecobinding.scrollview.setVisibility(View.GONE);
        }
        ecobinding.mapView.setVisibility(View.GONE);

        ecobinding.scrollview.setHorizontalScrollBarEnabled(false);
        imageList.observe(this,observer);

        tag();

        ecobinding.completionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                post();
            }
        });
    }

    public void settingToolbar(){
        Toolbar toolbar=ecobinding.toolbar;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
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
                } catch (Exception e) {

                }
            }else if(resultCode==RESULT_CANCELED){
            }
        }
        if(requestCode==1){
            if(resultCode==1) {
                String tag = data.getStringExtra("tag");
                tagList.add(tag);
                TextView textView = new TextView(getApplicationContext());
                LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.leftMargin= one_zero;
                textView.setLayoutParams(params);
                textView.setText(tag);
                textView.setBackgroundResource(R.drawable.tag_design);
                textView.setPadding(two_zero,one_zero,two_zero,one_zero);
                ecobinding.tagArea.addView(textView);
            }
        }
        if(requestCode==2&&resultCode==2){
            ecobinding.searchBar.setVisibility(View.GONE);
            ecobinding.mapView.setVisibility(View.VISIBLE);
            placeName=data.getStringExtra("place");
            ecobinding.locationText.setText(placeName);
            settingMapView(data.getDoubleExtra("x",0),data.getDoubleExtra("y",0));
        }
    }

    public View.OnClickListener listener=new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, REQUEST_CODE);
        }
    };

    Observer<ArrayList<Uri>> observer=new Observer<ArrayList<Uri>>() {
        @Override
        public void onChanged(ArrayList<Uri> uris) {
            if(uris.size()==0){
                ecobinding.insertImage.setVisibility(View.VISIBLE);
                ecobinding.scrollview.setVisibility(View.GONE);
            }
            else{
                ecobinding.insertImage.setVisibility(View.GONE);
                ecobinding.scrollview.setVisibility(View.VISIBLE);
                ecobinding.imageArea.removeAllViews();
                for(Uri uri:uris) {
                    ImageView iv = new ImageView(getApplicationContext());
                    LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(eight_six, eight_six);
                    params.width= eight_six;
                    params.height= eight_six;
                    params.leftMargin= one_six;
                    iv.setLayoutParams(params);

                    Glide.with(getApplicationContext()).load(uri)
                            .transform(new CenterCrop(), new RoundedCorners(30))
                            .into(iv);

                    ecobinding.imageArea.addView(iv);
                }
            }
        }
    };

    public void tag(){
        ecobinding.tagAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),TagPageActivity.class);
                startActivityForResult(intent, 1);
            }
        });
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
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);
        mapView.addPOIItem(marker);
    }

    public void post(){
        HashMap<String, RequestBody> map=new HashMap<>();
        String userString=SharedPreferenceManager.getInstance(getApplicationContext()).getString("user_pk","");
        RadioGroup rdgGroup = ecobinding.radioGroup;
        RadioButton rdoButton = findViewById( rdgGroup.getCheckedRadioButtonId() );
        String strPgmId = rdoButton.getText().toString();
        String tagString="[";
        for(int i=0;i<tagList.size();i++){
            String tag='"'+tagList.get(i)+'"';
            tagString=tagString+tag;
            if(i!=tagList.size()-1)
                tagString=tagString+",";
        }
        tagString=tagString+"]";

        RequestBody user = RequestBody.create(MediaType.parse("text/plain"),userString);
        RequestBody latitude = RequestBody.create(MediaType.parse("text/plain"),String.valueOf(mapPoint.getMapPointGeoCoord().latitude));
        RequestBody longitude = RequestBody.create(MediaType.parse("text/plain"),String.valueOf(mapPoint.getMapPointGeoCoord().longitude));
        RequestBody title = RequestBody.create(MediaType.parse("text/plain"),ecobinding.title.getText().toString());
        RequestBody text = RequestBody.create(MediaType.parse("text/plain"),ecobinding.content.getText().toString());
        RequestBody trash = RequestBody.create(MediaType.parse("text/plain"),strPgmId);
        RequestBody tags = RequestBody.create(MediaType.parse("text/plain"), tagString);
        map.put("user", user);
        map.put("latitude", latitude);
        map.put("longitude", longitude);
        map.put("title", title);
        map.put("text", text);
        map.put("trash", trash);
        map.put("tags", tags);

        File file = new File(uriList2.get(0));
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part image=MultipartBody.Part.createFormData("image", file.getName() , requestBody);

        TotalApiClient.getEcoApiService(getApplicationContext()).userEdit(image, map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<CommonClass>() {
                    @Override
                    public void onSuccess(@NonNull CommonClass commonClass) {
                        //setReviewData("recent",commonClass.getData());
                        System.out.println("post 성공");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        System.out.println("post 실패");
                    }
                });
    }

    public String getPath(Uri uri){
        String filepath;
        Cursor cursor=getContentResolver().query(uri, null, null, null, null);
        if(cursor==null){
            filepath=uri.getPath();
        }else{
            cursor.moveToFirst();
            int idx=cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            filepath=cursor.getString(idx);
            cursor.close();
        }
        return filepath;
    }

}