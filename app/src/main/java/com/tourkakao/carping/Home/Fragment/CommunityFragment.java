package com.tourkakao.carping.Home.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tourkakao.carping.databinding.CommunityFragmentBinding;

public class CommunityFragment extends Fragment {
    private CommunityFragmentBinding communitybinding;
    Context context;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        communitybinding=CommunityFragmentBinding.inflate(inflater, container, false);
        context=getActivity().getApplicationContext();

        return communitybinding.getRoot();
    }
}
