package com.tourkakao.carping.Post.Fragment;

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

import com.bumptech.glide.Glide;
import com.tourkakao.carping.EcoCarping.Activity.EcoCarpingDetailActivity;
import com.tourkakao.carping.EcoCarping.Adapter.CommentAdapter;
import com.tourkakao.carping.Post.Adapter.ReviewAdapter;
import com.tourkakao.carping.Post.DTO.PostInfoDetail;
import com.tourkakao.carping.Post.ViewModel.PostDetailViewModel;
import com.tourkakao.carping.R;
import com.tourkakao.carping.databinding.PostIntroduceFragmentBinding;
import com.tourkakao.carping.databinding.PostReviewFragmentBinding;

public class ReviewFragment extends Fragment {
    private PostReviewFragmentBinding binding;
    private Context context;
    private PostDetailViewModel viewModel;
    private ReviewAdapter reviewAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = PostReviewFragmentBinding.inflate(inflater, container, false);
        context = getActivity().getApplicationContext();

        viewModel =new ViewModelProvider(requireActivity()).get(PostDetailViewModel.class);
        viewModel.setContext(context);

        binding.reviews.setLayoutManager(new LinearLayoutManager(context));
        Glide.with(context).load(R.drawable.review_more_button).into(binding.reviewMore);
        settingInfo();

        return binding.getRoot();
    }

    public void settingInfo(){
        viewModel.getPostInfo().observe(this, new Observer<PostInfoDetail>() {
            @Override
            public void onChanged(PostInfoDetail postInfoDetail) {
                //Glide.with(context).load(postInfoDetail.).into(binding.profile);
                //binding.currentUser.setText(postInfoDetail.);
                binding.reviewCount.setText("리뷰 "+postInfoDetail.getReview_count());
                binding.reviewAvg.setText(Float.toString(postInfoDetail.getTotal_star_avg()));
                binding.totalStar.setRating(postInfoDetail.getTotal_star_avg());
                binding.star1.setText(Float.toString(postInfoDetail.getStar1_avg()));
                binding.star2.setText(Float.toString(postInfoDetail.getStar2_avg()));
                binding.star3.setText(Float.toString(postInfoDetail.getStar3_avg()));
                binding.star4.setText(Float.toString(postInfoDetail.getStar4_avg()));
                binding.start1Star.setRating(postInfoDetail.getStar1_avg());
                binding.start2Star.setRating(postInfoDetail.getStar2_avg());
                binding.start3Star.setRating(postInfoDetail.getStar3_avg());
                binding.start4Star.setRating(postInfoDetail.getStar4_avg());
                if(postInfoDetail.getReview()!=null) {
                    reviewAdapter = new ReviewAdapter(context,postInfoDetail.getReview());
                    binding.reviews.setAdapter(reviewAdapter);
                }
            }
        });
    }
}
