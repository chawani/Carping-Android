package com.tourkakao.carping.Post;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.bumptech.glide.Glide;
import com.tourkakao.carping.EcoCarping.Adapter.EcoTotalReviewAdapter;
import com.tourkakao.carping.Post.Adapter.ReviewAdapter;
import com.tourkakao.carping.Post.DTO.PostInfoDetail;
import com.tourkakao.carping.Post.DTO.Review;
import com.tourkakao.carping.Post.DTO.Star;
import com.tourkakao.carping.Post.ViewModel.PostListViewModel;
import com.tourkakao.carping.Post.ViewModel.PostReviewViewModel;
import com.tourkakao.carping.R;
import com.tourkakao.carping.databinding.ActivityPostRegisterBinding;
import com.tourkakao.carping.databinding.ActivityPostReviewBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PostReviewActivity extends AppCompatActivity {
    private ActivityPostReviewBinding binding;
    private PostReviewViewModel viewModel;
    private Spinner spinner;
    private List<String> sort_list=new ArrayList<>();
    private LinearLayoutManager mLayoutManager;
    private String postId;
    private ReviewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityPostReviewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel =new ViewModelProvider(this).get(PostReviewViewModel.class);
        viewModel.setContext(this);

        postId=Integer.toString(getIntent().getIntExtra("postId",0));

        settingLayout();
        selectSinnerItem();
        settingInfo();
        clickWriteButton();
    }

    public void settingLayout(){
        mLayoutManager = new LinearLayoutManager(this);
        binding.recyclerview.setLayoutManager(mLayoutManager);
        Glide.with(getApplicationContext()).load(R.drawable.eco_carping_write_text).into(binding.writeButton);
        Glide.with(getApplicationContext()).load(R.drawable.cancel_img).into(binding.cancelButton);
        binding.cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void selectSinnerItem(){
        spinner=binding.sortSpinner;
        sort_list= Arrays.asList(getResources().getStringArray(R.array.eco_sort_item));
        ArrayAdapter<String> spinner_adapter=new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,sort_list);
        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinner_adapter);
        spinner.setOnItemSelectedListener(spinnerListener);
        spinner.setSelection(0);
    }

    public android.widget.AdapterView.OnItemSelectedListener spinnerListener=new AdapterView.OnItemSelectedListener(){
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            if(sort_list.get(i).equals("인기순")){
                viewModel.loadReviews(postId,"popular");
            }
            if(sort_list.get(i).equals("최신순")){
                viewModel.loadReviews(postId,"recent");
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
            adapter=new ReviewAdapter(getApplicationContext(),viewModel.getRecentOrderReviews().getValue());
            binding.recyclerview.setAdapter(adapter);
            clickReview();
        }
    };

    public void settingInfo(){
        viewModel.getRecentOrderReviews().observe(this, new Observer<ArrayList<Review>>() {
            @Override
            public void onChanged(ArrayList<Review> reviews) {
                binding.reviewCount.setText("리뷰 "+reviews.size());
                if(reviews.size()!=0){
                    adapter=new ReviewAdapter(getApplicationContext(),reviews);
                    binding.recyclerview.setAdapter(adapter);
                    clickReview();
                }
            }
        });
        viewModel.getPopularOrderReviews().observe(this, new Observer<ArrayList<Review>>() {
            @Override
            public void onChanged(ArrayList<Review> reviews) {
                if(reviews.size()!=0){
                    adapter=new ReviewAdapter(getApplicationContext(),reviews);
                    binding.recyclerview.setAdapter(adapter);
                    clickReview();
                }
            }
        });
        viewModel.getStarMutableLiveData().observe(this, new Observer<Star>() {
            @Override
            public void onChanged(Star star) {
                binding.reviewAvg.setText(Float.toString(star.getTotal_star_avg()));
                binding.totalStar.setRating(star.getTotal_star_avg());
                binding.star1.setText(Float.toString(star.getStar1_avg()));
                binding.star2.setText(Float.toString(star.getStar2_avg()));
                binding.star3.setText(Float.toString(star.getStar3_avg()));
                binding.star4.setText(Float.toString(star.getStar4_avg()));
                binding.start1Star.setRating(star.getStar1_avg());
                binding.start2Star.setRating(star.getStar2_avg());
                binding.start3Star.setRating(star.getStar3_avg());
                binding.start4Star.setRating(star.getStar4_avg());
            }
        });
    }

    public void clickWriteButton(){
        binding.writeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), PostReviewWriteActivity.class);
                intent.putExtra("postId",postId);
                startActivity(intent);
            }
        });
    }

    public void clickReview(){
        adapter.setOnItemClickListener(new ReviewAdapter.OnDeleteItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                dialog(adapter.getId(position));
            }
        });
        adapter.setOnItemLikeClickListener(new ReviewAdapter.OnLikeItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                if(adapter.getLike(position)){
                    viewModel.cancelLike(adapter.getId(position));
                    adapter.cancelLike(position);
                }else{
                    viewModel.pushLike(adapter.getId(position));
                    adapter.setLike(position);
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    public void dialog(int pk) {
        AlertDialog.Builder msgBuilder = new AlertDialog.Builder(this)
                .setMessage("정말 삭제하시겠습니까?")
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialogInterface, int i) {
                        viewModel.deleteComment(pk);
                        viewModel.loadReviews(postId,"recent");
                        viewModel.loadReviews(postId,"popular");
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        AlertDialog msgDlg = msgBuilder.create();
        msgDlg.setOnShowListener( new DialogInterface.OnShowListener() {
            @Override public void onShow(DialogInterface arg0) {
                msgDlg.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#5f51ef"));
                msgDlg.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#5f51ef"));
            }
        });
        msgDlg.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.loadReviews(postId,"recent");
        spinner.setSelection(0);
    }
}