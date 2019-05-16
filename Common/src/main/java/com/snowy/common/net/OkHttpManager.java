package com.snowy.common.net;


import com.snowy.common.BaseConstants;
import com.snowy.common.entity.DataPart;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @author guobaolun
 */
public class OkHttpManager {


    public enum Type {
        /**
         * 文件类型
         */
        FILE,
        /**
         * 字符串类型
         */
        STRING
    }

    private final static int CONNECT_TIMEOUT = 10;
    private final static int READ_TIMEOUT = 10;
    private final static int WRITE_TIMEOUT = 20;
    private final static int CACHE_SIZE = 10 * 1024 * 1024;

    private MultipartBody.Builder mBody;
    private Call call;
    private HttpCallback mResponse;
    private String url;
//    private UploadFileManager uploadFileManager;


    public void post(String url, List<DataPart> list, HttpCallback response) {
        mResponse = response;
        this.url = url;
        //设置超时时间及缓存

        mBody = new MultipartBody.Builder().setType(MultipartBody.FORM);


        for (DataPart dataPart : list) {
            if (dataPart.getValue() != null) {
                switch (dataPart.getType()) {
                    case STRING:
                        mBody.addFormDataPart(dataPart.getName(), dataPart.getValue());
                        break;
                    default:
                        break;
                }
            }
        }
        enqueue();

//        List<DataPart> fileList = new ArrayList<>();
//        for (DataPart dataPart : list) {
//            if (dataPart.getValue() != null) {
//                switch (dataPart.getType()) {
//                    case STRING:
//                        mBody.addFormDataPart(dataPart.getName(), dataPart.getValue());
//                        break;
//                    case FILE:
//                        fileList.add(dataPart);
//                        break;
//                    default:
//                        break;
//                }
//            }
//        }

//        uploadFile(fileList,list);
    }


//    private void uploadFile(List<DataPart> fileList,List<DataPart> list){
//        if (fileList.size() != 0) {
//            uploadFileManager = new UploadFileManager(new UploadFileCallback() {
//
//                @Override
//                public void onFailure() {
//                    mResponse.onFailure(new IOException("上传文件失败"));
//                }
//
//                @Override
//                public void onResponse(List<DataPart> dataList) {
//                    for (DataPart dataPart : dataList) {
//                        mBody.addFormDataPart(dataPart.getName(), dataPart.getValue());
//                    }
//                    enqueue();
//                }
//            });
//
//            uploadFileManager.setDataList(fileList);
//
//            String token = null;
//            for (DataPart dataPart : list) {
//                if ("token".equals(dataPart.getName())) {
//                    token = dataPart.getValue();
//                }
//            }
//
//            requestUploadToken(token);
//        } else {
//            enqueue();
//        }
//    }


//    private void requestUploadToken(String token) {
//        ArrayList<DataPart> list = new ArrayList<>();
//        list.add(new DataPart("token", token, Type.STRING));
//        post("Constants.UPLOAD_TOKEN_URL", list, new HttpCallback() {
//
//
//            @Override
//            public void onResponse(String body) {
//
//                OkResponse<String> response = JSON.parseObject(body, new TypeReference<OkResponse<String>>() {
//                });
//                if (response != null && CodeUtils.OK == response.getCode()) {
//                    uploadFileManager.setUploadToken(response.getData());
//                    uploadFileManager.uploadFile();
//                } else {
//                    onFailure(new IOException("service error"));
//                }
//            }
//
//            @Override
//            public void onFailure(IOException e) {
//                mResponse.onFailure(new IOException("连接失败"));
//            }
//        });
//    }


    private void enqueue() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .cache(new Cache(new File(BaseConstants.ROOT_PATH), CACHE_SIZE));
        RequestBody requestBody = mBody.build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        OkHttpClient mOkHttpClient = builder.build();
        call = mOkHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mResponse.onFailure(e);
            }

            @Override
            public void onResponse(Call call, Response response) {
                try {
                    ResponseBody responseBody = response.body();
                    if (response.code() == CodeUtils.OK && responseBody != null) {
                        String text = responseBody.string();
                        mResponse.onResponse(text);
                    } else {
                        mResponse.onFailure(new IOException("OkHttpManager responseCode is " + response.code()));
                    }
                } catch (IOException e) {
                    mResponse.onFailure(e);
                    e.printStackTrace();
                }
            }
        });
    }

    public void cancel() {
        if (call != null) {
            call.cancel();
        }
//        if (uploadFileManager != null) {
//            uploadFileManager.cancel();
//        }
    }


}
