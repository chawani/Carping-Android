package com.tourkakao.carping.registernewshare.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import com.bumptech.glide.Glide;
import com.tourkakao.carping.R;
import com.tourkakao.carping.databinding.ActivityLocateSelectBinding;
import com.tourkakao.carping.registernewshare.viewmodel.LocateViewModel;

public class LocateSelectActivity extends AppCompatActivity {
    ActivityLocateSelectBinding selectBinding;
    LocateViewModel locateViewModel;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        selectBinding=ActivityLocateSelectBinding.inflate(getLayoutInflater());
        setContentView(selectBinding.getRoot());
        context=this;

        locateViewModel=new ViewModelProvider(this).get(LocateViewModel.class);
        locateViewModel.setContext(context);
        selectBinding.setLifecycleOwner(this);
        selectBinding.setLocateviewmodel(locateViewModel);

        setting_recyclerview();
        setting_search();
        starting_observe_all_select();
    }
    public void setting_recyclerview(){
        selectBinding.locateRecyclerview.setLayoutManager(new LinearLayoutManager(context));
        selectBinding.locateRecyclerview.setAdapter(locateViewModel.setting_locate_adapter());
        selectBinding.locateRecyclerview.scrollToPosition(0);
    }
    public void setting_search(){
        selectBinding.searchEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                locateViewModel.change_list_bysearch(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
    public void starting_observe_all_select(){
        locateViewModel.dong_id.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if(integer!=-1){
                    String locate=locateViewModel.first+" "+locateViewModel.second+" "+locateViewModel.third;
                    Intent intent=new Intent();
                    intent.putExtra("locate", locate);
                    intent.putExtra("locate_id", locateViewModel.dong_id.getValue());
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }
}