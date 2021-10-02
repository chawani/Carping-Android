package com.tourkakao.carping.MypageMainActivities.Fragment;

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
import androidx.recyclerview.widget.RecyclerView;

import com.tourkakao.carping.EcoCarping.Adapter.EcoTotalReviewAdapter;
import com.tourkakao.carping.MypageMainActivities.Adapter.MyCarpingAdapter;
import com.tourkakao.carping.MypageMainActivities.Adapter.ScrapAutoAdapter;
import com.tourkakao.carping.MypageMainActivities.DTO.MyCarpingPost;
import com.tourkakao.carping.MypageMainActivities.MypageViewModel.MypageCarpingViewModel;
import com.tourkakao.carping.MypageMainActivities.MypageViewModel.MypageEcoViewModel;
import com.tourkakao.carping.databinding.MypageTabFragmentBinding;
import com.tourkakao.carping.newcarping.Activity.Each_NewCarpingActivity;

import java.util.ArrayList;

public class MyCarpingFragment extends Fragment {
    private MypageTabFragmentBinding binding;
    private Context context;
    private MypageCarpingViewModel viewModel;
    private MyCarpingAdapter adapter;
    private RecyclerView.LayoutManager mLayoutManager;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = MypageTabFragmentBinding.inflate(inflater, container, false);
        context = getActivity().getApplicationContext();

        viewModel = new ViewModelProvider(this).get(MypageCarpingViewModel.class);
        viewModel.setContext(context);

        initLayout();
        initDatas();

        return binding.getRoot();
    }

    void initLayout(){
        binding.mypageEmptyText.setText("첫 차박지를 등록해보세요!");
        mLayoutManager = new LinearLayoutManager(getActivity());
        binding.mypageRecycler.setLayoutManager(mLayoutManager);
    }
    void initDatas(){
        viewModel.getMyCarpings().observe(this, new Observer<ArrayList<MyCarpingPost>>() {
            @Override
            public void onChanged(ArrayList<MyCarpingPost> myCarpingPosts) {
                if(myCarpingPosts.size()==0){
                    binding.mypageRecycler.setVisibility(View.GONE);
                    binding.mypageEmptyText.setVisibility(View.VISIBLE);
                }
                else{
                    binding.mypageRecycler.setVisibility(View.VISIBLE);
                    binding.mypageEmptyText.setVisibility(View.GONE);
                    adapter=new MyCarpingAdapter(context,myCarpingPosts);
                    binding.mypageRecycler.setAdapter(adapter);
                    adapter.setOnSelectItemCLickListener(new MyCarpingAdapter.OnSelectItemClickListener() {
                        @Override
                        public void OnSelectItemClick(View v, int pos, int pk) {
                            Intent intent=new Intent(context, Each_NewCarpingActivity.class);
                            intent.putExtra("pk", pk);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        }
                    });
                }
            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        viewModel.loadMyCarpings();
    }
}
