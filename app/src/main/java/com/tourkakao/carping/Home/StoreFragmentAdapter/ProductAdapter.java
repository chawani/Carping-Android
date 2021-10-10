package com.tourkakao.carping.Home.StoreFragmentAdapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tourkakao.carping.Home.ShareFragmentAdapter.ShareAdapter;
import com.tourkakao.carping.Home.StoreDataClass.Product;
import com.tourkakao.carping.R;
import com.tourkakao.carping.databinding.EachProductBinding;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter {
    Context context;
    EachProductBinding productBinding;
    ArrayList<Product> products;
    public ProductAdapter(Context context, ArrayList<Product> products){
        this.context=context;
        this.products=products;
    }
    public interface OnSelectItemClickListener{
        void OnSelectItemClick(View v, int pos, int pk);
    }
    private OnSelectItemClickListener mListener=null;
    public void setOnSelectItemClickListener(OnSelectItemClickListener listener){
        this.mListener=listener;
    }
    public class Product_ViewHolder extends RecyclerView.ViewHolder{
        private EachProductBinding binding;
        public Product_ViewHolder(EachProductBinding binding){
            super(binding.getRoot());
            this.binding=binding;
            binding.getRoot().setOnClickListener(v -> {
                int pos=getAdapterPosition();
                int pk=products.get(pos).getPk();
                if(pos!=RecyclerView.NO_POSITION){
                    if(mListener!=null){
                        mListener.OnSelectItemClick(v, pos, pk);
                    }
                }
            });
        }
        public void setItem(Product product){
            binding.productName.setText(product.getName());
            binding.productPrice.setText(product.getPrice());
            Glide.with(context).load(product.getImage()).into(binding.productImg);
            if(product.getPk()%2==1){
                LinearLayout.LayoutParams mlayout=(LinearLayout.LayoutParams)binding.productImg.getLayoutParams();
                mlayout.rightMargin=40;
                binding.productImg.setLayoutParams(mlayout);
            }
            Glide.with(context).load(R.drawable.to_kakaopage_img).into(binding.toKakaopageBtn);
            binding.toKakaopageBtn.setOnClickListener(v -> {
                Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("http://pf.kakao.com/_Cnxdvs"));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            });
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        productBinding=EachProductBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new Product_ViewHolder(productBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Product_ViewHolder vh=(Product_ViewHolder)holder;
        vh.setItem(products.get(position));
    }

    @Override
    public int getItemCount() {
        return products==null?0:products.size();
    }
    public void update_Item(ArrayList<Product> items){
        if(products!=null){
            products=null;
        }
        products=items;
        notifyDataSetChanged();
    }
}
