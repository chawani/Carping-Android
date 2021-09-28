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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tourkakao.carping.Home.HomeContract;
import com.tourkakao.carping.Home.HomeViewModel.ThemeViewModel;
import com.tourkakao.carping.R;
import com.tourkakao.carping.theme.Activity.ThemeActivity;
import com.tourkakao.carping.thisweekend.Activity.ThisWeekend_totalActivity;
import com.tourkakao.carping.databinding.MainThemeFragmentBinding;

public class ThemeFragment extends Fragment implements HomeContract.ThemeFragment_Contract {
    private MainThemeFragmentBinding themebinding;
    Context context;
    ThemeViewModel themeViewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        themebinding=MainThemeFragmentBinding.inflate(inflater, container, false);
        context=getActivity().getApplicationContext();
        //setting view binding

        themeViewModel=new ViewModelProvider(this).get(ThemeViewModel.class);
        themeViewModel.setContext(context);

        initialize_img();
        setting_thema_click();

        setting_thisweekend_posts();
        starting_observe_this_weekends();

        setting_az_posts();
        starting_observe_az();

        setting_newcarping_posts();
        starting_observe_newcarping();

        setting_popularcarping_posts();
        starting_observe_popularcarping();

        setting_this_weekend_total_btn();
        setting_az_total_btn();

