package com.snowy.common.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class  ExceptionMessageHelper extends SQLiteOpenHelper {



	// 由于父类没有无参构造函数, 所以子类必须指定调用父类哪个有参的构造函数
	ExceptionMessageHelper(Context context) {
		super(context, " Exception.db", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {


		//手机系统语言, 系统版本号, 手机型号, 手机厂商, 设备唯一标识码, SIM卡的序列号, 手机IMSI, Sim卡状态, 本机IP地址, 本机的物理地址, app版本名, app版本号, 当前时间, 网络类型, 屏幕的度, 屏幕高度, 省, 市, 区, 详细地址, 经度, 纬度
		db.execSQL("CREATE TABLE message(_id INTEGER PRIMARY KEY AUTOINCREMENT,msg,SystemLanguage,SystemVersion,SystemModel,DeviceBrand," +
				"DeviceId,IMEI,IMSI,SimState,LocalIpAddr,LocalMacAddr,VersionName,VersionCode,time,NetType,ScreenWidth,ScreenHeight,province,city,area,address," +
				"lng,lat,availMemory,availTotalMemory,romMemroy,romTotalMemory,cpuName)");
	}


	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//		db.execSQL("ALTER TABLE message ADD balance INTEGER");
	}
	
}
