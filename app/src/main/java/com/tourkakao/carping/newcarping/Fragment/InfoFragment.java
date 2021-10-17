package com.tourkakao.carping.newcarping.Fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.tourkakao.carping.R;
import com.tourkakao.carping.databinding.EachNewcarpingDetailImageBinding;
import com.tourkakao.carping.databinding.EachNewcarpingReviewImageBinding;
import com.tourkakao.carping.databinding.NewcarpingInfoFragmentBinding;
import com.tourkakao.carping.newcarping.viewmodel.EachNewCarpingViewModel;

import java.util.ArrayList;

public class InfoFragment extends Fragment {
    private NewcarpingInfoFragmentBinding infoFragmentBinding;
    private EachNewCarpingViewModel eachNewCarpingViewModel;
    Context context;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        infoFragmentBinding=NewcarpingInfoFragmentBinding.inflate(inflater, container, false);
        context=getActivity().getApplicationContext();
        infoFragmentBinding.setLifecycleOwner(this);
        infoFragmentBinding.setEachNewCarpingViewModel(eachNewCarpingViewModel);

        starting_observe_info_image();
        starting_observe_info_tags();
        return infoFragmentBinding.getRoot();
    }

    public void setting_viewmodel(EachNewCarpingViewModel eachNewCarpingViewModel){
        this.eachNewCarpingViewModel=eachNewCarpingViewModel;
    }
    public void starting_observe_info_image(){
        eachNewCarpingViewModel.image1.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                infoFragmentBinding.imageLayout.removeAllViews();
                if(s!=null) {
                    EachNewcarpingDetailImageBinding binding= EachNewcarpingDetailImageBinding.inflate(getLayoutInflater());
                    ImageView img=binding.newcarpingInfoImg;
                    img.setScaleType(ImageView.ScaleType.FIT_XY);
                    Glide.with(context).load(s).transform(new CenterCrop(), new RoundedCorners(30)).into(img);
                    infoFragmentBinding.imageLayout.addView(binding.getRoot());
                }
            }
        });
        eachNewCarpingViewModel.image2.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(s!=null) {
                    EachNewcarpingDetailImageBinding binding= EachNewcarpingDetailImageBinding.inflate(getLayoutInflater());
                    ImageView img=binding.newcarpingInfoImg;
                    Glide.with(context).load(s).transform(new CenterCrop(), new RoundedCorners(30)).into(img);
                    infoFragmentBinding.imageLayout.addView(binding.getRoot());
                }
            }
        });
        eachNewCarpingViewModel.image3.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(s!=null) {
                    EachNewcarpingDetailImageBinding binding= EachNewcarpingDetailImageBinding.inflate(getLayoutInflater());
                    ImageView img=binding.newcarpingInfoImg;
                    Glide.with(context).load(s).transform(new CenterCrop(), new RoundedCorners(30)).into(img);
                    infoFragmentBinding.imageLayout.addView(binding.getRoot());
                }
            }
        });
        eachNewCarpingViewModel.image4.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(s!=null) {
                    EachNewcarpingDetailImageBinding binding= EachNewcarpingDetailImageBinding.inflate(getLayoutInflater());
                    ImageView img=binding.newcarpingInfoImg;
                    Glide.with(context).load(s).transform(new CenterCrop(), new RoundedCorners(30)).into(img);
                    infoFragmentBinding.imageLayout.addView(binding.getRoot());
                }
            }
        });
    }
    public void starting_observe_info_tags(){
        eachNewCarpingViewModel.info_tags.observe(this, new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> strings) {
                infoFragmentBinding.tagsLayout.removeAllViews();
                for(int i=0; i<strings.size(); i++){
                    TextView tag=new TextView(context);
                    tag.setText("#"+strings.get(i));
                    tag.setTextSize(12.0f);
                    tag.setBackgroundResource(R.drawable.purple_border_round);
                    tag.setPadding(60, 30, 60, 30);
                    tag.setTextColor(Color.parseColor("#5f51ef"));
                    LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    params.rightMargin=25;
                    tag.setLayoutParams(params);
                    infoFragmentBinding.tagsLayout.addView(tag);
                }
            }
        });
    }
}
