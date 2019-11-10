package com.snowy.ttword.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.snowy.ttword.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


class PhoneticHelper {
	private static final String DB_NAME = "phonetic.db";
	private final String DB_PATH ; // 存放路径
	private Context mContext;
	private SQLiteDatabase database;

	PhoneticHelper(Context context) {
		this.mContext = context;

		DB_PATH = mContext.getApplicationContext().getFilesDir().getAbsolutePath();
	}

	/**
	 * * 打开数据库
	 */
	private void initDateBase() {
		File file = new File(DB_PATH + "/" + DB_NAME);
		
		if (!file.exists()) {
			// // 打开raw中得数据库文件，获得stream流
			InputStream stream = this.mContext.getResources().openRawResource(R.raw.phonetic);
			try {

				// 将获取到的stream 流写入道data中
				FileOutputStream outputStream = new FileOutputStream(DB_PATH + "/" + DB_NAME);
				int BUFFER_SIZE = 400000;
				byte[] buffer = new byte[BUFFER_SIZE];
				int count;
				while ((count = stream.read(buffer)) > 0) {
					outputStream.write(buffer, 0, count);
				}
				outputStream.close();
				stream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	void closeDatabase() {
		if (database != null && database.isOpen()) {
			this.database.close();
		}
	}
	
	

	/**
	 * 被调用方法
	 */
	SQLiteDatabase openDateBase() {
		initDateBase();
		database = SQLiteDatabase.openOrCreateDatabase(DB_PATH + "/" + DB_NAME,null);
		return database;
	}

}
