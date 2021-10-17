package com.tourkakao.carping.Post;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.tourkakao.carping.EcoCarping.Activity.EcoCarpingEditActivity;
import com.tourkakao.carping.Post.DTO.PostDetail;
import com.tourkakao.carping.Post.ViewModel.PostDetailViewModel;
import com.tourkakao.carping.R;
import com.tourkakao.carping.SharedPreferenceManager.SharedPreferenceManager;
import com.tourkakao.carping.databinding.ActivityPostDetailBinding;

public class PostDetailActivity extends AppCompatActivity {
    private ActivityPostDetailBinding binding;
    private PostDetailViewModel viewModel;
    private Context context;
    private int author_id;
    private int post_id;
    private PostInfoActivity postInfoActivity;
    private int otherPostId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityPostDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        context=getApplicationContext();
        postInfoActivity = (PostInfoActivity)PostInfoActivity.postInfoActivity;

        viewModel =new ViewModelProvider(this).get(PostDetailViewModel.class);
        viewModel.setContext(this);

        initLayout();
        settingLayout();

        binding.other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, PostInfoActivity.class);
                intent.putExtra("pk",otherPostId);
                startActivity(intent);
            }
        });
    }

    void initLayout(){
        Glide.with(context).load(R.drawable.back).into(binding.back);
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        binding.privateDeleteButton.setVisibility(View.GONE);
        Glide.with(context).load(R.drawable.list_show_btn).into(binding.privateDeleteButton);
        binding.privateDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerForContextMenu(view);
                view.showContextMenu();
                unregisterForContextMenu(view);
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.detail_page_delete, menu);
    }

    public boolean onContextItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
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
                        viewModel.deletePost(post_id);
                        postInfoActivity.finish();
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

    void settingLayout(){
        int current_user= SharedPreferenceManager.getInstance(getApplicationContext()).getInt("id",0);
        int pk=getIntent().getIntExtra("pk",0);
        viewModel.loadPostDetail(pk);
        viewModel.getPost().observe(this, new Observer<PostDetail>() {
            @Override
            public void onChanged(PostDetail postDetail) {
                post_id=postDetail.getId();
                author_id =getIntent().getIntExtra("author_id",-1);
//                if(current_user== author_id){
//                    binding.privateDeleteButton.setVisibility(View.VISIBLE);
//                }
                Glide.with(context).load(postDetail.getThumbnail()).into(binding.thumbnail);
                binding.thumbnail.setColorFilter(Color.parseColor("#595959"), PorterDuff.Mode.MULTIPLY);
                binding.title.setText(postDetail.getTitle());
                Glide.with(context).load(postDetail.getAuthor_profile())
                        .transform(new CenterCrop(), new RoundedCorners(100))
                        .into(binding.profileImg);
                binding.name.setText(postDetail.getAuthor_name());
                binding.date.setText(postDetail.getCreated_at());
                if(postDetail.getSub_title1()==null||postDetail.getSub_title1().equals("")){
                    binding.content1.setVisibility(View.GONE);
                }else {
                    binding.title1.setText(postDetail.getSub_title1());
                    if(postDetail.getImage1()==null||postDetail.getImage1().equals("")){
                        binding.image1.setVisibility(View.GONE);
                    }else {
                        Glide.with(context).load(postDetail.getImage1()).into(binding.image1);
                    }if(postDetail.getText1()==null||postDetail.getText1().equals("")){
                        binding.text1.setVisibility(View.GONE);
                    }else {
                        binding.text1.setText(postDetail.getText1());
                    }
                }
                if(postDetail.getSub_title2()==null||postDetail.getSub_title2().equals("")){
                    binding.content2.setVisibility(View.GONE);
                }else {
                    binding.title2.setText(postDetail.getSub_title2());
                    if(postDetail.getImage2()==null||postDetail.getImage2().equals("")){
                        binding.image2.setVisibility(View.GONE);
                    }else {
                        Glide.with(context).load(postDetail.getImage2()).into(binding.image2);
                    }if(postDetail.getText2()==null||postDetail.getText2().equals("")){
                        binding.text2.setVisibility(View.GONE);
                    }else {
                        binding.text2.setText(postDetail.getText1());
                    }
                }
                if(postDetail.getSub_title3()==null||postDetail.getSub_title3().equals("")){
                    binding.content3.setVisibility(View.GONE);
                }else {
                    binding.title3.setText(postDetail.getSub_title3());
                    if(postDetail.getImage3()==null||postDetail.getImage3().equals("")){
                        binding.image3.setVisibility(View.GONE);
                    }else {
                        Glide.with(context).load(postDetail.getImage3()).into(binding.image3);
                    }if(postDetail.getText3()==null||postDetail.getText3().equals("")){
                        binding.text3.setVisibility(View.GONE);
                    }else {
                        binding.text3.setText(postDetail.getText3());
                    }
                }
                if(postDetail.getSub_title4()==null||postDetail.getSub_title4().equals("")){
                    binding.content4.setVisibility(View.GONE);
                }else {
                    binding.title4.setText(postDetail.getSub_title4());
                    if(postDetail.getImage4()==null||postDetail.getImage4().equals("")){
                        binding.image4.setVisibility(View.GONE);
                    }else {
                        Glide.with(context).load(postDetail.getImage4()).into(binding.image4);
                    }if(postDetail.getText4()==null||postDetail.getText4().equals("")){
                        binding.text4.setVisibility(View.GONE);
                    }else {
                        binding.text4.setText(postDetail.getText4());
                    }
                }
                if(postDetail.getSub_title5()==null||postDetail.getSub_title5().equals("")){
                    binding.content5.setVisibility(View.GONE);
                }else {
                    binding.title5.setText(postDetail.getSub_title5());
                    if(postDetail.getImage5()==null||postDetail.getImage5().equals("")){
                        binding.image5.setVisibility(View.GONE);
                    }else {
                        Glide.with(context).load(postDetail.getImage5()).into(binding.image5);
                    }if(postDetail.getText5()==null||postDetail.getText5().equals("")){
                        binding.text5.setVisibility(View.GONE);
                    }else {
                        binding.text5.setText(postDetail.getText5());
                    }
                }
                Glide.with(context).load(postDetail.getAuthor_profile())
                        .transform(new CenterCrop(), new RoundedCorners(100))
                        .into(binding.bottomProfile);
                binding.bottomName.setText(postDetail.getAuthor_name());
                binding.channelIntroduce.setText(postDetail.getAuthor_comment());
                if(postDetail.getOther_post()==null){
                    binding.other.setVisibility(View.GONE);
                }
                else {
                    otherPostId = postDetail.getOther_post().getId();
                    Glide.with(context).load(postDetail.getOther_post().getThumbnail())
                            .transform(new CenterCrop(), new RoundedCorners(4))
                            .into(binding.recommend);
                    binding.recommend.setColorFilter(Color.parseColor("#75000000"));
                    if (postDetail.getOther_post().getPay_type() == 0) {
                        Glide.with(context).load(R.drawable.free_mark).into(binding.payTypeImg);
                    }
                    if (postDetail.getOther_post().getPay_type() == 1) {
                        Glide.with(context).load(R.drawable.premium_mark).into(binding.payTypeImg);
                    }
                    binding.recommendTitle.setText(postDetail.getOther_post().getTitle());
                    Glide.with(context).load(R.drawable.writer_mark).into(binding.writerImg);
                }
            }
        });
    }
}