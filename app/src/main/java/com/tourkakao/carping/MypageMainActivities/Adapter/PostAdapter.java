package com.tourkakao.carping.MypageMainActivities.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.tourkakao.carping.MypageMainActivities.DTO.MyCarpingPost;
import com.tourkakao.carping.MypageMainActivities.DTO.Post;
import com.tourkakao.carping.Post.DTO.PostListItem;
import com.tourkakao.carping.Post.PostInfoActivity;
import com.tourkakao.carping.R;
import com.tourkakao.carping.databinding.MypageCarpingApiListItemBinding;
import com.tourkakao.carping.databinding.MypagePostItemBinding;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder>{
    Context context;
    ArrayList<Post> items;
    int pageType;

    public class ViewHolder extends RecyclerView.ViewHolder{
        private MypagePostItemBinding binding;
        public ViewHolder(MypagePostItemBinding binding){
            super(binding.getRoot());
            this.binding=binding;
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, PostInfoActivity.class);
                    int pk=Integer.parseInt(binding.pk.getText().toString());
                    intent.putExtra("pk",pk);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
        }
        public void bind(Post item){
            binding.pk.setText(Integer.toString(item.getId()));
            Glide.with(context)
                    .load(item.getProfile())
                    .transform(new CenterCrop(), new RoundedCorners(100))
                    .into(binding.profile);
            Glide.with(context).load(item.getThumbnail()).into(binding.thumbnail);
            if(item.getPay_type()==0) {
                Glide.with(context).load(R.drawable.free_mark).into(binding.premiumImage);
            }else{
                Glide.with(context).load(R.drawable.premium_mark).into(binding.premiumImage);
            }
            binding.thumbnail.setColorFilter(Color.parseColor("#595959"), PorterDuff.Mode.MULTIPLY);
            if(pageType==0||item.isIs_approved()) {
                binding.examineText.setVisibility(View.GONE);
                Glide.with(context).load(R.drawable.right_arrow_red).into(binding.arrowImg);
            }
            if(pageType==1||!item.isIs_approved()){
                binding.arrowImg.setVisibility(View.GONE);
                binding.examineText.setVisibility(View.VISIBLE);
            }
            binding.title.setText(item.getTitle());
            binding.name.setText(item.getAuthor());
            binding.star.setText("★ "+item.getTotal_star_avg());
        }
    }

    public PostAdapter(Context context,ArrayList<Post> items,int pageType){
        this.context=context;
        this.items=items;
        this.pageType=pageType;
    }

    @NonNull
    @Override
    public PostAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PostAdapter.ViewHolder(MypagePostItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.ViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items==null?0:items.size();
    }
}
