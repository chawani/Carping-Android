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
import com.tourkakao.carping.EcoCarping.EcoCarpingListActivity;
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
    private MainEcoFragmentBinding echobinding;
    Context context;
    EcoViewModel ecoViewModel;
    private EcoReviewAdapter ecoReviewAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        echobinding= MainEcoFragmentBinding.inflate(inflater, container, false);
        context=getActivity().getApplicationContext();

        ecoViewModel =new ViewModelProvider(this).get(EcoViewModel.class);
        ecoViewModel.setContext(context);

        initializeImg();

        settingEchoReview();
        settingEchoRanking();

        echobinding.totalButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.total_button:
                        Intent intent=new Intent(getActivity(),EcoCarpingListActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });

        return echobinding.getRoot();
    }



    public void initializeImg(){
        Glide.with(this).load(R.drawable.main_eco_check_mark).into(echobinding.mainEcoCheckMark);
        Glide.with(this).load(R.drawable.main_eco_image).into(echobinding.mainEcoImage);
        Glide.with(this).load(R.drawable.main_eco_write_button).into(echobinding.mainEcoWriteButton);
        Glide.with(this).load(R.drawable.no_ranking_image).into(echobinding.noRankingImg);
        Glide.with(this).load(R.drawable.no_review_image).into(echobinding.noReviewImg);
    }

    public void settingEchoReview(){
        echobinding.ecoCarpingReview.setLayoutManager(new LinearLayoutManager(context));
        ecoViewModel.ecoReviewLiveData.observe(this,echoReviewObserver);
        ecoViewModel.todayLiveCount.observe(this,echoTodayCountObserver);
    }

    Observer<Integer> echoTodayCountObserver=new Observer<Integer>() {
        @Override
        public void onChanged(Integer integer) {
            echobinding.todayEcoCarpingCount
                    .setText("오늘 "+Integer.toString(integer)+"개의 에코카핑 인증");
        }
    };

    Observer<ArrayList<EcoReview>> echoReviewObserver=new Observer<ArrayList<EcoReview>>() {
        @Override
        public void onChanged(ArrayList<EcoReview> ecoReviews) {
            if(ecoReviews==null){
                echobinding.noReviewImg.setVisibility(View.VISIBLE);
                echobinding.ecoCarpingReview.setVisibility(View.GONE);
            }else{
                echobinding.noReviewImg.setVisibility(View.GONE);
                echobinding.ecoCarpingReview.setVisibility(View.VISIBLE);
                echobinding.ecoCarpingReview.setAdapter(new EcoReviewAdapter(context, ecoReviews));
            }
        }
    };

    public void settingEchoRanking(){
        echobinding.ecoRanking.setLayoutManager(new LinearLayoutManager(context));
        ecoViewModel.monthlyEcoCount.observe(this,echoMonthlyCountObserver);
        ecoViewModel.ecoRankingLiveData.observe(this,echoRankingObserver);
    }

    Observer<ArrayList<EcoRanking>> echoRankingObserver=new Observer<ArrayList<EcoRanking>>() {
        @Override
        public void onChanged(ArrayList<EcoRanking> ecoRankings) {
            if(ecoRankings==null){
                echobinding.noRankingImg.setVisibility(View.VISIBLE);
                echobinding.ecoRanking.setVisibility(View.GONE);
            }else{
                echobinding.noRankingImg.setVisibility(View.GONE);
                echobinding.ecoRanking.setVisibility(View.VISIBLE);
                echobinding.ecoRanking.setAdapter(new EcoRankingAdapter(context, ecoRankings));
            }
        }
    };

    Observer<Integer> echoMonthlyCountObserver=new Observer<Integer>() {
        @Override
        public void onChanged(Integer integer) {
            echobinding.username.setText(ecoViewModel.currentUser.getValue().getUsername());
            echobinding.monthWrite
                    .setText("최근 1개월 간\n에코리뷰 "+Integer.toString(integer)+"개 작성");
        }
    };
}
