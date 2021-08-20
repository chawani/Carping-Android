package com.tourkakao.carping.newcarping.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.tourkakao.carping.databinding.NewcarpingReviewFragmentBinding;
import com.tourkakao.carping.newcarping.viewmodel.EachNewCarpingViewModel;

public class ReviewFragment extends Fragment {
    private NewcarpingReviewFragmentBinding reviewFragmentBinding;
    Context context;
    EachNewCarpingViewModel eachNewCarpingViewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        reviewFragmentBinding=NewcarpingReviewFragmentBinding.inflate(inflater, container, false);
        context=getActivity().getApplicationContext();
        reviewFragmentBinding.setLifecycleOwner(this);
        reviewFragmentBinding.setEachnewcarpingviewmodel(eachNewCarpingViewModel);

        starting_observe_profile_image();
        setting_review_recyclerview();
        return reviewFragmentBinding.getRoot();
    }

    public void setting_viewmodel(EachNewCarpingViewModel eachNewCarpingViewModel){
        this.eachNewCarpingViewModel=eachNewCarpingViewModel;
    }

    public void setting_review_recyclerview(){
        LinearLayoutManager layoutManager=new LinearLayoutManager(context);
        reviewFragmentBinding.newcarpingReviewRecyclerview.setLayoutManager(layoutManager);
        reviewFragmentBinding.newcarpingReviewRecyclerview.setAdapter(eachNewCarpingViewModel.setting_newcarping_review_adapter());
    }
    public void starting_observe_profile_image(){
        eachNewCarpingViewModel.review_profile.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Glide.with(context).load(s).circleCrop().into(reviewFragmentBinding.newcarpingProfileImg);
            }
        });
    }
}
