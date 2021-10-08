package com.tourkakao.carping.EcoCarping.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.bumptech.glide.Glide;
import com.tourkakao.carping.EcoCarping.Adapter.EcoTotalReviewAdapter;
import com.tourkakao.carping.EcoCarping.ViewModel.EcoTotalViewModel;
import com.tourkakao.carping.GpsLocation.GpsTracker;
import com.tourkakao.carping.Home.EcoDataClass.EcoReview;
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
    private List<String> sort_list=new ArrayList<>();
    private int sort=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lifecycleOwner=this;
        context=getApplicationContext();
        ecobinding=ActivityEcoCarpingTotalBinding.inflate(getLayoutInflater());
        setContentView(ecobinding.getRoot());

        ecoTotalViewModel =new ViewModelProvider(this).get(EcoTotalViewModel.class);
        ecoTotalViewModel.setContext(this);
        
        initializeImg();
        settingEchoReview();
        selectSinnerItem();

        ecobinding.writeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, EcoCarpingWriteActivity.class);
                startActivity(intent);
            }
        });
    }

    public void initializeImg(){
        Glide.with(getApplicationContext()).load(R.drawable.back).into(ecobinding.back);
        ecobinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void selectSinnerItem(){
        spinner=ecobinding.sortSpinner;
        sort_list= Arrays.asList(getResources().getStringArray(R.array.eco_sort_item));
        ArrayAdapter<String> spinner_adapter=new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,sort_list);
        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinner_adapter);
        spinner.setOnItemSelectedListener(spinnerListener);
        spinner.setSelection(sort);
    }

    public void settingEchoReview(){
        mLayoutManager = new LinearLayoutManager(this);
        ecobinding.totalReviewRecycler.setLayoutManager(mLayoutManager);
        ecoTotalViewModel.getPopularOrderReviews().observe(lifecycleOwner,reviewsObserver);
        ecoTotalViewModel.getRecentOrderReviews().observe(lifecycleOwner,reviewsObserver);
    }

    public android.widget.AdapterView.OnItemSelectedListener spinnerListener=new AdapterView.OnItemSelectedListener(){
        EcoTotalReviewAdapter adapter;
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            if(sort_list.get(i).equals("인기순")){
                sort=1;
                adapter=new EcoTotalReviewAdapter(getApplicationContext(),ecoTotalViewModel.getPopularOrderReviews().getValue());
                ecobinding.totalReviewRecycler.setAdapter(adapter);
            }
            if(sort_list.get(i).equals("최신순")){
                sort=0;
                adapter=new EcoTotalReviewAdapter(getApplicationContext(),ecoTotalViewModel.getRecentOrderReviews().getValue());
                ecobinding.totalReviewRecycler
                        .setAdapter(adapter);
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
                ecobinding.noReviewImg.setVisibility(View.VISIBLE);
                ecobinding.totalReviewRecycler.setVisibility(View.GONE);
            }else{
                selectSinnerItem();
                ecobinding.noReviewImg.setVisibility(View.GONE);
                ecobinding.totalReviewRecycler.setVisibility(View.VISIBLE);
                spinner.setOnItemSelectedListener(spinnerListener);
            }
        }
    };


    @Override
    public void onResume() {
        super.onResume();
        ecoTotalViewModel.loadPopularReviews();
        ecoTotalViewModel.loadRecentReviews();
    }

}