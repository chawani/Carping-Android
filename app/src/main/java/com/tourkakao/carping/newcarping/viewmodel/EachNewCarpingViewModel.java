package com.tourkakao.carping.newcarping.viewmodel;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.view.View;
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
import java.util.HashMap;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class EachNewCarpingViewModel extends ViewModel {
    public MutableLiveData<Integer> post_userpk=new MutableLiveData<>();
    public MutableLiveData<Integer> review_cnt_num=new MutableLiveData<>();
    public MutableLiveData<String> image1=new MutableLiveData<>();
    public MutableLiveData<String> image2=new MutableLiveData<>();
    public MutableLiveData<String> image3=new MutableLiveData<>();
    public MutableLiveData<String> image4=new MutableLiveData<>();
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
    public MutableLiveData<Boolean> check_bookmark=new MutableLiveData<>();
    public MutableLiveData<String> address=new MutableLiveData<>();
    public MutableLiveData<ArrayList<String>> info_tags=new MutableLiveData<>();
    public MutableLiveData<String> review_username=new MutableLiveData<>();
    public MutableLiveData<String> review_profile=new MutableLiveData<>();
    public MutableLiveData<Double> carpingplace_lat=new MutableLiveData<>();
    public MutableLiveData<Double> carpingplace_lon=new MutableLiveData<>();
    public MutableLiveData<Integer> review_send_ok=new MutableLiveData<>();
    public MutableLiveData<Integer> fix_send_ok=new MutableLiveData<>();
    public MutableLiveData<Integer> delete_review_ok=new MutableLiveData<>();
    private Context context;
    public int pk;
    public int userpk;
    Newcarping_Review_Adapter newcarping_review_adapter;
    Newcarping_Review_Image_Adapter newcarping_review_image_adapter;
    ArrayList<Newcarping_Review> reviews=null;
    ArrayList<String> review_images=null;

    public float r_star1, r_star2, r_star3, r_star4, r_totalstar;
    public String r_text=null;
    public Uri r_uri;

    public float fix_star1, fix_star2, fix_star3, fix_star4, fix_totalstar;
    public String fix_text=null;
    public Uri fix_uri=null;
    public EachNewCarpingViewModel(){
        post_userpk.setValue(-1);
        review_cnt_num.setValue(-1);
        my_review_cnt.setValue(0);
        check_bookmark.setValue(false);
        review_count.setValue("리뷰 0");
        r_star1=0; r_star2=0; r_star3=0; r_star4=0; r_totalstar=0;
        review_send_ok.setValue(0);
        fix_send_ok.setValue(0);
        delete_review_ok.setValue(0);
    }
    public void setContext(Context context){
        this.context=context;
    }
    public void setPk(int pk){
        this.pk=pk;
    }
    public void setUserpk(int userpk) {
        this.userpk = userpk;
    }

    public Newcarping_Review_Adapter setting_newcarping_review_adapter(){
        reviews=new ArrayList<>();
        newcarping_review_adapter=new Newcarping_Review_Adapter(context, reviews, userpk, pk);
        newcarping_review_adapter.setOnSelectItemClickListener(new Newcarping_Review_Adapter.OnSelectItemClickListener() {
            @Override
            public void OnSelectItemClick(View v, int pos, int pk) {
                deleting_review(pk, pos);
            }
        });
        return newcarping_review_adapter;
    }
    public Newcarping_Review_Image_Adapter setting_newcarping_review_image_adapter(){
        newcarping_review_image_adapter=new Newcarping_Review_Image_Adapter(context, review_images);
        return newcarping_review_image_adapter;
    }

    public void get_newcarping_detail(){
        TotalApiClient.getApiService(context).get_each_newcarping_place_detail(pk)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        detail -> {
                            if(detail.isSuccess()) {
                                if (detail.getData() != null) {
                                    Type type = new TypeToken<NewCarping>() {
                                    }.getType();
                                    String result = new Gson().toJson(detail.getData().get(0));
                                    NewCarping newCarping = new Gson().fromJson(result, type);

                                    pk=newCarping.getId();
                                    title.setValue(newCarping.getTitle());
                                    total_star.setValue(newCarping.getTotal_star());
                                    review_cnt_num.setValue(newCarping.getReview_count());
                                    check_bookmark.setValue(newCarping.isCheck_bookmark());
                                    total_star_num.setValue(total_star.getValue() + " (" + review_cnt_num.getValue() + ")");
                                    if(userpk==newCarping.getUser()){
                                        post_userpk.setValue(1);
                                    }else{
                                        post_userpk.setValue(0);
                                    }
                                    //common data
                                    carpingplace_lat.setValue(newCarping.getLatitude());
                                    carpingplace_lon.setValue(newCarping.getLongitude());
                                    //map & address
                                    info_review.setValue(newCarping.getText());
                                    image1.setValue(newCarping.getImage1());
                                    image2.setValue(newCarping.getImage2());
                                    image3.setValue(newCarping.getImage3());
                                    image4.setValue(newCarping.getImage4());
                                    if(info_tags.getValue()!=null){
                                        info_tags.getValue().clear();
                                    }
                                    info_tags.setValue(newCarping.getTags());
                                    //newcarping info
                                    review_profile.setValue(SharedPreferenceManager.getInstance(context).getString("profile", ""));
                                    review_username.setValue(SharedPreferenceManager.getInstance(context).getString("username", "") + "님");
                                    star1.setValue(newCarping.getStar1());
                                    star2.setValue(newCarping.getStar2());
                                    star3.setValue(newCarping.getStar3());
                                    star4.setValue(newCarping.getStar4());
                                    my_star_avg.setValue(newCarping.getMy_star_avg());
                                    my_review_cnt.setValue(newCarping.getMy_review_cnt());
                                    review_count.setValue("리뷰 " + review_cnt_num.getValue());
                                    //newcarping total review
                                    reviews = newCarping.getReviews();
                                    newcarping_review_adapter.update_Item(reviews);
                                    //reviews recyclerview
                                    if (review_images != null) {
                                        review_images.clear();
                                        review_images = null;
                                    }
                                    review_images = new ArrayList<>();
                                    for (int i = 0; i < reviews.size(); i++) {
                                        review_images.add(reviews.get(i).getImage());
                                    }
                                    newcarping_review_image_adapter.update_Item(review_images);
                                }
                            }else{
                                System.out.println(detail.getError_message());
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
                            if(result.isSuccess()) {
                                check_bookmark.setValue(true);
                            }else{
                                System.out.println(result.getError_message());
                            }
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
                            if(result.isSuccess()) {
                                check_bookmark.setValue(false);
                            }else{
                                System.out.println(result.getError_message());
                            }
                        },
                        error -> {

                        }
                );
    }

    public void sending_newcarping_review(){
        String path=getPath(r_uri);
        File file=new File(path);
        RequestBody requestBody=RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part image=MultipartBody.Part.createFormData("image", "review.jpg", requestBody);
        int userpk=SharedPreferenceManager.getInstance(context).getInt("id", 0);
        //Newcarping_Review_post post=new Newcarping_Review_post(userpk, pk, r_text, r_star1, r_star2, r_star3, r_star4, r_totalstar);
        HashMap<String, RequestBody> map=new HashMap<>();
        RequestBody rtext=RequestBody.create(MediaType.parse("text/plain"), r_text);
        map.put("text", rtext);
        TotalApiClient.getApiService(context).send_newcarping_review(image, map, userpk, pk, r_star1, r_star2, r_star3, r_star4, r_totalstar)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        res -> {
                            if(res.isSuccess()) {
                                review_send_ok.setValue(1);
                            }else{
                                System.out.println(res.getError_message());
                                review_send_ok.setValue(-1);
                            }
                        },
                        error -> {
                        }
                );
    }
    public void fixing_newcarping_review(int id, int upk, int postpk){
        String path;
        File file;
        RequestBody requestBody;
        MultipartBody.Part image;
        if(fix_uri!=null) {
            path=getPath(fix_uri);
            file=new File(path);
            requestBody=RequestBody.create(MediaType.parse("multipart/form-data"), file);
            image=MultipartBody.Part.createFormData("image", "review.jpg", requestBody);
        }else{
            image=null;
        }
        HashMap<String, RequestBody> map=new HashMap<>();
        RequestBody ftext=RequestBody.create(MediaType.parse("text/plain"), fix_text);
        map.put("text", ftext);
        if(image!=null) {
            TotalApiClient.getApiService(context).change_newcarping_review(id, image, map, upk, postpk, fix_star1, fix_star2, fix_star3, fix_star4, fix_totalstar)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            res -> {
                                if (res.isSuccess()) {
                                    fix_send_ok.setValue(1);
                                } else {
                                    System.out.println(res.getError_message());
                                    fix_send_ok.setValue(-1);
                                }
                            },
                            error -> {
                            }
                    );
        }else{
            TotalApiClient.getApiService(context).change_newcarping_review_no_image(id, map, upk, postpk, fix_star1, fix_star2, fix_star3, fix_star4, fix_totalstar)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            res -> {
                                if (res.isSuccess()) {
                                    fix_send_ok.setValue(1);
                                } else {
                                    System.out.println(res.getError_message());
                                    fix_send_ok.setValue(-1);
                                }
                            },
                            error -> {
                            }
                    );
        }
    }
    public void deleting_review(int id, int pos){
        TotalApiClient.getApiService(context).delete_newcarping_review(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        res -> {
                            if(res.isSuccess()){
                                delete_review_ok.setValue(1);
                                Toast.makeText(context, "리뷰가 삭제되었어요", Toast.LENGTH_SHORT).show();
                                reviews.remove(pos);
                                newcarping_review_adapter.update_Item(reviews);
                                delete_review_ok.setValue(0);
                            }
                        },
                        error -> {}
                );
    }
    public String getPath(Uri uri){
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
// DocumentProvider
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
// ExternalStorageProvider
                if (isExternalStorageDocument(uri)) {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];
                    if ("primary".equalsIgnoreCase(type)) {
                        return Environment.getExternalStorageDirectory() + "/" + split[1];
                    }
                }
// DownloadsProvider
                else if (isDownloadsDocument(uri)) {
                    final String id = DocumentsContract.getDocumentId(uri);
                    final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                    return getDataColumn(context, contentUri, null, null);
                }
// MediaProvider
                else if (isMediaDocument(uri)) {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];
                    Uri contentUri = null;
                    if ("image".equals(type)) {
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    } else if ("video".equals(type)) {
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                    } else if ("audio".equals(type)) {
                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                    }
                    final String selection = "_id=?";
                    final String[] selectionArgs = new String[]{
                            split[1]
                    };
                    return getDataColumn(context, contentUri, selection, selectionArgs);
                }
            }
// MediaStore (and general)
            else if ("content".equalsIgnoreCase(uri.getScheme())) {
                return getDataColumn(context, uri, null, null);
            }
// File
            else if ("file".equalsIgnoreCase(uri.getScheme())) {
                return uri.getPath();
            }
        }
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }
}
