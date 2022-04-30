package com.mhdarslan.notificationsample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private static final String CHANNEL_ID = "My Channel";
    private static final int NOTIFICATION_ID = 100;
    private static final int REQ_CODE = 100;

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
        * CUSTOMIZING NOTIFICATION
        *
        * Create Intent which you want to be open
        * i.e : Intent intentNotify
        * addFlags to this intent because we don't want to be make it duplicate the activities on background
        *
        * Create a pending intent
        * also create a REQ_CODE for unique request for the pending intent
        * i.e : PendingIntent pendingIntent
        *
        * Big Picture Style Notification
        *
        * */

        Drawable drawable = ResourcesCompat.getDrawable(getResources(),R.drawable.new_icon, null);
        BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
        Bitmap largeIcon = bitmapDrawable.getBitmap();

        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notification;

        Intent intentNotify = new Intent(getApplicationContext(),MainActivity.class);
        intentNotify.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // it will clear all the activities from the stack

        // here 'PendingIntent.FLAG_UPDATE_CURRENT' will update the already existed Intent and will never create multiple instances withing the stack
        PendingIntent pendingIntent = PendingIntent.getActivity(this,REQ_CODE,intentNotify,PendingIntent.FLAG_UPDATE_CURRENT);

        // Big Picture Style
        Notification.BigPictureStyle bigPictureStyle = new Notification.BigPictureStyle()
//                .bigPicture(((BitmapDrawable) (ResourcesCompat.getDrawable(getResources(),R.drawable.new_icon, null))).getBitmap())
                .bigPicture(largeIcon)
                .bigLargeIcon(largeIcon)
                .setBigContentTitle("Image sent by Arslan")
                .setSummaryText("Image Message");

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notification = new Notification.Builder(this)
                    .setLargeIcon(largeIcon)
                    .setSmallIcon(R.drawable.ic_message)
                    .setContentText("New Message")
                    .setSubText("New Message from Arslan")
                    .setContentIntent(pendingIntent) // this will open the required INTENT
                    .setStyle(bigPictureStyle) // this will create a Picture style notification
                    .setChannelId(CHANNEL_ID)
                    .build();
            nm.createNotificationChannel(new NotificationChannel(CHANNEL_ID,"New Channel",NotificationManager.IMPORTANCE_HIGH));
        }else {
            notification = new Notification.Builder(this)
                    .setLargeIcon(largeIcon)
                    .setSmallIcon(R.drawable.new_icon)
                    .setContentText("New Message")
                    .setSubText("new Message from Arslan")
                    .setContentIntent(pendingIntent) // this will open the required INTENT
                    .setStyle(bigPictureStyle) // this will create a Picture style notification
                    .build();
        }

        nm.notify(NOTIFICATION_ID,notification);


    }
}