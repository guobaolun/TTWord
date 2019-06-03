package com.snowy.ttword.fragment.model;

import com.snowy.common.net.HttpCallback;
import com.snowy.common.net.OkHttpManager;

/**
 * @author guobaolun
 * Created by estel on 2015/10/12.
 */
public interface IWordModel {

        void getWordCountData(HttpCallback callback);
        OkHttpManager getOkHttpManager();


}
