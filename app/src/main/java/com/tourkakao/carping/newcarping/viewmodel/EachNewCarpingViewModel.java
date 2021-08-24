package com.tourkakao.carping.newcarping.viewmodel;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tourkakao.carping.NetworkwithToken.TotalApiClient;
import com.tourkakao.carping.SharedPreferenceManager.SharedPreferenceManager;
import com.tourkakao.carping.newcarping.Activity.Each_NewCarpingActivity;
import com.tourkakao.carping.newcarping.Adapter.Newcarping_Review_Adapter;
import com.tourkakao.carping.newcarping.Adapter.Newcarping_Review_Image_Adapter;
import com.tourkakao.carping.newcarping.DataClass.NewCarping;
import com.tourkakao.carping.newcarping.DataClass.Newcarping_Review;
import com.tourkakao.carping.newcarping.DataClass.Newcarping_Review_post;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class EachNewCarpingViewModel extends ViewModel {
    public MutableLiveData<Integer> review_cnt_num=new MutableLiveData<>();
    public MutableLiveData<String> image=new MutableLiveData<>();
    public MutableLiveData<String> title=new MutableLiveData<>();
    public MutableLiveData<String> info_review=new MutableLiveData<>();
    public MutableLiveData<Float> star1=new MutableLiveData<>();
    public MutableLiveData<Float> star2=new MutableLiveData<>();
    public MutableLiveData<Float> star3=new MutableLiveData<>();
    public MutableLiveData<Float> star4=new MutableLiveData<>();
    public MutableLiveData<Float> total_star=new MutableLiveData<>();
    public MutableLiveData<String> total_star_num=new MutableLiveData<>();
    public MutableLiveData<Integer> my_review_cnt=new MutableLiveData<>();
    public MutableLiveData<Float> my_star_avg=new MutableLiveData<>();
    public MutableLiveData<String> review_count=new MutableLiveData<>();
    public MutableLiveData<Integer> check_bookmark=new MutableLiveData<>();
    public MutableLiveData<String> address=new MutableLiveData<>();
    public MutableLiveData<String> info_tags=new MutableLiveData<>();
    public MutableLiveData<String> review_username=new MutableLiveData<>();
    public MutableLiveData<String> review_profile=new MutableLiveData<>();
    public MutableLiveData<Double> carpingplace_lat=new MutableLiveData<>();
    public MutableLiveData<Double> carpingplace_lon=new MutableLiveData<>();
    public MutableLiveData<Integer> review_send_ok=new MutableLiveData<>();
    private Context context;
    public int pk;
    Newcarping_Review_Adapter newcarping_review_adapter;
    Newcarping_Review_Image_Adapter newcarping_review_image_adapter;
    ArrayList<Newcarping_Review> reviews=null;
    ArrayList<String> review_images=null;

    public float r_star1, r_star2, r_star3, r_star4, r_totalstar;
    public String r_text=null;
    public Uri r_uri;

    public int g_id, g_user, g_like_count, g_check_like;
    public String g_username, g_review_profile, g_text, g_image, g_created_at;
    public float g_star1, g_star2, g_star3, g_star4, g_total_star;
    public EachNewCarpingViewModel(){
        review_cnt_num.setValue(-1);
        my_review_cnt.setValue(0);
        check_bookmark.setValue(0);
        review_count.setValue("리뷰 0");
        r_star1=0; r_star2=0; r_star3=0; r_star4=0; r_totalstar=0;
        review_send_ok.setValue(0);
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
                            if(detail.getData()!=null){
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
                                my_star_avg.setValue(newCarping.getMy_star_avg());
                                my_review_cnt.setValue(newCarping.getMy_review_cnt());
                                review_count.setValue("리뷰 "+review_cnt_num.getValue());
                                //newcarping total review
                                reviews=newCarping.getReviews();
                                newcarping_review_adapter.update_Item(reviews);
                                //reviews recyclerview
                                for(int i=0; i<reviews.size(); i++){
                                    review_images.add(reviews.get(i).getImage());
                                }
                                newcarping_review_image_adapter.update_Item(review_images);
                            }
                        },
                        error -> {
                            System.out.println(error);
                        }
                );

    }

    public void setting_newcarping_bookmark(){
        TotalApiClient.getApiService(context).set_bookmark(pk)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        result -> {
                            check_bookmark.setValue(1);
                            System.out.println(result.getData().get(0));
                        },
                        error -> {

                        }
                );
    }
    public void releasing_newcarping_bookmark(){
        TotalApiClient.getApiService(context).release_bookmark(pk)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        result -> {
                            check_bookmark.setValue(0);
                            System.out.println(result.getData().get(0));
                        },
                        error -> {

                        }
                );
    }

    public void sending_newcarping_review(){
        String path=getPath(r_uri);
        File file=new File(path);
        System.out.println(r_text);
        RequestBody requestBody=RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part image=MultipartBody.Part.createFormData("image", "review.jpg", requestBody);
        int userpk=SharedPreferenceManager.getInstance(context).getInt("id", 0);
        TotalApiClient.getApiService(context).send_newcarping_review(image, userpk, pk, r_text, r_star1, r_star2, r_star3, r_star4, r_totalstar)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        res -> {
                            Type type=new TypeToken<Newcarping_Review>(){}.getType();
                            String result=new Gson().toJson(res.getData().get(0));
                            Newcarping_Review newcarping_review=new Gson().fromJson(result, type);
                            g_id=newcarping_review.getId(); g_user=newcarping_review.getUser();
                            g_username=newcarping_review.getUsername(); g_review_profile=newcarping_review.getReview_profile();
                            g_text=newcarping_review.getText(); g_image=newcarping_review.getImage();
                            g_star1=newcarping_review.getStar1(); g_star2=newcarping_review.getStar2();
                            g_star3=newcarping_review.getStar3(); g_star4=newcarping_review.getStar4(); g_total_star=newcarping_review.getTotal_star();
                            g_created_at=newcarping_review.getCreated_at(); g_like_count=newcarping_review.getLike_count(); g_check_like=newcarping_review.getCheck_like();
                            review_send_ok.setValue(1);
                        },
                        error -> {
                            review_send_ok.setValue(-1);
                        }
                );
    }
    public String getPath(Uri uri){
        String filepath;
        Cursor cursor=context.getContentResolver().query(uri, null, null, null, null);
        if(cursor==null){
            filepath=uri.getPath();
        }else{
            cursor.moveToFirst();
            int idx=cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            filepath=cursor.getString(idx);
            cursor.close();
        }
        return filepath;
    }
    public void update_review(Newcarping_Review review){
        reviews.add(0, review);
        newcarping_review_adapter.update_Item(reviews);
    }
    public void update_review_image(String image){
        review_images.add(0, image);
        newcarping_review_image_adapter.update_Item(review_images);
    }
}
