package com.tourkakao.carping.Home.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.tourkakao.carping.EcoCarping.Activity.EcoCarpingListActivity;
import com.tourkakao.carping.EcoCarping.Activity.EcoCarpingWriteActivity;
import com.tourkakao.carping.Home.EcoDataClass.EcoRanking;
import com.tourkakao.carping.Home.EcoDataClass.EcoReview;
import com.tourkakao.carping.Home.EcoFragmentAdapter.EcoRankingAdapter;
import com.tourkakao.carping.Home.EcoFragmentAdapter.EcoReviewAdapter;
import com.tourkakao.carping.Home.HomeViewModel.EcoViewModel;
import com.tourkakao.carping.R;
import com.tourkakao.carping.databinding.MainEcoFragmentBinding;

import androidx.lifecycle.Observer;

import java.util.ArrayList;

public class EcoFragment extends Fragment {
    private MainEcoFragmentBinding ecobinding;
    Context context;
    EcoViewModel ecoViewModel;
    private EcoReviewAdapter ecoReviewAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ecobinding = MainEcoFragmentBinding.inflate(inflater, container, false);
        context=getActivity().getApplicationContext();

        ecoViewModel =new ViewModelProvider(this).get(EcoViewModel.class);
        ecoViewModel.setContext(context);

        initializeImg();

        settingEchoReview();
        settingEchoRanking();

        ecobinding.totalButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),EcoCarpingListActivity.class);
                startActivity(intent);
            }
        });

        ecobinding.mainEcoWriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), EcoCarpingWriteActivity.class);
                startActivity(intent);
            }
        });

        return ecobinding.getRoot();
    }



    public void initializeImg(){
        Glide.with(this).load(R.drawable.main_eco_check_mark).into(ecobinding.mainEcoCheckMark);
        Glide.with(this).load(R.drawable.main_eco_image).into(ecobinding.mainEcoImage);
        Glide.with(this).load(R.drawable.main_eco_write_button).into(ecobinding.mainEcoWriteButton);
        Glide.with(this).load(R.drawable.no_ranking_image).into(ecobinding.noRankingImg);
        Glide.with(this).load(R.drawable.no_review_image).into(ecobinding.noReviewImg);
    }

    public void settingEchoReview(){
        ecobinding.ecoCarpingReview.setLayoutManager(new LinearLayoutManager(context));
        ecoViewModel.ecoReviewLiveData.observe(this,echoReviewObserver);
        ecoViewModel.todayLiveCount.observe(this,echoTodayCountObserver);
    }

    Observer<Integer> echoTodayCountObserver=new Observer<Integer>() {
        @Override
        public void onChanged(Integer integer) {
            ecobinding.todayEcoCarpingCount
                    .setText("오늘 "+Integer.toString(integer)+"개의 에코카핑 인증");
        }
    };

    Observer<ArrayList<EcoReview>> echoReviewObserver=new Observer<ArrayList<EcoReview>>() {
        @Override
        public void onChanged(ArrayList<EcoReview> ecoReviews) {
            if(ecoReviews==null){
                ecobinding.noReviewImg.setVisibility(View.VISIBLE);
                ecobinding.ecoCarpingReview.setVisibility(View.GONE);
            }else{
                ecobinding.noReviewImg.setVisibility(View.GONE);
                ecobinding.ecoCarpingReview.setVisibility(View.VISIBLE);
                ecobinding.ecoCarpingReview.setAdapter(new EcoReviewAdapter(context, ecoReviews));
            }
        }
    };

    public void settingEchoRanking(){
        ecobinding.ecoRanking.setLayoutManager(new LinearLayoutManager(context));
        ecoViewModel.monthlyEcoCount.observe(this,echoMonthlyCountObserver);
        ecoViewModel.ecoRankingLiveData.observe(this,echoRankingObserver);
    }

    Observer<ArrayList<EcoRanking>> echoRankingObserver=new Observer<ArrayList<EcoRanking>>() {
        @Override
        public void onChanged(ArrayList<EcoRanking> ecoRankings) {
            if(ecoRankings==null){
                ecobinding.noRankingImg.setVisibility(View.VISIBLE);
                ecobinding.ecoRanking.setVisibility(View.GONE);
            }else{
                ecobinding.noRankingImg.setVisibility(View.GONE);
                ecobinding.ecoRanking.setVisibility(View.VISIBLE);
                ecobinding.ecoRanking.setAdapter(new EcoRankingAdapter(context, ecoRankings));
            }
        }
    };

    Observer<Integer> echoMonthlyCountObserver=new Observer<Integer>() {
        @Override
        public void onChanged(Integer integer) {
            ecobinding.username.setText(ecoViewModel.currentUser.getValue().getUsername());
            ecobinding.monthWrite
                    .setText("최근 1개월 간\n에코리뷰 "+Integer.toString(integer)+"개 작성");
        }
    };
}
