package com.snowy.common.manager;


import android.content.Context;
import android.os.Looper;
import android.os.SystemClock;
import android.widget.Toast;


import com.snowy.common.dao.ExceptionMessageDao;
import com.snowy.common.net.NetUtils;
import com.snowy.common.utils.ScreenUtils;
import com.snowy.common.utils.SystemUtils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;

public class CrashHandler implements UncaughtExceptionHandler {




    private static CrashHandler instance = new CrashHandler();
    private Context mContext;

    private CrashHandler() {
    }

    public static CrashHandler getInstance() {
        return instance;
    }


    // 系统默认的UncaughtException处理类
    private UncaughtExceptionHandler mDefaultHandler;

    /**
     * 初始化
     *
     * @param context context
     */
    public void init(Context context) {
        mContext = context;
        // 获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        // 设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {

        saveCrashInfo(mContext, ex);

        boolean notMessage = handleException(ex);

        if (notMessage && mDefaultHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理

            mDefaultHandler.uncaughtException(thread, ex);
        } else {
//            SystemClock.sleep(1000);
            // 退出程序
            System.exit(0);
        }


    }


    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @param ex ex
     * @return true:如果处理了该异常信息; 否则返回false.
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return true;
        }
        try {

            // 使用Toast来显示异常信息
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Looper.prepare();
                    Toast.makeText(mContext, "很抱歉,程序出现异常,即将重启.", Toast.LENGTH_LONG).show();
                    Looper.loop();

                    SystemClock.sleep(1000);
                }
            }).start();


        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


    /**
     * 保存错误信息到文件中
     */
    private void saveCrashInfo(Context context, Throwable ex) {

        ExceptionMessageDao messageDao = new ExceptionMessageDao(context);

        String systemLanguage = SystemUtils.getSystemLanguage();  //当前手机系统语言
        String systemVersion = SystemUtils.getSystemVersion();  //系统版本号
        String systemModel = SystemUtils.getSystemModel();  //手机型号
        String deviceBrand = SystemUtils.getDeviceBrand();  //手机厂商
        String deviceId = SystemUtils.getDeviceId(context);  //设备唯一标识码
        String imei = SystemUtils.getIMEI(context);  //SIM卡的序列号
        String imsi = SystemUtils.getIMSI(context);  //手机IMSI
        String simState = SystemUtils.getSimState(context);  //Sim卡状态
        String localIpAddr = SystemUtils.getLocalIpAddress(context);  //本机IP地址
        String localMacAddr = SystemUtils.getLocalMacAddress(context);  //本机的物理地址
        String versionName = SystemUtils.getVersionName(context);  //app版本名
        String versionCode = SystemUtils.getVersionCode(context) + "";  //app版本号
        String time = System.currentTimeMillis() + "";  //当前时间
        String netType = NetUtils.checkNetType(context);  //网络类型
        String screenWidth = ScreenUtils.getScreenWidth(context) + "";  //屏幕宽度
        String screenHeight = ScreenUtils.getScreenHeight(context) + "";  //屏幕高度

        String availMemory = SystemUtils.getAvailMemory(context) + "";        //获取可用运存大小
        String availTotalMemory = SystemUtils.getAvailTotalMemory() + "";     //获取总运存大小
        String romMemroy = SystemUtils.getRomMemroy() + "";                   //获取SD卡可用内存大小
        String romTotalMemory = SystemUtils.getRomTotalMemory() + "";         //获取SD卡总内存大小
        String cpuName = SystemUtils.getCpuName();                            //获取cpu名称


        String province = "";   //省
        String city = "";       //市
        String area = "";       //区
        String address = "";    //详细地址
        String lng = "";        //经度
        String lat = "";        //纬度


        try {
            ex.printStackTrace();

            Writer writer = new StringWriter();
            PrintWriter printWriter = new PrintWriter(writer);
            ex.printStackTrace(printWriter);
            Throwable cause = ex.getCause();
            while (cause != null) {
                cause.printStackTrace(printWriter);
                cause = cause.getCause();
            }
            printWriter.flush();
            printWriter.close();
            String result = writer.toString();
            messageDao.insert(result, systemLanguage, systemVersion, systemModel, deviceBrand, deviceId, imei, imsi, simState, localIpAddr, localMacAddr, versionName, versionCode, time, netType, screenWidth, screenHeight, province, city, area, address, lng, lat, availMemory, availTotalMemory, romMemroy, romTotalMemory, cpuName);
//            writeText(result);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


//    private void writeText(String context) {
//        try {
//            if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
//                /* 写入Txt文件 */
//                File writeFile = new File(BaseConstants.EXCEPTION_PATH + TimeUtils.getCurrentFormatDate(TimeUtils.TIME_FORMAT_CN) + ".txt"); // 相对路径，如果没有则要建立一个新的output。txt文件
//                // 创建新文件
//                if (writeFile.createNewFile()) {
//                    BufferedWriter out = new BufferedWriter(new FileWriter(writeFile));
//                    out.write(context + "\r\n"); // \r\n即为换行
//                    out.flush(); // 把缓存区内容压入文件
//                    out.close(); // 最后记得关闭文件
//                }
//
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

}
