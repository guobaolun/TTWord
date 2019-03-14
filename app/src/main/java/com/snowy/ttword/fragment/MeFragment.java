package com.snowy.ttword.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.english.storm.R;
import com.english.storm.activity.LoginActivity;
import com.english.storm.activity.RegisterActivity;
import com.english.storm.activity.SettingCenterActivity;
import com.english.storm.common.fragment.BaseMVPFragment;
import com.english.storm.fragment.presenter.MePresenter;
import com.english.storm.fragment.view.IMeView;
import com.english.storm.glide.GlideUtils;

/**
 * @author guobaolun
 */
public class MeFragment extends BaseMVPFragment<MePresenter> implements OnClickListener ,IMeView{

    private RelativeLayout loginRl;
    private LinearLayout itemLl;
    private LinearLayout infoLl;
    private TextView nicknameTv;
    private ImageView portraitIv;
    private LinearLayout notLonginPortraitLl;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        initView(view);

        return view;
    }

    private void initView(View view) {
        ImageButton settingBt = view.findViewById(R.id.setting_bt);
        settingBt.setOnClickListener(this);

        portraitIv = view.findViewById(R.id.portrait_iv);
        portraitIv.setOnClickListener(this);

        notLonginPortraitLl = view.findViewById(R.id.not_longin_portrait_ll);
        notLonginPortraitLl.setOnClickListener(this);

        Button registerBt = view.findViewById(R.id.register_bt);
        registerBt.setOnClickListener(this);


        Button loginBt = view.findViewById(R.id.login_bt);
        loginBt.setOnClickListener(this);

        nicknameTv = view.findViewById(R.id.nickname_tv);

        infoLl = view.findViewById(R.id.info_ll);
        loginRl = view.findViewById(R.id.login_rl);
        itemLl = view.findViewById(R.id.item_ll);

    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.setUserInfo();
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.setting_bt:
                intent.setClass(getActivity(), SettingCenterActivity.class);
                startActivity(intent);
                break;
            case R.id.portrait_iv:
                mPresenter.showPortraitDialog();
                break;
            case R.id.register_bt:
                intent.setClass(getActivity(), RegisterActivity.class);
                startActivityForResult(intent, LoginActivity.LOGIN_CODE);
                break;
            case R.id.not_longin_portrait_ll:
            case R.id.login_bt:
                intent.setClass(getActivity(), LoginActivity.class);
                startActivityForResult(intent, LoginActivity.LOGIN_CODE);
                break;
            default:
                break;
        }
    }

    @Override
    public void setLonginView(String portraitUrl, String nickname) {
        if (!TextUtils.isEmpty(portraitUrl)) {
            GlideUtils.loadAsBitmap(getActivity(),portraitUrl, portraitIv, 0, 0);
        }
        nicknameTv.setText(nickname);
        notLonginPortraitLl.setVisibility(View.GONE);
        infoLl.setVisibility(View.VISIBLE);
        loginRl.setVisibility(View.GONE);
        itemLl.setVisibility(View.VISIBLE);
    }

    @Override
    public void setNotLonginView() {
        notLonginPortraitLl.setVisibility(View.VISIBLE);
        infoLl.setVisibility(View.GONE);
        loginRl.setVisibility(View.VISIBLE);
        itemLl.setVisibility(View.GONE);
    }

    @Override
    public ImageView getPortraitImageView() {
        return portraitIv;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    protected MePresenter createPresenter() {
        return  new MePresenter(this);
    }
}