package com.fastext.notification_fastext;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationManagerCompat;

public class NotificationReceiver extends BroadcastReceiver{
    private String TAG = this.getClass().getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {

        String message = intent.getStringExtra("message");

        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.cancel(1);
    }

}
