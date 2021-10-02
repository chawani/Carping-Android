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

import com.tourkakao.carping.Post.ViewModel.PostReviewViewModel;
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