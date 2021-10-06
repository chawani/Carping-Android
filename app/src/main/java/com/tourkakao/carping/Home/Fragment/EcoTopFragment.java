package com.tourkakao.carping.Home.Fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.tourkakao.carping.Home.EcoDataClass.EcoRanking;
import com.tourkakao.carping.Home.HomeViewModel.EcoViewModel;
import com.tourkakao.carping.databinding.MainEcoTopFragmentBinding;

import java.time.LocalDate;

public class EcoTopFragment extends Fragment {
    private MainEcoTopFragmentBinding ecobinding;
    private Context context;
    private EcoViewModel ecoViewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ecobinding= MainEcoTopFragmentBinding.inflate(inflater, container, false);

        ecoViewModel =new ViewModelProvider(this).get(EcoViewModel.class);
        ecoViewModel.setContext(context);

        ecoViewModel.ecoPercentage.observe(this,percentageObserver);
        ecoViewModel.currentUser.observe(this,userObserver);

        setting_today_date();

        return ecobinding.getRoot();
    }

    Observer<Integer> percentageObserver=new Observer<Integer>() {
        @Override
        public void onChanged(Integer integer) {
            ecobinding.percent.setText(Integer.toString(integer)+"%");
        }
    };

    Observer<EcoRanking> userObserver=new Observer<EcoRanking>() {
        @Override
        public void onChanged(EcoRanking ecoRanking) {
            ecobinding.username.setText(ecoRanking.getUsername());
            ecobinding.level.setText("LV. "+ecoRanking.getLevel());
        }
    };

    public void setting_today_date(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O) {
            LocalDate now = LocalDate.now();
            int month=now.getMonthValue();
            int dayofmonth=now.getDayOfMonth();
            int day=now.getDayOfWeek().getValue();
            String daystr="";
            if(day==1){
                daystr="월요일";
            }else if(day==2){
                daystr="화요일";
            }else if(day==3){
                daystr="수요일";
            }else if(day==4){
                daystr="목요일";
            }else if(day==5){
                daystr="금요일";
            }else if(day==6){
                daystr="토요일";
            }else if(day==7){
                daystr="일요일";
            }
            ecobinding.date.setText(month+"월 "+dayofmonth+"일 "+daystr);

        }else{
            ecobinding.date.setVisibility(View.GONE);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        ecoViewModel.getRanking();
    }
}
