package com.snowy.ttword.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.snowy.ttword.dao.entity.Country;

import java.util.ArrayList;
import java.util.Collections;


public class CountryDao {


    private CountryHelper dbHelper;

    public CountryDao(Context context) {
        dbHelper = new CountryHelper(context);
        dbHelper.closeDatabase();
    }

    /**
     * 查询数据
     */
    public ArrayList<Country> queryCountry() {
        SQLiteDatabase db = dbHelper.openDateBase();
        ArrayList<Country> countryList = new ArrayList<>();


        Cursor c = db.query(
                "country",
                new String[]{"_id", "name", "zh_name", "code", "area_code", "first_letter", "letter"},
                null,
                null,
                null,
                null,
                null);
        while (c.moveToNext()) {
            String id = c.getString(0);
            String name = c.getString(1);
            String zhName = c.getString(2);
            String code = c.getString(3);
            String areaCode = c.getString(4);
            String firstLetter = c.getString(5);
            String letter = c.getString(6);
            Country country = new Country(id, name, zhName, code, areaCode, firstLetter, letter);
            countryList.add(country);
        }
        c.close();
        db.close(); // 关闭数据库
        Collections.sort(countryList);
        return  countryList;
    }


}
