package com.tourkakao.carping.EcoCarping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.bumptech.glide.Glide;
import com.tourkakao.carping.GpsLocation.GpsTracker;
import com.tourkakao.carping.Home.EcoDataClass.EcoReview;
import com.tourkakao.carping.Home.EcoFragmentAdapter.EcoReviewAdapter;
import com.tourkakao.carping.Home.HomeViewModel.EcoViewModel;
import com.tourkakao.carping.R;
import com.tourkakao.carping.databinding.ActivityEcoCarpingTotalBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EcoCarpingListActivity extends AppCompatActivity {
    private RecyclerView.LayoutManager mLayoutManager;
    private EcoTotalViewModel ecoTotalViewModel;
   private ActivityEcoCarpingTotalBinding ecobinding;
   private Context context;
   private LifecycleOwner lifecycleOwner;
    private Spinner spinner;
    private float currentLatitude,currentLongitude;
    private List<String> sort_list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lifecycleOwner=this;
        context=getApplicationContext();
        ecobinding=ActivityEcoCarpingTotalBinding.inflate(getLayoutInflater());
        setContentView(ecobinding.getRoot());

        ecoTotalViewModel =new ViewModelProvider(this).get(EcoTotalViewModel.class);
        ecoTotalViewModel.setContext(this);

        initializeToolbar();
        initializeImg();
        settingEchoReview();
        selectSinnerItem();

        ecobinding.writeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.write_button:
                        Intent intent=new Intent(context,EcoCarpingWriteActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });
    }

    public void initializeToolbar(){
        Toolbar toolbar=ecobinding.toolbar;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //getSupportActionBar().setHomeAsUpIndicator(R.drawable);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void initializeImg(){
        Glide.with(this).load(R.drawable.eco_carping_write_text).into(ecobinding.writeButton);
    }

    public void selectSinnerItem(){
        spinner=ecobinding.sortSpinner;
        sort_list= Arrays.asList(getResources().getStringArray(R.array.eco_sort_item));
        ArrayAdapter<String> spinner_adapter=new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,sort_list);
        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinner_adapter);
        spinner.setSelection(2);
        spinner.setOnItemSelectedListener(spinnerListener);
    }

    public void settingEchoReview(){
        mLayoutManager = new LinearLayoutManager(this);
        ecobinding.totalReviewRecycler.setLayoutManager(mLayoutManager);
        ecoTotalViewModel.getPopularOrderReviews().observe(lifecycleOwner,reviewsObserver);
        ecoTotalViewModel.getRecentOrderReviews().observe(lifecycleOwner,reviewsObserver);
        ecoTotalViewModel.getDistanceOrderReviews().observe(lifecycleOwner,reviewsObserver);
        //수정
        GpsTracker gpsTracker = new GpsTracker(getApplicationContext());
        currentLatitude = (float)gpsTracker.getLatitude();
        currentLongitude = (float)gpsTracker.getLongitude();
        ecoTotalViewModel.setLocation(currentLatitude,currentLongitude);
        ecoTotalViewModel.startDistance();
    }

    public android.widget.AdapterView.OnItemSelectedListener spinnerListener=new AdapterView.OnItemSelectedListener(){
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            if(sort_list.get(i).equals("인기순")){
                ecobinding.totalReviewRecycler
                        .setAdapter(new EcoTotalReviewAdapter(getApplicationContext(),ecoTotalViewModel.getPopularOrderReviews().getValue()));
            }
            if(sort_list.get(i).equals("거리순")){
                ecobinding.totalReviewRecycler
                        .setAdapter(new EcoTotalReviewAdapter(getApplicationContext(),ecoTotalViewModel.getDistanceOrderReviews().getValue()));
            }
            if(sort_list.get(i).equals("최신순")){
                ecobinding.totalReviewRecycler
                        .setAdapter(new EcoTotalReviewAdapter(getApplicationContext(),ecoTotalViewModel.getRecentOrderReviews().getValue()));
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
            ecobinding.totalReviewRecycler
                    .setAdapter(new EcoTotalReviewAdapter(getApplicationContext(),ecoTotalViewModel.getRecentOrderReviews().getValue()));
        }
    };

    Observer<ArrayList<EcoReview>> reviewsObserver=new Observer<ArrayList<EcoReview>>() {
        @Override
        public void onChanged(ArrayList<EcoReview> ecoReviews) {
            if(ecoReviews==null){
                System.out.println("널임");
                ecobinding.noReviewImg.setVisibility(View.VISIBLE);
                ecobinding.totalReviewRecycler.setVisibility(View.GONE);
            }else{
                System.out.println("널아님"+ecoReviews.get(0).getTitle());
                ecobinding.noReviewImg.setVisibility(View.GONE);
                ecobinding.totalReviewRecycler.setVisibility(View.VISIBLE);
                spinner.setOnItemSelectedListener(spinnerListener);
            }
        }
    };
}