package com.tourkakao.carping.Post.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.tourkakao.carping.Post.DTO.PostInfoDetail;
import com.tourkakao.carping.Post.ViewModel.PostDetailViewModel;
import com.tourkakao.carping.R;
import com.tourkakao.carping.databinding.PostIntroduceFragmentBinding;
import com.tourkakao.carping.databinding.PostQuestionFragmentBinding;

public class QuestionFragment extends Fragment {
    private PostQuestionFragmentBinding binding;
    private Context context;
    private String openchat;
    private PostDetailViewModel viewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = PostQuestionFragmentBinding.inflate(inflater, container, false);
        context = getActivity().getApplicationContext();

        viewModel =new ViewModelProvider(requireActivity()).get(PostDetailViewModel.class);
        viewModel.setContext(context);

        initLayout();
        settingInfo();

        return binding.getRoot();
    }

    public void initLayout(){
        Glide.with(context).load(R.drawable.question_button).into(binding.questionButton);
    }

    void settingInfo(){
        viewModel.getPostInfo().observe(this, new Observer<PostInfoDetail>() {
            @Override
            public void onChanged(PostInfoDetail postInfoDetail) {
                openchat=postInfoDetail.getKakao_openchat_url();
            }
        });
        binding.questionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(openchat));
                startActivity(intent);
            }
        });
    }
}
