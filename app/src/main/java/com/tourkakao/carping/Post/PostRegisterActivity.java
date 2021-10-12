package com.tourkakao.carping.Post;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.tourkakao.carping.NetworkwithToken.CommonClass;
import com.tourkakao.carping.NetworkwithToken.TotalApiClient;
import com.tourkakao.carping.R;
import com.tourkakao.carping.SharedPreferenceManager.SharedPreferenceManager;
import com.tourkakao.carping.databinding.ActivityPostRegisterBinding;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PostRegisterActivity extends AppCompatActivity {
    private ActivityPostRegisterBinding binding;
    private Context context;
    private boolean phone_check=false;
    private boolean phoneRecordCheck=false;
    private Toast myToast;
    private int minute, second;
    private boolean timerCheck=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityPostRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        context=getApplicationContext();

        phoneRecordCheck= SharedPreferenceManager.getInstance(context).getBoolean("phone",false);
        initLayout();

        observeChanges();

        binding.numberPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(phone_check) return;
                if(binding.phoneNumber.getText().length()!=11){
                    myToast = Toast.makeText(getApplicationContext(),"휴대폰 번호가 11자리인지 확인해주세요", Toast.LENGTH_SHORT);
                    myToast.show();
                    return;
                }
                HashMap<String,Object> phone=new HashMap<>();
                phone.put("phone",binding.phoneNumber.getText().toString());
                TotalApiClient.getMypageApiService(context).postPhoneNumber(phone)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<CommonClass>() {
                            @Override
                            public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull CommonClass commonClass) {
                                if(commonClass.getCode()==200) {
                                    myToast = Toast.makeText(getApplicationContext(),"요청 완료", Toast.LENGTH_SHORT);
                                    myToast.show();
                                    binding.phoneCertificationArea.setVisibility(View.VISIBLE);
                                    timerCheck=false;
                                    timer();
                                }
                                else {
                                    myToast = Toast.makeText(getApplicationContext(),"요청 실패. 문의해주세요", Toast.LENGTH_SHORT);
                                    myToast.show();
                                }
                            }

                            @Override
                            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                                myToast = Toast.makeText(getApplicationContext(),"서버 오류. 문의해주세요", Toast.LENGTH_SHORT);
                                myToast.show();
                            }
                        });
            }
        });
        binding.certificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(phone_check) return;
                if(binding.certificationNumber.getText().length()==0){
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
                                        binding.certificationButton.setClickable(false);
                                        binding.numberPostButton.setClickable(false);
                                        phone_check=true;
                                        changeButtonColor(checkAll());
                                    }
                                    if(message.equals("인증 문자 발송을 다시 요청해주세요.")){
                                        Toast myToast = Toast.makeText(getApplicationContext(),"인증 문자 발송을 다시 요청해주세요.", Toast.LENGTH_SHORT);
                                        myToast.show();
                                    }
                                }
                                else {
                                    myToast = Toast.makeText(getApplicationContext(),"인증 오류. 문의해주세요", Toast.LENGTH_SHORT);
                                    myToast.show();
                                }
                            }

                            @Override
                            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                                myToast = Toast.makeText(getApplicationContext(),"서버 오류. 문의해주세요", Toast.LENGTH_SHORT);
                                myToast.show();
                            }
                        });
            }
        });
        binding.completionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!phone_check){
                    Toast myToast = Toast.makeText(getApplicationContext(),"휴대폰 인증은 필수입니다", Toast.LENGTH_SHORT);
                    myToast.show();
                    return;
                }
                if(!checkAgreeList()){
                    Toast myToast = Toast.makeText(getApplicationContext(),"필수 항목들에 모두 체크해주세요", Toast.LENGTH_SHORT);
                    myToast.show();
                    return;
                }
                Intent intent=new Intent(context, PostRegisterActivity2.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void initLayout(){
        Glide.with(context).load(R.drawable.back).into(binding.back);
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        if(phoneRecordCheck==true){
            binding.phone.setVisibility(View.GONE);
            phone_check=true;
        }
        binding.phoneCertificationArea.setVisibility(View.GONE);
    }

    public boolean checkAgreeList(){
        if(binding.check1.isChecked()
                &&binding.check2.isChecked()
                &&binding.check3.isChecked()
                &&binding.check4.isChecked())
            return true;
        return false;
    }

    public boolean checkAll(){
        if(phone_check&&checkAgreeList())
            return true;
        return false;
    }

    public void changeButtonColor(boolean check){
        if(check)
            binding.completionButton.setBackgroundColor(Color.BLACK);
        else
            binding.completionButton.setBackgroundColor(Color.parseColor("#999999"));
    }

    void showDialog() {
        AlertDialog.Builder msgBuilder = new AlertDialog.Builder(this)
                .setTitle("알림")
                .setMessage("번호 인증에 성공하였습니다.")
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialogInterface, int i) {

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

    public void observeChanges(){
        binding.check1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeButtonColor(checkAll());
            }
        });
        binding.check2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeButtonColor(checkAll());
            }
        });
        binding.check3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeButtonColor(checkAll());
            }
        });
        binding.check4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeButtonColor(checkAll());
            }
        });
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

}