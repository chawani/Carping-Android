package com.tourkakao.carping.Mypage;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.tourkakao.carping.MypageMainActivities.MypageViewModel.MypageEcoViewModel;
import com.tourkakao.carping.databinding.MypageTabFragmentBinding;

public class ReviewCompleteFragment extends Fragment {
    MypageTabFragmentBinding binding;
    Context context;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = MypageTabFragmentBinding.inflate(inflater, container, false);
        context = getActivity().getApplicationContext();

        initLayout();

        return binding.getRoot();
    }

    void initLayout(){
        binding.mypageEmptyText.setText("스크랩한 포스트가 없습니다.");
    }
}
