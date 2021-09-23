package com.tourkakao.carping.Home.Fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.tourkakao.carping.Home.EcoDataClass.EcoRanking;
import com.tourkakao.carping.Home.HomeViewModel.EcoViewModel;
import com.tourkakao.carping.databinding.MainEcoTopFragmentBinding;

public class EcoTopFragment extends Fragment {
    private MainEcoTopFragmentBinding ecobinding;
    private Context context;
    private EcoViewModel ecoViewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ecobinding= MainEcoTopFragmentBinding.inflate(inflater, container, false);

        ecoViewModel =new ViewModelProvider(this).get(EcoViewModel.class);
        ecoViewModel.setContext(context);

        ecoViewModel.ecoPercentage.observe(this,percentageObserver);
        ecoViewModel.currentUser.observe(this,userObserver);

        return ecobinding.getRoot();
    }

    Observer<Integer> percentageObserver=new Observer<Integer>() {
        @Override
        public void onChanged(Integer integer) {
            ecobinding.percent.setText(Integer.toString(integer)+"%");
        }
    };

    Observer<EcoRanking> userObserver=new Observer<EcoRanking>() {
        @Override
        public void onChanged(EcoRanking ecoRanking) {

            int length=ecoRanking.getUsername().length();
            SpannableString spannableString = new SpannableString(ecoRanking.getUsername());
            spannableString.setSpan(new StyleSpan(Typeface.BOLD),0,spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            ecobinding.username.setText(spannableString);
            ecobinding.level.setText("LV. "+ecoRanking.getLevel());
        }
    };
}
