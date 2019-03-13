package com.snowy.common.entity;


import com.snowy.common.net.DownloadManager;

public class DownloadMassage {

    private String url;
    private String filePath;
    private int progress;
    private long bytesRead;
    private long totalBytes;
    private Exception exception;

    private DownloadManager.OnDownloadListener listener;


    public DownloadManager.OnDownloadListener getListener() {
        return listener;
    }

    public void setListener(DownloadManager.OnDownloadListener listener) {
        this.listener = listener;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public long getBytesRead() {
        return bytesRead;
    }

    public void setBytesRead(long bytesRead) {
        this.bytesRead = bytesRead;
    }

    public long getTotalBytes() {
        return totalBytes;
    }

    public void setTotalBytes(long totalBytes) {
        this.totalBytes = totalBytes;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }
}
