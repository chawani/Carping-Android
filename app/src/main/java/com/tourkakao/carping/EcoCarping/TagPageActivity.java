package com.tourkakao.carping.EcoCarping;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.tourkakao.carping.R;
import com.tourkakao.carping.databinding.ActivityTagPageBinding;

public class TagPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityTagPageBinding binding=ActivityTagPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.tagWriteBar.getText().toString().equals("")) {
                }
                else{
                    Intent intent=getIntent();
                    intent.putExtra("tag", "#"+binding.tagWriteBar.getText().toString());
                    setResult(1, intent);
                    finish();
                }

            }
        });

    }
}