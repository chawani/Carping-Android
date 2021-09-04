package com.tourkakao.carping.theme.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.tourkakao.carping.GpsLocation.GpsTracker;
import com.tourkakao.carping.R;
import com.tourkakao.carping.theme.viewmodel.ThemeViewModel;
import com.tourkakao.carping.databinding.ActivityThemeBinding;

public class ThemeActivity extends AppCompatActivity {
    private ActivityThemeBinding themeBinding;
    Context context;
    ThemeViewModel themeViewModel;
    String theme;
    String sort="views";
    String select;
    double lat, lon;
    int select_position;
    int first_or_second_select=1;
    String[] common=new String[]{"인기순", "최신순", "거리순"};
    String[] season=new String[]{"봄", "여름", "가을", "겨울"};
    String[] leport=new String[]{"수상레저", "액티비티", "스키", "항공레저", "낚시"};
    String[] nature=new String[]{"봄꽃여행", "여름물놀이", "가을단풍명소", "겨울눈꽃명소", "걷기길", "일몰명소", "일출명소"};
    String[] other=new String[]{"일반야영장", "글램핑", "카라반"};
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        themeBinding=ActivityThemeBinding.inflate(getLayoutInflater());
        setContentView(themeBinding.getRoot());
        context=getApplicationContext();

        themeViewModel=new ViewModelProvider(this).get(ThemeViewModel.class);
        themeViewModel.setContext(context);

