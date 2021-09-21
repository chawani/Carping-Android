package com.tourkakao.carping.Post;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.tourkakao.carping.EcoCarping.Adapter.EcoTotalReviewAdapter;
import com.tourkakao.carping.Post.Adapter.PostTotalAdapter;
import com.tourkakao.carping.Post.DTO.PostListItem;
import com.tourkakao.carping.R;
import com.tourkakao.carping.databinding.ActivityPostSearchBinding;
import com.tourkakao.carping.databinding.ActivityPostTotalBinding;

import java.util.ArrayList;

public class PostTotalActivity extends AppCompatActivity {
    private ActivityPostTotalBinding binding;
    private Context context;
    private PostTotalAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityPostTotalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        context=getApplicationContext();

        initLayout();
        initPopular();
        initBeginner();
        initAllOf();
        initCar();
        clickTotal();

        binding.searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, PostSearchActivity.class);
                startActivity(intent);
            }
        });
    }

    public void initLayout(){
        Glide.with(context).load(R.drawable.main_reading_glasses).into(binding.searchButton);
        Glide.with(context).load(R.drawable.right_arrow_black).into(binding.allOfTotal);
        Glide.with(context).load(R.drawable.right_arrow_black).into(binding.beginnerTotal);
        Glide.with(context).load(R.drawable.right_arrow_black).into(binding.carTotal);
    }
    public void initPopular() {
        LinearLayoutManager layoutManager=new LinearLayoutManager(context);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        binding.popularRecycler.setLayoutManager(layoutManager);
        ArrayList<PostListItem> arrayList=new ArrayList<>();
        arrayList.add(new PostListItem("TEST","TEST","TEST"));
        arrayList.add(new PostListItem("TEST","TEST","TEST"));
        arrayList.add(new PostListItem("TEST","TEST","TEST"));
        adapter=new PostTotalAdapter(getApplicationContext(),arrayList);
        binding.popularRecycler.setAdapter(adapter);
    }
    public void initBeginner() {
        LinearLayoutManager layoutManager=new LinearLayoutManager(context);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        binding.beginnerRecycler.setLayoutManager(layoutManager);
        ArrayList<PostListItem> arrayList=new ArrayList<>();
        arrayList.add(new PostListItem("TEST","TEST","TEST"));
        arrayList.add(new PostListItem("TEST","TEST","TEST"));
        arrayList.add(new PostListItem("TEST","TEST","TEST"));
        adapter=new PostTotalAdapter(getApplicationContext(),arrayList);
        binding.beginnerRecycler.setAdapter(adapter);
    }
    public void initAllOf() {
        LinearLayoutManager layoutManager=new LinearLayoutManager(context);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        binding.allOfRecycler.setLayoutManager(layoutManager);
        ArrayList<PostListItem> arrayList=new ArrayList<>();
        arrayList.add(new PostListItem("TEST","TEST","TEST"));
        arrayList.add(new PostListItem("TEST","TEST","TEST"));
        arrayList.add(new PostListItem("TEST","TEST","TEST"));
        adapter=new PostTotalAdapter(getApplicationContext(),arrayList);
        binding.allOfRecycler.setAdapter(adapter);
    }
    public void initCar() {
        LinearLayoutManager layoutManager=new LinearLayoutManager(context);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        binding.carRecycler.setLayoutManager(layoutManager);
        ArrayList<PostListItem> arrayList=new ArrayList<>();
        arrayList.add(new PostListItem("TEST","TEST","TEST"));
        arrayList.add(new PostListItem("TEST","TEST","TEST"));
        arrayList.add(new PostListItem("TEST","TEST","TEST"));
        adapter=new PostTotalAdapter(getApplicationContext(),arrayList);
        binding.carRecycler.setAdapter(adapter);
    }

    public void clickTotal(){
        binding.beginnerTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,PostCategoryActivity.class);
                intent.putExtra("category","beginner");
                startActivity(intent);
            }
        });
        binding.allOfTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,PostCategoryActivity.class);
                intent.putExtra("category","allOf");
                startActivity(intent);
            }
        });
        binding.carTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,PostCategoryActivity.class);
                intent.putExtra("category","car");
                startActivity(intent);
            }
        });
    }
}