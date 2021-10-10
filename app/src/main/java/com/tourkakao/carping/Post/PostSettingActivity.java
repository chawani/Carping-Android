package com.tourkakao.carping.Post;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tourkakao.carping.EcoCarping.Adapter.EcoTotalReviewAdapter;
import com.tourkakao.carping.NetworkwithToken.CommonClass;
import com.tourkakao.carping.NetworkwithToken.TotalApiClient;
import com.tourkakao.carping.Post.DTO.PriceInfo;
import com.tourkakao.carping.Post.ViewModel.PostDetailViewModel;
import com.tourkakao.carping.Post.ViewModel.PostSearchViewModel;
import com.tourkakao.carping.R;
import com.tourkakao.carping.databinding.ActivityPostRegister3Binding;
import com.tourkakao.carping.databinding.ActivityPostSettingBinding;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class PostSettingActivity extends AppCompatActivity {
    private ActivityPostSettingBinding binding;
    private Context context;
    private boolean radioCheck,priceCheck,introCheck,recommendCheck,accountCheck=false;
    private Spinner categorySpinner,bankSpinner;
    private List<String> categoryList,bankList;
    private int bankNum,categoryNum;
    private boolean categoryCheck,bankCheck=false;
    private boolean addCheck;
    private int price;
    private PostSearchViewModel viewModel;
    private PostWriteActivity postWriteActivity;
    private  Toast myToast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityPostSettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        context=getApplicationContext();

        viewModel =new ViewModelProvider(this).get(PostSearchViewModel.class);
        viewModel.setContext(this);
        postWriteActivity = (PostWriteActivity)PostWriteActivity.postWriteActivity;

        Glide.with(context).load(R.drawable.fee_terms).into(binding.feeTerms);
        binding.premiumArea.setVisibility(View.GONE);
        settingPaidPost();
        checkWriteAll();
        selectSinnerItem();

        Glide.with(context).load(R.drawable.cancel_img).into(binding.back);
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog();
            }
        });
        binding.completionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(addCheck&&!(price%100==0)){
                    myToast = Toast.makeText(getApplicationContext(),"가격은 100단위로 입력해주세요", Toast.LENGTH_SHORT);
                    myToast.show();
                    return;
                }
                if(!checkAll()){
                    myToast = Toast.makeText(getApplicationContext(),"모든 항목을 입력해주세요", Toast.LENGTH_SHORT);
                    myToast.show();
                    return;
                }
                post();
            }
        });
        viewModel.getPriceInfoMutableLiveData().observe(this, new Observer<PriceInfo>() {
            @Override
            public void onChanged(PriceInfo priceInfo) {
                binding.priceText.setText(priceInfo.getPrice()+"");
                binding.tradeFee.setText(priceInfo.getTrade_fee()+"");
                binding.platformFee.setText(priceInfo.getPlatform_fee()+"");
                binding.withholdingTax.setText(priceInfo.getWithholding_tax()+"");
                binding.vat.setText(priceInfo.getVat()+"");
                binding.finalPoint.setText(priceInfo.getFinal_point()+"");
            }
        });
    }

    public void selectSinnerItem(){
        categorySpinner=binding.categorySpinner;
        bankSpinner=binding.bankSpinner;
        ArrayAdapter<?> spinner_adapter = ArrayAdapter.createFromResource(
                this, R.array.post_category, R.layout.spinner_item);
        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(spinner_adapter);
        categorySpinner.setOnItemSelectedListener(categorySpinnerListener);
        categorySpinner.setSelection(0);
        ArrayAdapter<?> spinner_adapter2 = ArrayAdapter.createFromResource(
                this, R.array.bank, R.layout.spinner_item);
        spinner_adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bankSpinner.setAdapter(spinner_adapter2);
        bankSpinner.setOnItemSelectedListener(bankSpinnerListener);
        bankSpinner.setSelection(0);
    }

    public android.widget.AdapterView.OnItemSelectedListener categorySpinnerListener=new AdapterView.OnItemSelectedListener(){
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            if(i==0) categoryCheck=false;
            else categoryCheck=true;
            categoryNum=i;
            changeButtonColor(checkAll());
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    };
    public android.widget.AdapterView.OnItemSelectedListener bankSpinnerListener=new AdapterView.OnItemSelectedListener(){
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            if(i==0) bankCheck=false;
            else bankCheck=true;
            bankNum=i+1;
            changeButtonColor(checkAll());
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    };

    public void settingPaidPost(){
        binding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                radioCheck=true;
                if(i==R.id.pay){
                    addCheck=true;
                    binding.premiumArea.setVisibility(View.VISIBLE);
                    if(binding.price.getText().toString().length()==0){
                        priceCheck=false;
                    }
                    else{
                        priceCheck=true;
                    }
                    binding.price.setBackground(ContextCompat.getDrawable(context, R.drawable.edit_text_box_purple));
                    changeButtonColor(checkAll());
                }
                if(i==R.id.free){
                    addCheck=false;
                    binding.premiumArea.setVisibility(View.GONE);
                    priceCheck=true;
                    binding.price.setBackground(ContextCompat.getDrawable(context, R.drawable.edit_text_box_img));
                    changeButtonColor(checkAll());
                }
            }
        });
    }

    public void checkWriteAll(){
        binding.price.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 입력되는 텍스트에 변화가 있을 때 호출된다.
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // 입력이 끝났을 때 호출된다.
                if(binding.price.getText().toString().length()==0){
                    priceCheck=false;
                }
                else{
                    price=Integer.parseInt(binding.price.getText().toString());
                    if(price>20000){
                        price=20000;
                        binding.price.setText("20000");
                    }
                    viewModel.loadCalcResult(price);
                    priceCheck=true;
                }
                changeButtonColor(checkAll());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // 입력하기 전에 호출된다.
            }
        });
        binding.introduce.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 입력되는 텍스트에 변화가 있을 때 호출된다.
                binding.introduceTextLength.setText(s.toString().length()+"/100");
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // 입력이 끝났을 때 호출된다.
                if(binding.introduce.getText().toString().length()==0){
                    introCheck=false;
                }
                else{
                    introCheck=true;
                }
                changeButtonColor(checkAll());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // 입력하기 전에 호출된다.
            }
        });
        binding.recommend.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 입력되는 텍스트에 변화가 있을 때 호출된다.
                binding.recommendTextLength.setText(s.toString().length()+"/100");
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // 입력이 끝났을 때 호출된다.
                if(binding.recommend.getText().toString().length()==0){
                    recommendCheck=false;
                }
                else{
                    recommendCheck=true;
                }
                changeButtonColor(checkAll());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // 입력하기 전에 호출된다.
            }
        });
        binding.account.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 입력되는 텍스트에 변화가 있을 때 호출된다.
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // 입력이 끝났을 때 호출된다.
                if(binding.account.getText().toString().length()==0){
                    accountCheck=false;
                }
                else{
                    accountCheck=true;
                }
                changeButtonColor(checkAll());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // 입력하기 전에 호출된다.
            }
        });
    }

    public void dialog() {
        AlertDialog.Builder msgBuilder = new AlertDialog.Builder(this)
                .setTitle("정말 나가시겠습니까?")
                .setMessage("작성한 포스트는 저장되지 않습니다")
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialogInterface, int i) {
                        postWriteActivity.finish();
                        finish();
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        AlertDialog msgDlg = msgBuilder.create();
        msgDlg.setOnShowListener( new DialogInterface.OnShowListener() {
            @Override public void onShow(DialogInterface arg0) {
                msgDlg.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#5f51ef"));
                msgDlg.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#5f51ef"));
            }
        });
        msgDlg.show();
    }

    public boolean checkAll(){
        if(!addCheck) {
            if (categoryCheck && introCheck && recommendCheck && radioCheck)
                return true;
        }
        else{
            if (categoryCheck && introCheck && recommendCheck && radioCheck
                    && bankCheck && accountCheck && priceCheck)
                return true;
        }
        return false;
    }

    public void changeButtonColor(boolean check){
        if(check)
            binding.completionButton.setBackgroundColor(Color.BLACK);
        else
            binding.completionButton.setBackgroundColor(Color.parseColor("#999999"));
    }

    void post(){
        HashMap<String,String> post=(HashMap<String, String>) getIntent().getSerializableExtra("map");
        String[] uriList=getIntent().getStringArrayExtra("image");
        HashMap<String,RequestBody> requestBodyHashMap=new HashMap<>();

        RequestBody author_comment = RequestBody.create(MediaType.parse("text/plain"),post.get("author_comment"));
        RequestBody kakao_openchat_url = RequestBody.create(MediaType.parse("text/plain"),post.get("kakao_openchat_url"));
        RequestBody title = RequestBody.create(MediaType.parse("text/plain"),post.get("title"));
        RequestBody sub_title1 = RequestBody.create(MediaType.parse("text/plain"),post.get("sub_title1"));
        RequestBody text1=RequestBody.create(MediaType.parse("text/plain"),post.get("text1"));
        RequestBody sub_title2 = RequestBody.create(MediaType.parse("text/plain"),post.get("sub_title2"));
        RequestBody text2 = RequestBody.create(MediaType.parse("text/plain"),post.get("text2"));
        RequestBody sub_title3 = RequestBody.create(MediaType.parse("text/plain"),post.get("sub_title3"));
        RequestBody text3=RequestBody.create(MediaType.parse("text/plain"),post.get("text3"));
        RequestBody sub_title4 = RequestBody.create(MediaType.parse("text/plain"),post.get("sub_title4"));
        RequestBody text4 = RequestBody.create(MediaType.parse("text/plain"),post.get("text4"));
        RequestBody sub_title5 = RequestBody.create(MediaType.parse("text/plain"),post.get("sub_title5"));
        RequestBody text5=RequestBody.create(MediaType.parse("text/plain"),post.get("text5"));
        RequestBody info = RequestBody.create(MediaType.parse("text/plain"), binding.introduce.getText().toString());
        RequestBody recommend_to = RequestBody.create(MediaType.parse("text/plain"), binding.recommend.getText().toString());
        String payTypeString="0";
        if(addCheck) payTypeString="1";
        RequestBody pay_type = RequestBody.create(MediaType.parse("text/plain"),payTypeString);
        RequestBody point=RequestBody.create(MediaType.parse("text/plain"),"0");
        if(payTypeString.equals("1")){
            point = RequestBody.create(MediaType.parse("text/plain"),binding.price.getText().toString());
            RequestBody account_num=RequestBody.create(MediaType.parse("text/plain"), binding.account.getText().toString());
            requestBodyHashMap.put("account_num",account_num);
        }
        RequestBody category=RequestBody.create(MediaType.parse("text/plain"), Integer.toString(categoryNum));
        RequestBody bank=RequestBody.create(MediaType.parse("text/plain"), Integer.toString(bankNum));



        requestBodyHashMap.put("author_comment", author_comment);
        requestBodyHashMap.put("kakao_openchat_url", kakao_openchat_url);
        requestBodyHashMap.put("title", title);
        requestBodyHashMap.put("sub_title1", sub_title1);
        requestBodyHashMap.put("text1", text1);
        requestBodyHashMap.put("sub_title2", sub_title2);
        requestBodyHashMap.put("text2", text2);
        requestBodyHashMap.put("sub_title3", sub_title3);
        requestBodyHashMap.put("text3", text3);
        requestBodyHashMap.put("sub_title4", sub_title4);
        requestBodyHashMap.put("text4", text4);
        requestBodyHashMap.put("sub_title5", sub_title5);
        requestBodyHashMap.put("text5", text5);
        requestBodyHashMap.put("pay_type", pay_type);
        requestBodyHashMap.put("point", point);
        requestBodyHashMap.put("info", info);
        requestBodyHashMap.put("recommend_to", recommend_to);
        requestBodyHashMap.put("category", category);
        requestBodyHashMap.put("bank", bank);

        MultipartBody.Part thumbnail=null;
        MultipartBody.Part image1=null;
        MultipartBody.Part image2=null;
        MultipartBody.Part image3=null;
        MultipartBody.Part image4=null;
        MultipartBody.Part image5=null;


        if(!uriList[0].equals("")) {
            File file = new File(uriList[0]);
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            thumbnail = MultipartBody.Part.createFormData("thumbnail", file.getName(), requestBody);
        }
        if(!uriList[1].equals("")) {
            File file = new File(uriList[1]);
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            image1 = MultipartBody.Part.createFormData("image1", file.getName(), requestBody);
        }
        if(!uriList[2].equals("")) {
            File file = new File(uriList[2]);
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            image2=MultipartBody.Part.createFormData("image2", file.getName(), requestBody);
        }
        if(!uriList[3].equals("")) {
            File file = new File(uriList[3]);
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            image3 = MultipartBody.Part.createFormData("image3", file.getName(), requestBody);
        }
        if(!uriList[4].equals("")) {
            File file = new File(uriList[4]);
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            image4 = MultipartBody.Part.createFormData("image4", file.getName(), requestBody);
        }
        if(!uriList[5].equals("")) {
            File file = new File(uriList[5]);
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            image5 = MultipartBody.Part.createFormData("image5", file.getName(), requestBody);
        }

        TotalApiClient.getPostApiService(getApplicationContext()).writePost(thumbnail,image1,image2,image3,image4,image5,requestBodyHashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<CommonClass>() {
                    @Override
                    public void onSuccess(@NonNull CommonClass commonClass) {
                        if(commonClass.getCode()==200) {
//                            myToast = Toast.makeText(getApplicationContext(),"작성 성공", Toast.LENGTH_SHORT);
//                            myToast.show();
                            completeDialog();
                        }
                        else{
                            System.out.println("포스트 작성 실패"+commonClass.getCode()+commonClass.getError_message());
                            myToast = Toast.makeText(getApplicationContext(),"작성 실패. 문의해주세요", Toast.LENGTH_SHORT);
                            myToast.show();
                            postWriteActivity.finish();
                            finish();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        postWriteActivity.finish();
                        finish();
                        System.out.println("작성하기 오류"+e.getMessage());
                        myToast = Toast.makeText(getApplicationContext(),"작성 오류. 문의해주세요", Toast.LENGTH_SHORT);
                        myToast.show();
                    }
                });
    }

    public void completeDialog(){
        Dialog dialog = new Dialog(this); // Dialog 초기화
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        dialog.setContentView(R.layout.post_dialog); // xml 레이아웃 파일과 연결
        dialog.show();
        TextView positive = dialog.findViewById(R.id.post_positive);
        ImageView background=dialog.findViewById(R.id.post_background);
        Glide.with(context).load(R.drawable.post_alert_background).into(background);
        positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                postWriteActivity.finish();
                finish();
            }
        });
    }
}