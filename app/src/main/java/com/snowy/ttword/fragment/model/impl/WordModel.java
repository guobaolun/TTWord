package com.snowy.ttword.fragment.model.impl;

import com.snowy.common.entity.DataPart;
import com.snowy.common.mvp.BaseModel;
import com.snowy.common.net.HttpCallback;
import com.snowy.common.net.OkHttpManager;
import com.snowy.ttword.fragment.model.IWordModel;

import java.util.ArrayList;

/**
 * @author guobaolun
 */
public class WordModel extends BaseModel implements IWordModel {

    private OkHttpManager okhttpManager;

    @Override
    public  void getWordCountData(HttpCallback callback) {
        ArrayList<DataPart> list = new ArrayList<>();
        list.add(new DataPart("mobile", "13380050751", OkHttpManager.Type.STRING));
        list.add(new DataPart("code", "4006", OkHttpManager.Type.STRING));

        okhttpManager = new OkHttpManager();
        okhttpManager.post("http://enteb.any1door.com/api/login", list, callback);
        System.out.println("------getWordCountData--------");
    }


    @Override
    public OkHttpManager getOkHttpManager() {
        return okhttpManager;
    }





}
