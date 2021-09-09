package com.tourkakao.carping.Home.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.tourkakao.carping.Mypage.ProfileEditActivity;
import com.tourkakao.carping.MypageMainActivities.Activity.MypageCarpingActivity;
import com.tourkakao.carping.MypageMainActivities.Activity.MypageEcoReviewActivity;
import com.tourkakao.carping.MypageMainActivities.Activity.MypageFreeSharingActivity;
import com.tourkakao.carping.MypageMainActivities.Activity.MypagePostActivity;
import com.tourkakao.carping.R;
import com.tourkakao.carping.SharedPreferenceManager.SharedPreferenceManager;
import com.tourkakao.carping.databinding.MypageFragmentBinding;

public class MypageFragment extends Fragment {
    private MypageFragmentBinding mypagebinding;
    Context context;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mypagebinding=MypageFragmentBinding.inflate(inflater, container, false);
        context=getActivity().getApplicationContext();

        settingImg();
        settingProfile();

        mypagebinding.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, ProfileEditActivity.class);
                startActivity(intent);
            }
        });
        mypagebinding.eco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, MypageEcoReviewActivity.class);
                startActivity(intent);
            }
        });
        mypagebinding.carping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, MypageCarpingActivity.class);
                startActivity(intent);
            }
        });
        mypagebinding.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, MypageFreeSharingActivity.class);
                startActivity(intent);
            }
        });
        mypagebinding.post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, MypagePostActivity.class);
                startActivity(intent);
            }
        });
        mypagebinding.postAppro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return mypagebinding.getRoot();
    }

    public void settingImg(){
        Glide.with(context).load(R.drawable.mypage_profile_edit_button).into(mypagebinding.editButton);
    }

    public void settingProfile(){
        String name=SharedPreferenceManager.getInstance(context).getString("username","");
        String image=SharedPreferenceManager.getInstance(context).getString("profile","");
        Glide.with(context).load(image)
                .transform(new CenterCrop(), new RoundedCorners(100))
                .into(mypagebinding.profile);
        mypagebinding.userId.setText(name);
    }
}
