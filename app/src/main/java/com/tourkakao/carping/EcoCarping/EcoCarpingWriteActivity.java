package com.tourkakao.carping.EcoCarping;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.tourkakao.carping.BuildConfig;
import com.tourkakao.carping.R;
import com.tourkakao.carping.databinding.ActivityEcoCarpingWriteBinding;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.util.ArrayList;

public class EcoCarpingWriteActivity extends AppCompatActivity {
    ActivityEcoCarpingWriteBinding ecobinding;
    private static final int REQUEST_CODE = 0;
    private MutableLiveData<ArrayList<Uri>> imageList=new MutableLiveData<>();
    private ArrayList<Uri> uriList=new ArrayList<>();
    private String KAKAO_KEY="KakaoAK "+ BuildConfig.KAKAO_REST_API_KEY;
    private MapPOIItem marker;
    private MapView mapView;
    private String placeName;
    private int eight_six;
    private int one_six;
    private int one_zero;
    private int two_zero;
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
                    uriList.add(uri);
                    imageList.setValue(uriList);
                } catch (Exception e) {

                }
            }else if(resultCode==RESULT_CANCELED){

            }
        }
        if(requestCode==1){
            if(resultCode==1) {
                String tag = data.getStringExtra("tag");
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
        MapPoint mapPoint=MapPoint.mapPointWithGeoCoord(y,x);
        ecobinding.mapView.addView(mapView);
        mapView.setMapCenterPoint(mapPoint,true);
        marker.setItemName(placeName);
        marker.setMapPoint(mapPoint);
        marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);
        mapView.addPOIItem(marker);
    }

}