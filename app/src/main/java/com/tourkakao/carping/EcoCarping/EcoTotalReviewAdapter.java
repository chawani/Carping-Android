package com.tourkakao.carping.EcoCarping;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.tourkakao.carping.Home.EcoDataClass.EcoReview;
import com.tourkakao.carping.R;
import com.tourkakao.carping.databinding.EcoCarpingTotalListItemBinding;
import com.tourkakao.carping.databinding.EcoReviewListItemBinding;

import java.util.ArrayList;
import java.util.List;



public class EcoTotalReviewAdapter extends RecyclerView.Adapter<EcoTotalReviewAdapter.ViewHolder>{
    Context context;
    ArrayList<EcoReview> ecoReviews;

    public class ViewHolder extends RecyclerView.ViewHolder{
        private EcoCarpingTotalListItemBinding binding;
        public ViewHolder(EcoCarpingTotalListItemBinding binding){
            super(binding.getRoot());
            this.binding=binding;
            binding.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(context,EcoCarpingDetailActivity.class);
                    intent.putExtra("pk",binding.pk.getText().toString());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
        }
        public void bind(EcoReview ecoReview){
            Glide.with(context).load(R.drawable.eco_certification_mark).into(binding.ecoCertification);
            Glide.with(context)
                    .load(ecoReview.getImage())
                    .transform(new CenterCrop(), new RoundedCorners(30))
                    .into(binding.image);
            binding.pk.setText(ecoReview.getId());
            binding.title.setText(ecoReview.getTitle());
            binding.content.setText(ecoReview.getText());
            binding.username.setText(ecoReview.getUser());
            binding.date.setText(ecoReview.getCreated_at());
        }
    }

    public EcoTotalReviewAdapter(Context context,ArrayList<EcoReview> ecoReviews){
        this.context=context;
        this.ecoReviews=ecoReviews;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(EcoCarpingTotalListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(ecoReviews.get(position));
    }

    @Override
    public int getItemCount() {
        return ecoReviews==null?0:ecoReviews.size();
    }
}
