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
import com.tourkakao.carping.databinding.NewcarpingReviewFragmentBinding;
import com.tourkakao.carping.newcarping.Activity.Write_newcarping_reviewActivity;
import com.tourkakao.carping.newcarping.DataClass.Newcarping_Review;
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
                Newcarping_Review review=new Newcarping_Review(data.getIntExtra("id", 0),
                        data.getIntExtra("user", 0),
                        data.getStringExtra("username"),
                        data.getStringExtra("profile"),
                        data.getStringExtra("text"),
                        data.getStringExtra("image"),
                        data.getFloatExtra("star1", 0),
                        data.getFloatExtra("star2", 0),
                        data.getFloatExtra("star3", 0),
                        data.getFloatExtra("star4", 0),
                        data.getFloatExtra("total_star", 0),
                        data.getStringExtra("created_at"),
                        data.getIntExtra("like_count", 0),
                        data.getIntExtra("check_like", 0));
                eachNewCarpingViewModel.update_review(review);
                eachNewCarpingViewModel.update_review_image(data.getStringExtra("image"));
                int mytotal=eachNewCarpingViewModel.my_review_cnt.getValue();
                float myn=(eachNewCarpingViewModel.my_star_avg.getValue()*mytotal+data.getFloatExtra("total_star", 0))/(mytotal+1);
                myn=Math.round(myn*10)/10;
                eachNewCarpingViewModel.my_star_avg.setValue(myn);
                eachNewCarpingViewModel.my_review_cnt.setValue(mytotal+1);
                int total=eachNewCarpingViewModel.review_cnt_num.getValue();
                float star1=(eachNewCarpingViewModel.star1.getValue()*total+data.getFloatExtra("star1", 0))/(total+1);
                star1=Math.round(star1*10)/10;
                float star2=(eachNewCarpingViewModel.star2.getValue()*total+data.getFloatExtra("star2", 0))/(total+1);
                star2=Math.round(star2*10)/10;
                float star3=(eachNewCarpingViewModel.star3.getValue()*total+data.getFloatExtra("star3", 0))/(total+1);
                star3=Math.round(star3*10)/10;
                float star4=(eachNewCarpingViewModel.star4.getValue()*total+data.getFloatExtra("star4", 0))/(total+1);
                star4=Math.round(star4*10)/10;
                float t_star=(eachNewCarpingViewModel.total_star.getValue()*total+data.getFloatExtra("total_star", 0))/(total+1);
                t_star=Math.round(t_star*10)/10;
                eachNewCarpingViewModel.star1.setValue(star1);
                eachNewCarpingViewModel.star2.setValue(star2);
                eachNewCarpingViewModel.star3.setValue(star3);
                eachNewCarpingViewModel.star4.setValue(star4);
                eachNewCarpingViewModel.total_star.setValue(t_star);
                eachNewCarpingViewModel.review_cnt_num.setValue(total+1);
                eachNewCarpingViewModel.review_count.setValue("리뷰 "+(total+1));
                eachNewCarpingViewModel.total_star_num.setValue(t_star+"("+(total+1)+")");
            }
        }
    }
}
