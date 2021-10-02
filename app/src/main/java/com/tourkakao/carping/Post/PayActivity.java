package com.tourkakao.carping.Post;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.tourkakao.carping.Post.DTO.PayResponse;
import com.tourkakao.carping.Post.ViewModel.PostDetailViewModel;
import com.tourkakao.carping.databinding.ActivityPayBinding;
import com.tourkakao.carping.databinding.ActivityPostDetailBinding;

public class PayActivity extends AppCompatActivity {
    private ActivityPayBinding binding;
    private PostDetailViewModel viewModel;
    private String pgToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityPayBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel =new ViewModelProvider(this).get(PostDetailViewModel.class);
        viewModel.setContext(this);

        int id=getIntent().getIntExtra("id",0);

        WebSettings webSettings = binding.webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        binding.webView.setWebViewClient(new MyWebViewClient());

        viewModel.readyPayment(id);
        viewModel.getPayResponse().observe(this, new Observer<PayResponse>() {
            @Override
            public void onChanged(PayResponse payResponse) {
                binding.webView.loadUrl(payResponse.getNext_redirect_app_url());
                Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(payResponse.getAndroid_app_scheme()));
                startActivity(intent);
            }
        });
    }

    public class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url != null && url.startsWith("intent://")) {
                try {
                    Intent intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                    Intent existPackage = getPackageManager().getLaunchIntentForPackage(intent.getPackage());
                    if (existPackage != null) {
                        startActivity(intent);
                    }
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                view.loadUrl(url);
            }else{
                finish();
            }
            return false;
        }
    }
}