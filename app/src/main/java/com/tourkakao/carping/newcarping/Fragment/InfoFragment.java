package com.tourkakao.carping.newcarping.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.bumptech.glide.Glide;
import com.tourkakao.carping.databinding.NewcarpingInfoFragmentBinding;
import com.tourkakao.carping.newcarping.viewmodel.EachNewCarpingViewModel;

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

        staring_observe_info_image();
        return infoFragmentBinding.getRoot();
    }

    public void setting_viewmodel(EachNewCarpingViewModel eachNewCarpingViewModel){
        this.eachNewCarpingViewModel=eachNewCarpingViewModel;
    }
    public void staring_observe_info_image(){
        eachNewCarpingViewModel.image.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Glide.with(context).load(s).into(infoFragmentBinding.newcarpingInfoImg);
            }
        });
    }
}
