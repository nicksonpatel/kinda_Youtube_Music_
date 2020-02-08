package com.example.youtubedemo;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class NotificationService extends Service{

    private int Notificatin_id;

    public NotificationService() {
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Notificatin_id=(int)intent.getExtras().get("noti_id");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        closeNotification(Notificatin_id);
        stopSelf();
    }

    @Override
    public void onDestroy() {
        closeNotification(Notificatin_id);
        super.onDestroy();
    }

    private void closeNotification(int notificationid) {
        NotificationProvider.destroyByNotificationByID(notificationid);
    }
}
