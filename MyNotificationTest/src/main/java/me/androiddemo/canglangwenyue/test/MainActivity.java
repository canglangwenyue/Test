package me.androiddemo.canglangwenyue.test;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {


    private NotificationManager manager;

    NotificationCompat.Builder notifyBuilder;

    private Intent myIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    /**
     * 显示最简单的通知，以下method中的前三个set方法是必须设置的
     *
     * @param view
     */
    public void simNotification(View view) {

        /*实例化NotificationManager以获取系统服务*/
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        notifyBuilder = new NotificationCompat.Builder(this)
                /**
                 * 前三个属性必须设置
                 */
                /*设置small icon*/
                .setSmallIcon(R.drawable.ic_launcher)
                /*设置title*/
                .setContentTitle("通知")
                /*设置详细文本*/
                .setContentText("Hello world")
                 /*设置发出通知的时间为发出通知时的系统时间*/
                .setWhen(System.currentTimeMillis())
                 /*设置发出通知时在status bar进行提醒*/
                .setTicker("来自问月的祝福")
                 /*设置点击后通知消失*/
                .setAutoCancel(true)
                        /**
                         * 设置
                         notification的默认效果有以下几种
                         Notification.DEFAULT_ALL：铃声、闪光、震动均系统默认。
                         Notification.DEFAULT_SOUND：系统默认铃声。
                         Notification.DEFAULT_VIBRATE：系统默认震动。
                         Notification.DEFAULT_LIGHTS：系统默认闪光。
                         */
                .setDefaults(Notification.DEFAULT_VIBRATE)
                 /*setOngoing(boolean)设为true,notification将无法通过左右滑动的方式清除
                * 可用于添加常驻通知，必须调用cancle方法来清除
                */
                .setOngoing(true)
                 /*设置通知数量的显示类似于QQ那种，用于同志的合并*/
                .setNumber(2);

        manager.notify(100, notifyBuilder.build());
    }

    /**
     * 点击跳转到指定Activity
     *
     * @param view
     */
    public void largePicture(View view) {
         /*实例化NotificationManager以获取系统服务*/
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        //点击的意图ACTION是跳转到Intent
        Intent resultIntent = new Intent(this, MainActivity.class);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
        notifyBuilder = new NotificationCompat.Builder(this)
                /*设置large icon*/
                .setLargeIcon(bitmap)
                 /*设置small icon*/
                .setSmallIcon(R.drawable.ic_launcher)
                /*设置title*/
                .setContentTitle("通知")
                /*设置详细文本*/
                .setContentText("Hello world")
                 /*设置发出通知的时间为发出通知时的系统时间*/
                .setWhen(System.currentTimeMillis())
                 /*设置发出通知时在status bar进行提醒*/
                .setTicker("来自问月的祝福")
                /*setOngoing(boolean)设为true,notification将无法通过左右滑动的方式清除
                * 可用于添加常驻通知，必须调用cancle方法来清除
                */
                .setOngoing(true)
                 /*设置点击后通知消失*/
                .setAutoCancel(true)
                 /*设置通知数量的显示类似于QQ那种，用于同志的合并*/
                .setNumber(2)
                 /*点击跳转到MainActivity*/
                .setContentIntent(pendingIntent);

        manager.notify(121, notifyBuilder.build());
    }

    /**
     * 类似于系统截图的效果
     *
     * @param view
     */
    public void comNotification(View view) {

        /*实例化NotificationManager以获取系统服务*/
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
        notifyBuilder = new NotificationCompat.Builder(this)
                 /*设置small icon*/
                .setSmallIcon(R.drawable.ic_launcher)
                /*设置title*/
                .setContentTitle("通知")
                /*设置详细文本*/
                .setContentText("Hello world")
                .setWhen(System.currentTimeMillis())
                .setOngoing(true)
                .setNumber(2);

        NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle();
        bigPictureStyle.bigPicture(bitmap);
        notifyBuilder.setStyle(bigPictureStyle);

        manager.notify(121, notifyBuilder.build());
    }

    /**
     * 自定义notification样式
     *
     * @param view
     */
    public void Cus_Notification(View view) {

        Toast.makeText(MainActivity.this, "AHa", Toast.LENGTH_LONG).show();
        /*实例化NotificationManager以获取系统服务*/
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.customnotification);

        remoteViews.setImageViewResource(R.id.imageView, R.drawable.psb);
        remoteViews.setTextViewText(R.id.textView, "Your Haven");

        remoteViews.setTextViewText(R.id.textView2, "YUI");
        remoteViews.setTextViewText(R.id.textView3, "豆瓣－FNM －我的红心 MHZ");

        remoteViews.setViewVisibility(R.id.my_large_button, View.VISIBLE);
        notifyBuilder = new NotificationCompat.Builder(this)
                .setContent(remoteViews)
                .setContentIntent(getDefalutIntent(Notification.FLAG_AUTO_CANCEL))
                 /*设置small icon*/
                .setSmallIcon(R.drawable.ic_launcher)
                /*设置title*/
                .setContentTitle("通知")
                /*设置详细文本*/
                .setTicker("Your Haven")
                .setContentText("Hello world")
                .setWhen(System.currentTimeMillis())
                .setPriority(Notification.PRIORITY_DEFAULT)// 设置该通知优先级
                .setOngoing(true);

        Notification noty = notifyBuilder.build();
        noty.contentView = remoteViews;
        manager.notify(313, noty);

    }

    /**
     * 有进度条的notification
     * @param view
     */
    public void Pro_Notification(View view) {

        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notifyBuilder = new NotificationCompat.Builder(this);
        notifyBuilder.setContentTitle("Picture Download")
                .setContentText("Download in progress")
                .setOngoing(true)
                .setSmallIcon(R.drawable.ic_launcher);
// Start a lengthy operation in a background thread
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        int incr;
                        // Do the "lengthy" operation 20 times
                        for (incr = 0; incr <= 100; incr += 5) {
                            // Sets the progress indicator to a max value, the
                            // current completion percentage, and "determinate"
                            // state
                            notifyBuilder.setProgress(100, incr, false);
                            // Displays the progress bar for the first time.
                            manager.notify(0, notifyBuilder.build());
                            // Sleeps the thread, simulating an operation
                            // that takes time
                            try {
                                // Sleep for 5 seconds
                                Thread.sleep(5 * 1000);
                            } catch (InterruptedException e) {
                                Log.d("NOTIFICATION", "sleep failure");
                            }
                        }
                        // When the loop is finished, updates the notification
                        notifyBuilder.setContentText("Download complete")
                                // Removes the progress bar
                                .setProgress(0, 0, false);
                        manager.notify(213, notifyBuilder.build());
                    }
                }
// Starts the thread by calling the run() method in its Runnable
        ).start();

    }

    public PendingIntent getDefalutIntent(int flags) {
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, new Intent(), flags);
        return pendingIntent;
    }

    /**
     * 创建一个类似于日历事件的notification
     * @param view
     */
    public void add_action(View view) {

        myIntent = new Intent(getApplicationContext(), MyIntentService.class);

        myIntent.putExtra(myConstants.EXTRA_MESSAGE, " 来自问月的祝福");
        myIntent.setAction(myConstants.ACTION_PING);
        myIntent.putExtra(myConstants.EXTRA_TIMER, 1000);
        startService(myIntent);

    }

    public void clearAll(View view) {
        /*
        清除所有的notification
         */
        manager.cancelAll();

          /*
        清除指定的notification用下面的方法，传入notification的ID即可
         */

//        manager.cancel(121);

    }


}
