package com.tourkakao.carping.Post;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
    private PostInfoDetail post;
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

        post = (PostInfoDetail) getIntent().getSerializableExtra("post");
        postId=Integer.toString(post.getId());

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
        binding.reviewCount.setText("리뷰 "+post.getReview_count());
        binding.reviewAvg.setText(Float.toString(post.getTotal_star_avg()));
        binding.totalStar.setRating(post.getTotal_star_avg());
        binding.star1.setText(Float.toString(post.getStar1_avg()));
        binding.star2.setText(Float.toString(post.getStar2_avg()));
        binding.star3.setText(Float.toString(post.getStar3_avg()));
        binding.star4.setText(Float.toString(post.getStar4_avg()));
        binding.start1Star.setRating(post.getStar1_avg());
        binding.start2Star.setRating(post.getStar2_avg());
        binding.start3Star.setRating(post.getStar3_avg());
        binding.start4Star.setRating(post.getStar4_avg());
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
                if(reviews!=null){
                    adapter=new ReviewAdapter(getApplicationContext(),reviews);
                    binding.recyclerview.setAdapter(adapter);
                    clickReview();
                }
            }
        });
        viewModel.getPopularOrderReviews().observe(this, new Observer<ArrayList<Review>>() {
            @Override
            public void onChanged(ArrayList<Review> reviews) {
                if(reviews!=null){
                    adapter=new ReviewAdapter(getApplicationContext(),reviews);
                    binding.recyclerview.setAdapter(adapter);
                    clickReview();
                }
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
                .setTitle("삭제")
                .setMessage("삭제하시겠습니까?")
                .setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialogInterface, int i) {
                        viewModel.deleteComment(pk);
                        viewModel.loadReviews(postId,"recent");
                        viewModel.loadReviews(postId,"popular");
                    }
                })
                .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        AlertDialog msgDlg = msgBuilder.create();
        msgDlg.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.loadReviews(postId,"recent");
        spinner.setSelection(0);
    }
}