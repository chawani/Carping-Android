package com.tourkakao.carping.registernewcarping.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tourkakao.carping.R;
import com.tourkakao.carping.databinding.ActivityTagsBinding;

public class TagsActivity extends AppCompatActivity {
    ActivityTagsBinding tagsBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tagsBinding=ActivityTagsBinding.inflate(getLayoutInflater());
        setContentView(tagsBinding.getRoot());

        Glide.with(this).load(R.drawable.back).into(tagsBinding.back);
        tagsBinding.back.setOnClickListener(v -> {
            finish();
        });

        tagsBinding.complete.setOnClickListener(v -> {
            String tag=tagsBinding.tagWriteBar.getText().toString().trim();
            if(tag.equals("")){
                Toast.makeText(this, "태그를 입력해주세요", Toast.LENGTH_SHORT).show();
            }else{
                Intent intent=new Intent();
                intent.putExtra("tag", tag);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}