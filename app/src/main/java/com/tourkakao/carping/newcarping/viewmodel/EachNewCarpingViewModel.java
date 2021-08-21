package com.tourkakao.carping.newcarping.viewmodel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tourkakao.carping.NetworkwithToken.TotalApiClient;
import com.tourkakao.carping.SharedPreferenceManager.SharedPreferenceManager;
import com.tourkakao.carping.newcarping.Adapter.Newcarping_Review_Adapter;
import com.tourkakao.carping.newcarping.Adapter.Newcarping_Review_Image_Adapter;
import com.tourkakao.carping.newcarping.DataClass.NewCarping;
import com.tourkakao.carping.newcarping.DataClass.Newcarping_Review;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class EachNewCarpingViewModel extends ViewModel {
    public MutableLiveData<Integer> review_cnt_num=new MutableLiveData<>();
    public MutableLiveData<String> image=new MutableLiveData<>();
    public MutableLiveData<String> title=new MutableLiveData<>();
    public MutableLiveData<String> info_review=new MutableLiveData<>();
    public MutableLiveData<Integer> views=new MutableLiveData<>();
    public MutableLiveData<Float> star1=new MutableLiveData<>();
    public MutableLiveData<Float> star2=new MutableLiveData<>();
    public MutableLiveData<Float> star3=new MutableLiveData<>();
    public MutableLiveData<Float> star4=new MutableLiveData<>();
    public MutableLiveData<Float> total_star=new MutableLiveData<>();
    public MutableLiveData<String> total_star_num=new MutableLiveData<>();
    public MutableLiveData<String> review_count=new MutableLiveData<>();
    public MutableLiveData<Integer> check_bookmark=new MutableLiveData<>();
    public MutableLiveData<String> address=new MutableLiveData<>();
    public MutableLiveData<String> info_tags=new MutableLiveData<>();
    public MutableLiveData<String> review_username=new MutableLiveData<>();
    public MutableLiveData<String> review_profile=new MutableLiveData<>();
    public MutableLiveData<Double> carpingplace_lat=new MutableLiveData<>();
    public MutableLiveData<Double> carpingplace_lon=new MutableLiveData<>();
    private Context context;
    private int pk;
    Newcarping_Review_Adapter newcarping_review_adapter;
    Newcarping_Review_Image_Adapter newcarping_review_image_adapter;
    ArrayList<Newcarping_Review> reviews=null;
    ArrayList<String> review_images=null;

    public EachNewCarpingViewModel(){
        review_cnt_num.setValue(-1);
        check_bookmark.setValue(0);
        review_count.setValue("리뷰 0");
    }
    public void setContext(Context context){
        this.context=context;
    }
    public void setPk(int pk){
        this.pk=pk;
    }
    public Newcarping_Review_Adapter setting_newcarping_review_adapter(){
        reviews=new ArrayList<>();
        newcarping_review_adapter=new Newcarping_Review_Adapter(context, reviews);
        return newcarping_review_adapter;
    }
    public Newcarping_Review_Image_Adapter setting_newcarping_review_image_adapter(){
        review_images=new ArrayList<>();
        newcarping_review_image_adapter=new Newcarping_Review_Image_Adapter(context, review_images);
        return newcarping_review_image_adapter;
    }

    public void get_newcarping_detail(){
        TotalApiClient.getApiService(context).get_each_newcarping_place_detail(pk)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        detail -> {
                            Type type=new TypeToken<NewCarping>(){}.getType();
                            String result=new Gson().toJson(detail.getData().get(0));
                            NewCarping newCarping=new Gson().fromJson(result, type);

                            title.setValue(newCarping.getTitle());
                            total_star.setValue(newCarping.getTotal_star());
                            review_cnt_num.setValue(newCarping.getReview_count());
                            check_bookmark.setValue(newCarping.getCheck_bookmark());
                            total_star_num.setValue(total_star.getValue()+" ("+review_cnt_num.getValue()+")");
                            //common data
                            carpingplace_lat.setValue(newCarping.getLatitude());
                            carpingplace_lon.setValue(newCarping.getLongitude());
                            //map & address
                            info_review.setValue(newCarping.getText());
                            image.setValue(newCarping.getImage());
                            String tags="";
                            for(int i=0; i<newCarping.getTags().size(); i++){
                                tags=tags+"#"+newCarping.getTags().get(i)+" ";
                            }
                            info_tags.setValue(tags);
                            //newcarping info
                            review_profile.setValue(SharedPreferenceManager.getInstance(context).getString("profile", ""));
                            review_username.setValue(SharedPreferenceManager.getInstance(context).getString("username", "")+"님");
                            star1.setValue(newCarping.getStar1());
                            star2.setValue(newCarping.getStar2());
                            star3.setValue(newCarping.getStar3());
                            star4.setValue(newCarping.getStar4());
                            review_count.setValue("리뷰 "+review_cnt_num.getValue());
                            //newcarping total review
                            reviews=newCarping.getReviews();
                            newcarping_review_adapter.update_Item(reviews);
                            //reviews recyclerview
                            for(int i=0; i<reviews.size(); i++){
                                review_images.add(reviews.get(i).getImage());
                            }
                            newcarping_review_image_adapter.update_Item(review_images);
                        },
                        error -> {
                            System.out.println(error);
                        }
                );

    }
}
