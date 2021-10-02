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

import com.tourkakao.carping.EcoCarping.Adapter.EcoTotalReviewAdapter;
import com.tourkakao.carping.Home.EcoDataClass.EcoReview;
import com.tourkakao.carping.Home.ShareDataClass.Share;
import com.tourkakao.carping.MypageMainActivities.MypageViewModel.MypageEcoViewModel;
import com.tourkakao.carping.MypageMainActivities.MypageViewModel.MypageSharingViewModel;
import com.tourkakao.carping.databinding.MypageTabFragmentBinding;

import java.util.ArrayList;

public class LikeSharingFragment extends Fragment {
    private MypageTabFragmentBinding binding;
    private Context context;
    private MypageSharingViewModel viewModel;
    private RecyclerView.LayoutManager mLayoutManager;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = MypageTabFragmentBinding.inflate(inflater, container, false);
        context = getActivity().getApplicationContext();

        viewModel = new ViewModelProvider(this).get(MypageSharingViewModel.class);
        viewModel.setContext(context);

        initLayout();
        observeData();

        return binding.getRoot();
    }

    void initLayout(){
        binding.mypageEmptyText.setText("좋아요한 무료나눔이 없습니다.");
        mLayoutManager = new LinearLayoutManager(getActivity());
        binding.mypageRecycler.setLayoutManager(mLayoutManager);
        binding.mypageRecycler.setAdapter(viewModel.settingLikeAdapter());
    }

    void observeData(){
        viewModel.getLikeShare().observe(this, new Observer<ArrayList<Share>>() {
            @Override
            public void onChanged(ArrayList<Share> shares) {
                if(shares==null){
                    binding.mypageEmptyText.setVisibility(View.VISIBLE);
                    binding.mypageRecycler.setVisibility(View.GONE);
                }
                else {
                    binding.mypageEmptyText.setVisibility(View.GONE);
                    binding.mypageRecycler.setVisibility(View.VISIBLE);
                }
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        viewModel.getSharePosts("like");
    }
}
