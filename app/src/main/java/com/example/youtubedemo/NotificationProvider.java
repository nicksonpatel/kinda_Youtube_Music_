package com.example.youtubedemo;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotificationProvider{

    /**
     *This is the Notification Channel ID. More about this in the next section
     */
    private String NOTIFICATION_CHANNEL_ID = null;
    /**
     * User visible Channel Name
     */
    private String NOTIFICATION_CHANNEL_NAME = null;
    /**
     * Importance applicable to all the notifications in this Channel
     */
    private int importance = NotificationManager.IMPORTANCE_DEFAULT;
    /**
     * Notification ID to identify notification
     */
    private int NOTIFICATION_ID = 0;
    /**
     * Notification Icon
     */
    private int NOTIFICATION_ICON = 0;
    /**
     * Notification title
     */
    private String NOTIFICATION_TITLE = null;
    /**
     * Notification Description
     */
    private String NOTIFICATION_DESCRIPTION = null;


    private int NOTIFICATION_PRIORITY=NotificationCompat.PRIORITY_MAX;

    private  boolean alwaysOnNotification=false;

    private static NotificationManagerCompat notificationManagerCompat;

    private Notification notification;

    private NotificationCompat.Builder builder;

    private Context context;

    private Activity activity;

    private SharedPreferences mPreferences;

    public static String sharedPrefFile = "com.example.android.hellosharedprefs";

    public NotificationProvider(Context context, Activity activity) {
        this.context=context;
        this.activity=activity;
    }


    public void createNotificationChannel(String Channel_id,String Channel_name) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        NOTIFICATION_CHANNEL_ID=Channel_id;
        NOTIFICATION_CHANNEL_NAME=Channel_name;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID,NOTIFICATION_CHANNEL_NAME, importance);
            channel.setDescription("demo notification");

            //Boolean value to set if lights are enabled for Notifications from this Channel
            channel.enableLights(true);
            //Boolean value to set if vibration are enabled for Notifications from this Channel
            channel.enableVibration(true);
            //Sets the color of Notification Light
            channel.setLightColor(Color.GREEN);
            //Set the vibration pattern for notifications. Pattern is in milliseconds with the format {delay,play,sleep,play,sleep...}
//            channel.setVibrationPattern(new long[] {
//                    500,
//                    500,
//                    500,
//                    500,
//                    500
//            });
            //Sets whether notifications from these Channel should be visible on Lockscreen or not
            channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);

            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = activity.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void setupNotification(int notification_id,int notificaton_icon,String title,String notification_text) {
        NOTIFICATION_ID=notification_id;
        NOTIFICATION_ICON=notificaton_icon;
        NOTIFICATION_TITLE=title;
        NOTIFICATION_DESCRIPTION=notification_text;
    }

    public void sendNotification() {
        if(NOTIFICATION_CHANNEL_ID != null && NOTIFICATION_CHANNEL_NAME != null) {

            if (NOTIFICATION_ID != 0 && NOTIFICATION_ICON != 0 && NOTIFICATION_TITLE != null && NOTIFICATION_DESCRIPTION != null) {

                 builder = new NotificationCompat.Builder(activity, NOTIFICATION_CHANNEL_ID)
                        .setSmallIcon(NOTIFICATION_ICON)
                        .setContentTitle(NOTIFICATION_TITLE)
                        .setContentText(NOTIFICATION_DESCRIPTION)
                        .setPriority(NOTIFICATION_PRIORITY);
                notification = builder.build();
                if(alwaysOnNotification) {
                    notification.flags |= Notification.FLAG_NO_CLEAR
                            | Notification.FLAG_ONGOING_EVENT;
                }
                notificationManagerCompat = NotificationManagerCompat.from(context);
                notificationManagerCompat.notify(NOTIFICATION_ID, notification);

            } else {
                Log.e("NotificatonProvider", "Notification setup missing");
            }
        } else {
            Log.e("NotificatonProvider","Notificaton Channel creation missing");
        }
    }

    public void setImportance(int importance) {
        this.importance=importance;
    }

    public void setNotificationPriority(int priority) {
        NOTIFICATION_PRIORITY=priority;
    }

    public void setAlwaysOnNotification(boolean alwaysOnNotification) {
        this.alwaysOnNotification=alwaysOnNotification;
    }

    public void updateNotification(String title,String notification_text) {
        NOTIFICATION_TITLE=title;
        NOTIFICATION_DESCRIPTION=notification_text;
        sendNotification();
    }

    public void destroyNotification() {
        notificationManagerCompat.cancel(NOTIFICATION_ID);
    }

    public int getNOTIFICATION_ID() {
        return NOTIFICATION_ID;
    }

    public static void destroyByNotificationByID(int noticatoinid) {
        notificationManagerCompat.cancel(noticatoinid);
    }
}