        theme=getIntent().getStringExtra("thema");
        listView=themeBinding.listView;
        Glide.with(context).load(R.drawable.back).into(themeBinding.back);
        setting_initial_tab();
        setting_tab();
        setting_sliding_up();
        getting_user_place();
        setting_recycleview();
    }
    public void setting_initial_tab(){
        switch(theme){
            case "event":
                select_position=0;
                select="";
                themeBinding.themeTabs.getTabAt(0).select();
                themeBinding.firstSelect.setVisibility(View.GONE);
                break;
            case "brazier":
                select_position=1;
                select="";
                themeBinding.themeTabs.getTabAt(1).select();
                themeBinding.firstSelect.setVisibility(View.GONE);
                break;
            case "animal":
                select_position=2;
                select="";
                themeBinding.themeTabs.getTabAt(2).select();
                themeBinding.firstSelect.setVisibility(View.GONE);
                break;
            case "season":
                select_position=3;
                select="봄";
                themeBinding.themeTabs.getTabAt(3).select();
                themeBinding.firstSelect.setText("봄 ▼");
                break;
            case "leports":
                select_position=4;
                select="수상레저";
                themeBinding.themeTabs.getTabAt(4).select();
                themeBinding.firstSelect.setText("수상레저 ▼");
                break;
            case "nature":
                select_position=5;
                select="봄꽃여행";
                themeBinding.themeTabs.getTabAt(5).select();
                themeBinding.firstSelect.setText("봄꽃여행 ▼");
                break;
            case "program":
                select_position=6;
                select="";
                themeBinding.themeTabs.getTabAt(6).select();
                themeBinding.firstSelect.setVisibility(View.GONE);
                break;
            case "others":
                select_position=7;
                select="일반야영장";
                themeBinding.themeTabs.getTabAt(7).select();
                themeBinding.firstSelect.setText("일반야영장 ▼");
                break;
        }
    }
    public void setting_tab(){
        themeBinding.themeTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position=tab.getPosition();
                switch(position){
                    case 0:
                        select_position=0;
                        theme="event"; sort="views"; select="";
                        themeBinding.themeRecyclerview.scrollToPosition(0);
                        themeViewModel.get_each_thema_carpingplace(theme, sort, select, lat, lon);
                        themeBinding.firstSelect.setVisibility(View.GONE);
                        themeBinding.secondSelect.setText("인기순▼");
                        break;
                    case 1:
                        select_position=1;
                        theme="brazier"; sort="views"; select="";
                        themeBinding.themeRecyclerview.scrollToPosition(0);
                        themeViewModel.get_each_thema_carpingplace(theme, sort, select, lat, lon);
                        themeBinding.firstSelect.setVisibility(View.GONE);
                        themeBinding.secondSelect.setText("인기순▼");
                        break;
                    case 2:
                        select_position=2;
                        theme="animal"; sort="views"; select="";
                        themeBinding.themeRecyclerview.scrollToPosition(0);
                        themeViewModel.get_each_thema_carpingplace(theme, sort, select, lat, lon);
                        themeBinding.firstSelect.setVisibility(View.GONE);
                        themeBinding.secondSelect.setText("인기순▼");
                        break;
                    case 3:
                        select_position=3;
                        theme="season"; sort="views"; select="봄";
                        themeBinding.themeRecyclerview.scrollToPosition(0);
                        themeViewModel.get_each_thema_carpingplace(theme, sort, select, lat, lon);
                        themeBinding.firstSelect.setVisibility(View.VISIBLE);
                        themeBinding.firstSelect.setText("봄 ▼");
                        themeBinding.secondSelect.setText("인기순▼");
                        break;
                    case 4:
                        select_position=4;
                        theme="leports"; sort="views"; select="수상레저";
                        themeBinding.themeRecyclerview.scrollToPosition(0);
                        themeViewModel.get_each_thema_carpingplace(theme, sort, select, lat, lon);
                        themeBinding.firstSelect.setVisibility(View.VISIBLE);
                        themeBinding.firstSelect.setText("수상레저 ▼");
                        themeBinding.secondSelect.setText("인기순▼");
                        break;
                    case 5:
                        select_position=5;
                        theme="nature"; sort="views"; select="봄꽃여행";
                        themeBinding.themeRecyclerview.scrollToPosition(0);
                        themeViewModel.get_each_thema_carpingplace(theme, sort, select, lat, lon);
                        themeBinding.firstSelect.setVisibility(View.VISIBLE);
                        themeBinding.firstSelect.setText("봄꽃여행 ▼");
                        themeBinding.secondSelect.setText("인기순▼");
                        break;
                    case 6:
                        select_position=6;
                        theme="program"; sort="views"; select="";
                        themeBinding.themeRecyclerview.scrollToPosition(0);
                        themeViewModel.get_each_thema_carpingplace(theme, sort, select, lat, lon);
                        themeBinding.firstSelect.setVisibility(View.GONE);
                        themeBinding.secondSelect.setText("인기순▼");
                        break;
                    case 7:
                        select_position=7;
                        theme="others"; sort="views"; select="일반야영장";
                        themeBinding.themeRecyclerview.scrollToPosition(0);
                        themeViewModel.get_each_thema_carpingplace(theme, sort, select, lat, lon);
                        themeBinding.firstSelect.setVisibility(View.VISIBLE);
                        themeBinding.firstSelect.setText("일반야영장 ▼");
                        themeBinding.secondSelect.setText("인기순▼");
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    public void setting_sliding_up(){
        themeBinding.firstSelect.setOnClickListener(v -> {
            first_or_second_select=0;
            switch(select_position){
                case 3:
                    listView.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, season));
                    themeBinding.title.setText("계절 선택");
                    themeBinding.slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
                    break;
                case 4:
                    listView.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, leport));
                    themeBinding.title.setText("레포츠 선택");
                    themeBinding.slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
                    break;
                case 5:
                    listView.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, nature));
                    themeBinding.title.setText("테마 선택");
                    themeBinding.slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
                    break;
                case 7:
                    listView.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, other));
                    themeBinding.title.setText("종류 선택");
                    themeBinding.slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
                    break;
            }
        });
        themeBinding.secondSelect.setOnClickListener(v -> {
            first_or_second_select=1;
            listView.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, common));
            themeBinding.title.setText("기본 선택");
            themeBinding.slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String select_str=parent.getItemAtPosition(position).toString();
                themeBinding.slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                if(first_or_second_select==1){
                    themeBinding.secondSelect.setText(select_str+"▼");
                    if(select_str.equals("인기순")){
                        sort="views";
                    }else if(select_str.equals("최신순")){
                        sort="recent";
                    }else{
                        sort="distance";
                    }
                }else{
                    themeBinding.firstSelect.setText(select_str+"▼");
                    select=select_str;
                }
                themeBinding.themeRecyclerview.scrollToPosition(0);
                themeViewModel.get_each_thema_carpingplace(theme, sort, select, lat, lon);
            }
        });
    }
    public void getting_user_place(){
        GpsTracker gpsTracker=new GpsTracker(context);
        lat=gpsTracker.getLatitude();
        lon=gpsTracker.getLongitude();
        gpsTracker.stopUsingGPS();
    }
    public void setting_recycleview(){
        themeBinding.themeRecyclerview.setLayoutManager(new LinearLayoutManager(context));
        themeBinding.themeRecyclerview.setAdapter(themeViewModel.setting_theme_adapter());
        themeBinding.themeRecyclerview.scrollToPosition(0);
        themeViewModel.get_each_thema_carpingplace(theme, sort, select, lat, lon);
    }
    @Override
    public void onBackPressed() {
        if(themeBinding.slidingLayout.getPanelState()==SlidingUpPanelLayout.PanelState.EXPANDED){
            themeBinding.slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        }
        else{
            finish();
        }
    }
}