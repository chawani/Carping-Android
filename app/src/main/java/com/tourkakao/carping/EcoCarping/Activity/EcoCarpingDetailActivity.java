package com.tourkakao.carping.EcoCarping.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.tourkakao.carping.BuildConfig;
import com.tourkakao.carping.EcoCarping.Adapter.CommentAdapter;
import com.tourkakao.carping.EcoCarping.Adapter.DetailPagerAdapter;
import com.tourkakao.carping.EcoCarping.DTO.Comment;
import com.tourkakao.carping.EcoCarping.DTO.EcoPost;
import com.tourkakao.carping.EcoCarping.DTO.PostComment;
import com.tourkakao.carping.EcoCarping.ViewModel.EcoDetailViewModel;
import com.tourkakao.carping.NetworkwithToken.CommonClass;
import com.tourkakao.carping.NetworkwithToken.TotalApiClient;
import com.tourkakao.carping.R;
import com.tourkakao.carping.SharedPreferenceManager.SharedPreferenceManager;
import com.tourkakao.carping.databinding.ActivityEcoCarpingDetailBinding;

import net.daum.mf.map.api.MapReverseGeoCoder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Retrofit;

public class EcoCarpingDetailActivity extends AppCompatActivity {
    ActivityEcoCarpingDetailBinding binding;
    private EcoDetailViewModel ecoDetailViewModel;
    private int pk;
    private DetailPagerAdapter detailPagerAdapter;
    private MapReverseGeoCoder reverseGeoCoder;
    private String KAKAO_KEY=BuildConfig.KAKAO_REST_API_KEY;
    private Retrofit retrofit=null;
    private int five;
    private int one_zero;
    private CommentAdapter commentAdapter;
    private String postId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityEcoCarpingDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ecoDetailViewModel =new ViewModelProvider(this).get(EcoDetailViewModel.class);
        ecoDetailViewModel.setContext(this);

        getDetailInfo();

        five = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,5,getResources().getDisplayMetrics());
        one_zero =(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,10,getResources().getDisplayMetrics());

        binding.commentView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        ecoDetailViewModel.getPost().observe(this, new Observer<EcoPost>() {
            @Override
            public void onChanged(EcoPost ecoPost) {
                binding.username.setText(ecoPost.getUsername());
                binding.hour.setText(ecoPost.getCreated_at());
                binding.title.setText(ecoPost.getTitle());
                binding.content.setText(ecoPost.getText());
                int like=(int)Double.parseDouble(ecoPost.getLike_count());
                binding.likeComment.setText("좋아요"+Integer.toString(like)+" · 댓글"+ecoPost.getComment().size());
                Glide.with(getApplicationContext())
                        .load(ecoPost.getProfile())
                        .transform(new CenterCrop(), new RoundedCorners(100))
                        .into(binding.profile);
                binding.location.setText(ecoPost.getPlace());
                if(ecoPost.getCheck_like().equals("1")){
                    Glide.with(getApplicationContext()).load(R.drawable.is_pushed_like).into(binding.likeMark);
                }else{
                    Glide.with(getApplicationContext()).load(R.drawable.like_mark).into(binding.likeMark);
                }
                postId=ecoPost.getId();
                binding.likeCount.setText(Integer.toString(like));
            }
        });
        ecoDetailViewModel.getImages().observe(this, new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> strings) {
                detailPagerAdapter =new DetailPagerAdapter(getApplicationContext(),strings);
                binding.imageViewPager.setAdapter(detailPagerAdapter);
            }
        });
        ecoDetailViewModel.getTags().observe(this, new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> strings) {
                for(String tag:strings) {
                    TextView textView = new TextView(getApplicationContext());
                    LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.rightMargin= five;
                    textView.setLayoutParams(params);
                    textView.setBackgroundResource(R.drawable.tag_design);
                    textView.setPadding(one_zero,five,one_zero,five);
                    textView.setTextColor(Color.parseColor("#5f51ef"));
                    textView.setText("#"+tag);
                    binding.tagArea.addView(textView);
                }
            }
        });
        ecoDetailViewModel.getComments().observe(this, new Observer<ArrayList<Comment>>() {
            @Override
            public void onChanged(ArrayList<Comment> comments) {
                if(comments!=null) {
                    commentAdapter = new CommentAdapter(getApplicationContext(), comments);
                    binding.commentView.setAdapter(commentAdapter);
                }
            }
        });
        binding.completeComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!binding.commentEditView.getText().toString().equals("")) {
                    setCommentMap();
                }
            }
        });
    }

    public void getDetailInfo(){
        Intent intent=getIntent();
        pk=(int)Float.parseFloat(intent.getStringExtra("pk"));
        ecoDetailViewModel.setPk(pk);

        ecoDetailViewModel.loadDetail();
    }

    public void setCommentMap(){
        String userString= SharedPreferenceManager.getInstance(getApplicationContext()).getString("id","");
        int userInteger=(int)Double.parseDouble(userString);
        userString=Integer.toString(userInteger);
        int postIdInteger=(int)Double.parseDouble(postId);

        PostComment comment=new PostComment(userString,Integer.toString(postIdInteger),binding.commentEditView.getText().toString());
        ecoDetailViewModel.updateComments(comment);
    }
}