package com.fastext.notification_fastext;

import android.app.Notification;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
public class NotificationService extends NotificationListenerService {

    private String TAG = this.getClass().getSimpleName();
    Context context ;
    static MyListener myListener;

    @Override
    public void onCreate () {
        super .onCreate() ;
        context = getApplicationContext() ;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onNotificationPosted (StatusBarNotification sbn) {


        Bundle extras = sbn.getNotification().extras;
        String text = "";
        Log.i(TAG ,"********** onNotificationPosted");
        Log.i(TAG ,"ID :" + sbn.getId() + " \t " + sbn.getNotification(). tickerText + " \t " + sbn.getPackageName());

        try{
            if(extras.get(Notification.EXTRA_TEXT) != null){
                text = extras.get(Notification.EXTRA_TEXT).toString();
            }else{
                text = "no hay texto";
            }

            myListener.setValue(sbn.getPackageName(),text);
        }catch (Exception e){
            Log.i(TAG ,"error:" + e.getMessage());
        }
        //text
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn){

    }

    public void setListener (MyListener myListener) {
        NotificationService. myListener = myListener;
    }

}
