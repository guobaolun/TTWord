package com.snowy.ttword.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.snowy.ttword.dao.entity.School;

import java.util.ArrayList;


public class SchoolDao {

    private SchoolHelper dbHelper;

    public SchoolDao(Context context) {
        dbHelper = new SchoolHelper(context);
        dbHelper.closeDatabase();
    }

    /**
     * 查询学校数据
     */
    public  ArrayList<School> querySchoolList(String text) {
        SQLiteDatabase db = dbHelper.openDateBase();

        Cursor c = db.query(
                "schools",
                new String[]{"id", "name", "pinyin", "py"},
                "name like ? or pinyin like ? or py like ?",
                new String[] { "%"+text+"%","%"+text+"%","%"+text+"%" },
                null, null, null);

        ArrayList<School> schoolList = new ArrayList<>();
        while (c.moveToNext()) {
            School school = new School();
            school.setId(c.getInt(0));
            school.setName(c.getString(1));
            school.setPinyin(c.getString(2));
            school.setPy(c.getString(3));
            schoolList.add(school);
        }
        c.close();
        db.close(); // 关闭数据库
        return schoolList;
    }


    /**
     * 查询学校数据
     */
    public  ArrayList<School> queryAllSchoolList() {
        SQLiteDatabase db = dbHelper.openDateBase();

        Cursor c = db.query(
                "schools",
                new String[]{"id", "school_id", "name", "pinyin", "py"},
                null, null, null, null, null);

        ArrayList<School> schoolList = new ArrayList<>();
        while (c.moveToNext()) {
            int id = c.getInt(0);
            int school_id = c.getInt(1);
            String name = c.getString(2);
            String pinyin = c.getString(3);
            String py = c.getString(4);
            School school = new School();
            school.setId(id);
            school.setSchoolId(school_id);
            school.setName(name);
            school.setPinyin(pinyin);
            school.setPy(py);
        }
        c.close();
        db.close(); // 关闭数据库
        return schoolList;
    }


}
