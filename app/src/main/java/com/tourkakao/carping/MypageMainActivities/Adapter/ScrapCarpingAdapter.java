package com.tourkakao.carping.MypageMainActivities.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.tourkakao.carping.MypageMainActivities.DTO.Campsite;
import com.tourkakao.carping.databinding.MypageCarpingListItemBinding;

import java.util.ArrayList;

public class ScrapCarpingAdapter extends RecyclerView.Adapter<ScrapCarpingAdapter.ViewHolder>{
    Context context;
    ArrayList<Campsite> campsites;

    public class ViewHolder extends RecyclerView.ViewHolder{
        private MypageCarpingListItemBinding binding;
        public ViewHolder(MypageCarpingListItemBinding binding){
            super(binding.getRoot());
            this.binding=binding;
//            binding.view.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent intent=new Intent(context, EcoCarpingDetailActivity.class);
//                    intent.putExtra("pk",binding.pk.getText().toString());
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    context.startActivity(intent);
//                }
//            });
        }
        public void bind(Campsite campsite){
            Glide.with(context)
                    .load(campsite.getImage())
                    .transform(new CenterCrop(), new RoundedCorners(30))
                    .into(binding.image);
            binding.name.setText(campsite.getName());
            binding.address.setText(campsite.getAddress());
            binding.distance.setText(campsite.getDistance());
            binding.bookmarkCount.setText(campsite.getBookmark_count());
        }
    }

    public ScrapCarpingAdapter(Context context,ArrayList<Campsite> campsites){
        this.context=context;
        this.campsites=campsites;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(MypageCarpingListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(campsites.get(position));
    }

    @Override
    public int getItemCount() {
        return campsites==null?0:campsites.size();
    }
}
