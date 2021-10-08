package com.tourkakao.carping.Home.CommunityViewModel;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tourkakao.carping.Home.StoreDataClass.Product;
import com.tourkakao.carping.Home.StoreFragmentAdapter.ProductAdapter;
import com.tourkakao.carping.NetworkwithToken.TotalApiClient;
import com.tourkakao.carping.store.Activity.StoreDetailActivity;

import java.lang.reflect.Type;
import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class StoreViewModel extends ViewModel {
    private Context context;
    ProductAdapter productAdapter;
    ArrayList<Product> products=null;
    public StoreViewModel(){

    }
    public void setContext(Context context){
        this.context=context;
    }
    public ProductAdapter setting_product_adapter(){
        products=new ArrayList<>();
        productAdapter=new ProductAdapter(context, products);
        productAdapter.setOnSelectItemClickListener(new ProductAdapter.OnSelectItemClickListener() {
            @Override
            public void OnSelectItemClick(View v, int pos, int pk) {
                Intent intent=new Intent(context, StoreDetailActivity.class);
                intent.putExtra("image", products.get(pos).getImage());
                intent.putExtra("price", products.get(pos).getPrice());
                intent.putExtra("name", products.get(pos).getName());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        return productAdapter;
    }
    public void get_product(){
        TotalApiClient.getCommunityApiService(context).get_product()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        lists -> {
                            if(lists.isSuccess()){
                                Type type=new TypeToken<ArrayList<Product>>(){}.getType();
                                String result=new Gson().toJson(lists.getData());
                                products=new Gson().fromJson(result, type);
                                productAdapter.update_Item(products);
                            }else{
                                System.out.println(lists.getError_message());
                            }
                        },
                        error -> {

                        }
                );
    }
}
