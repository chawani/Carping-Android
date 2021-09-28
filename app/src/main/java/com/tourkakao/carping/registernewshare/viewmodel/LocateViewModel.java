package com.tourkakao.carping.registernewshare.viewmodel;

import android.content.Context;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tourkakao.carping.NetworkwithToken.TotalApiClient;
import com.tourkakao.carping.registernewshare.Adapter.LocateAdapter;
import com.tourkakao.carping.registernewshare.Dataclass.Dong;
import com.tourkakao.carping.registernewshare.Dataclass.Sigungu;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LocateViewModel extends ViewModel {
    Context context;
    ArrayList<String> locates;
    ArrayList<String> searchlist;
    ArrayList<Integer> dong;
    ArrayList<Sigungu> sigungus;
    ArrayList<Dong> dongs;
    LocateAdapter locateAdapter;
    String search;
    public String first=null, second=null, third=null;
    public MutableLiveData<String> title=new MutableLiveData<>();
    public MutableLiveData<String> searchhint=new MutableLiveData<>();
    public MutableLiveData<String> searchnull=new MutableLiveData<>();
    public MutableLiveData<Integer> dong_id=new MutableLiveData<>();
    public LocateViewModel(){
        search=null;
        searchlist=new ArrayList<>();
        title.setValue("시도 선택");
        dong_id.setValue(-1);
        searchhint.setValue("위치를 검색해주세요(시도)");
        searchnull.setValue(null);
    }
    public void setContext(Context context){
        this.context=context;
    }
    public LocateAdapter setting_locate_adapter(){
        locates=new ArrayList<>(Arrays.asList("서울특별시", "세종특별자치시", "제주특별자치도", "인천광역시", "대전광역시", "대구광역시",
                "부산광역시", "울산광역시", "광주광역시", "강원도", "경기도", "경상남도", "경상북도", "전라남도",
                "전라북도", "충청남도", "충청북도"));
        locateAdapter=new LocateAdapter(context, locates);
        locateAdapter.setOnSelectItemClickListener(new LocateAdapter.OnSelectItemClickListener() {
            @Override
            public void OnSelectItemClick(View v, int pos) {
                if(first==null){
                    if(search!=null){
                        first=searchlist.get(pos);
                    }else {
                        first = locates.get(pos);
                    }
                    if(first.equals("세종특별자치시")){
                        second="";
                        get_dong(first, second);
                    }else {
                        get_sigungoo(first);
                    }
                }else if(second==null){
                    if(search!=null){
                        String[] tmp=searchlist.get(pos).split(" ");
                        second=tmp[1];
                    }else {
                        String[] tmp = locates.get(pos).split(" ");
                        second = tmp[1];
                    }
                    get_dong(first, second);
                }else{
                    if(search!=null){
                        String[] tmp=searchlist.get(pos).split(" ");
                        if(tmp[0].equals("세종특별자치시")){
                            third=tmp[1];
                        }else {
                            third = tmp[2];
                        }
                        for(int i=0; i<locates.size(); i++){
                            if(searchlist.get(pos).equals(locates.get(i))){
                                dong_id.setValue(dong.get(i));
                            }
                        }
                    }else {
                        String[] tmp = locates.get(pos).split(" ");
                        if(tmp[0].equals("세종특별자치시")){
                            third=tmp[1];
                        }else {
                            third = tmp[2];
                        }
                        dong_id.setValue(dong.get(pos));
                    }
                }
            }
        });
        return locateAdapter;
    }
    public void get_sigungoo(String str){
        TotalApiClient.getCommunityApiService(context).get_sigungu(str)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        data -> {
                            if(data.isSuccess()){
                                sigungus=new ArrayList<>();
                                Type type=new TypeToken<ArrayList<Sigungu>>(){}.getType();
                                String result=new Gson().toJson(data.getData());
                                sigungus=new Gson().fromJson(result, type);
                                locates=null;
                                locates=new ArrayList<>();
                                for(int i=0; i<sigungus.size(); i++){
                                    if(str.equals(sigungus.get(i).getSido())) {
                                        locates.add(sigungus.get(i).getSido() + " " + sigungus.get(i).getSigungu());
                                    }
                                }
                                locateAdapter.updateItem(locates);
                                search=null;
                                title.setValue("시군구 선택");
                                searchhint.setValue("위치를 검색해주세요(시군구)");
                                searchnull.setValue(null);
                            }else{
                                System.out.println(data.getError_message());
                            }
                        },
                        error -> {}
                );
    }
    public void get_dong(String first, String second){

        TotalApiClient.getCommunityApiService(context).get_dong(first, second)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        data -> {
                            if(data.isSuccess()){
                                dongs=new ArrayList<>();
                                dong=new ArrayList<>();
                                Type type=new TypeToken<ArrayList<Dong>>(){}.getType();
                                String result=new Gson().toJson(data.getData());
                                dongs=new Gson().fromJson(result, type);
                                locates=null;
                                locates=new ArrayList<>();
                                for(int i=0; i<dongs.size(); i++){
                                    if(second==""){
                                        locates.add(dongs.get(i).getSido()+" "+dongs.get(i).getDong());
                                        dong.add(dongs.get(i).getId());
                                    }
                                    else if(first.equals(dongs.get(i).getSido()) && second.equals(dongs.get(i).getSigungu())) {
                                        locates.add(dongs.get(i).getSido() + " " + dongs.get(i).getSigungu() + " " + dongs.get(i).getDong());
                                        dong.add(dongs.get(i).getId());
                                    }
                                }
                                locateAdapter.updateItem(locates);
                                search=null;
                                title.setValue("읍면동 선택");
                                searchhint.setValue("위치를 검색해주세요(읍면동)");
                                searchnull.setValue(null);
                            }else{
                                System.out.println(data.getError_message());
                            }
                        },
                        error -> {

                        }
                );
    }
    public void change_list_bysearch(String search){
        this.search=search;
        if(search==null){
            locateAdapter.updateItem(locates);
        }else{
            if(searchlist!=null){
                searchlist=null;
                searchlist=new ArrayList<>();
            }
            if(locates!=null) {
                for (int i = 0; i < locates.size(); i++) {
                    if (locates.get(i).contains(search)) {
                        searchlist.add(locates.get(i));
                    }
                }
                locateAdapter.updateItem(searchlist);
            }
        }
    }
}
