package com.tourkakao.carping.sharedetail.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.tourkakao.carping.SharedPreferenceManager.SharedPreferenceManager;
import com.tourkakao.carping.databinding.ActivityShareTotalBinding;
import com.tourkakao.carping.registernewshare.Activity.Register_ShareActivity;
import com.tourkakao.carping.sharedetail.viewmodel.ShareDetailViewModel;

public class ShareTotalActivity extends AppCompatActivity {
    ActivityShareTotalBinding shareTotalBinding;
    Context context;
    ShareDetailViewModel shareDetailViewModel;
    String[] items={"최신순", "인기순"};
    int share_new=0;
    int spinner_pos=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shareTotalBinding=ActivityShareTotalBinding.inflate(getLayoutInflater());
        setContentView(shareTotalBinding.getRoot());
        context=getApplicationContext();

        shareDetailViewModel=new ViewModelProvider(this).get(ShareDetailViewModel.class);
        shareDetailViewModel.setContext(context);

        setting_share();
        setting_spinner();
        setting_share_btn();
        setting_back_btn();
    }
    public void setting_share(){
        shareTotalBinding.shareRecyclerview.setLayoutManager(new LinearLayoutManager(context));
        shareTotalBinding.shareRecyclerview.setAdapter(shareDetailViewModel.setting_share_adapter());
        shareDetailViewModel.get_total_share("recent", 0);
    }
    public void setting_spinner(){
        ArrayAdapter<String> adapter=new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, items);
        shareTotalBinding.shareSpinner.setAdapter(adapter);
        shareTotalBinding.shareSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    spinner_pos=0;
                    shareDetailViewModel.get_total_share("recent", 0);
                }else {
                    spinner_pos=1;
                    shareDetailViewModel.get_total_share("popular", 0);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    public void setting_share_btn(){
        shareTotalBinding.shareWriteButton.setOnClickListener(v -> {
            Intent intent=new Intent(context, Register_ShareActivity.class);
            share_new=1;
            startActivityForResult(intent, 1001);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if(requestCode==1001){
                if(spinner_pos==0){
                    shareDetailViewModel.get_total_share("recent", 0);
                }else{
                    shareDetailViewModel.get_total_share("popular", 0);
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
    public void setting_back_btn(){
        shareTotalBinding.back.setOnClickListener(v -> {
            finish();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(SharedPreferenceManager.getInstance(context).getInt("change_isshare", 0)==1){
            if(spinner_pos==0){
                shareDetailViewModel.get_total_share("recent", 0);
            }else{
                shareDetailViewModel.get_total_share("popular", 0);
            }
        }
    }
}