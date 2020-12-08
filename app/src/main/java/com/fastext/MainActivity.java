package com.fastext;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.fastext.notification_fastext.MyListener;
import com.fastext.notification_fastext.NotificationReceiver;
import com.fastext.notification_fastext.NotificationService;

public class MainActivity extends AppCompatActivity implements MyListener {

    private TextView txtView;
    public static final String NOTIFICATION_CHANNEL_ID = "10001";
    private final static String default_notification_channel_id = "default";
    private String TAG = this.getClass().getSimpleName();

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new NotificationService().setListener(this);

        txtView = findViewById(R.id.textView);
        Button btnCreateNotification = findViewById(R.id. btnCreateNotification);
        Button btnActiveNotifications = findViewById(R.id.btn_active_read_notifications);

        btnActiveNotifications.setOnClickListener( v -> {
            Intent intent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
            startActivity(intent);
        });

        btnCreateNotification.setOnClickListener(v -> { this.sendNotification("TEST"); });
    }

    private void sendNotification(String message){
        RemoteViews collapsedView = new RemoteViews(getPackageName(),R.layout.notification_collapsed);
        RemoteViews expandedView = new RemoteViews(getPackageName(), R.layout.notification_expanded);

        Intent clickInOption = new Intent(this, NotificationReceiver.class);
        clickInOption.putExtra("message","Hola, como estas?");
        PendingIntent clickPendingIntent = PendingIntent.getBroadcast(this,0, clickInOption,0);
        expandedView.setOnClickPendingIntent(R.id.btn_option_1,clickPendingIntent);

        NotificationManager mNotificationManager = (NotificationManager) getSystemService( NOTIFICATION_SERVICE );
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(MainActivity. this, default_notification_channel_id );
        mBuilder.setContentTitle( "My Notification" );
        mBuilder.setTicker("Notification");
        mBuilder.setSmallIcon(R.drawable. ic_launcher_foreground);
        mBuilder.setCustomContentView(collapsedView);
        mBuilder.setCustomBigContentView(expandedView);
        mBuilder.setStyle(new NotificationCompat.DecoratedCustomViewStyle());

        mBuilder.setAutoCancel(true);
        if(Build.VERSION. SDK_INT >= Build.VERSION_CODES. O ){
            int importance = NotificationManager. IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel( NOTIFICATION_CHANNEL_ID , "NOTIFICATION_CHANNEL_NAME" , importance);
            mBuilder.setChannelId( NOTIFICATION_CHANNEL_ID );
            assert mNotificationManager != null;
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
        assert mNotificationManager != null;
        mNotificationManager.notify(( int ) System. currentTimeMillis () , mBuilder.build());
    }

    @Override
    public void setValue(String packageName,String text){
        txtView.append( " \n " + packageName + '\n' + text + '\n');
        if( "com.whatsapp".equals(packageName) ){
            sendNotification("prueba prueba prueba");
        }

    }
}