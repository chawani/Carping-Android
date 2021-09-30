package com.tourkakao.carping.Home.Fragment;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tourkakao.carping.Home.ThemeDataClass.Search;
import com.tourkakao.carping.R;
import com.tourkakao.carping.databinding.MainThemeTopFragmentBinding;

import java.time.LocalDate;
import java.util.ArrayList;

public class ThemeTopFragment extends Fragment {
    public MainThemeTopFragmentBinding themebinding;
    Context context;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        themebinding=MainThemeTopFragmentBinding.inflate(inflater, container, false);
        context=getContext();

        setting_today_date();

        return themebinding.getRoot();
    }
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
            themebinding.date.setText(month+"월 "+dayofmonth+"일 "+daystr);

        }else{
            themebinding.date.setVisibility(View.GONE);
        }

    }
    public void changing_region(String region, ArrayList<Search> populars){
        themebinding.titleMassage.setText("현재 "+region+"에서 인기있는");
        if(populars.get(0).getName().length()>13){
            themebinding.text1.setText(populars.get(0).getName().substring(0, 12)+"...");
        }else {
            themebinding.text1.setText(populars.get(0).getName());
        }
        if(populars.get(1).getName().length()>13){
            themebinding.text2.setText(populars.get(1).getName().substring(0, 12)+"...");
        }else {
            themebinding.text2.setText(populars.get(1).getName());
        }
        if(populars.get(2).getName().length()>13){
            themebinding.text3.setText(populars.get(2).getName().substring(0, 12)+"...");
        }else {
            themebinding.text3.setText(populars.get(2).getName());
        }
        if(populars.get(3).getName().length()>13){
            themebinding.text4.setText(populars.get(3).getName().substring(0, 12)+"...");
        }else {
            themebinding.text4.setText(populars.get(3).getName());
        }
        if(populars.get(4).getName().length()>13){
            themebinding.text5.setText(populars.get(4).getName().substring(0, 12)+"...");
        }else {
            themebinding.text5.setText(populars.get(4).getName());
        }
        themebinding.changeContainer.setFlipInterval(3000);
        themebinding.changeContainer.setAutoStart(true);
        themebinding.changeContainer.setInAnimation(context, R.anim.search_in_anim);
        themebinding.changeContainer.setOutAnimation(context, R.anim.search_out_anim);
        themebinding.changeContainer.startFlipping();
    }

}
