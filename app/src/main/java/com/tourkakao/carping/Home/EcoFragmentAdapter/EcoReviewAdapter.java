package com.tourkakao.carping.Home.EcoFragmentAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.tourkakao.carping.Home.EcoDataClass.EcoReview;
import com.tourkakao.carping.databinding.EcoReviewListItemBinding;

import java.util.ArrayList;

public class EcoReviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    ArrayList<EcoReview> ecoReviews;
    EcoReviewListItemBinding ecoReviewListItemBinding;
    public EcoReviewAdapter(Context context, ArrayList<EcoReview> ecoReviews){
        this.context=context;
        this.ecoReviews = ecoReviews;
    }

    public class EcoReviewViewHoler extends RecyclerView.ViewHolder{
        private EcoReviewListItemBinding binding;
        public EcoReviewViewHoler(EcoReviewListItemBinding binding){
            super(binding.getRoot());
            this.binding=binding;
        }
        public void bind(EcoReview ecoReview){
            Glide.with(context)
                    .load(ecoReview.getImage())
                    .transform(new CenterCrop(), new RoundedCorners(30))
                    .into(binding.image);
            binding.title.setText(ecoReview.getTitle());
            binding.content.setText(ecoReview.getText());
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ecoReviewListItemBinding= ecoReviewListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new EcoReviewViewHoler(ecoReviewListItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        EcoReviewViewHoler vh=(EcoReviewAdapter.EcoReviewViewHoler)holder;
        vh.bind(ecoReviews.get(position));
    }

    @Override
    public int getItemCount() {
        return ecoReviews ==null?0: ecoReviews.size();
    }
}
