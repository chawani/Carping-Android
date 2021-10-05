package com.tourkakao.carping.Post;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.tourkakao.carping.Post.Adapter.PostCategoryAdapter;
import com.tourkakao.carping.Post.DTO.PostListItem;
import com.tourkakao.carping.Post.ViewModel.PostListViewModel;
import com.tourkakao.carping.databinding.ActivityPostCategoryBinding;

import java.util.ArrayList;

public class PostCategoryActivity extends AppCompatActivity {
    private ActivityPostCategoryBinding binding;
    private GridView gridView;
    private PostCategoryAdapter adapter;
    private PostListViewModel viewModel;
    private String category;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityPostCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel =new ViewModelProvider(this).get(PostListViewModel.class);
        viewModel.setContext(this);

        initLayout();
        clickItem();
    }

    public void initLayout(){
        gridView=binding.gridview;
        Intent intent=getIntent();
        category=intent.getStringExtra("category");
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
                clickLike();
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
                clickLike();
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
                clickLike();
            }
        });
    }
    public void clickItem(){
        binding.gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a_parent, View a_view, int a_position, long a_id) {
                PostListItem item = (PostListItem) adapter.getItem(a_position);
                Intent intent=new Intent(getApplicationContext(), PostInfoActivity.class);
                intent.putExtra("pk",item.getId());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    public void clickLike(){
        adapter.setOnItemClickListener(new PostCategoryAdapter.OnLikeItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                if(adapter.getLike(position)){
                    viewModel.cancelLike(adapter.getId(position));
                }else{
                    viewModel.postLike(adapter.getId(position));
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if(category.equals("beginner")){
            viewModel.loadCategoryList(1);
        }
        if(category.equals("allOf")){
            viewModel.loadCategoryList(2);
        }
        if(category.equals("car")){
            viewModel.loadCategoryList(3);
        }
    }
}