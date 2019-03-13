package com.snowy.common.net;




class UploadFileManager {
//
//    private Configuration config = new Configuration.Builder()
//            .chunkSize(512 * 1024)        // 分片上传时，每片的大小。 默认256K
//            .putThreshhold(1024 * 1024)   // 启用分片上传阀值。默认512K
//            .connectTimeout(10)           // 链接超时。默认10秒
//            .useHttps(true)               // 是否使用https上传域名
//            .responseTimeout(60)          // 服务器响应超时。默认60秒
//            .zone(FixedZone.zone2)        // 设置区域，指定不同区域的上传域名、备用域名、备用IP。
//            .build();
//
//
//    private int failureCount = 0;
//    private int position = 0;
//    private boolean isCancelled = false;
//    private String uploadToken;
//    private List<DataPart> dataList;
//    private UploadFileCallback callback;
//
//    /**
//     * 重用uploadManager。一般地，只需要创建一个uploadManager对象
//     */
//    private UploadManager uploadManager = new UploadManager(config);
//
//    UploadFileManager(UploadFileCallback callback) {
//        this.callback = callback;
//    }
//
//    public void setDataList(List<DataPart> dataList) {
//        this.dataList = dataList;
//    }
//
//
//    public void setUploadToken(String uploadToken) {
//        this.uploadToken = uploadToken;
//    }
//
//    public void uploadFile() {
//        if (isCancelled) {
//            return;
//        }
//
//        DataPart dataPart = dataList.get(position);
//        File file = new File(dataPart.getValue());
//        String fileName = file.getName();
//        String prefix = fileName.substring(fileName.lastIndexOf(".") + 1);
//
//
//        UUID uuid = UUID.randomUUID();
//        String key = uuid.toString() + "." + prefix;
//        uploadManager.put(file, key, uploadToken, new UpCompletionHandler() {
//            @Override
//            public void complete(String key, ResponseInfo info, JSONObject res) {
//                //res包含hash、key等信息，具体字段取决于上传策略的设置
//                if (info.isOK()) {
//                    failureCount = 0;
//                    try {
//                        dataList.get(position).setValue(res.getString("key"));
//                        dataList.get(position).setType(OkHttpManager.Type.STRING);
//                        position++;
//
//                        if (position < dataList.size()) {
//                            uploadFile();
//                        } else {
//                            callback.onResponse(dataList);
//                        }
//
//                    } catch (JSONException e) {
//                        callback.onFailure();
//                        e.printStackTrace();
//                    }
//                } else {
//                    if (failureCount < 2) {
//                        failureCount++;
//                        uploadFile();
//                    } else {
//                        callback.onFailure();
//                    }
//                    //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
//                }
//            }
//        }, new UploadOptions(null, null, false, null,
//                new UpCancellationSignal() {
//                    @Override
//                    public boolean isCancelled() {
//                        return isCancelled;
//                    }
//                }));
//    }
//
//
//    void cancel() {
//        isCancelled = true;
//    }


}
