package com.tourkakao.carping.Thisweekend.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.tourkakao.carping.R;
import com.tourkakao.carping.Thisweekend.ViewModel.Total_ThisWeekend_ViewModel;
import com.tourkakao.carping.databinding.ActivityThisWeekendTotalBinding;

public class ThisWeekend_totalActivity extends AppCompatActivity {
    private ActivityThisWeekendTotalBinding thisWeekendTotalBinding;
    Context context;
    Total_ThisWeekend_ViewModel totalThisWeekendViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        thisWeekendTotalBinding=ActivityThisWeekendTotalBinding.inflate(getLayoutInflater());
        setContentView(thisWeekendTotalBinding.getRoot());
        context=getApplicationContext();
        Glide.with(context).load(R.drawable.empty_data_img).into(thisWeekendTotalBinding.noWeekendImg);

        totalThisWeekendViewModel=new ViewModelProvider(this).get(Total_ThisWeekend_ViewModel.class);
        totalThisWeekendViewModel.setContext(context);

        setting_total_thisweekend_posts();
        starting_observe_total_thisweekend();
    }

    public void setting_total_thisweekend_posts(){
        GridLayoutManager layoutManager=new GridLayoutManager(context, 2);
        thisWeekendTotalBinding.thisWeekendRecyclerview.setLayoutManager(layoutManager);
        thisWeekendTotalBinding.thisWeekendRecyclerview.setAdapter(totalThisWeekendViewModel.setting_total_thisweekend_adpater());
        totalThisWeekendViewModel.getTotal_thisweekends();
    }

    public void starting_observe_total_thisweekend(){
        totalThisWeekendViewModel.total_thisweekends_post_cnt.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if(integer==0){
                    thisWeekendTotalBinding.noWeekendImg.setVisibility(View.VISIBLE);
                    thisWeekendTotalBinding.thisWeekendRecyclerview.setVisibility(View.GONE);
                }else{
                    thisWeekendTotalBinding.noWeekendImg.setVisibility(View.GONE);
                    thisWeekendTotalBinding.thisWeekendRecyclerview.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}