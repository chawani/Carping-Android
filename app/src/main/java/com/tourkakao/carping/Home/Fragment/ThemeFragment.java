package com.tourkakao.carping.Home.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.tourkakao.carping.Home.HomeContract;
import com.tourkakao.carping.Home.HomeNetwork.ThemeInterface;
import com.tourkakao.carping.Home.MainActivity;
import com.tourkakao.carping.Home.ThemeFragmentAdapter.Az_Adapter;
import com.tourkakao.carping.Home.ThemeFragmentAdapter.NewCarpingPlace_Adapter;
import com.tourkakao.carping.Home.ThemeFragmentAdapter.PopularCarpingPlace_Adapter;
import com.tourkakao.carping.Home.ThemeFragmentAdapter.ThisWeekend_Adapter;
import com.tourkakao.carping.R;
import com.tourkakao.carping.databinding.MainThemeFragmentBinding;

public class ThemeFragment extends Fragment implements HomeContract.ThemeFragment_Contract, View.OnClickListener {
    private MainThemeFragmentBinding themebinding;
    NewCarpingPlace_Adapter newCarpingPlace_adapter;
    ThisWeekend_Adapter thisWeekend_adapter;
    Az_Adapter az_adapter;
    PopularCarpingPlace_Adapter popularCarpingPlace_adapter;
    Context context;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        themebinding=MainThemeFragmentBinding.inflate(inflater, container, false);

        initialize_img();
        initialize_new_carping_place_recyclerview();
        initialize_this_weekend_recyclerview();
        initialize_az_recyclerview();
        setting_this_weekend_total_btn();
        setting_az_total_btn();

        return themebinding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        themebinding=null;
    }

    @Override
    public void initialize_img() {
        Glide.with(this).load(R.drawable.new_carping_place_title_img).into(themebinding.newCarpingPlaceTitleImg);
        Glide.with(this).load(R.drawable.thema_title_img).into(themebinding.themeTitleImg);
        Glide.with(this).load(R.drawable.this_weekend_title_img).into(themebinding.thisWeekendTitleImg);
        Glide.with(this).load(R.drawable.az_title_img).into(themebinding.azImg);
        Glide.with(this).load(R.drawable.popular_carping_title_img).into(themebinding.popularCarpingImg);
        Glide.with(this).load(R.drawable.total_img).into(themebinding.weekendTotalImg);
        Glide.with(this).load(R.drawable.total_img).into(themebinding.azTotalImg);
        Glide.with(this).load(R.drawable.theme_culture_img).into(themebinding.themeCulture);
        Glide.with(this).load(R.drawable.theme_fire_img).into(themebinding.themeFire);
        Glide.with(this).load(R.drawable.theme_pet_img).into(themebinding.themePet);
        Glide.with(this).load(R.drawable.theme_etc_img).into(themebinding.themeEtc);
        Glide.with(this).load(R.drawable.theme_season_img).into(themebinding.themeSeason);
        Glide.with(this).load(R.drawable.theme_reports_img).into(themebinding.themeReports);
        Glide.with(this).load(R.drawable.theme_nature_img).into(themebinding.themeNature);
        Glide.with(this).load(R.drawable.theme_exp_img).into(themebinding.themeExp);
    }

    @Override
    public void initialize_new_carping_place_recyclerview() {
        newCarpingPlace_adapter=new NewCarpingPlace_Adapter(context);
        themebinding.newCarpingPlaceRecyclerview.setLayoutManager();
        themebinding.newCarpingPlaceRecyclerview.setAdapter(newCarpingPlace_adapter);
        newCarpingPlace_adapter.setOnSelectItemCLickListener();
    }

    @Override
    public void initialize_this_weekend_recyclerview() {
        thisWeekend_adapter=new ThisWeekend_Adapter(context);
        themebinding.thisWeekendRecyclerview.setLayoutManager();
        themebinding.thisWeekendRecyclerview.setAdapter(thisWeekend_adapter);
        thisWeekend_adapter.setOnSelectItemCLickListener();
    }

    @Override
    public void initialize_az_recyclerview() {
        az_adapter=new Az_Adapter(context);
        themebinding.azRecyclerview.setLayoutManager();
        themebinding.azRecyclerview.setAdapter(az_adapter);
        az_adapter.setOnSelectItemCLickListener();
    }

    @Override
    public void initialize_popular_carping_place_recyclerview() {
        popularCarpingPlace_adapter=new PopularCarpingPlace_Adapter(context);
        themebinding.popularCarpingRecyclerview.setLayoutManager();
        themebinding.popularCarpingRecyclerview.setAdapter(popularCarpingPlace_adapter);
    }


    @Override
    public void setting_this_weekend_total_btn() {

    }

    @Override
    public void setting_az_total_btn() {

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.theme_culture:
                break;
            case R.id.theme_fire:
                break;
            case R.id.theme_pet:
                break;
            case R.id.theme_etc:
                break;
            case R.id.theme_season:
                break;
            case R.id.theme_reports:
                break;
            case R.id.theme_nature:
                break;
            case R.id.theme_exp:
                break;
        }
    }
}
