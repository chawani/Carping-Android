package com.tourkakao.carping.Home.HomeViewModel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.tourkakao.carping.Mypage.Profile;
import com.tourkakao.carping.NetworkwithToken.CommonClass;
import com.tourkakao.carping.NetworkwithToken.TotalApiClient;
import com.tourkakao.carping.SharedPreferenceManager.SharedPreferenceManager;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MypageViewModel extends ViewModel {
    private Context context;
    private Profile profile;
    private MutableLiveData<Profile> profileMutableLiveData=new MutableLiveData<>();

    public void setContext(Context context) {
        this.context = context;
    }
    public MutableLiveData<Profile> getProfileMutableLiveData(){return profileMutableLiveData;}

    public void setMyProfile(List data){
        Gson gson=new Gson();
        String totalString=gson.toJson(data.get(0));
        System.out.println(totalString);
        profile=gson.fromJson(totalString,Profile.class);
        profileMutableLiveData.setValue(profile);
    }

    public void loadProfile(){
        String userId= SharedPreferenceManager.getInstance(context).getString("id","");
        TotalApiClient.getMypageApiService(context).getMypageInfo(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<CommonClass>() {
                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull CommonClass commonClass) {
                        if(commonClass.getCode()==200) {
                            setMyProfile(commonClass.getData());
                        }
                        else {
                            System.out.println("요청실패:"+commonClass.getCode()+commonClass.getError_message());
                        }
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }
                });
    }
}
