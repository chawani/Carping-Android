package com.tourkakao.carping.newcarping.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tourkakao.carping.databinding.NewcarpingReviewFragmentBinding;

public class ReviewFragment extends Fragment {
    private NewcarpingReviewFragmentBinding reviewFragmentBinding;
    Context context;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        reviewFragmentBinding=NewcarpingReviewFragmentBinding.inflate(inflater, container, false);
        context=getActivity().getApplicationContext();

        return reviewFragmentBinding.getRoot();
    }
}
