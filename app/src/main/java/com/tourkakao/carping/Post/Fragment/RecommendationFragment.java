package com.tourkakao.carping.Post.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tourkakao.carping.databinding.PostIntroduceFragmentBinding;
import com.tourkakao.carping.databinding.PostRecommendationFragmentBinding;

public class RecommendationFragment extends Fragment {
    private PostRecommendationFragmentBinding binding;
    private Context context;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = PostRecommendationFragmentBinding.inflate(inflater, container, false);
        context = getActivity().getApplicationContext();

        initLayout();

        return binding.getRoot();
    }

    public void initLayout(){

    }
}
