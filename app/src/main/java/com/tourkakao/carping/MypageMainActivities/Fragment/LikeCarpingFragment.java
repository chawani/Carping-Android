package com.tourkakao.carping.MypageMainActivities.Fragment;

import android.content.Context;
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

import com.tourkakao.carping.MypageMainActivities.Adapter.ScrapAutoAdapter;
import com.tourkakao.carping.MypageMainActivities.Adapter.ScrapCarpingAdapter;
import com.tourkakao.carping.MypageMainActivities.DTO.Autocamp;
import com.tourkakao.carping.MypageMainActivities.DTO.Campsite;
import com.tourkakao.carping.MypageMainActivities.MypageViewModel.MypageCarpingViewModel;
import com.tourkakao.carping.databinding.MypageCarpingScrapTabFragmentBinding;

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
        viewModel.loadScrapCarpings();
        viewModel.getPostSize().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if(integer==0){
                    binding.mypageEmptyText.setVisibility(View.VISIBLE);
                    binding.mypageRecycler.setVisibility(View.GONE);
                    binding.mypageRecycler2.setVisibility(View.GONE);
                }else{
                    binding.mypageEmptyText.setVisibility(View.GONE);
                }
            }
        });
        viewModel.getAutocampsLiveData().observe(this, new Observer<ArrayList<Autocamp>>() {
            @Override
            public void onChanged(ArrayList<Autocamp> autocamps) {
                if(autocamps==null){
                    binding.mypageRecycler.setVisibility(View.GONE);
                }
                else{
                    mLayoutManager = new LinearLayoutManager(getActivity());
                    binding.mypageRecycler.setLayoutManager(mLayoutManager);
                    binding.mypageEmptyText.setVisibility(View.GONE);
                    binding.mypageRecycler.setVisibility(View.VISIBLE);
                    adapter=new ScrapAutoAdapter(context,autocamps);
                    binding.mypageRecycler.setAdapter(adapter);
                }
            }
        });
        viewModel.getCampsitesLiveData().observe(this, new Observer<ArrayList<Campsite>>() {
            @Override
            public void onChanged(ArrayList<Campsite> campsites) {
                if(campsites==null){
                    binding.mypageRecycler2.setVisibility(View.GONE);
                }
                else{
                    mLayoutManager2=new LinearLayoutManager(getActivity());
                    binding.mypageRecycler2.setLayoutManager(mLayoutManager2);
                    binding.mypageEmptyText.setVisibility(View.GONE);
                    binding.mypageRecycler2.setVisibility(View.VISIBLE);
                    adapter2=new ScrapCarpingAdapter(context,campsites);
                    binding.mypageRecycler2.setAdapter(adapter2);
                }
            }
        });
    }
}
