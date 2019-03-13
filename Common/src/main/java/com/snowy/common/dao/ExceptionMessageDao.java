package com.snowy.common.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class ExceptionMessageDao {
    private ExceptionMessageHelper helper;

    public ExceptionMessageDao(Context context) {
        helper = new ExceptionMessageHelper(context);
    }

    public void insert(String msg ,String systemLanguage, String systemVersion, String systemModel, String deviceBrand,
                       String deviceId, String IMEI, String IMSI,String simState, String localIpAddr, String localMacAddr, String versionName, String versionCode,
                       String time,String netType, String screenWidth, String screenHeight, String province, String city, String area, String address, String lng, String lat
    , String availMemory , String availTotalMemory , String romMemroy , String romTotalMemory , String cpuName ) {




        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("msg", msg);
        values.put("SystemLanguage", systemLanguage);
        values.put("SystemVersion", systemVersion);
        values.put("SystemModel", systemModel);
        values.put("DeviceBrand", deviceBrand);
        values.put("DeviceId", deviceId);
        values.put("IMEI", IMEI);
        values.put("IMSI", IMSI);
        values.put("SimState", simState);
        values.put("LocalIpAddr", localIpAddr);
        values.put("LocalMacAddr", localMacAddr);
        values.put("VersionName", versionName);
        values.put("VersionCode", versionCode);
        values.put("time", time);
        values.put("NetType", netType);
        values.put("ScreenWidth", screenWidth);
        values.put("ScreenHeight", screenHeight);
        values.put("province", province);
        values.put("city", city);
        values.put("area", area);
        values.put("address", address);
        values.put("lng", lng);
        values.put("lat", lat);
        values.put("availMemory", availMemory);
        values.put("availTotalMemory", availTotalMemory);
        values.put("romMemroy", romMemroy);
        values.put("romTotalMemory", romTotalMemory);
        values.put("cpuName", cpuName);

        db.insert("message", null, values);
        db.close(); // 关闭数据库
    }


    /**
     * 删除数据
     *
     * @param id messageID
     */
    public void delete(String id) {
        SQLiteDatabase db = helper.getWritableDatabase();
        // db.execSQL("DELETE FROM account WHERE _id=?", new Object[]{info.});
        // db.delete(“表”,”条件”,new String[] { id+””} )
        db.delete("message", "_id=?", new String[]{id});
        db.close();

    }


}
