package com.snowy.ttword.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.snowy.ttword.dao.entity.PhoneticFile;

import java.util.ArrayList;


public class PhoneticDao {

    private PhoneticHelper dbHelper;

    public PhoneticDao(Context context) {
        dbHelper = new PhoneticHelper(context);
        dbHelper.closeDatabase();

    }


    /**
     * 查询数据
     */
    public ArrayList<PhoneticFile> queryPhoneticFile() {
        SQLiteDatabase db = dbHelper.openDateBase();

        Cursor c = db.query(
                "phonetic",
                new String[]{"file","text","regex"},
                null,
                null,
                null,
                null,
                null);

        ArrayList<PhoneticFile> list = new ArrayList<>();

        while (c.moveToNext()) {
            String file = c.getString(0);
            String text = c.getString(1);
            String regex = c.getString(2);


            PhoneticFile phoneticFile = new PhoneticFile(file, text,regex);
            list.add(phoneticFile);

        }

        c.close();
        db.close(); // 关闭数据库
        return list;
    }


    /**
     * 查询数据
     */
    public String queryCombination(String text) {
        SQLiteDatabase db = dbHelper.openDateBase();

        Cursor c = db.query(
                "phonetic",
                new String[]{"combination"},
                "text = ?",
                new String[] {text},
                null,
                null,
                null);

        String combination = "";

        while (c.moveToNext()) {
            combination = c.getString(0);
        }
        c.close();
        db.close(); // 关闭数据库

        return combination;
    }



    /**
     * 查询数据
     */
    public String queryRegex(String text) {
        SQLiteDatabase db = dbHelper.openDateBase();

        Cursor c = db.query(
                "phonetic",
                new String[]{"regex"},
                "text = ?",
                new String[] {text},
                null,
                null,
                null);

        String regex = "";

        while (c.moveToNext()) {
            regex = c.getString(0);
        }
        c.close();
        db.close(); // 关闭数据库

        return regex;
    }


}
