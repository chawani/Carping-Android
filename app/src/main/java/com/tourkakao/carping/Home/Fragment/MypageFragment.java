package com.tourkakao.carping.Home.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tourkakao.carping.databinding.MypageFragmentBinding;

public class MypageFragment extends Fragment {
    private MypageFragmentBinding mypagebinding;
    Context context;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mypagebinding=MypageFragmentBinding.inflate(inflater, container, false);
        context=getActivity().getApplicationContext();

        return mypagebinding.getRoot();
    }
}
