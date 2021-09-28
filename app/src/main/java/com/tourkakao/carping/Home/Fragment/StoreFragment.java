package com.tourkakao.carping.Home.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.tourkakao.carping.Home.CommunityViewModel.StoreViewModel;
import com.tourkakao.carping.databinding.StoreFragmentBinding;

public class StoreFragment extends Fragment {
    StoreFragmentBinding storebinding;
    Context context;
    StoreViewModel storeViewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        storebinding=StoreFragmentBinding.inflate(inflater, container, false);
        context=getActivity().getApplicationContext();

        storeViewModel=new ViewModelProvider(this).get(StoreViewModel.class);
        storeViewModel.setContext(context);

        setting_product();

        return storebinding.getRoot();
    }
    public void setting_product(){
        storebinding.storeRecyclerview.setLayoutManager(new GridLayoutManager(context, 2));
        storebinding.storeRecyclerview.setAdapter(storeViewModel.setting_product_adapter());
        storeViewModel.get_product();
    }
}
