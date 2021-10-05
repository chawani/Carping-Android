package com.tourkakao.carping.Mypage.Fragment;

import android.content.Context;
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
import androidx.recyclerview.widget.RecyclerView;

import com.tourkakao.carping.MypageMainActivities.Adapter.DepositAdapter;
import com.tourkakao.carping.MypageMainActivities.Adapter.PostAdapter;
import com.tourkakao.carping.MypageMainActivities.DTO.Post;
import com.tourkakao.carping.MypageMainActivities.MypageViewModel.MypagePostViewModel;
import com.tourkakao.carping.Post.DTO.PostListItem;
import com.tourkakao.carping.databinding.MypageTabFragmentBinding;

import java.util.ArrayList;

public class DepositPostFragment extends Fragment {
    MypageTabFragmentBinding binding;
    Context context;
    MypagePostViewModel viewModel;
    private RecyclerView.LayoutManager mLayoutManager;
    private DepositAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = MypageTabFragmentBinding.inflate(inflater, container, false);
        context = getActivity().getApplicationContext();

        viewModel = new ViewModelProvider(this).get(MypagePostViewModel.class);
        viewModel.setContext(context);

        initLayout();
        observeData();

        return binding.getRoot();
    }

    void initLayout(){
        binding.mypageEmptyText.setText("구매 된 포스트가 없습니다.");
        mLayoutManager = new LinearLayoutManager(getActivity());
        binding.mypageRecycler.setLayoutManager(mLayoutManager);
    }
    void observeData(){
        viewModel.getDeposit().observe(this, new Observer<ArrayList<Post>>() {
            @Override
            public void onChanged(ArrayList<Post> items) {
                if(items.size()==0){
                    binding.mypageEmptyText.setVisibility(View.VISIBLE);
                    binding.mypageRecycler.setVisibility(View.GONE);
                }
                else {
                    binding.mypageEmptyText.setVisibility(View.GONE);
                    binding.mypageRecycler.setVisibility(View.VISIBLE);
                    adapter = new DepositAdapter(context, items);
                    binding.mypageRecycler.setAdapter(adapter);
                }
            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        viewModel.loadApproval(2);
    }
}