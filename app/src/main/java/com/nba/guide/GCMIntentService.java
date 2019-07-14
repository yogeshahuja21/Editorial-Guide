package com.nba.guide;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;
import com.google.android.gcm.GCMRegistrar;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class GCMIntentService extends GCMBaseIntentService {


   @SuppressWarnings("hiding")

    private static final String TAG = "GCMIntentService";

    int user_id = 0;

    int site_id = 0;

    String token = "";

    android.support.v4.app.TaskStackBuilder stackBuilder;

    PendingIntent resultPendingIntent;

    private NotificationManager mNotificationManager;

    private int notificationID = 100;

    private int numMessages = 0;

    public static  int NOTIFICATION_ID = 1;

    NotificationCompat.Builder builder;

    public static String item_type;

    public static String extras_data;

    public static String alert;

    public static String badge;

    public static Intent resultIntent;

    public static String item_id;

    public static Intent notificationIntent;

    public GCMIntentService() {
        super("91514789263");
    }

    @Override
    protected void onRegistered(Context context, String registrationId) {

        SessionManager session = new SessionManager(context);

        String reg_id = registrationId;

        System.out.println("rehid   "+reg_id);

        ServerUtilities.register(context, registrationId);
    }

    @Override
    protected void onUnregistered(Context context, String registrationId) {

       if (GCMRegistrar.isRegisteredOnServer(context)) {

            SessionManager session = new SessionManager(context);

            ServerUtilities.unregister(context, registrationId, user_id, token, site_id);

        } else {

        }
    }

    @Override
    protected void onMessage(Context context, Intent intent) {
        DataHandler db = new DataHandler(this);

        Log.i(TAG, "Received message");

        Bundle extras = intent.getExtras();

        alert = intent.getExtras().getString("alert");

        item_type = intent.getExtras().getString("item_type");

        extras_data = intent.getExtras().getString("message");

        badge = intent.getExtras().getString("badge");

        item_id = intent.getExtras().getString("item_id");

        Log.i("C2DM", extras.toString()+"-------"+extras_data);

        String msg = intent.getStringExtra("message");

        intent = new Intent(getApplicationContext(), LoginActivity.class);

        // notifies user
        Log.d("Insert: ", "Inserting ..");
        String time = getDateTime();
        String readvalue = "0";
        NotificationModel notificationModel = new NotificationModel();

        notificationModel.setDescription(extras_data);
        notificationModel.setNotification_date(time);
        notificationModel.setId("1");
        db.addContact(notificationModel);

        displayNotification(extras_data);

    }

    @Override
    protected void onDeletedMessages(Context context, int total) {

        Log.i(TAG, "Received deleted messages notification");

    }

    @Override
    public void onError(Context context, String errorId) {

        Log.i(TAG, "Received error: " + errorId);

    }

    @Override
    protected boolean onRecoverableError(Context context, String errorId) {

        return super.onRecoverableError(context, errorId);
    }

    protected void displayNotification(String message) {

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(GCMIntentService.this);

        mBuilder.setContentTitle("NBA");

        mBuilder.setContentText(message);

        mBuilder.setTicker("New Message Alert!");

        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);

        mBuilder.setSmallIcon(R.mipmap.ic_launcher);

        mBuilder.setLargeIcon(largeIcon);

        mBuilder.setOngoing(false);

        mBuilder.setPriority(Notification.PRIORITY_HIGH);

        mBuilder.setLights(Color.MAGENTA, 500, 500);

        long[] pattern = {500, 500, 500, 500, 500, 500, 500, 500, 500};

        mBuilder.setVibrate(pattern);

        mBuilder.setStyle(new NotificationCompat.InboxStyle());

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        mBuilder.setSound(alarmSound);

            stackBuilder = android.support.v4.app.TaskStackBuilder.create(GCMIntentService.this);

            stackBuilder.addParentStack(com.nba.guide.DashBoard.class);

            resultIntent = new Intent(GCMIntentService.this, com.nba.guide.Notification.class);

            resultIntent.putExtra("Extras", extras_data);

            resultIntent.putExtra("BACK_ID", item_id);

            stackBuilder.addNextIntent(resultIntent);

        mBuilder.setAutoCancel(true);

        resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_ONE_SHOT);

        mBuilder.setContentIntent(resultPendingIntent);

        Notification noti = mBuilder.build();

        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(NOTIFICATION_ID, noti);

        NOTIFICATION_ID++;
    }

    private String getDateTime() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }


}


