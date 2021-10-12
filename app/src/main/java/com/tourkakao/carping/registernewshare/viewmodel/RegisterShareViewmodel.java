package com.tourkakao.carping.registernewshare.viewmodel;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.JsonParser;
import com.tourkakao.carping.Loading.CustomLoadingDialog;
import com.tourkakao.carping.NetworkwithToken.TotalApiClient;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class RegisterShareViewmodel extends ViewModel {
    Context context;
    int userpk;
    public String s_locate;
    public int s_locate_id;
    public String s_title;
    public String s_body;
    public String s_chat;
    public ArrayList<String> s_tags;
    public ArrayList<Uri> s_uri;
    File file, file2, file3, file4;
    RequestBody body1, body2, body3, body4;
    MultipartBody.Part image1, image2, image3, image4;
    public MutableLiveData<Integer> send_ok=new MutableLiveData<>();
    public RegisterShareViewmodel(){
        send_ok.setValue(-1);
        s_tags=new ArrayList<>();
        s_uri=new ArrayList<>();
        for(int i=0; i<4; i++){
            s_uri.add(null);
        }
        s_title=null;
        s_body=null;
        s_chat=null;
        s_locate=null;
        s_locate_id=-1;
    }
    public void setContext(Context context){
        this.context=context;
    }
    public void setUserpk(int userpk){
        this.userpk=userpk;
    }
    public void sending_newshare(){
        CustomLoadingDialog.getInstance(context, "등록 중입니다..").show();
        if(s_uri.get(0)!=null) {
            file = new File(getPath(s_uri.get(0)));
            body1 = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            image1=MultipartBody.Part.createFormData("image1", "newcarping1.png", body1);
        }else{
            image1=null;
        }
        if(s_uri.get(1)!=null) {
            file2 = new File(getPath(s_uri.get(1)));
            body2 = RequestBody.create(MediaType.parse("multipart/form-data"), file2);
            image2=MultipartBody.Part.createFormData("image2", "newcarping2.png", body2);
        }else{
            image2=null;
        }
        if(s_uri.get(2)!=null) {
            file3 = new File(getPath(s_uri.get(2)));
            body3 = RequestBody.create(MediaType.parse("multipart/form-data"), file3);
            image3=MultipartBody.Part.createFormData("image3", "newcarping3.png", body3);
        }else{
            image3=null;
        }
        if(s_uri.get(3)!=null) {
            file4 = new File(getPath(s_uri.get(3)));
            body4 = RequestBody.create(MediaType.parse("multipart/form-data"), file4);
            image4=MultipartBody.Part.createFormData("image4", "newcarping4.png", body4);
        }else{
            image4=null;
        }
        String tagString="[";
        for(int i=0; i<s_tags.size(); i++){
            String tag='"'+s_tags.get(i)+'"';
            tagString=tagString+tag;
            if(i!=s_tags.size()-1){
                tagString=tagString+",";
            }
        }
        tagString=tagString+"]";
        HashMap<String, RequestBody> map=new HashMap<>();
        RequestBody text=RequestBody.create(MediaType.parse("text/plain"), s_body);
        RequestBody title=RequestBody.create(MediaType.parse("text/plain"), s_title);
        RequestBody user=RequestBody.create(MediaType.parse("text/plain"), Integer.toString(userpk));
        RequestBody chat=RequestBody.create(MediaType.parse("text/plain"), s_chat);
        RequestBody region=RequestBody.create(MediaType.parse("text/plain"), Integer.toString(s_locate_id));
        RequestBody tags=RequestBody.create(MediaType.parse("text/plain"), tagString);
        map.put("user", user);
        map.put("title", title);
        map.put("text", text);
        map.put("chat_addr", chat);
        map.put("region", region);
        map.put("tags", tags);
        System.out.println(s_chat);
        TotalApiClient.getCommunityApiService(context).send_newshare(image1, image2, image3, image4, map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        lists -> {
                            if(lists.isSuccess()){
                                send_ok.setValue(1);
                                CustomLoadingDialog.getInstance(context, null).dismiss();
                            }else{
                                send_ok.setValue(-1);
                                if(lists.getError_message().contains("chat_addr")){
                                    CustomLoadingDialog.getInstance(context, null).dismiss();
                                    Toast.makeText(context, "주소 형식에 맞게 작성해주세요!", Toast.LENGTH_SHORT).show();
                                };
                            }
                        },
                        error -> {

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
