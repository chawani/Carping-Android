package com.tourkakao.carping.newcarping.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.RatingBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tourkakao.carping.R;
import com.tourkakao.carping.databinding.ActivityWriteNewcarpingReviewBinding;

public class Write_newcarping_reviewActivity extends AppCompatActivity {
    private ActivityWriteNewcarpingReviewBinding reviewBinding;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reviewBinding=ActivityWriteNewcarpingReviewBinding.inflate(getLayoutInflater());
        setContentView(reviewBinding.getRoot());
        context=this;

        Glide.with(context).load(R.drawable.picture_btn_img).into(reviewBinding.pictureBtn);
        reviewBinding.title.setText(getIntent().getStringExtra("title"));

        setting_ratingbar_and_related_text();
        setting_review_edittext();
        setting_getting_review_image();
    }

    public void setting_review_edittext(){
        reviewBinding.reviewEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                reviewBinding.textCount.setText(s.toString().length() + "/300");
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
    public void setting_ratingbar_and_related_text(){
        reviewBinding.totalstar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if(rating==0){
                  reviewBinding.starMent.setText("이 캠핑장을 평가해주세요!");
                  reviewBinding.starMent.setTextColor(Color.LTGRAY);
                } else if(rating<=1){
                    reviewBinding.starMent.setText("안좋았어요");
                    reviewBinding.starMent.setTextColor(Color.BLACK);
                }else if(rating<=2){
                    reviewBinding.starMent.setText("나쁘지 않아요");
                    reviewBinding.starMent.setTextColor(Color.BLACK);
                }else if(rating<=3){
                    reviewBinding.starMent.setText("보통이에요");
                    reviewBinding.starMent.setTextColor(Color.BLACK);
                }else if(rating<=4){
                    reviewBinding.starMent.setText("맘에 들어요!");
                    reviewBinding.starMent.setTextColor(Color.BLACK);
                }else{
                    reviewBinding.starMent.setText("최고예요!");
                    reviewBinding.starMent.setTextColor(Color.BLACK);
                }
            }
        });
    }
    public void setting_getting_review_image(){
        reviewBinding.pictureBtn.setOnClickListener(v -> {

        });
    }
}