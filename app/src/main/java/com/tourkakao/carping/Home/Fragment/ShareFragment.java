package com.tourkakao.carping.Home.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.tourkakao.carping.Home.CommunityViewModel.ShareViewModel;
import com.tourkakao.carping.R;
import com.tourkakao.carping.SharedPreferenceManager.SharedPreferenceManager;
import com.tourkakao.carping.databinding.ShareFragmentBinding;
import com.tourkakao.carping.registernewshare.Activity.Register_ShareActivity;
import com.tourkakao.carping.sharedetail.Activity.ShareTotalActivity;

public class ShareFragment extends Fragment {
    ShareFragmentBinding sharebinding;
    Context context;
    ShareViewModel shareViewModel;
    int share_new=0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        sharebinding=ShareFragmentBinding.inflate(inflater, container, false);
        context=getActivity().getApplicationContext();

        Glide.with(context).load(R.drawable.main_eco_check_mark).into(sharebinding.shareCheckMark);
        Glide.with(context).load(R.drawable.main_eco_write_button).into(sharebinding.shareWriteButton);
        Glide.with(context).load(R.drawable.community_share_img).into(sharebinding.shareImage);

        shareViewModel=new ViewModelProvider(this).get(ShareViewModel.class);
        shareViewModel.setContext(context);

        setting_share();
        setting_share_btn();
        starting_observe_share_count();
        setting_total_btn();

        return sharebinding.getRoot();
    }
    public void setting_share(){
        sharebinding.shareRecyclerview.setLayoutManager(new LinearLayoutManager(context));
        sharebinding.shareRecyclerview.setAdapter(shareViewModel.setting_share_adapter());
        shareViewModel.get_share("recent", 10);
    }
    public void setting_share_btn(){
        sharebinding.shareWriteButton.setOnClickListener(v -> {
            Intent intent=new Intent(context, Register_ShareActivity.class);
            share_new=1;
            startActivityForResult(intent, 1001);
        });
    }
    public void starting_observe_share_count(){
        shareViewModel.share_count.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                sharebinding.totalShareCnt.setText(s);
            }
        });
    }
    public void setting_total_btn(){
        sharebinding.shareTotal.setOnClickListener(v -> {
            Intent intent=new Intent(context, ShareTotalActivity.class);
            startActivityForResult(intent, 1002);
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode== Activity.RESULT_OK){
            if(requestCode==1001){
                shareViewModel.get_share("recent", 10);
            }else if(requestCode==1002){
                if(data.getIntExtra("new", 0)==1){
                    shareViewModel.get_share("recent", 10);
                }
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(SharedPreferenceManager.getInstance(context).getInt("change_isshare", 0)==1){
            shareViewModel.get_share("recent", 10);
            SharedPreferenceManager.getInstance(context).setInt("change_isshare", 0);
        }
        if(SharedPreferenceManager.getInstance(context).getInt("share_delete", 0)==1){
            shareViewModel.get_share("recent", 10);
            SharedPreferenceManager.getInstance(context).setInt("share_delete", 0);
        }
    }

}
