package com.snowy.ttword.manager;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;


import androidx.core.app.NotificationCompat;

import com.snowy.ttword.R;

import java.util.Random;

import static android.app.Notification.VISIBILITY_SECRET;

/**
 * @author guobaolun
 * @since 2019/4/8 17:03
 */


public class MyNotificationManager extends ContextWrapper {

    public static final String CHANNEL_ID = "普通推送";
    public static final String CHANNEL_NAME = "普通推送";

    private NotificationManager manager;

    public MyNotificationManager(Context context, String channelId) {
        super(context);

        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            channel.enableLights(true);    //闪光灯
            channel.setLockscreenVisibility(VISIBILITY_SECRET);       //锁屏显示通知
            channel.setLightColor(Color.YELLOW); //闪关灯的灯光颜色
            channel.enableVibration(true);  //是否允许震动
            channel.setVibrationPattern(new long[]{100, 100, 200});        //设置震动模式
            channel.setBypassDnd(true);       //设置可绕过  请勿打扰模式

            manager.createNotificationChannel(channel);
//            channel.canBypassDnd();//是否绕过请勿打扰模式
//            channel.canShowBadge();    //桌面launcher的消息角标
//            channel.getAudioAttributes();  //获取系统通知响铃声音的配置
//            channel.getGroup();          //获取通知取到组
//            channel.shouldShowLights();            //是否会有灯光
        }

    }

    /**
     * 正常样式通知
     */
    public void showNotification(String channelId, int id, CharSequence title, CharSequence text, Bitmap icon, Intent intent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);
        builder.setGroupSummary(false);
        builder.setGroup("group");
        PendingIntent pendingIntent = PendingIntent.getActivity(this, new Random().nextInt(100000), intent, PendingIntent.FLAG_UPDATE_CURRENT);// explain
        builder.setDefaults(Notification.DEFAULT_ALL);  // 设置使用所有默认值（声音、震动、闪屏等）
        builder.setContentIntent(pendingIntent);
        setBuilder(builder, title, text, icon);


        manager.notify(id, builder.build());

    }


    private void setBuilder(NotificationCompat.Builder builder, CharSequence title, CharSequence text, Bitmap icon) {
//    private NotificationCompat.Builder setBuilder(NotificationCompat.Builder builder,CharSequence title, CharSequence text, CharSequence tickerText, Bitmap icon, int smallIcon){
        builder.setAutoCancel(true); // 设置自动清除
        builder.setContentTitle(title); // 设置内容标题
        builder.setContentText(text);// 设置内容文本
        builder.setLargeIcon(icon); // 设置大型图标
        builder.setSmallIcon(R.mipmap.ic_launcher);  // 设置小型图标
        builder.setTicker(title); // 设置状态栏提示信息
    }

    /**
     * 收件箱样式通知
     */
    private void showInbox(int id, CharSequence contentInfo, CharSequence title, CharSequence text, CharSequence tickerText, Bitmap icon, int smallIcon, CharSequence[] lines, CharSequence summaryText) {

        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle(); // 设置通知样式为收件箱样式
        for (CharSequence line : lines) {
            inboxStyle.addLine(line);
        }
        inboxStyle.setSummaryText(summaryText);

        Notification notification = new NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setContentInfo(contentInfo)
                .setContentTitle(title)
                .setContentText(text)
                .setDefaults(Notification.DEFAULT_ALL)
                .setLargeIcon(icon)
                .setSmallIcon(smallIcon)
                .setTicker(tickerText)
                .setStyle(inboxStyle) // 设置在细节区域底端添加一行文本
                .build();
        manager.notify(id, notification);
    }

    /**
     * 大文本样式通知
     */
    private void showBigText(int id, CharSequence contentInfo, CharSequence title, CharSequence text, CharSequence tickerText, Bitmap icon, int smallIcon, CharSequence bigText) {
        Notification notification = new NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setContentInfo(contentInfo)
                .setContentTitle(title)
                .setContentText(text)
                .setDefaults(Notification.DEFAULT_ALL)
                .setLargeIcon(icon)
                .setSmallIcon(smallIcon)
                .setTicker(tickerText)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(bigText))// 设置通知样式为大型文本样式
                .build();
        manager.notify(id, notification);
    }


    /**
     * 大图片样式通知
     */
    private void showBigPicture(int id, CharSequence contentInfo, CharSequence title, CharSequence text, CharSequence tickerText, Bitmap icon, int smallIcon, Bitmap bigPicture) {
        Notification notification = new NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setContentInfo(contentInfo)
                .setContentTitle(title)
                .setContentText(text)
                .setDefaults(Notification.DEFAULT_ALL)
                .setLargeIcon(icon)
                .setSmallIcon(smallIcon)
                .setTicker(tickerText)
                .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bigPicture)) // 设置通知样式为大型图片样式
                .build();
        manager.notify(id, notification);
    }


    //******************************************************************************************************


}
