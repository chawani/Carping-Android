package com.tourkakao.carping.Post.Fragment;

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

import com.tourkakao.carping.Post.Adapter.PostCategoryAdapter;
import com.tourkakao.carping.Post.Adapter.PostTotalAdapter;
import com.tourkakao.carping.Post.DTO.PostInfoDetail;
import com.tourkakao.carping.Post.ViewModel.PostDetailViewModel;
import com.tourkakao.carping.Post.ViewModel.PostListViewModel;
import com.tourkakao.carping.databinding.PostIntroduceFragmentBinding;
import com.tourkakao.carping.databinding.PostRecommendationFragmentBinding;

public class RecommendationFragment extends Fragment {
    private PostRecommendationFragmentBinding binding;
    private Context context;
    private PostDetailViewModel detailViewModel;
    private PostListViewModel listViewModel;
    private PostTotalAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = PostRecommendationFragmentBinding.inflate(inflater, container, false);
        context = getActivity().getApplicationContext();

        detailViewModel =new ViewModelProvider(requireActivity()).get(PostDetailViewModel.class);
        detailViewModel.setContext(context);
        listViewModel =new ViewModelProvider(requireActivity()).get(PostListViewModel.class);
        listViewModel.setContext(context);

        initLayout();

        return binding.getRoot();
    }

    public void initLayout(){
        LinearLayoutManager layoutManager=new LinearLayoutManager(context);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        binding.recyclerview.setLayoutManager(layoutManager);
        detailViewModel.getPostInfo().observe(this, new Observer<PostInfoDetail>() {
            @Override
            public void onChanged(PostInfoDetail postInfoDetail) {
                adapter=new PostTotalAdapter(context,postInfoDetail.getRecommend_psots());
                binding.recyclerview.setAdapter(adapter);
                adapter.setOnItemClickListener(new PostCategoryAdapter.OnLikeItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                        if(adapter.getLike(position)){
                            listViewModel.cancelLike(adapter.getId(position));
                            adapter.cancelLike(position);
                        }else{
                            listViewModel.postLike(adapter.getId(position));
                            adapter.setLike(position);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }
}
