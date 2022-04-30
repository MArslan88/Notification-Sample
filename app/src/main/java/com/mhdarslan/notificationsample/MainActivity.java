package com.mhdarslan.notificationsample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private static final String CHANNEL_ID = "My Channel";
    private static final int NOTIFICATION_ID = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        * NotificationManager
        * Notification
        * largeIcon (this should be change into Bitmap first)
        * set icons and text for notification, in the end always use .build() to complete the notification
        *
        * now use CHANNEL
        * add setChannelId() within 'notification'
        * it will ask you to wrap within if else to check OS version
        * now createNotificationChannel for NotificationManager
        * now use 'nm.notify(NOTIFICATION_ID , notification) to notify the Notifications
        *
        * */

        Drawable drawable = ResourcesCompat.getDrawable(getResources(),R.drawable.new_icon, null);
        BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
        Bitmap largeIcon = bitmapDrawable.getBitmap();

        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notification;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notification = new Notification.Builder(this)
                    .setLargeIcon(largeIcon)
                    .setSmallIcon(R.drawable.ic_message)
                    .setContentText("New Message")
                    .setSubText("New Message from Arslan")
                    .setChannelId(CHANNEL_ID)
                    .build();
            nm.createNotificationChannel(new NotificationChannel(CHANNEL_ID,"New Channel",NotificationManager.IMPORTANCE_HIGH));
        }else {
            notification = new Notification.Builder(this)
                    .setLargeIcon(largeIcon)
                    .setSmallIcon(R.drawable.new_icon)
                    .setContentText("New Message")
                    .setSubText("new Message from Arslan")
                    .build();
        }

        nm.notify(NOTIFICATION_ID,notification);


    }
}