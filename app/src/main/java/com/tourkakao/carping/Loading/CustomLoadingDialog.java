package com.tourkakao.carping.Loading;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDialog;

import com.bumptech.glide.Glide;
import com.tourkakao.carping.R;

public class CustomLoadingDialog {
    private static AppCompatDialog progressDialog;

    private CustomLoadingDialog(){
    }
    public static AppCompatDialog getInstance(Context context, String message){
        if(progressDialog==null || !progressDialog.isShowing()){
            progressDialog=new AppCompatDialog(context);
            progressDialog.setCancelable(false);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            progressDialog.setContentView(R.layout.loading_layout);
        }
        TextView m=progressDialog.findViewById(R.id.dialog_message);
        m.setText(message);
        ImageView img=progressDialog.findViewById(R.id.loading_img);
        Glide.with(context).load(R.drawable.loading).into(img);
        return progressDialog;
    }
}
