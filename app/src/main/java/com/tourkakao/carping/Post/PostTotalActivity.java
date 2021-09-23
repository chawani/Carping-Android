package com.tourkakao.carping.Post;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.tourkakao.carping.EcoCarping.Adapter.EcoTotalReviewAdapter;
import com.tourkakao.carping.EcoCarping.ViewModel.EcoTotalViewModel;
import com.tourkakao.carping.Post.Adapter.PostTotalAdapter;
import com.tourkakao.carping.Post.DTO.PostListItem;
import com.tourkakao.carping.Post.ViewModel.PostListViewModel;
import com.tourkakao.carping.R;
import com.tourkakao.carping.databinding.ActivityPostSearchBinding;
import com.tourkakao.carping.databinding.ActivityPostTotalBinding;

import java.util.ArrayList;

public class PostTotalActivity extends AppCompatActivity {
    private ActivityPostTotalBinding binding;
    private Context context;
    private PostTotalAdapter adapter;
    private PostListViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityPostTotalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        context=getApplicationContext();

        viewModel =new ViewModelProvider(this).get(PostListViewModel.class);
        viewModel.setContext(this);
        viewModel.loadTotalList();

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
        Glide.with(context).load(R.drawable.right_arrow_black).into(binding.allOfArrow);
        Glide.with(context).load(R.drawable.right_arrow_black).into(binding.beginnerArrow);
        Glide.with(context).load(R.drawable.right_arrow_black).into(binding.carArrow);
    }
    public void initPopular() {
        LinearLayoutManager layoutManager=new LinearLayoutManager(context);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        binding.popularRecycler.setLayoutManager(layoutManager);
        viewModel.getPopularLiveData().observe(this, new Observer<ArrayList<PostListItem>>() {
            @Override
            public void onChanged(ArrayList<PostListItem> postListItems) {
                adapter=new PostTotalAdapter(getApplicationContext(),postListItems);
                binding.popularRecycler.setAdapter(adapter);
            }
        });

    }
    public void initBeginner() {
        LinearLayoutManager layoutManager=new LinearLayoutManager(context);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        binding.beginnerRecycler.setLayoutManager(layoutManager);
        viewModel.getBeginnerLiveData().observe(this, new Observer<ArrayList<PostListItem>>() {
            @Override
            public void onChanged(ArrayList<PostListItem> postListItems) {
                adapter=new PostTotalAdapter(getApplicationContext(),postListItems);
                binding.beginnerRecycler.setAdapter(adapter);
            }
        });
    }
    public void initAllOf() {
        LinearLayoutManager layoutManager=new LinearLayoutManager(context);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        binding.allOfRecycler.setLayoutManager(layoutManager);
        viewModel.getAllOfLiveData().observe(this, new Observer<ArrayList<PostListItem>>() {
            @Override
            public void onChanged(ArrayList<PostListItem> postListItems) {
                adapter=new PostTotalAdapter(getApplicationContext(),postListItems);
                binding.allOfRecycler.setAdapter(adapter);
            }
        });
    }
    public void initCar() {
        LinearLayoutManager layoutManager=new LinearLayoutManager(context);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        binding.carRecycler.setLayoutManager(layoutManager);
        viewModel.getCarLiveData().observe(this, new Observer<ArrayList<PostListItem>>() {
            @Override
            public void onChanged(ArrayList<PostListItem> postListItems) {
                adapter=new PostTotalAdapter(getApplicationContext(),postListItems);
                binding.carRecycler.setAdapter(adapter);
            }
        });
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