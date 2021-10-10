package com.tourkakao.carping.Post;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.RatingBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tourkakao.carping.Post.ViewModel.PostReviewViewModel;
import com.tourkakao.carping.R;
import com.tourkakao.carping.SharedPreferenceManager.SharedPreferenceManager;
import com.tourkakao.carping.databinding.ActivityPostReviewWriteBinding;
import com.tourkakao.carping.databinding.ActivityPostWriteBinding;

import java.util.HashMap;

public class PostReviewWriteActivity extends AppCompatActivity {
    private ActivityPostReviewWriteBinding binding;
    private boolean reviewCheck=false;
    private PostReviewViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityPostReviewWriteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel =new ViewModelProvider(this).get(PostReviewViewModel.class);
        viewModel.setContext(this);

        writeReview();
        binding.completionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!checkAll()){
                    Toast myToast = Toast.makeText(getApplicationContext(),"모두 입력해주세요", Toast.LENGTH_SHORT);
                    myToast.show();
                    return;
                }
                post();
                finish();
            }
        });
        Glide.with(getApplicationContext()).load(R.drawable.cancel_img).into(binding.cancelButton);
        binding.cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void writeReview(){
        binding.reviewText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 입력되는 텍스트에 변화가 있을 때 호출된다.
                binding.lengthCount.setText(s.toString().length()+"/300");
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // 입력이 끝났을 때 호출된다.
                if(binding.reviewText.getText().toString().length()==0){
                    reviewCheck=false;
                }
                else{
                    reviewCheck=true;
                }
                changeButtonColor(checkAll());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // 입력하기 전에 호출된다.
            }
        });
        binding.totalRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if(rating==0){
                    binding.reviewComment.setText("이 포스트를 평가해주세요!");
                    binding.reviewComment.setTextColor(Color.LTGRAY);
                } else if(rating<=1){
                    binding.reviewComment.setText("안좋았어요");
                    binding.reviewComment.setTextColor(Color.BLACK);
                }else if(rating<=2){
                    binding.reviewComment.setText("나쁘지 않아요");
                    binding.reviewComment.setTextColor(Color.BLACK);
                }else if(rating<=3){
                    binding.reviewComment.setText("보통이에요");
                    binding.reviewComment.setTextColor(Color.BLACK);
                }else if(rating<=4){
                    binding.reviewComment.setText("맘에 들어요!");
                    binding.reviewComment.setTextColor(Color.BLACK);
                }else{
                    binding.reviewComment.setText("최고예요!");
                    binding.reviewComment.setTextColor(Color.BLACK);
                }
            }
        });
    }

    public void post(){
        HashMap<String,Object> map=new HashMap<>();
        int userId= SharedPreferenceManager.getInstance(getApplicationContext()).getInt("id",0);
        String postId=getIntent().getStringExtra("postId");
        System.out.println("리뷰 등록 포스트 아이디"+postId);
        map.put("user",userId);
        map.put("post",postId);
        map.put("text",binding.reviewText.getText().toString());
        map.put("total_star",binding.totalRating.getRating());
        map.put("star1",binding.totalRating.getRating());
        map.put("star2",binding.totalRating.getRating());
        map.put("star3",binding.totalRating.getRating());
        map.put("star4",binding.totalRating.getRating());
        viewModel.postReview(map);
    }

    public boolean checkAll(){
        if(reviewCheck)
            return true;
        return false;
    }

    public void changeButtonColor(boolean check){
        if(check)
            binding.completionButton.setBackgroundColor(Color.BLACK);
        else
            binding.completionButton.setBackgroundColor(Color.parseColor("#999999"));
    }
}