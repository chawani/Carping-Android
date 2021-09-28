package com.tourkakao.carping.Post.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.tourkakao.carping.MypageMainActivities.MypageViewModel.MypageCarpingViewModel;
import com.tourkakao.carping.Post.DTO.PostInfoDetail;
import com.tourkakao.carping.Post.ViewModel.PostDetailViewModel;
import com.tourkakao.carping.R;
import com.tourkakao.carping.databinding.MypageCarpingScrapTabFragmentBinding;
import com.tourkakao.carping.databinding.PostIntroduceFragmentBinding;

public class IntroduceFragment extends Fragment {
    private PostIntroduceFragmentBinding binding;
    private Context context;
    private PostDetailViewModel viewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = PostIntroduceFragmentBinding.inflate(inflater, container, false);
        context = getActivity().getApplicationContext();

        viewModel =new ViewModelProvider(requireActivity()).get(PostDetailViewModel.class);
        viewModel.setContext(context);

        initLayout();
        settingInfo();

        return binding.getRoot();
    }

    public void initLayout(){
        Glide.with(context).load(R.drawable.posting_icon).into(binding.postingImg);
        Glide.with(context).load(R.drawable.contents_icon).into(binding.contentsNumberImg);
        Glide.with(context).load(R.drawable.like_icon_purple).into(binding.likeImg);
    }

    public void settingInfo(){
        viewModel.getPostInfo().observe(this, new Observer<PostInfoDetail>() {
            @Override
            public void onChanged(PostInfoDetail postInfoDetail) {
                //목차,찜
                if(postInfoDetail.getPreview_image1()!=null) {
                    ImageView imageView = new ImageView(context);
                    Glide.with(context).load(postInfoDetail.getPreview_image1()).into(imageView);
                    binding.imageArea.addView(imageView);
                }
                if(postInfoDetail.getPreview_image2()!=null) {
                    ImageView imageView = new ImageView(context);
                    Glide.with(context).load(postInfoDetail.getPreview_image1()).into(imageView);
                    binding.imageArea.addView(imageView);
                }
                if(postInfoDetail.getPreview_image3()!=null) {
                    ImageView imageView = new ImageView(context);
                    Glide.with(context).load(postInfoDetail.getPreview_image1()).into(imageView);
                    binding.imageArea.addView(imageView);
                }
                binding.postIntroduce.setText(postInfoDetail.getInfo());
                binding.recommendText.setText(postInfoDetail.getRecommend_to());
                //작가의 한마디
            }
        });
    }
}