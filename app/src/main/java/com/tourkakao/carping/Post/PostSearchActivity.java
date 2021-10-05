package com.tourkakao.carping.Post;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.bumptech.glide.Glide;
import com.tourkakao.carping.Post.Adapter.KeywordAdapter;
import com.tourkakao.carping.Post.Adapter.PostCategoryAdapter;
import com.tourkakao.carping.Post.DTO.PostListItem;
import com.tourkakao.carping.Post.ViewModel.PostSearchViewModel;
import com.tourkakao.carping.R;
import com.tourkakao.carping.databinding.ActivityPostSearchBinding;

import java.util.ArrayList;

public class PostSearchActivity extends AppCompatActivity {
    private ActivityPostSearchBinding binding;
    private Context context;
    private PostSearchViewModel viewModel;
    private GridView gridView;
    private PostCategoryAdapter postAdapter;
    private KeywordAdapter popularAdapter;
    private KeywordAdapter recentAdapter;
    private LinearLayoutManager mLayoutManager;
    private LinearLayoutManager mLayoutManager2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityPostSearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        context=getApplicationContext();

        viewModel =new ViewModelProvider(this).get(PostSearchViewModel.class);
        viewModel.setContext(this);

        initLayout();
        search();
        settingKeyword();
    }

    public void initLayout(){
        binding.searchResult.setVisibility(View.GONE);
        Glide.with(context).load(R.drawable.back).into(binding.back);
        Glide.with(context).load(R.drawable.search_img).into(binding.searchImg);
        mLayoutManager = new LinearLayoutManager(this);
        binding.recentKeyword.setLayoutManager(mLayoutManager);
        mLayoutManager2=new LinearLayoutManager(this);
        binding.popularKeyword.setLayoutManager(mLayoutManager2);
    }

    public void search(){
        binding.searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 입력되는 텍스트에 변화가 있을 때 호출된다.
                if(s.toString().length()==0) {
                    binding.searchResult.setVisibility(View.GONE);
                    binding.quickList.setVisibility(View.VISIBLE);
                }else{
                    binding.searchResult.setVisibility(View.VISIBLE);
                    binding.quickList.setVisibility(View.GONE);
                    viewModel.searchInfo(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // 입력이 끝났을 때 호출된다.
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // 입력하기 전에 호출된다.
            }
        });
        viewModel.getSearchList().observe(this, new Observer<ArrayList<PostListItem>>() {
            @Override
            public void onChanged(ArrayList<PostListItem> postListItems) {
                gridView=binding.searchResult;
                postAdapter=new PostCategoryAdapter(getApplicationContext(),postListItems);
                gridView.setAdapter(postAdapter);
            }
        });
        binding.searchResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a_parent, View a_view, int a_position, long a_id) {
                PostListItem item = (PostListItem) postAdapter.getItem(a_position);
                viewModel.completeSearch(binding.searchText.getText().toString(),item.getTitle());
                Intent intent=new Intent(context, PostInfoActivity.class);
                intent.putExtra("pk",item.getId());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    public void settingKeyword(){
        viewModel.getPopularList().observe(this, new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> strings) {
                popularAdapter=new KeywordAdapter(getApplicationContext(),binding,strings,"popular");
                binding.popularKeyword.setAdapter(popularAdapter);
            }
        });
        viewModel.getRecentList().observe(this, new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> strings) {
                recentAdapter=new KeywordAdapter(getApplicationContext(),binding,strings,"recent");
                binding.recentKeyword.setAdapter(recentAdapter);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.loadKeyword();
    }
}