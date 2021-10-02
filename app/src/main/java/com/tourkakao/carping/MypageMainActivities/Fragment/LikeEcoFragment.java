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
import com.tourkakao.carping.MypageMainActivities.MypageViewModel.MypageEcoViewModel;
import com.tourkakao.carping.databinding.MypageTabFragmentBinding;

import java.util.ArrayList;

public class LikeEcoFragment extends Fragment {
    private MypageTabFragmentBinding binding;
    private Context context;
    private MypageEcoViewModel viewModel;
    private EcoTotalReviewAdapter adapter;
    private RecyclerView.LayoutManager mLayoutManager;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = MypageTabFragmentBinding.inflate(inflater, container, false);
        context = getActivity().getApplicationContext();

        viewModel = new ViewModelProvider(this).get(MypageEcoViewModel.class);
        viewModel.setContext(context);

        initLayout();
        observeData();

        return binding.getRoot();
    }

    void initLayout(){
        binding.mypageEmptyText.setText("좋아요한 에코인증이 없습니다.");
        mLayoutManager = new LinearLayoutManager(getActivity());
        binding.mypageRecycler.setLayoutManager(mLayoutManager);
    }
    void observeData(){
        viewModel.getLikeReviews().observe(this, new Observer<ArrayList<EcoReview>>() {
            @Override
            public void onChanged(ArrayList<EcoReview> ecoReviews) {
                if(ecoReviews.size()==0){
                    binding.mypageEmptyText.setVisibility(View.VISIBLE);
                    binding.mypageRecycler.setVisibility(View.GONE);
                }
                else {
                    binding.mypageEmptyText.setVisibility(View.GONE);
                    binding.mypageRecycler.setVisibility(View.VISIBLE);
                    adapter = new EcoTotalReviewAdapter(context, ecoReviews);
                    binding.mypageRecycler.setAdapter(adapter);
                }
            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        viewModel.getEcoPosts("like");
    }
}