        return themebinding.getRoot();
    }

    @Override
    public void initialize_img() {
        Glide.with(this).load(R.drawable.new_carping_place_title_img).into(themebinding.newCarpingPlaceTitleImg);
        Glide.with(this).load(R.drawable.thema_title_img).into(themebinding.themeTitleImg);
        Glide.with(this).load(R.drawable.this_weekend_title_img).into(themebinding.thisWeekendTitleImg);
        Glide.with(this).load(R.drawable.az_title_img).into(themebinding.azImg);
        Glide.with(this).load(R.drawable.popular_carping_title_img).into(themebinding.popularCarpingImg);
        Glide.with(this).load(R.drawable.theme_culture_img).into(themebinding.themeCulture);
        Glide.with(this).load(R.drawable.theme_fire_img).into(themebinding.themeFire);
        Glide.with(this).load(R.drawable.theme_pet_img).into(themebinding.themePet);
        Glide.with(this).load(R.drawable.theme_etc_img).into(themebinding.themeEtc);
        Glide.with(this).load(R.drawable.theme_season_img).into(themebinding.themeSeason);
        Glide.with(this).load(R.drawable.theme_reports_img).into(themebinding.themeReports);
        Glide.with(this).load(R.drawable.theme_nature_img).into(themebinding.themeNature);
        Glide.with(this).load(R.drawable.theme_exp_img).into(themebinding.themeExp);
    }


    public void setting_thisweekend_posts(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        themebinding.thisWeekendRecyclerview.setLayoutManager(layoutManager);
        themebinding.thisWeekendRecyclerview.setAdapter(themeViewModel.setting_thisweekend_adapter());

    }

    public void starting_observe_this_weekends(){
        themeViewModel.main_thisweekends_post_cnt.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if(integer==0){
                    Glide.with(context).load(R.drawable.empty_data_img).into(themebinding.noWeekendImg);
                    themebinding.noWeekendImg.setVisibility(View.VISIBLE);
                    themebinding.thisWeekendRecyclerview.setVisibility(View.GONE);
                }else if(integer>=1){
                    themebinding.noWeekendImg.setVisibility(View.GONE);
                    themebinding.thisWeekendRecyclerview.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void setting_az_posts(){
        LinearLayoutManager layoutManager=new LinearLayoutManager(context);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        themebinding.azRecyclerview.setLayoutManager(layoutManager);
        themebinding.azRecyclerview.setAdapter(themeViewModel.setting_az_adapter());

    }

    public void starting_observe_az(){
        themeViewModel.main_az_post_cnt.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                System.out.println(integer+" "+"az");
                if(integer==0){
                    Glide.with(context).load(R.drawable.empty_az_img).into(themebinding.noAzImg);
                    themebinding.noAzImg.setVisibility(View.VISIBLE);
                    themebinding.azRecyclerview.setVisibility(View.GONE);
                }else if(integer>=1){
                    themebinding.noAzImg.setVisibility(View.GONE);
                    themebinding.azRecyclerview.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void setting_newcarping_posts(){
        LinearLayoutManager layoutManager=new LinearLayoutManager(context);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        themebinding.newCarpingPlaceRecyclerview.setLayoutManager(layoutManager);
        themebinding.newCarpingPlaceRecyclerview.setAdapter(themeViewModel.setting_newcarping_place_adapter());

    }

    public void starting_observe_newcarping(){
        themeViewModel.main_new_post_cnt.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if(integer==0){
                    Glide.with(context).load(R.drawable.empty_new_carping_place_img).into(themebinding.noNewImg);
                    themebinding.noNewImg.setVisibility(View.VISIBLE);
                    themebinding.newCarpingPlaceRecyclerview.setVisibility(View.GONE);
                }else if(integer>=1){
                    themebinding.noNewImg.setVisibility(View.GONE);
                    themebinding.newCarpingPlaceRecyclerview.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void setting_popularcarping_posts(){
        LinearLayoutManager layoutManager=new LinearLayoutManager(context);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        themebinding.popularCarpingRecyclerview.setLayoutManager(layoutManager);
        themebinding.popularCarpingRecyclerview.setAdapter(themeViewModel.setting_popularcarping_place_adapter());

    }

    public void starting_observe_popularcarping(){
        themeViewModel.main_popular_cnt.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if(integer==0){
                    Glide.with(context).load(R.drawable.empty_data_img).into(themebinding.noPopularImg);
                    themebinding.noPopularImg.setVisibility(View.VISIBLE);
                    themebinding.popularCarpingRecyclerview.setVisibility(View.GONE);
                }else if(integer>=1){
                    themebinding.noPopularImg.setVisibility(View.GONE);
                    themebinding.popularCarpingRecyclerview.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public void setting_this_weekend_total_btn() {
        themebinding.thisWeekendTotalTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, ThisWeekend_totalActivity.class));
            }
        });
    }

    @Override
    public void setting_az_total_btn() {

    }

    public void setting_thema_click(){
        Intent themeintent=new Intent(context, ThemeActivity.class);
        themebinding.themeCulture.setOnClickListener(v -> {
            themeintent.putExtra("thema", "event");
            startActivity(themeintent);
        });
        themebinding.themeFire.setOnClickListener(v -> {
            themeintent.putExtra("thema", "brazier");
            startActivity(themeintent);
        });
        themebinding.themePet.setOnClickListener(v -> {
            themeintent.putExtra("thema", "animal");
            startActivity(themeintent);
        });
        themebinding.themeEtc.setOnClickListener(v -> {
            themeintent.putExtra("thema", "others");
            startActivity(themeintent);
        });
        themebinding.themeSeason.setOnClickListener(v -> {
            themeintent.putExtra("thema", "season");
            startActivity(themeintent);
        });
        themebinding.themeReports.setOnClickListener(v -> {
            themeintent.putExtra("thema", "leports");
            startActivity(themeintent);
        });
        themebinding.themeNature.setOnClickListener(v -> {
            themeintent.putExtra("thema", "nature");
            startActivity(themeintent);
        });
        themebinding.themeExp.setOnClickListener(v -> {
            themeintent.putExtra("thema", "program");
            startActivity(themeintent);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        themebinding=null;
    }


    @Override
    public void onResume() {
        super.onResume();
        themeViewModel.getMain_thisweekends();
        themeViewModel.getAz();
        themeViewModel.getNewCarpingPlace();
        themeViewModel.getPopularCarpingPlace();
    }
}
