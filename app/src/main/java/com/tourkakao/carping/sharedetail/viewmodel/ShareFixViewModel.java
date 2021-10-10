package com.tourkakao.carping.sharedetail.viewmodel;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.tourkakao.carping.Loading.CustomLoadingDialog;
import com.tourkakao.carping.NetworkwithToken.TotalApiClient;
import com.tourkakao.carping.sharedetail.DataClass.ShareDetail;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ShareFixViewModel extends ViewModel {
    Context context;
    int userpk;
    int postpk;
    public int suser, slocate_id;
    public String susername, sprofile, sregion, simage1, simage2, simage3, simage4;
    public String stitle, stext, schat_addr;
    public ArrayList<String> stags;
    public MutableLiveData<Integer> get_clear=new MutableLiveData<>();
    public ArrayList<String> initial_images;
    public ArrayList<String> f_images;
    public ArrayList<Uri> f_uri;
    ArrayList<Integer> is_null;
    File file, file2, file3, file4;
    RequestBody body1, body2, body3, body4;
    MultipartBody.Part image1, image2, image3, image4;
    public MutableLiveData<Integer> share_edit_ok=new MutableLiveData<>();
    public ShareFixViewModel(){
        get_clear.setValue(0);
        is_null=new ArrayList<>();
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setPostpk(int postpk) {
        this.postpk = postpk;
    }


    public void getting_share(){
        TotalApiClient.getCommunityApiService(context).get_share_detail(postpk)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        res -> {
                            if(res.isSuccess()){
                                String result=new Gson().toJson(res.getData().get(0));
                                ShareDetail shareDetail=new Gson().fromJson(result, ShareDetail.class);
                                sregion=shareDetail.getRegion();
                                susername=shareDetail.getUsername();
                                suser=shareDetail.getUser();
                                sprofile=shareDetail.getProfile();
                                simage1=shareDetail.getImage1();
                                simage2=shareDetail.getImage2();
                                simage3=shareDetail.getImage3();
                                simage4=shareDetail.getImage4();
                                stitle=shareDetail.getTitle();
                                stext=shareDetail.getText();
                                schat_addr=shareDetail.getChat_addr();
                                stags=new ArrayList<>();
                                for(int i=0; i<shareDetail.getTags().size(); i++){
                                    stags.add(shareDetail.getTags().get(i));
                                }
                                f_uri=new ArrayList<>();
                                for(int i=0; i<4; i++){
                                    f_uri.add(i, null);
                                }
                                initial_images=new ArrayList<>();
                                f_images=new ArrayList<>();
                                f_images.add(simage1);
                                f_images.add(simage2);
                                f_images.add(simage3);
                                f_images.add(simage4);
                                initial_images.add(simage1);
                                initial_images.add(simage2);
                                initial_images.add(simage3);
                                initial_images.add(simage4);
                                get_clear.setValue(1);
                            }
                        },
                        error -> {}
                );
    }
    public void edit_share(boolean locate){
        CustomLoadingDialog.getInstance(context, "수정중입니다..").show();
        if(f_uri.get(0)!=null) {
            file = new File(getPath(f_uri.get(0)));
            body1 = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            image1=MultipartBody.Part.createFormData("image1", "share1.png", body1);
        }else{
            image1=null;
        }
        if(f_uri.get(1)!=null) {
            file2 = new File(getPath(f_uri.get(1)));
            body2 = RequestBody.create(MediaType.parse("multipart/form-data"), file2);
            image2=MultipartBody.Part.createFormData("image2", "share2.png", body2);
        }else{
            image2=null;
        }
        if(f_uri.get(2)!=null) {
            file3 = new File(getPath(f_uri.get(2)));
            body3 = RequestBody.create(MediaType.parse("multipart/form-data"), file3);
            image3=MultipartBody.Part.createFormData("image3", "share3.png", body3);
        }else{
            image3=null;
        }
        if(f_uri.get(3)!=null) {
            file4 = new File(getPath(f_uri.get(3)));
            body4 = RequestBody.create(MediaType.parse("multipart/form-data"), file4);
            image4=MultipartBody.Part.createFormData("image4", "share4.png", body4);
        }else{
            image4=null;
        }
        String tagString="[";
        for(int i=0; i<stags.size(); i++){
            String tag='"'+stags.get(i)+'"';
            tagString=tagString+tag;
            if(i!=stags.size()-1){
                tagString=tagString+",";
            }
        }
        tagString=tagString+"]";
        HashMap<String, RequestBody> map=new HashMap<>();
        RequestBody text=RequestBody.create(MediaType.parse("text/plain"), stext);
        RequestBody title=RequestBody.create(MediaType.parse("text/plain"), stitle);
        RequestBody user=RequestBody.create(MediaType.parse("text/plain"), Integer.toString(suser));
        RequestBody tags=RequestBody.create(MediaType.parse("text/plain"), tagString);
        RequestBody region=RequestBody.create(MediaType.parse("text/plain"), Integer.toString(slocate_id));
        map.put("user", user);
        map.put("title", title);
        map.put("text", text);
        map.put("tags", tags);
        if(locate==true){
            map.put("region", region);
            System.out.println(slocate_id);
        }
        for(int i=0; i<4; i++){
            if(initial_images.get(i)!=null) {
                if (!initial_images.get(i).equals(f_images.get(i))) {
                    is_null.add(i + 1);
                    System.out.println(i+1+"---------------"+"delete");
                }
            }
        }
        System.out.println(image1+" "+image2+" "+image3+" "+image4);
        System.out.println(is_null);
        TotalApiClient.getCommunityApiService(context).edit_share(postpk, image1, image2, image3, image4, map, is_null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        res -> {
                            if(res.isSuccess()){
                                share_edit_ok.setValue(1);
                                CustomLoadingDialog.getInstance(context, null).dismiss();
                            }else{
                                share_edit_ok.setValue(-1);
                            }
                        },
                        error -> {
                            System.out.println(error);
                        }
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
