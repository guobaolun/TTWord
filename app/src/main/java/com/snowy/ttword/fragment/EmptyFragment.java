package com.snowy.ttword.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDelegate;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.snowy.common.utils.ScreenUtils;
import com.snowy.common.utils.SharedPreferencesManager;
import com.snowy.ttword.Constants;
import com.snowy.ttword.R;
import com.snowy.ttword.adapter.PhoneticListAdapter;


/**
 * 只为占位
 */
public class EmptyFragment extends Fragment {


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        LinearLayout view = (LinearLayout) inflater.inflate(R.layout.fragment_study, container, false);


        Button button = view.findViewById(R.id.bt);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int mode = SharedPreferencesManager.getInstance(getActivity()).getInt(Constants.SP_KEY_THEME_MODE, AppCompatDelegate.MODE_NIGHT_NO);

                if (mode == AppCompatDelegate.MODE_NIGHT_NO) {
                    mode = AppCompatDelegate.MODE_NIGHT_YES;
                }else{
                    mode = AppCompatDelegate.MODE_NIGHT_NO;
                }

                AppCompatDelegate.setDefaultNightMode(mode);
                SharedPreferencesManager.getInstance(getActivity()).putInt(Constants.SP_KEY_THEME_MODE,mode);
                System.out.println("=============="+mode);
                getActivity().recreate();
            }
        });


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("====f====onResume======");
    }

    @Override
    public void onPause() {
        super.onPause();
        System.out.println("=====f===onPause======");
    }
}