package com.tourkakao.carping.Post.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.tourkakao.carping.EcoCarping.Activity.EcoCarpingDetailActivity;
import com.tourkakao.carping.EcoCarping.Adapter.CommentAdapter;
import com.tourkakao.carping.Post.Adapter.ReviewAdapter;
import com.tourkakao.carping.Post.DTO.PostInfoDetail;
import com.tourkakao.carping.Post.DTO.PostListItem;
import com.tourkakao.carping.Post.DTO.Review;
import com.tourkakao.carping.Post.PostDetailActivity;
import com.tourkakao.carping.Post.PostReviewActivity;
import com.tourkakao.carping.Post.ViewModel.PostDetailViewModel;
import com.tourkakao.carping.R;
import com.tourkakao.carping.SharedPreferenceManager.SharedPreferenceManager;
import com.tourkakao.carping.databinding.PostIntroduceFragmentBinding;
import com.tourkakao.carping.databinding.PostReviewFragmentBinding;

public class ReviewFragment extends Fragment {
    private PostReviewFragmentBinding binding;
    private Context context;
    private PostDetailViewModel viewModel;
    private ReviewAdapter reviewAdapter;
    private PostInfoDetail post;
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
        clickReviewMore();

        return binding.getRoot();
    }

    public void settingInfo(){
        String userImg= SharedPreferenceManager.getInstance(context).getString("profile","");
        String userName= SharedPreferenceManager.getInstance(context).getString("username","");
        Glide.with(context).load(userImg)
                .transform(new CenterCrop(), new RoundedCorners(100))
                .into(binding.profile);
        binding.currentUser.setText(userName);
        viewModel.getPostInfo().observe(this, new Observer<PostInfoDetail>() {
            @Override
            public void onChanged(PostInfoDetail postInfoDetail) {
                post=postInfoDetail;
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
                    reviewAdapter.setOnItemClickListener(new ReviewAdapter.OnDeleteItemClickListener() {
                        @Override
                        public void onItemClick(View v, int position) {
                            showDialog(reviewAdapter.getId(position));
                        }
                    });
                    binding.reviews.setAdapter(reviewAdapter);
                }
            }
        });
    }

    public void clickReviewMore(){
        binding.reviewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, PostReviewActivity.class);
                intent.putExtra("post", post);
                startActivity(intent);
            }
        });
    }

    void showDialog(int pk) {
        AlertDialog.Builder msgBuilder = new AlertDialog.Builder(getActivity())
                .setTitle("삭제")
                .setMessage("삭제하시겠습니까?")
                .setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialogInterface, int i) {
                        viewModel.deleteComment(pk);
                        viewModel.loadInfoDetail();
                    }
                })
                .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        AlertDialog msgDlg = msgBuilder.create();
        msgDlg.show();
    }
}
