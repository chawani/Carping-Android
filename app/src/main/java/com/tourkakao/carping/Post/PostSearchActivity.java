package com.tourkakao.carping.Post;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.GridView;

import com.bumptech.glide.Glide;
import com.tourkakao.carping.Post.Adapter.KeywordAdapter;
import com.tourkakao.carping.Post.Adapter.PostCategoryAdapter;
import com.tourkakao.carping.Post.DTO.PostListItem;
import com.tourkakao.carping.Post.ViewModel.PostListViewModel;
import com.tourkakao.carping.Post.ViewModel.PostSearchViewModel;
import com.tourkakao.carping.R;
import com.tourkakao.carping.databinding.ActivityPostSearchBinding;

import java.util.ArrayList;

public class PostSearchActivity extends AppCompatActivity {
    private ActivityPostSearchBinding binding;
    private Context context;
    private PostSearchViewModel searchViewModel;
    private PostListViewModel listViewModel;
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

        searchViewModel =new ViewModelProvider(this).get(PostSearchViewModel.class);
        searchViewModel.setContext(this);
        listViewModel =new ViewModelProvider(this).get(PostListViewModel.class);
        listViewModel.setContext(this);

        initLayout();
        search();
        settingKeyword();
    }

    public void initLayout(){
        binding.searchResult.setVisibility(View.GONE);
        binding.deleteText.setVisibility(View.GONE);
        Glide.with(context).load(R.drawable.back).into(binding.back);
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Glide.with(context).load(R.drawable.search_img).into(binding.searchImg);
        Glide.with(context).load(R.drawable.search_remove_img).into(binding.deleteText);
        binding.deleteText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.searchText.setText("");
            }
        });
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
                    binding.deleteText.setVisibility(View.GONE);
                    binding.searchResult.setVisibility(View.GONE);
                    binding.quickList.setVisibility(View.VISIBLE);
                }else{
                    binding.deleteText.setVisibility(View.VISIBLE);
                    binding.searchResult.setVisibility(View.VISIBLE);
                    binding.quickList.setVisibility(View.GONE);
                    searchViewModel.searchInfo(s.toString());
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
        searchViewModel.getSearchList().observe(this, new Observer<ArrayList<PostListItem>>() {
            @Override
            public void onChanged(ArrayList<PostListItem> postListItems) {
                gridView=binding.searchResult;
                postAdapter=new PostCategoryAdapter(getApplicationContext(),postListItems);
                gridView.setAdapter(postAdapter);
                clickLike();
            }
        });
        binding.searchResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a_parent, View a_view, int a_position, long a_id) {
                PostListItem item = (PostListItem) postAdapter.getItem(a_position);
                searchViewModel.completeSearch(binding.searchText.getText().toString(),item.getTitle());
                Intent intent=new Intent(context, PostInfoActivity.class);
                intent.putExtra("pk",item.getId());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        binding.searchText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(binding.searchText.getWindowToken(), 0);    //hide keyboard
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View focusView = getCurrentFocus();
        if (focusView != null) {
            Rect rect = new Rect();
            focusView.getGlobalVisibleRect(rect);
            int x = (int) ev.getX(), y = (int) ev.getY();
            if (!rect.contains(x, y)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                if (imm != null)
                    imm.hideSoftInputFromWindow(focusView.getWindowToken(), 0);
                focusView.clearFocus();
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    public void settingKeyword(){
        searchViewModel.getPopularList().observe(this, new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> strings) {
                popularAdapter=new KeywordAdapter(getApplicationContext(),binding,strings,"popular");
                binding.popularKeyword.setAdapter(popularAdapter);
            }
        });
        searchViewModel.getRecentList().observe(this, new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> strings) {
                recentAdapter=new KeywordAdapter(getApplicationContext(),binding,strings,"recent");
                binding.recentKeyword.setAdapter(recentAdapter);
                recentAdapter.setOnItemClickListener(new KeywordAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                        String keyword=recentAdapter.getItem(position);
                        searchViewModel.deleteKeyword(keyword);
                    }
                });
            }
        });
    }

    public void clickLike(){
        postAdapter.setOnItemClickListener(new PostCategoryAdapter.OnLikeItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                if(postAdapter.getLike(position)){
                    listViewModel.cancelLike(postAdapter.getId(position));
                }else{
                    listViewModel.postLike(postAdapter.getId(position));
                }
                postAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        searchViewModel.loadKeyword();
    }
}