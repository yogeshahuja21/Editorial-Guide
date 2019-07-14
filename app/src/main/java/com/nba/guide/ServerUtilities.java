package com.nba.guide;

import android.content.Context;
import android.util.Log;

import com.google.android.gcm.GCMRegistrar;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public final class ServerUtilities {

    private static final int MAX_ATTEMPTS = 5;
    private static final int BACKOFF_MILLI_SECONDS = 2000;
    private static final Random random = new Random();

    static boolean register(final Context context, final String regId) {

        SessionManager sessionManager = new SessionManager(context);

        Log.i(Constants.TAG2, "registering device (regId = " + regId + ")");

        if(regId!=null){
            sessionManager.setRegId(regId);
        }
       // String userId = Integer.toString(user_id);
       // String siteId = Integer.toString(site_id);
        //String serverUrl = Constants.SERVICE_URL + "/registergcm";
        Map<String, String> params = new HashMap<String, String>();
        params.put("regId", regId);
       // params.put("user_id", userId);
       // params.put("token", token);
       // params.put("site_id", siteId);

        long backoff = BACKOFF_MILLI_SECONDS + random.nextInt(1000);
        // Once GCM returns a registration id, we need to register it in the
        // demo server. As the server might be down, we will retry it a couple
        // times.
        for (int i = 1; i <= MAX_ATTEMPTS; i++) {
            Log.d(Constants.TAG2, "Attempt #" + i + " to register");

               // displayMessage(context, context.getString(
                //        R.string.server_registering, i, MAX_ATTEMPTS));
              //  post(serverUrl, params);
                GCMRegistrar.setRegisteredOnServer(context, true);
              //  String message = context.getString(R.string.server_registered);
               // displayMessage(context, message);
               // return true;

                // Here we are simplifying and retrying on any error; in a real
                // application, it should retry only on unrecoverable errors
                // (like HTTP error code 503).
                //Log.e(TAG2, "Failed to register on attempt " + i, e);
                if (i == MAX_ATTEMPTS) {
                    break;
                }
                try {
                   // Log.d(TAG2, "Sleeping for " + backoff + " ms before retry");
                    Thread.sleep(backoff);
                } catch (InterruptedException e1) {
                    // Activity finished before we complete - exit.
                   // Log.d(TAG2, "Thread interrupted: abort remaining retries!");
                    Thread.currentThread().interrupt();
                    return false;
                }
                // increase backoff exponentially
                backoff *= 2;

        }
        String message = context.getString(R.string.server_register_error,
                MAX_ATTEMPTS);
        //displayMessage(context, message);
        return false;
    }

    /**
     * Unregister this account/device pair within the server.
     */
    static void unregister(final Context context, final String regId, final int user_id, final String token, final int site_id) {
        //Log.i(TAG2, "unregistering device (regId = " + regId + ")");
        //String serverUrl = SERVICE_URL + "/unregister";
        Map<String, String> params = new HashMap<String, String>();
        params.put("regId", regId);


        //try {
           // post(serverUrl, params);
            GCMRegistrar.setRegisteredOnServer(context, false);
            String message = context.getString(R.string.server_unregistered);
            Constants.displayMessage(context, message);
       // } catch (IOException e) {

            //String message = context.getString(R.string.server_unregister_error,
           //         e.getMessage());
            Constants.displayMessage(context, message);
       // }
    }


   /* public static void post(String endpoint, Map<String, String> params)
            throws IOException {

        HttpClient httpClient = new DefaultHttpClient();
        HttpEntity httpEntity = null;
        HttpResponse httpResponse = null;

        String chaturl = SERVICE_URL + "/registergcm";
        HttpPost httpPost = new HttpPost(chaturl);

        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
        nameValuePair.add(new BasicNameValuePair("user_id", params.get("user_id")));
        nameValuePair.add(new BasicNameValuePair("token", params.get("token")));
        nameValuePair.add(new BasicNameValuePair("regid", params.get("regId")));
        nameValuePair.add(new BasicNameValuePair("site_id", params.get("site_id")));
        //Encoding POST data
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
        httpPost.addHeader("Authorization", params.get("token"));

        try {
            HttpResponse response = httpClient.execute(httpPost);
            httpEntity = response.getEntity();
            String response_str = EntityUtils.toString(httpEntity);
            // write response to log
            Log.i(TAG2, response_str);

        } catch (ClientProtocolException e) {
            // Log exception
            e.printStackTrace();
        } catch (IOException e) {
            // Log exception
            e.printStackTrace();
        }

    }*/


}
