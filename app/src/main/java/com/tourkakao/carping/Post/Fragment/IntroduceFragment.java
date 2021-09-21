package com.tourkakao.carping.Post.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.tourkakao.carping.MypageMainActivities.MypageViewModel.MypageCarpingViewModel;
import com.tourkakao.carping.R;
import com.tourkakao.carping.databinding.MypageCarpingScrapTabFragmentBinding;
import com.tourkakao.carping.databinding.PostIntroduceFragmentBinding;

public class IntroduceFragment extends Fragment {
    private PostIntroduceFragmentBinding binding;
    private Context context;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = PostIntroduceFragmentBinding.inflate(inflater, container, false);
        context = getActivity().getApplicationContext();

        initLayout();

        return binding.getRoot();
    }

    public void initLayout(){
        Glide.with(context).load(R.drawable.posting_icon).into(binding.postingImg);
        Glide.with(context).load(R.drawable.contents_icon).into(binding.contentsNumberImg);
        Glide.with(context).load(R.drawable.like_icon_purple).into(binding.postingImg);
    }
}
