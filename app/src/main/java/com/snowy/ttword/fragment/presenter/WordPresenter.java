package com.snowy.ttword.fragment.presenter;

import com.snowy.common.mvp.BasePresenter;
import com.snowy.common.net.HttpCallback;
import com.snowy.ttword.fragment.model.IWordModel;
import com.snowy.ttword.fragment.model.impl.WordModel;
import com.snowy.ttword.fragment.view.IWordView;

import java.io.IOException;

/**
 * @author guobaolun
 * @since 2017/12/22
 */

public class WordPresenter extends BasePresenter<IWordView, IWordModel> {


    public WordPresenter(IWordView view) {
        super(view);
    }

    @Override
    protected IWordModel createModel() {
        return new WordModel();
    }


    public void setData() {
        model.getWordCountData(new MyHttpCallback());
    }


    class MyHttpCallback implements HttpCallback {


        @Override
        public void onFailure(IOException e) {
            System.out.println("-+--------------- 数据加载失败,下拉重新加载");
        }

        @Override
        public void onResponse(String body) {




        }
    }



}
