package com.tourkakao.carping.MypageMainActivities.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.tourkakao.carping.MypageMainActivities.DTO.Autocamp;
import com.tourkakao.carping.databinding.MypageCarpingApiListItemBinding;

import java.util.ArrayList;

public class ScrapAutoAdapter extends RecyclerView.Adapter<ScrapAutoAdapter.ViewHolder>{
    Context context;
    ArrayList<Autocamp> autocamps;

    public class ViewHolder extends RecyclerView.ViewHolder{
        private MypageCarpingApiListItemBinding binding;
        public ViewHolder(MypageCarpingApiListItemBinding binding){
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
        public void bind(Autocamp autocamp){
            Glide.with(context)
                    .load(autocamp.getImage1())
                    .transform(new CenterCrop(), new RoundedCorners(30))
                    .into(binding.image);
            binding.title.setText(autocamp.getTitle());
            binding.bookmarkCount.setText(autocamp.getBookmark_count());
        }
    }

    public ScrapAutoAdapter(Context context,ArrayList<Autocamp> autocamps){
        this.context=context;
        this.autocamps=autocamps;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ScrapAutoAdapter.ViewHolder(MypageCarpingApiListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(autocamps.get(position));
    }

    @Override
    public int getItemCount() {
        return autocamps==null?0:autocamps.size();
    }
}
