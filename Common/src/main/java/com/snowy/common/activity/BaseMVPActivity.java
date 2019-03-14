package com.snowy.common.activity;

import android.os.Bundle;

import com.snowy.common.mvp.BasePresenter;

/**
 * @author guobaolun
 * @since 2018/6/22
 */
public abstract class BaseMVPActivity<T extends BasePresenter> extends BaseActivity{

    public T mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mPresenter = createPresenter();
        super.onCreate(savedInstanceState);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    protected abstract T createPresenter();





}
