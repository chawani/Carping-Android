package com.tourkakao.carping.MypageMainActivities.Fragment;

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

import com.tourkakao.carping.Home.ThemeFragmentAdapter.ThisWeekend_Adapter;
import com.tourkakao.carping.MypageMainActivities.Adapter.ScrapAutoAdapter;
import com.tourkakao.carping.MypageMainActivities.Adapter.ScrapCarpingAdapter;
import com.tourkakao.carping.MypageMainActivities.DTO.Autocamp;
import com.tourkakao.carping.MypageMainActivities.DTO.Campsite;
import com.tourkakao.carping.MypageMainActivities.DTO.MyCarpingPost;
import com.tourkakao.carping.MypageMainActivities.MypageViewModel.MypageCarpingViewModel;
import com.tourkakao.carping.databinding.MypageCarpingScrapTabFragmentBinding;
import com.tourkakao.carping.newcarping.Activity.Each_NewCarpingActivity;
import com.tourkakao.carping.theme.Activity.ThemeDetailActivity;
import com.tourkakao.carping.thisweekend.Activity.Each_ThisWeekendActivity;

import java.util.ArrayList;

public class LikeCarpingFragment extends Fragment {
    private MypageCarpingScrapTabFragmentBinding binding;
    private Context context;
    private MypageCarpingViewModel viewModel;
    private ScrapAutoAdapter adapter;
    private ScrapCarpingAdapter adapter2;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.LayoutManager mLayoutManager2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = MypageCarpingScrapTabFragmentBinding.inflate(inflater, container, false);
        context = getActivity().getApplicationContext();

        viewModel = new ViewModelProvider(this).get(MypageCarpingViewModel.class);
        viewModel.setContext(context);

        initLayout();
        initDatas();

        return binding.getRoot();
    }

    void initLayout(){
        binding.mypageEmptyText.setText("스크랩한 차박지가 없습니다.");
    }
    void initDatas(){
        viewModel.getAutocampsLiveData().observe(this, new Observer<ArrayList<MyCarpingPost>>() {
            @Override
            public void onChanged(ArrayList<MyCarpingPost> autocamps) {
                if(autocamps.size()==0){
                    binding.mypageRecycler.setVisibility(View.GONE);
                }
                else{
                    mLayoutManager = new LinearLayoutManager(getActivity());
                    binding.mypageRecycler.setLayoutManager(mLayoutManager);
                    binding.mypageEmptyText.setVisibility(View.GONE);
                    binding.mypageRecycler.setVisibility(View.VISIBLE);
                    adapter=new ScrapAutoAdapter(context,autocamps);
                    binding.mypageRecycler.setAdapter(adapter);
                    adapter.setOnSelectItemCLickListener(new ScrapAutoAdapter.OnSelectItemClickListener() {
                        @Override
                        public void OnSelectItemClick(View v, int pos, int pk) {
                            Intent intent=new Intent(context, Each_NewCarpingActivity.class);
                            intent.putExtra("pk", pk);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        }
                    });
                }
            }
        });
        viewModel.getCampsitesLiveData().observe(this, new Observer<ArrayList<Campsite>>() {
            @Override
            public void onChanged(ArrayList<Campsite> campsites) {
                if(campsites.size()==0){
                    binding.mypageRecycler2.setVisibility(View.GONE);
                }
                else{
                    mLayoutManager2=new LinearLayoutManager(getActivity());
                    binding.mypageRecycler2.setLayoutManager(mLayoutManager2);
                    binding.mypageEmptyText.setVisibility(View.GONE);
                    binding.mypageRecycler2.setVisibility(View.VISIBLE);
                    adapter2=new ScrapCarpingAdapter(context,campsites);
                    binding.mypageRecycler2.setAdapter(adapter2);
                    adapter2.setOnSelectItemCLickListener(new ScrapCarpingAdapter.OnSelectItemClickListener() {
                        @Override
                        public void OnSelectItemClick(View v, int pos, int pk) {
                            Intent intent=new Intent(context, ThemeDetailActivity.class);
                            intent.putExtra("name", adapter2.getName(pos));
                            intent.putExtra("pk", pk);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        }
                    });
                }
            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        viewModel.loadScrapCarpings();
    }
}
