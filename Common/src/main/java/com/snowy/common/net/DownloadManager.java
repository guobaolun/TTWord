package com.snowy.common.net;

import android.os.Handler;
import android.os.Message;


import androidx.annotation.NonNull;

import com.snowy.common.entity.DownloadMassage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class DownloadManager {

    private static class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {

            DownloadMassage downloadMassage = (DownloadMassage) msg.obj;

            OnDownloadListener listener = downloadMassage.getListener();

            switch (msg.what) {
                case DOWNLOADING:
                    listener.onDownloading(downloadMassage.getUrl(), downloadMassage.getFilePath(), downloadMassage.getProgress(), downloadMassage.getBytesRead(), downloadMassage.getTotalBytes());
                    break;
                case SUCCESS:
                    listener.onDownloadSuccess(downloadMassage.getUrl(), downloadMassage.getFilePath());
                    break;

                case FAILED:
                    listener.onDownloadFailed(downloadMassage.getUrl(), downloadMassage.getException());
                    break;
                default:
                    break;
            }
        }
    }

    private final MyHandler mHandler = new MyHandler();


    private static DownloadManager manager;
    private final OkHttpClient okHttpClient;

    private static final int DOWNLOADING = 0;
    private static final int SUCCESS = 1;
    private static final int FAILED = 2;

    private static Map<Object, WeakReference<Call>> map = Collections.synchronizedMap(new HashMap<Object, WeakReference<Call>>());


    public static DownloadManager getManager() {
        if (manager == null) {
            manager = new DownloadManager();
        }
        return manager;
    }

    private DownloadManager() {
        okHttpClient = new OkHttpClient();
    }

    /**
     * @param url      下载连接
     * @param saveDir  储存下载文件的SDCard目录
     * @param listener 下载监听
     */
    public void download(final String url, final String saveDir, final OnDownloadListener listener) {
        final Request request = new Request.Builder().url(url).build();
        Call call = okHttpClient.newCall(request);
        map.put(url, (new WeakReference<>(call)));
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // 下载失败
                DownloadMassage downloadMassage = new DownloadMassage();
                Message msg = new Message();
                msg.what = FAILED;
                msg.obj = downloadMassage;
                mHandler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) {
                String savePath = isExistDir(saveDir);
                File file = new File(savePath, getNameFromUrl(url));
                DownloadMassage downloadMassage = new DownloadMassage();
                downloadMassage.setUrl(url);
                downloadMassage.setListener(listener);
                downloadMassage.setFilePath(file.getPath());

                Message msg = new Message();
                msg.obj = downloadMassage;

                if (response.code() == 200) {
                    InputStream is = null;
                    byte[] buf = new byte[2048];
                    int len;
                    FileOutputStream fos = null;
                    // 储存下载文件的目录
                    try {
                        ResponseBody body = response.body();
                        if (body ==null){
                            msg.what = FAILED;
                            mHandler.sendMessage(msg);
                            return;
                        }
                        is = body.byteStream();
                        long total = body.contentLength();
                        fos = new FileOutputStream(file);
                        long sum = 0;
                        while ((len = is.read(buf)) != -1) {
                            // 下载中
                            fos.write(buf, 0, len);
                            sum += len;
                            int progress = (int) (sum * 1.0f / total * 100);
                            downloadMassage.setProgress(progress);
                            downloadMassage.setBytesRead(sum);
                            downloadMassage.setTotalBytes(total);
                            msg.what = DOWNLOADING;
                        }
                        // 下载完成
                        fos.flush();
                        msg.what = SUCCESS;
                        mHandler.sendMessage(msg);
                    } catch (Exception e) {
                        msg.what = FAILED;
                        mHandler.sendMessage(msg);
                    } finally {
                        try {
                            if (is != null) {
                                is.close();
                            }
                            if (fos != null) {
                                fos.close();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    msg.what = FAILED;
                    mHandler.sendMessage(msg);
                }
            }

        });
    }


    /**
     * 取消下载
     */
    public void cancel(String url) {
        WeakReference<Call> weakReference = map.get(url);
        if (weakReference != null) {
            Call call = weakReference.get();
            if (call != null) {
                call.cancel();
            }
        }
        map.remove(url);
    }


    /**
     * 判断下载目录是否存在
     */
    private String isExistDir(String saveDir) {
        // 下载位置

        File downloadFile = new File(saveDir);
        if (!downloadFile.mkdirs()) {
            try {
               boolean isNewFile =  downloadFile.createNewFile();
               if (isNewFile){
                   return "";
               }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return downloadFile.getAbsolutePath();
    }

    /**
     * 从下载连接中解析出文件名
     */
    @NonNull
    private String getNameFromUrl(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }

    public interface OnDownloadListener {
        /**
         * 下载成功
         */
        void onDownloadSuccess(String url, String filePath);

        /**
         * 下载进度
         */
        void onDownloading(String url, String filePath, int progress, long bytesRead, long totalBytes);

        /**
         * 下载失败
         */
        void onDownloadFailed(String url, Exception e);
    }




}
