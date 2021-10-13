package com.tourkakao.carping.Home.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.tourkakao.carping.Home.HomeViewModel.MypageViewModel;
import com.tourkakao.carping.Login.GoogleLogin;
import com.tourkakao.carping.Login.GoogleLogout;
import com.tourkakao.carping.Login.KakaoLogout;
import com.tourkakao.carping.Login.LoginActivity;
import com.tourkakao.carping.Mypage.HelpListActivity;
import com.tourkakao.carping.Mypage.PostApprovalActivity;
import com.tourkakao.carping.Mypage.DTO.Profile;
import com.tourkakao.carping.Mypage.ProfileEditActivity;
import com.tourkakao.carping.Mypage.TermsOfServiceActivity;
import com.tourkakao.carping.MypageMainActivities.Activity.MypageCarpingActivity;
import com.tourkakao.carping.MypageMainActivities.Activity.MypageEcoReviewActivity;
import com.tourkakao.carping.MypageMainActivities.Activity.MypageFreeSharingActivity;
import com.tourkakao.carping.MypageMainActivities.Activity.MypagePostActivity;
import com.tourkakao.carping.Post.PostRegisterActivity;
import com.tourkakao.carping.R;
import com.tourkakao.carping.SharedPreferenceManager.SharedPreferenceManager;
import com.tourkakao.carping.databinding.MypageFragmentBinding;

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
        mypagebinding.help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, HelpListActivity.class);
                startActivity(intent);
            }
        });
        mypagebinding.terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, TermsOfServiceActivity.class);
                startActivity(intent);
            }
        });
        mypagebinding.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutDialog();
            }
        });
        myViewModel.getProfileMutableLiveData().observe(this, new Observer<Profile>() {
            @Override
            public void onChanged(Profile profile) {
                if(profile.getPhone()==null){
                    SharedPreferenceManager.getInstance(context).setBoolean("phone",false);
                }else{
                    SharedPreferenceManager.getInstance(context).setBoolean("phone",true);
                }
                Glide.with(context).load(profile.getImage())
                        .transform(new CenterCrop(), new RoundedCorners(100))
                        .into(mypagebinding.profile);
                mypagebinding.userId.setText(profile.getUsername());
                Glide.with(context).load(profile.getBadge()).into(mypagebinding.badge);
                int level=(int)Double.parseDouble(profile.getLevel());
                mypagebinding.level.setText("LV."+Integer.toString(level));
                if(profile.getBio()==null){
                    mypagebinding.introduce.setVisibility(View.GONE);
                }else {
                    mypagebinding.introduce.setText(profile.getBio());
                }
                String interest=profile.getInterest().toString();
                interest=interest.replace("[","");
                interest=interest.replace("]","");
                interest=interest.replace(" ","");
                if(interest.length()==0){
                    mypagebinding.tag.setVisibility(View.GONE);
                }else {
                    String[] tags=interest.split(",");
                    mypagebinding.tag.removeAllViews();
                    for (String tag : tags) {
                        TextView textView = new TextView(context);
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        params.rightMargin = convertDp(6);
                        textView.setLayoutParams(params);
                        textView.setBackgroundResource(R.drawable.tag_design);
                        textView.setPadding(convertDp(10), convertDp(5), convertDp(10), convertDp(5));
                        textView.setTextColor(Color.parseColor("#5f51ef"));
                        String convertedText=convertText(tag);
                        textView.setText(convertedText);
                        mypagebinding.tag.addView(textView);
                    }
                }
            }
        });

        return mypagebinding.getRoot();
    }

    String convertText(String s){
        if(s.equals("0")){
            return "\uD83D\uDE97 차크닉";
        }
        if(s.equals("1")){
            return "⛺혼차박";
        }
        if(s.equals("2")){
            return "\uD83C\uDF03 퇴근박";
        }
        if(s.equals("3")){
            return "\uD83D\uDD25 불멍";
        }
        if(s.equals("4")){
            return "\uD83C\uDF56 바베큐";
        }
        if(s.equals("5")){
            return "\uD83C\uDFD5 오지캠핑";
        }
        if(s.equals("6")){
            return "\uD83D\uDEA4 레저";
        }
        if(s.equals("7")){
            return "\uD83C\uDFA3 낚시";
        }
        if(s.equals("8")){
            return "\uD83C\uDF31 클린 차박";
        }
        return "";
    }

    void logoutDialog(){
        AlertDialog.Builder msgBuilder = new AlertDialog.Builder(getActivity())
                .setMessage("로그아웃 하시겠습니까?")
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialogInterface, int i) {
                        String loginType=SharedPreferenceManager.getInstance(context).getString("login","");
                        if(loginType.equals("kakao")){
                            KakaoLogout kakaoLogout=new KakaoLogout(context, getActivity());
                            kakaoLogout.signOut();
                        }
                        if(loginType.equals("google")) {
                            GoogleLogout googleLogout = new GoogleLogout(context, getActivity());
                            googleLogout.signOut();
                        }
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

    public void settingImg(){
        Glide.with(context).load(R.drawable.mypage_profile_edit_button).into(mypagebinding.editButton);
        Glide.with(context).load(R.drawable.right_arrow).into(mypagebinding.arrow1);
        Glide.with(context).load(R.drawable.right_arrow).into(mypagebinding.arrow2);
        Glide.with(context).load(R.drawable.right_arrow).into(mypagebinding.arrow3);
        Glide.with(context).load(R.drawable.right_arrow).into(mypagebinding.arrow4);
        Glide.with(context).load(R.drawable.right_arrow).into(mypagebinding.arrow5);
        Glide.with(context).load(R.drawable.right_arrow).into(mypagebinding.arrow6);
        Glide.with(context).load(R.drawable.right_arrow).into(mypagebinding.arrow7);
        Glide.with(context).load(R.drawable.right_arrow).into(mypagebinding.arrow10);
        Glide.with(context).load(R.drawable.right_arrow).into(mypagebinding.arrow11);
    }

    public int convertDp(int dp) {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, metrics);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mypagebinding=null;
    }

    @Override
    public void onResume() {
        super.onResume();
        myViewModel.loadProfile();
    }
}
