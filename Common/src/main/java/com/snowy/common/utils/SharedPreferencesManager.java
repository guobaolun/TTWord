package com.snowy.common.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.alibaba.fastjson.JSON;

import java.util.List;


public class SharedPreferencesManager {


    public static final long MINUTE = 1000 * 60;
    public static final long HOUR = MINUTE * 60;
    public static final long DAY = HOUR * 24;
    public static final long CACHE_TIME = 6;


    /**
     * SharedPreferences name
     */
    private static final String SP_NAME = "sp_name";





    public static final String TIME = "Time";
//    public static final String SP_KEY_LOGIN_DATA = "loginInfo";
//    public static final String SP_KEY_ENGLISH_CIRCLE_DATA = "englishCircle";
//    public static final String SP_KEY_COMMENT_DATA = "comment";
//    public static final String SP_KEY_PHONETIC_BUTTON = "phoneticButton";
//    public static final String SP_KEY_RECITE_WORD_COUNT_DATA = "reciteWordCountData";
//    public static final String SP_KEY_WORD_BOOK_DATA = "wordBook";
//
//    public static final String SP_KEY_THEME_MODE = "themeMode";

    private static SharedPreferences sp;

    private static SharedPreferencesManager single;

    private SharedPreferencesManager(Context context) {
        sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }

    public static SharedPreferencesManager getInstance(Context context) {
        if(single == null){
            single = new SharedPreferencesManager(context);
        }
        return single;
    }

    public void putString(String key, String src) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, src);
        editor.apply();
    }

    public String getString(String key, String src) {
        return sp.getString(key, src);
    }

    public void putInt(String key, int i) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(key, i);
        editor.apply();
    }

    public int getInt(String key, int i) {
        return sp.getInt(key, i);
    }

    public void putLong(String key, long l) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong(key, l);
        editor.apply();
    }

    public long getLong(String key, long l) {
        return sp.getLong(key, l);
    }

    public void putBoolean(String key, boolean b) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key, b);
        editor.apply();
    }

    public boolean getBoolean(String key, boolean b) {
        return sp.getBoolean(key, b);
    }

    public void putJSON(String key, Object obj) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, JSON.toJSONString(obj));
        editor.apply();
    }

    public <T> T getJSON( String key, Class<T> classOfT) {
        String str = sp.getString(key, null);
        return JSON.parseObject(str, classOfT);
    }

    public void putJSONArray(String key, Object obj) {
        putJSON(key,obj);
    }

    public <T> List<T> getJSONArray( String key, Class<T> classOfT) {
        String text = sp.getString(key, null);
        return JSON.parseArray(text, classOfT);
    }

    public void remove(String key) {
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        editor.apply();
    }

    public void clear() {
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.apply();
    }






//    public static void saveAndTime(Context context, String key, Object src) {
//        SharedPreferences.Editor editor = sp.edit();
//        editor.putString(key, JSON.toJSONString(src));
//        editor.putLong(key + TIME, System.currentTimeMillis());
//        editor.apply();
//    }
//
//
//    /**
//     * 获取缓存时间
//     */
//    private static long getSaveTime(Context context, String key) {
//        return sp.getLong(key + TIME, 0);
//    }


//    public static <T> T get(Context context, String key, Class<T> classOfT) {
//        String loginDataStr = sp.getString(key, null);
//        return JSON.parseObject(loginDataStr, classOfT);
//    }







//    /**
//     * 判断缓存是否过期
//     *
//     * @return 过期返回tree ,否则返回false
//     */
//    public static boolean isOverdue(Context context, String key) {
//        long saveTime = getSaveTime(context, key);
//        return System.currentTimeMillis() - saveTime > SharedPreferencesManager.HOUR * CACHE_TIME;
//    }
//

//    /**
//     * 清空登录缓存
//     */
//    public static void clear(Context context, String key) {
//        SharedPreferences.Editor editor = sp.edit();
//        editor.putString(key, null);
//        editor.apply();
//    }


//    /**
//     * 缓存音标按钮状态
//     */
//    public static void setPhoneticButton(Context context, boolean isSelected) {
//        ;
//        SharedPreferences.Editor editor = sp.edit();
//        editor.putBoolean(SP_KEY_PHONETIC_BUTTON, isSelected);
//        editor.apply();
//    }
//
//    /**
//     * 获取音标按钮状态
//     */
//    public static boolean getPhoneticButton(Context context) {
//        ;
//        return sp.getBoolean(SP_KEY_PHONETIC_BUTTON, false);
//    }


}
