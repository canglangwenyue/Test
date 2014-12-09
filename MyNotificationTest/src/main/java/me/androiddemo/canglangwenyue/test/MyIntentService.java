package me.androiddemo.canglangwenyue.test;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

/**
 * Created by canglangwenyue on 14-12-9.
 */
public class MyIntentService extends IntentService {

    private NotificationManager manager;
    private String message;
    private int mMills;
    NotificationCompat.Builder builder;

    public MyIntentService() {
        // The super call is required. The background thread that IntentService
        // starts is labeled with the string argument you pass.
        super("me.androiddemo.canglangwenyuet.test");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        message = intent.getStringExtra(myConstants.EXTRA_MESSAGE);

        mMills = intent.getIntExtra(myConstants.EXTRA_TIMER, myConstants.DEFAULT_TIMER_DURATION);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        String action = intent.getAction();
        issueNotification(intent,message);

        if (action.equals(myConstants.ACTION_PING)) {
            issueNotification(intent,message);
        }else if (action.equals(myConstants.ACTION_SNOOZE)) {
            notificationManager.cancel(myConstants.NOTIFICATION_ID);
            issueNotification(intent,"");
        }else if (action.equals(myConstants.ACTION_DISMISS)) {
            notificationManager.cancel(myConstants.NOTIFICATION_ID);
        }


    }

    private void issueNotification(Intent intent, String msg) {
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent dissmissItent = new Intent(this,MyIntentService.class);
        dissmissItent.setAction(myConstants.ACTION_DISMISS);
        PendingIntent disIntent = PendingIntent.getService(this,0,dissmissItent,0);

        Intent snoozeIntent = new Intent(this,MyIntentService.class);
        snoozeIntent.setAction(myConstants.ACTION_SNOOZE);
        PendingIntent snoopIntent = PendingIntent.getService(this,0,snoozeIntent,0);

        builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle("Information")
                .setContentText("lalallalala")
                .setDefaults(Notification.DEFAULT_ALL)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
                .addAction(R.drawable.ic_launcher, "Dismiss", disIntent)
                .addAction(R.drawable.ic_launcher,"snooze",snoopIntent);


        Intent resultIntent = new Intent(this,MainActivity2.class);
        resultIntent.putExtra(myConstants.EXTRA_MESSAGE,msg);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);


        PendingIntent resultPendingIntent = PendingIntent.getActivity(this,0,resultIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(resultPendingIntent);
//        manager.notify(myConstants.NOTIFICATION_ID,builder.build());
        startTimer(mMills);



    }

    private void startTimer(int mMills) {
        try {

            Thread.sleep(mMills);

        }catch (Exception e) {
            Log.d(myConstants.DEBUG_TAG, "ERROR");
        }
        issueNotification(builder);
    }

    private void issueNotification(NotificationCompat.Builder builder) {
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        manager.notify(myConstants.NOTIFICATION_ID,builder.build());
    }
}
