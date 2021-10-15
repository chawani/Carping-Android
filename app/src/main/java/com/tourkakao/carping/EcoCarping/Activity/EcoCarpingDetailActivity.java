package com.tourkakao.carping.EcoCarping.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager2.widget.ViewPager2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.google.gson.Gson;
import com.tourkakao.carping.EcoCarping.Adapter.CommentAdapter;
import com.tourkakao.carping.EcoCarping.Adapter.DetailPagerAdapter;
import com.tourkakao.carping.EcoCarping.Adapter.EcoTotalReviewAdapter;
import com.tourkakao.carping.EcoCarping.DTO.Comment;
import com.tourkakao.carping.EcoCarping.DTO.EcoPost;
import com.tourkakao.carping.EcoCarping.DTO.PostComment;
import com.tourkakao.carping.EcoCarping.ViewModel.EcoDetailViewModel;
import com.tourkakao.carping.NetworkwithToken.CommonClass;
import com.tourkakao.carping.NetworkwithToken.TotalApiClient;
import com.tourkakao.carping.R;
import com.tourkakao.carping.SharedPreferenceManager.SharedPreferenceManager;
import com.tourkakao.carping.databinding.ActivityEcoCarpingDetailBinding;

import java.util.ArrayList;
import java.util.Arrays;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class EcoCarpingDetailActivity extends AppCompatActivity {
    ActivityEcoCarpingDetailBinding binding;
    private EcoDetailViewModel ecoDetailViewModel;
    private int pk;
    private DetailPagerAdapter detailPagerAdapter;
    private CommentAdapter commentAdapter;
    private String postId;
    private int likeCount;
    private int current_user;
    private int commentCount;
    private int imageCount;
    private boolean commentCheck=false;
    private int writer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityEcoCarpingDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ecoDetailViewModel =new ViewModelProvider(this).get(EcoDetailViewModel.class);
        ecoDetailViewModel.setContext(this);

        initLayout();
        getDetailInfo();

        ecoDetailViewModel.getPost().observe(this, new Observer<EcoPost>() {
            @Override
            public void onChanged(EcoPost ecoPost) {
                writer=(int)Double.parseDouble(ecoPost.getUser());
                if(current_user!=writer){
                    binding.menu.setVisibility(View.GONE);
                }
                binding.username.setText(ecoPost.getUsername());
                binding.hour.setText(ecoPost.getCreated_at());
                binding.title.setText(ecoPost.getTitle());
                binding.content.setText(ecoPost.getText());
                likeCount=(int)Double.parseDouble(ecoPost.getLike_count());
                commentCount=ecoPost.getComment().size();
                binding.likeComment.setText("좋아요"+Integer.toString(likeCount)+" · 댓글"+ecoPost.getComment().size());
                Glide.with(getApplicationContext())
                        .load(ecoPost.getProfile())
                        .transform(new CenterCrop(), new RoundedCorners(100))
                        .into(binding.profile);
                binding.location.setText(ecoPost.getPlace());
                if(String.valueOf(ecoPost.getIs_liked()).equals("true")){
                    Glide.with(getApplicationContext()).load(R.drawable.is_pushed_like).into(binding.likeMark);
                }else{
                    Glide.with(getApplicationContext()).load(R.drawable.like_mark).into(binding.likeMark);
                }
                postId=ecoPost.getId();
                binding.likeCount.setText(Integer.toString(likeCount));
            }
        });
        binding.commentEditView.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // 입력이 끝났을 때 호출된다.
                if(binding.commentEditView.getText().toString().length()==0){
                    commentCheck=false;
                }
                else{
                    commentCheck=true;
                }
                changeButtonColor(commentCheck);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // 입력하기 전에 호출된다.
            }
        });
        ecoDetailViewModel.getImages().observe(this, new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> strings) {
                imageCount=strings.size();
                detailPagerAdapter =new DetailPagerAdapter(getApplicationContext(),strings);
                binding.imageViewPager.setAdapter(detailPagerAdapter);
            }
        });
        binding.imageViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                binding.pageNumber.setText((position+1)+"/"+imageCount);
            }
        });
        ecoDetailViewModel.getTags().observe(this, new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> strings) {
                binding.tagArea.removeAllViews();
                for(String tag:strings) {
                    TextView textView = new TextView(getApplicationContext());
                    LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.rightMargin= convertDp(5);
                    textView.setLayoutParams(params);
                    textView.setBackgroundResource(R.drawable.tag_design);
                    textView.setPadding(convertDp(10),convertDp(5),convertDp(10),convertDp(5));
                    textView.setTextColor(Color.parseColor("#5f51ef"));
                    textView.setText("#"+tag);
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                    binding.tagArea.addView(textView);
                }
            }
        });
        ecoDetailViewModel.getComments().observe(this, new Observer<ArrayList<Comment>>() {
            @Override
            public void onChanged(ArrayList<Comment> comments) {
                if(comments!=null) {
                    commentAdapter =
                            new CommentAdapter(getApplicationContext(),comments,ecoDetailViewModel,EcoCarpingDetailActivity.this,writer);
                    binding.commentView.setAdapter(commentAdapter);
                }
            }
        });
        binding.completeComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!commentCheck){
                    return;
                }
                if(!binding.commentEditView.getText().toString().equals("")) {
                    setCommentMap();
                }
            }
        });
        ecoDetailViewModel.getLikeStatus().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(s.equals("true")){
                    binding.likeCount.setTextColor(Color.parseColor("#ff4e5e"));
                    Glide.with(getApplicationContext()).load(R.drawable.is_pushed_like).into(binding.likeMark);
                }
                if(s.equals("false")){
                    binding.likeCount.setTextColor(Color.parseColor("#000000"));
                    Glide.with(getApplicationContext()).load(R.drawable.like_mark).into(binding.likeMark);
                }
            }
        });
        binding.likeMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ecoDetailViewModel.getLikeStatus().getValue().equals("true")){
                    ecoDetailViewModel.cancelLike();
                    likeCount=likeCount-1;
                    binding.likeCount.setText(Integer.toString(likeCount));
                    binding.likeComment.setText("좋아요"+likeCount+" · 댓글"+ecoDetailViewModel.getPost().getValue().getComment().size());
                }
                if(ecoDetailViewModel.getLikeStatus().getValue().equals("false")){
                    ecoDetailViewModel.pushLike();
                    likeCount=likeCount+1;
                    binding.likeCount.setText(Integer.toString(likeCount));
                    binding.likeComment.setText("좋아요"+likeCount+" · 댓글"+ecoDetailViewModel.getPost().getValue().getComment().size());
                }
            }
        });
        binding.menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerForContextMenu(view);
                view.showContextMenu();
                unregisterForContextMenu(view);
            }
        });
    }

    public void changeButtonColor(boolean check){
        if(check) {
            binding.completeComment.setBackground(ContextCompat.getDrawable(this, R.drawable.border_round_fill));
            binding.completeComment.setTextColor(Color.parseColor("#ffffff"));
        }
        else {
            binding.completeComment.setBackground(ContextCompat.getDrawable(this, R.drawable.border_round_fill_grey));
            binding.completeComment.setTextColor(Color.parseColor("#999999"));
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.detail_page_menu, menu);
    }

    public boolean onContextItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.edit:
                Intent intent=new Intent(getApplicationContext(), EcoCarpingEditActivity.class);
                intent.putExtra("pk",postId);
                startActivity(intent);
                return true;
            case R.id.delete:
                showDialog();
                return true;
        }

        return super.onContextItemSelected(item);
    }

    void showDialog() {
        AlertDialog.Builder msgBuilder = new AlertDialog.Builder(this)
                .setMessage("정말 삭제하시겠습니까?")
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialogInterface, int i) {
                        ecoDetailViewModel.deletePost();
                        finish();
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        AlertDialog msgDlg = msgBuilder.create();
        msgDlg.setOnShowListener( new DialogInterface.OnShowListener() {
            @Override public void onShow(DialogInterface arg0) {
                msgDlg.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#5f51ef"));
                msgDlg.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#5f51ef"));
            }
        });
        msgDlg.show();
    }

    public void scrollDown(){
        binding.scrollView.post(new Runnable() {
            @Override
            public void run() {
                binding.scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }

    public void initLayout(){
        binding.commentView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        Glide.with(getApplicationContext()).load(R.drawable.locate_img).into(binding.locateImg);
        Glide.with(getApplicationContext()).load(R.drawable.back).into(binding.back);
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void getDetailInfo(){
        current_user=SharedPreferenceManager.getInstance(getApplicationContext()).getInt("id",0);
        Intent intent=getIntent();
        pk=(int)Float.parseFloat(intent.getStringExtra("pk"));
        ecoDetailViewModel.setPk(pk);

        ecoDetailViewModel.loadDetail();
    }

    public void setCommentMap(){
        int userId= SharedPreferenceManager.getInstance(getApplicationContext()).getInt("id",0);
        String userString=Integer.toString(userId);
        int postIdInteger=(int)Double.parseDouble(postId);

        PostComment comment=new PostComment(userString,Integer.toString(postIdInteger),binding.commentEditView.getText().toString());
        updateComments(comment);
    }

    public void updateComments(PostComment comment){
        TotalApiClient.getEcoApiService(getApplicationContext()).postComment(comment)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<CommonClass>() {
                    @Override
                    public void onSuccess(@NonNull CommonClass commonClass) {
                        String totalString=new Gson().toJson(commonClass.getData().get(0));
                        ecoDetailViewModel.setCommentData(totalString);
                        commentCount=commentCount+1;
                        binding.commentEditView.setText("");
                        InputMethodManager keyboard=(InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                        keyboard.hideSoftInputFromWindow(binding.commentEditView.getWindowToken(), 0);
                        binding.likeComment.setText("좋아요"+likeCount+" · 댓글"+commentCount);
                        scrollDown();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        System.out.println("post 실패"+e.getMessage());

                    }
                });
    }

    public int convertDp(int dp) {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, metrics);
    }

    @Override
    public void onResume() {
        super.onResume();
        getDetailInfo();
    }
}