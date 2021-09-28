package com.tourkakao.carping.Post;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.GridView;

import com.tourkakao.carping.Post.Adapter.PostCategoryAdapter;
import com.tourkakao.carping.Post.Adapter.PostTotalAdapter;
import com.tourkakao.carping.Post.DTO.PostListItem;
import com.tourkakao.carping.Post.ViewModel.PostListViewModel;
import com.tourkakao.carping.databinding.ActivityPostCategoryBinding;
import com.tourkakao.carping.databinding.ActivityPostDetailBinding;

import java.util.ArrayList;

public class PostCategoryActivity extends AppCompatActivity {
    private ActivityPostCategoryBinding binding;
    private GridView gridView;
    private PostCategoryAdapter adapter;
    private PostListViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityPostCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel =new ViewModelProvider(this).get(PostListViewModel.class);
        viewModel.setContext(this);

        initLayout();
    }

    public void initLayout(){
        gridView=binding.gridview;
        Intent intent=getIntent();
        String category=intent.getStringExtra("category");
        if(category.equals("beginner")){
            initBeginner();
        }
        if(category.equals("allOf")){
            initAllOf();
        }
        if(category.equals("car")){
            initCar();
        }
    }

    public void initBeginner(){
        binding.title.setText("초보에게 딱 맞아!");
        viewModel.loadCategoryList(1);
        viewModel.getCategoryData().observe(this, new Observer<ArrayList<PostListItem>>() {
            @Override
            public void onChanged(ArrayList<PostListItem> postListItems) {
                binding.postCount.setText("전체포스트 "+postListItems.size()+"개");
                adapter=new PostCategoryAdapter(getApplicationContext(),postListItems);
                gridView.setAdapter(adapter);
            }
        });
    }
    public void initAllOf(){
        binding.title.setText("차박의 모든 것");
        viewModel.loadCategoryList(2);
        viewModel.getCategoryData().observe(this, new Observer<ArrayList<PostListItem>>() {
            @Override
            public void onChanged(ArrayList<PostListItem> postListItems) {
                binding.postCount.setText("전체포스트 "+postListItems.size()+"개");
                adapter = new PostCategoryAdapter(getApplicationContext(), postListItems);
                gridView.setAdapter(adapter);
            }
        });
    }
    public void initCar() {
        binding.title.setText("차에 맞는 차박여행");
        viewModel.loadCategoryList(3);
        viewModel.getCategoryData().observe(this, new Observer<ArrayList<PostListItem>>() {
            @Override
            public void onChanged(ArrayList<PostListItem> postListItems) {
                binding.postCount.setText("전체포스트 "+postListItems.size()+"개");
                adapter = new PostCategoryAdapter(getApplicationContext(), postListItems);
                gridView.setAdapter(adapter);
            }
        });
    }
}