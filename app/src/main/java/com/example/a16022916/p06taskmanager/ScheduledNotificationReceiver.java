package com.example.a16022916.p06taskmanager;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

public class ScheduledNotificationReceiver extends BroadcastReceiver {

    int reqCode = 12345;

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("default", "Default Channel",
                    NotificationManager.IMPORTANCE_DEFAULT);

            channel.setDescription("This is for default notificaiton");
            notificationManager.createNotificationChannel(channel);
        }

        Intent i = new Intent(context,MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(context,reqCode,i,PendingIntent.FLAG_CANCEL_CURRENT);

        // build notification
        String strName = intent.getStringExtra("name");
        String strDesc = intent.getStringExtra("desc");
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"default");
        builder.setContentTitle(strName);
        builder.setContentText(strDesc);
        builder.setSmallIcon(android.R.drawable.ic_dialog_info);
        builder.setContentIntent(pIntent);
        builder.setAutoCancel(true);

        Notification n = builder.build();
        notificationManager.notify(123, n);
    }
}
