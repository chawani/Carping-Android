package com.tourkakao.carping.Home.Fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.google.gson.Gson;
import com.tourkakao.carping.Home.HomeViewModel.EcoViewModel;
import com.tourkakao.carping.Home.HomeViewModel.MypageViewModel;
import com.tourkakao.carping.Mypage.PostApprovalActivity;
import com.tourkakao.carping.Mypage.Profile;
import com.tourkakao.carping.Mypage.ProfileEditActivity;
import com.tourkakao.carping.MypageMainActivities.Activity.MypageCarpingActivity;
import com.tourkakao.carping.MypageMainActivities.Activity.MypageEcoReviewActivity;
import com.tourkakao.carping.MypageMainActivities.Activity.MypageFreeSharingActivity;
import com.tourkakao.carping.MypageMainActivities.Activity.MypagePostActivity;
import com.tourkakao.carping.NetworkwithToken.CommonClass;
import com.tourkakao.carping.NetworkwithToken.TotalApiClient;
import com.tourkakao.carping.Post.PostRegisterActivity;
import com.tourkakao.carping.R;
import com.tourkakao.carping.SharedPreferenceManager.SharedPreferenceManager;
import com.tourkakao.carping.databinding.MypageFragmentBinding;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MypageFragment extends Fragment {
    private MypageFragmentBinding mypagebinding;
    private Context context;
    private MypageViewModel myViewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mypagebinding=MypageFragmentBinding.inflate(inflater, container, false);
        context=getActivity().getApplicationContext();

        myViewModel =new ViewModelProvider(this).get(MypageViewModel.class);
        myViewModel.setContext(context);
        settingImg();
        myViewModel.loadProfile();


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
                Intent intent=new Intent(context, PostApprovalActivity.class);
                startActivity(intent);
            }
        });
        mypagebinding.postRegi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, PostRegisterActivity.class);
                startActivity(intent);
            }
        });
        myViewModel.getProfileMutableLiveData().observe(this, new Observer<Profile>() {
            @Override
            public void onChanged(Profile profile) {
                Glide.with(context).load(profile.getImage())
                        .transform(new CenterCrop(), new RoundedCorners(100))
                        .into(mypagebinding.profile);
                mypagebinding.userId.setText(profile.getUsername());
                Glide.with(context).load(profile.getBadge()).into(mypagebinding.badge);
                int level=(int)Double.parseDouble(profile.getLevel());
                mypagebinding.level.setText("LV."+Integer.toString(level));
                mypagebinding.introduce.setText(profile.getBio());
                String interest=profile.getInterest().toString();
                interest=interest.replace("[","");
                interest=interest.replace("]","");
                interest=interest.replace(" ","");
                String[] tags=interest.split(",");
                //System.out.println("관심사 확인"+interest);
                for(String tag:tags){
                    TextView textView = new TextView(context);
                    LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.rightMargin= convertDp(6);
                    textView.setLayoutParams(params);
                    textView.setBackgroundResource(R.drawable.tag_design);
                    textView.setPadding(convertDp(10),convertDp(5),convertDp(10),convertDp(5));
                    textView.setTextColor(Color.parseColor("#5f51ef"));
                    textView.setText(tag);
                    mypagebinding.tag.addView(textView);
                }
            }
        });

        return mypagebinding.getRoot();
    }

    public void settingImg(){
        Glide.with(context).load(R.drawable.mypage_profile_edit_button).into(mypagebinding.editButton);
        Glide.with(context).load(R.drawable.right_arrow).into(mypagebinding.arrow1);
        Glide.with(context).load(R.drawable.right_arrow).into(mypagebinding.arrow2);
        Glide.with(context).load(R.drawable.right_arrow).into(mypagebinding.arrow3);
        Glide.with(context).load(R.drawable.right_arrow).into(mypagebinding.arrow4);
        Glide.with(context).load(R.drawable.right_arrow).into(mypagebinding.arrow5);
        Glide.with(context).load(R.drawable.right_arrow).into(mypagebinding.arrow6);
        Glide.with(context).load(R.drawable.right_arrow).into(mypagebinding.arrow7);
        Glide.with(context).load(R.drawable.right_arrow).into(mypagebinding.arrow8);
        Glide.with(context).load(R.drawable.right_arrow).into(mypagebinding.arrow9);
        Glide.with(context).load(R.drawable.right_arrow).into(mypagebinding.arrow10);
    }

    public int convertDp(int dp) {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, metrics);
    }
}
