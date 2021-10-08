package com.tourkakao.carping.Mypage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.webkit.HttpAuthHandler;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.tourkakao.carping.NetworkwithToken.CommonClass;
import com.tourkakao.carping.NetworkwithToken.TotalApiClient;
import com.tourkakao.carping.R;
import com.tourkakao.carping.databinding.ActivityMypageActivitiesBinding;
import com.tourkakao.carping.databinding.ActivityPhoneCertificationBinding;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PhoneCertificationActivity extends AppCompatActivity {
    private ActivityPhoneCertificationBinding binding;
    private Context context;
    private boolean number,phone=false;
    private int minute, second;
    private boolean timerCheck=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityPhoneCertificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        context=getApplicationContext();

        settingLayout();

        binding.certificationNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 입력되는 텍스트에 변화가 있을 때 호출된다.
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // 입력이 끝났을 때 호출된다.
                if (binding.certificationNumber.getText().length()==0) {
                    number = false;
                } else {
                    number = true;
                }
                changeButtonColor(checkAll());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // 입력하기 전에 호출된다.
            }
        });
        binding.numberPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.phoneNumber.getText().length()!=11){
                    Toast myToast = Toast.makeText(getApplicationContext(),"휴대폰 번호가 11자리인지 확인해주세요", Toast.LENGTH_SHORT);
                    myToast.show();
                    return;
                }
                phone=true;
                HashMap<String,Object> phone=new HashMap<>();
                phone.put("phone",binding.phoneNumber.getText().toString());
                TotalApiClient.getMypageApiService(context).postPhoneNumber(phone)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<CommonClass>() {
                            @Override
                            public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull CommonClass commonClass) {
                                if(commonClass.getCode()==200) {
                                    binding.certificationArea.setVisibility(View.VISIBLE);
                                    Toast myToast = Toast.makeText(getApplicationContext(),"요청 완료", Toast.LENGTH_SHORT);
                                    myToast.show();
                                    timer();
                                }
                                else {
                                    Toast myToast = Toast.makeText(getApplicationContext(),"요청 실패. 카핑 채널로 문의해주세요", Toast.LENGTH_SHORT);
                                    myToast.show();
                                }
                            }

                            @Override
                            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                            }
                        });
            }
        });
        binding.completionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast myToast;
                if(!phone){
                    myToast = Toast.makeText(getApplicationContext(),"휴대폰 번호를 입력하세요", Toast.LENGTH_SHORT);
                    myToast.show();
                    return;
                }
                if(!checkAll()){
                    myToast = Toast.makeText(getApplicationContext(),"인증번호를 입력하세요", Toast.LENGTH_SHORT);
                    myToast.show();
                    return;
                }
                if(timerCheck){
                    myToast = Toast.makeText(getApplicationContext(),"인증번호를 다시 요청해주세요", Toast.LENGTH_SHORT);
                    myToast.show();
                    return;
                }
                HashMap<String,Object> auth_num=new HashMap<>();
                auth_num.put("auth_num",binding.certificationNumber.getText().toString());
                TotalApiClient.getMypageApiService(context).postverificationNumber(auth_num)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<CommonClass>() {
                            @Override
                            public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull CommonClass commonClass) {
                                if(commonClass.getCode()==200) {
                                    Gson gson=new Gson();
                                    String dataString=gson.toJson(commonClass.getData().get(0));
                                    JsonParser parser = new JsonParser();
                                    JsonElement element = parser.parse(dataString);
                                    String message = element.getAsJsonObject().get("message").getAsString();
                                    if(message.equals("인증 실패")){
                                        Toast myToast = Toast.makeText(getApplicationContext(),"인증 번호가 다릅니다", Toast.LENGTH_SHORT);
                                        myToast.show();
                                    }
                                    if(message.equals("인증 완료")){
                                        showDialog();
                                    }
                                    if(message.equals("인증 문자 발송을 다시 요청해주세요.")){
                                        Toast myToast = Toast.makeText(getApplicationContext(),"인증 문자 발송을 다시 요청해주세요.", Toast.LENGTH_SHORT);
                                        myToast.show();
                                    }
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
        });
    }

    void showDialog() {
        AlertDialog.Builder msgBuilder = new AlertDialog.Builder(this)
                .setTitle("알림")
                .setMessage("번호 인증에 성공하였습니다.")
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
        AlertDialog msgDlg = msgBuilder.create();
        msgDlg.setOnShowListener( new DialogInterface.OnShowListener() {
            @Override public void onShow(DialogInterface arg0) {
                msgDlg.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#5f51ef"));
            }
        });
        msgDlg.show();
    }

    public boolean checkAll(){
        if(number)
            return true;
        return false;
    }

    public void changeButtonColor(boolean check){
        if(check)
            binding.completionButton.setBackgroundColor(Color.BLACK);
        else
            binding.completionButton.setBackgroundColor(Color.parseColor("#999999"));
    }

    void timer() {
        minute = 5;
        second = 0;
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                // 반복실행할 구문
                runOnUiThread(new Runnable(){
                    @Override
                    public void run() {
                        if (second != 0) { // 0초 이상이면
                            second--;
                        } else if (minute != 0) { // 0분 이상이면
                            // 1분 = 60초
                            second = 60;
                            second--;
                            minute--;
                        }
                        //시, 분, 초가 10이하(한자리수) 라면
                        // 숫자 앞에 0을 붙인다 ( 8 -> 08 )
                        if (second <= 9) {
                            binding.second.setText("0" + second);
                        } else {
                            binding.second.setText(Integer.toString(second));
                        }

                        if (minute <= 9) {
                            binding.minute.setText("0" + minute);
                        } else {
                            binding.minute.setText(Integer.toString(minute));
                        }

                        // 시분초가 다 0이라면 toast를 띄우고 타이머를 종료한다..
                        if (minute == 0 && second == 0) {
                            timer.cancel();//타이머 종료
                            timerCheck=true;
                        }

                    }
                });


            }
        };
        timer.schedule(timerTask,0,1000); //Timer 실행
    }

    void settingLayout(){
        Glide.with(getApplicationContext()).load(R.drawable.back).into(binding.back);
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        binding.certificationArea.setVisibility(View.GONE);
    }
}