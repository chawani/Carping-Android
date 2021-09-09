package com.tourkakao.carping.MypageMainActivities.Fragment;

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

public class LikeSharingFragment extends Fragment {
    MypageTabFragmentBinding binding;
    Context context;
    MypageEcoViewModel viewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = MypageTabFragmentBinding.inflate(inflater, container, false);
        context = getActivity().getApplicationContext();

        viewModel = new ViewModelProvider(this).get(MypageEcoViewModel.class);
        viewModel.setContext(context);

        return binding.getRoot();
    }

    void initLayout(){
        binding.mypageEmptyText.setText("좋아요한 무료나눔이 없습니다.");
    }
}
