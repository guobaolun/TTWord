package com.snowy.common.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.telephony.TelephonyManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Locale;

/**
 * 系统工具类
 */
public class SystemUtils {

    /**
     * 获取当前手机系统语言。
     *
     * @return 返回当前系统语言。例如：当前设置的是“中文-中国”，则返回“zh-CN”
     */
    public static String getSystemLanguage() {
        return Locale.getDefault().getLanguage();
    }

    /**
     * 获取当前系统上的语言列表(Locale列表)
     *
     * @return 语言列表
     */
    public static Locale[] getSystemLanguageList() {
        return Locale.getAvailableLocales();
    }

    /**
     * 获取当前手机系统版本号
     *
     * @return 系统版本号
     */
    public static String getSystemVersion() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取手机型号
     *
     * @return 手机型号
     */
    public static String getSystemModel() {
        return Build.MODEL;
    }

    /**
     * 获取手机厂商
     *
     * @return 手机厂商
     */
    public static String getDeviceBrand() {
        return Build.BRAND;
    }


    /**
     * 获取Android设备唯一标识码
     *
     * @return 设备id
     */
    public static String getDeviceId(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Activity.TELEPHONY_SERVICE);
        if (tm != null) {
            //TODO
            return tm.getDeviceId();
        }
        return null;
    }

    /**
     * SIM卡的序列号
     *
     * @return 手机IMEI
     */
    public static String getIMEI(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Activity.TELEPHONY_SERVICE);
        if (tm != null) {

            //TODO
            return tm.getSimSerialNumber();
        }
        return null;
    }


    /**
     * 获取手机IMSI
     *
     * @return IMSI
     */
    public static String getIMSI(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Activity.TELEPHONY_SERVICE);
        if (tm != null) {
            //TODO
            return tm.getSubscriberId();
        }
        return null;
    }


    /**
     * 获取Sim卡状态
     *
     * @return Sim卡状态
     */
    public static String getSimState(Context ctx) {
        TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Activity.TELEPHONY_SERVICE);
        if (tm != null) {
            if (tm.getSimState() == TelephonyManager.SIM_STATE_READY) {
                return "良好";
            } else if (tm.getSimState() == TelephonyManager.SIM_STATE_ABSENT) {
                return "无SIM卡";
            } else {
                return "SIM卡被锁定或未知的状态";
            }
        }
        return "";
    }


    /**
     * 获取本机IP地址
     *
     * @return IP地址
     */
    public static String getLocalIpAddress(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();
        try {
            return InetAddress.getByName(String.format("%d.%d.%d.%d", (ipAddress & 0xff), (ipAddress >> 8 & 0xff), (ipAddress >> 16 & 0xff), (ipAddress >> 24 & 0xff))).toString();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 获取本机的物理地址
     *
     * @param context c
     * @return 本机的物理地址
     */
    public static String getLocalMacAddress(Context context) {
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        return info.getMacAddress();
    }


    /**
     * app版本名
     *
     * @param context c
     * @return VersionName
     */
    public static String getVersionName(Context context) {
        String versionName = "";
        PackageInfo info = getPackageInfo(context);
        if (info != null) {
            versionName = info.versionName;
        }
        return versionName;
    }

    /**
     * app版本号
     *
     * @param context c
     * @return VersionCode
     */
    public static int getVersionCode(Context context) {
        int versionCode = 0;
        PackageInfo info = getPackageInfo(context);
        if (info != null) {
            versionCode = info.versionCode;
        }
        return versionCode;
    }

    private static PackageInfo getPackageInfo(Context context) {

        try {
            PackageManager pm = context.getPackageManager();
            return pm.getPackageInfo(context.getPackageName(), PackageManager.GET_CONFIGURATIONS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 将得到的int类型的IP转换为String类型
     *
     * @param ip ip
     */
    private static String intIP2StringIP(int ip) {
        return (ip & 0xFF) + "." + ((ip >> 8) & 0xFF) + "." + ((ip >> 16) & 0xFF) + "." + (ip >> 24 & 0xFF);
    }



    /**
     * 获取可用运存大小
     */
    public static long getAvailMemory(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);

        return mi.availMem / (1024 * 1024);
    }


    /**
     * 获取总运存大小
     */
    public static long getAvailTotalMemory() {
        long initial_memory = 0;

        try {
            // 系统内存信息文件
            String str1 = "/proc/meminfo";
            FileReader localFileReader = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(
                    localFileReader, 8192);
            // 读取meminfo第一行，系统总内存大小
            String str2 = localBufferedReader.readLine();

            String[] arrayOfString = str2.split("\\s+");

            // 获得系统总内存，单位是KB，乘以1024转换为Byte
            initial_memory = Integer.valueOf(arrayOfString[1]) * 1024;
            localBufferedReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return initial_memory / (1024 * 1024);  //Byte转换为KB或者MB，内存大小规格化
    }


    /**
     * 获取SD卡可用大小
     */
    public static long getRomMemroy() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSizeLong();           // 每块存储块的大小
        long availableBlocks = stat.getAvailableBlocksLong();// 可用存储块的数量
        return blockSize * availableBlocks;
    }

    /**
     * 获取SD卡总内存大小
     */
    public static long getRomTotalMemory() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSizeLong();
        long totalBlocks = stat.getBlockCountLong();
        return totalBlocks * blockSize;
    }

    /**
     * 获取cpu名称
     */
    public static String getCpuName() {
        if (Build.CPU_ABI.equalsIgnoreCase("x86")) {
            return "Intel";
        }

        String strInfo = "";
        try {
            byte[] bs = new byte[1024];
            RandomAccessFile reader = new RandomAccessFile("/proc/cpuinfo", "r");
            reader.read(bs);
            String ret = new String(bs);
            int index = ret.indexOf(0);
            if (index != -1) {
                strInfo = ret.substring(0, index);
            } else {
                strInfo = ret;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return strInfo;
    }

}