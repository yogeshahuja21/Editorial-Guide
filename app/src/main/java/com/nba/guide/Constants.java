package com.nba.guide;

import android.content.Context;
import android.content.Intent;


public final class Constants {


    // Google project id

    public static final String SENDER_ID = "91514789263";

    /**
     * Tag used on log messages.
     */

    public static final String TAG2 = "JMart GCM";

    public static final String DISPLAY_MESSAGE_ACTION ="com.JMart.DISPLAY_MESSAGE";

    public static final String EXTRA_MESSAGE = "message";

    private static final int SECOND_MILLIS = 1000;

    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;

    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;

    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;

    static Context mContext;

    public Constants(Context c) {

        super();

        mContext = c;
    }

    public static void displayMessage(Context context, String message) {

        Intent intent = new Intent(DISPLAY_MESSAGE_ACTION);

        intent.putExtra(EXTRA_MESSAGE, message);

        context.sendBroadcast(intent);
    }

}






