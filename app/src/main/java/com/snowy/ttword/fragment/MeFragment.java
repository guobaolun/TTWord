package com.snowy.ttword.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.snowy.common.activity.BaseActivity;
import com.snowy.common.dialog.BottomSlideDialog;
import com.snowy.ttword.R;
import com.snowy.ttword.fragment.presenter.WordPresenter;
import com.snowy.ttword.fragment.view.IWordView;
import com.snowy.widget.ItemView;

/**
 * @author guobaolun
 */
public class MeFragment extends Fragment implements IWordView {



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_study, container,false);


        final WordPresenter wordPresenter = new WordPresenter(this);
        view.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("-*-----------onClick----------");
                wordPresenter.setData();
            }
        });
        return view;
    }


    @Override
    public void showToast(String msg) {

    }

    @Override
    public void finish() {

    }

    @Override
    public void finishActivity(Class<?> cls) {

    }

    @Override
    public void setResult(int resultCode) {

    }

    @Override
    public BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }
}