package com.tourkakao.carping.newcarping.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tourkakao.carping.R;
import com.tourkakao.carping.SharedPreferenceManager.SharedPreferenceManager;
import com.tourkakao.carping.databinding.NewcarpingReviewFragmentBinding;
import com.tourkakao.carping.newcarping.Activity.Write_newcarping_reviewActivity;
import com.tourkakao.carping.newcarping.DataClass.Newcarping_Review;
import com.tourkakao.carping.newcarping.viewmodel.EachNewCarpingViewModel;

public class ReviewFragment extends Fragment {
    private NewcarpingReviewFragmentBinding reviewFragmentBinding;
    Context context;
    EachNewCarpingViewModel eachNewCarpingViewModel;
    int userpk;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        reviewFragmentBinding=NewcarpingReviewFragmentBinding.inflate(inflater, container, false);
        context=getActivity().getApplicationContext();
        userpk= SharedPreferenceManager.getInstance(context).getInt("id", 0);
        reviewFragmentBinding.setLifecycleOwner(this);
        reviewFragmentBinding.setEachnewcarpingviewmodel(eachNewCarpingViewModel);

        starting_observe_profile_image();
        starting_observe_review_count();
        setting_review_recyclerview();
        setting_review_image_recyclerview();
        setting_write_review();
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
    public void setting_review_image_recyclerview(){
        LinearLayoutManager layoutManager=new LinearLayoutManager(context);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        reviewFragmentBinding.newcarpingImagereviewRecyclerview.setLayoutManager(layoutManager);
        reviewFragmentBinding.newcarpingImagereviewRecyclerview.setAdapter(eachNewCarpingViewModel.setting_newcarping_review_image_adapter());
    }
    public void starting_observe_profile_image(){
        eachNewCarpingViewModel.review_profile.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Glide.with(context).load(s).circleCrop().into(reviewFragmentBinding.newcarpingProfileImg);
            }
        });
    }
    public void starting_observe_review_count(){
        eachNewCarpingViewModel.review_cnt_num.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if(integer==0){
                    reviewFragmentBinding.newcarpingImagereviewRecyclerview.setVisibility(View.GONE);
                    Glide.with(context).load(R.drawable.empty_newcarping_review_img).into(reviewFragmentBinding.noReviewImg);
                    reviewFragmentBinding.noReviewImg.setVisibility(View.VISIBLE);
                    reviewFragmentBinding.reviewStarLayout.setVisibility(View.GONE);
                    reviewFragmentBinding.newcarpingReviewRecyclerview.setVisibility(View.GONE);
                }else if(integer==1){
                    reviewFragmentBinding.newcarpingImagereviewRecyclerview.setVisibility(View.VISIBLE);
                    reviewFragmentBinding.noReviewImg.setVisibility(View.GONE);
                    reviewFragmentBinding.reviewStarLayout.setVisibility(View.VISIBLE);
                    reviewFragmentBinding.newcarpingReviewRecyclerview.setVisibility(View.VISIBLE);
                }
            }
        });
    }
    public void setting_write_review(){
        reviewFragmentBinding.myreview.setOnClickListener(v -> {
            Intent intent=new Intent(context, Write_newcarping_reviewActivity.class);
            intent.putExtra("title", eachNewCarpingViewModel.title.getValue());
            intent.putExtra("pk", eachNewCarpingViewModel.pk);
            startActivityForResult(intent, 100);
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100){
            if(resultCode== Activity.RESULT_OK){
                eachNewCarpingViewModel.get_newcarping_detail();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
