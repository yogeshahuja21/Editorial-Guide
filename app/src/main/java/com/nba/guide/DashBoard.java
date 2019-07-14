package com.nba.guide;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;


public class DashBoard extends AppCompatActivity {

    Context context;
    int count = 0;

//    ImageView notifi,back,menu_item,title_text;

    android.support.v7.app.ActionBar mActionBar;

    SessionManager sessionManager;

    AsyncHttpClient client;

    ProgressDialog progressDialog;

    Constant constant;

    DashBoardAdapter dashBoardAdapter;

    TextView title,title1,title2,title3, description,description1,description2,description3;

    ImageView imageView,imageView1,imageView2,imageView3;

    TextView change_pass;

    List<CategriesModel> list;

    Boolean isInternetPresent;

    LinearLayout layout,layout1,layout2,layout3;

    ConnectionDetector cd;

    String android_id;

    String version;

    String verCode;

    String regId;

    AsyncTask<Void, Void, Void> mRegisterTask;
    private TextView feed_count;
    private RelativeLayout notificationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_dash_board);
        notificationView = (RelativeLayout)findViewById(R.id.notification_view);
        feed_count = (TextView)findViewById(R.id.feedback_count);
        sessionManager = new SessionManager(getApplicationContext());
        client = new AsyncHttpClient();
        constant = new Constant(getApplicationContext());
        final DataHandler db = new DataHandler(getApplicationContext());
        List<NotificationModel>list1 = db.getAllContacts();

        if(list1.size()>0){
            for (int i=0;i<list1.size();i++){
                System.out.println("listsize"+list1.get(i).getId());
                if(list1.get(i).getId().equalsIgnoreCase("1")){
                    count = count+1;
                }
            }
        }
        sessionManager.setList_Size(String.valueOf(count));
        if(list1.size()>0 && count>0) {
            feed_count.setVisibility(View.VISIBLE);
            feed_count.setText(String.valueOf(count));
        }else {
            feed_count.setVisibility(View.GONE);
        }
        notificationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashBoard.this,Notification.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
        list = new ArrayList<>();
        change_pass = (TextView)findViewById(R.id.change_pass);
        cd = new ConnectionDetector(getApplicationContext());
        isInternetPresent = cd.isConnectingToInternet();
        imageView = (ImageView)findViewById(R.id.image_view);
        imageView1 = (ImageView)findViewById(R.id.image_view1);
        imageView2 = (ImageView)findViewById(R.id.image_view2);
        imageView3 = (ImageView)findViewById(R.id.image_view3);
        title = (TextView) findViewById(R.id.title);
        title1 = (TextView) findViewById(R.id.title1);
        title2 = (TextView) findViewById(R.id.title2);
        title3 = (TextView) findViewById(R.id.title3);
        description = (TextView) findViewById(R.id.discription);
        description1 = (TextView) findViewById(R.id.discription1);
        description2 = (TextView) findViewById(R.id.discription2);
        description3 = (TextView) findViewById(R.id.discription3);
        layout = (LinearLayout) findViewById(R.id.layout);
        layout1 = (LinearLayout) findViewById(R.id.layout1);
        layout2 = (LinearLayout) findViewById(R.id.layout2);
        layout3 = (LinearLayout) findViewById(R.id.layout3);
        regId = FirebaseInstanceId.getInstance().getToken();
        sessionManager.setRegId(regId);
        CountryList();
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CategriesModel model = list.get(0);
                int pff_cpont = model.getPdf_total();
                System.out.println("cat" + model.getTitle());
                if (pff_cpont==1){
                    int id1 =model.getId();
                    Intent intent = new Intent(DashBoard.this,PdfView.class);
                    intent.putExtra("cat",id1);
                    intent.putExtra("tit",model.getTitle());
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_out_left, R.anim.slide_out_right);
                }else {
                    int id1 =model.getId();
                    Intent intent = new Intent(getApplicationContext(),GuidLines.class);
                    intent.putExtra("cat",id1);
                    intent.putExtra("tit",model.getTitle());
                    intent.putExtra("data","0");
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_out_left, R.anim.slide_out_right);
                }
            }
        });
        layout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CategriesModel model = list.get(1);
                int pff_cpont = model.getPdf_total();
                System.out.println("cat" + model.getTitle());
                if (pff_cpont==1){
                    int id1 =model.getId();
                    Intent intent = new Intent(DashBoard.this,PdfView.class);
                    intent.putExtra("cat",id1);
                    intent.putExtra("tit",model.getTitle());
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_out_left, R.anim.slide_out_right);
                }else {
                    int id1 =model.getId();
                    Intent intent = new Intent(getApplicationContext(),GuidLines.class);
                    intent.putExtra("cat",id1);
                    intent.putExtra("tit",model.getTitle());
                    intent.putExtra("data","1");
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_out_left, R.anim.slide_out_right);
                }
            }
        });
        layout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CategriesModel model = list.get(2);
                int pff_cpont = model.getPdf_total();
                System.out.println("cat" + model.getTitle());
                System.out.println("cat" + pff_cpont);
                if (pff_cpont==1){
                    int id1 =model.getId();
                    Intent intent = new Intent(DashBoard.this,PdfView.class);
                    intent.putExtra("cat",id1);
                    intent.putExtra("tit",model.getTitle());
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_out_left, R.anim.slide_out_right);
                }else {
                    int id1 =model.getId();
                    Intent intent = new Intent(getApplicationContext(),GuidLines.class);
                    intent.putExtra("cat",id1);
                    intent.putExtra("tit",model.getTitle());
                    intent.putExtra("data","2");
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_out_left, R.anim.slide_out_right);
                }
            }
        });
        layout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CategriesModel model = list.get(3);
                int pff_cpont = model.getPdf_total();
                System.out.println("cat" + model.getTitle());
                if (pff_cpont==1){
                    int id1 =model.getId();
                    Intent intent = new Intent(DashBoard.this,PdfView.class);
                    intent.putExtra("cat",id1);
                    intent.putExtra("tit",model.getTitle());
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_out_left, R.anim.slide_out_right);
                }else {
                    int id1 =model.getId();
                    Intent intent = new Intent(getApplicationContext(),GuidLines.class);
                    intent.putExtra("cat",id1);
                    intent.putExtra("tit",model.getTitle());
                    intent.putExtra("data","0");
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_out_left, R.anim.slide_out_right);
                }
            }
        });
//        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//               CategriesModel model = list.get(position);
//                int pff_cpont = model.getPdf_total();
//
//                System.out.println("cat" + pff_cpont);
//
//
//                if (pff_cpont==1){
//                    int id1 =model.getId();
//                    Intent intent = new Intent(DashBoard.this,PdfView.class);
//                    intent.putExtra("cat",id1);
//                    intent.putExtra("tit",model.getTitle());
//                    startActivity(intent);
//                    overridePendingTransition(R.anim.slide_out_left, R.anim.slide_out_right);
//
//                }else {
//                    int id1 =model.getId();
//                    Intent intent = new Intent(getApplicationContext(),GuidLines.class);
//                    intent.putExtra("cat",id1);
//                    intent.putExtra("tit",model.getTitle());
//                    startActivity(intent);
//                    overridePendingTransition(R.anim.slide_out_left, R.anim.slide_out_right);
//                }
//            }
//        });


        //**GCM CODE **//

       // final String regId = GCMRegistrar.getRegistrationId(this);

        android_id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        System.out.println("DeviceId--->" + android_id);

        System.out.println("regID--->" + regId);

        PackageInfo pInfo = null;

        try {

            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        version = String.valueOf(pInfo.versionCode);

        System.out.println("DeviceId--->" + pInfo.versionName + "-----" + pInfo.versionCode);

        pInfo = null;
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        version = pInfo.versionName;

        verCode = String.valueOf(pInfo.versionCode);

        System.out.println("version" + version + "----"+verCode);

        PostGcm();

//        if (regId != null){
//            sessionManager.setRegId(regId);
//
//            PostGcm();
//        }
//
//        if (regId.equals("")) {
//
//            GCMRegistrar.register(this, Constants.SENDER_ID);
//
//        } else {
//
//            if (GCMRegistrar.isRegisteredOnServer(this)) {
//
//            } else {
//
//                final Context context = this;
//
//                mRegisterTask = new AsyncTask<Void, Void, Void>() {
//
//                    @Override
//                    protected Void doInBackground(Void... params) {
//
//                        boolean registered = ServerUtilities.register(context, regId);
//
//                        sessionManager.setRegId(regId);
//
//                        PostGcm();
//
//                        if (!registered) {
//
//                            GCMRegistrar.unregister(context);
//
//                        }
//                        return null;
//                    }
//
//                    @Override
//                    protected void onPostExecute(Void result) {
//                        mRegisterTask = null;
//                    }
//
//                };
//
//                mRegisterTask.execute(null, null, null);
//            }
//        }

        //** GCM Complete **//


//        mActionBar.setCustomView(mCustomView, new android.support.v7.app.ActionBar.LayoutParams
//
//        (android.support.v7.app.ActionBar.LayoutParams.MATCH_PARENT, android.support.v7.app.ActionBar.LayoutParams.MATCH_PARENT));
//
//        mActionBar.setDisplayShowCustomEnabled(true);

    }

    public void CountryList() {

        client.addHeader("Content-Type", "application/x-www-form-urlencoded");

        client.addHeader("Authorization", "Basic c2lkZGhhcnRoc0BuZXVyb25pbWJ1cy5jb206TmV1cm9AMTIz");

        System.out.println("response"+ constant.common + "categories");

        client.get(DashBoard.this, constant.common + "categories", null, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                progressDialog = new ProgressDialog(DashBoard.this);

                progressDialog.setMessage("Please Wait...");

                progressDialog.setIndeterminate(false);

                progressDialog.show();

                super.onStart();
            }

            @Override
            public void onSuccess(int i, String jsonObject) {
                super.onSuccess(i, jsonObject);

                System.out.println("Otp" + jsonObject);

                try {
                    JSONObject object = new JSONObject(jsonObject);

                    JSONArray jsonArray = object.getJSONArray("categories");


                    for(int j=0;j<jsonArray.length();j++){

                        CategriesModel model = new CategriesModel();

                        JSONObject object1 = jsonArray.getJSONObject(j);
                        if(j==0){
                            title.setText(object1.getString("title"));
                            description.setText(object1.getString("description"));
                            String afterDecode = URLDecoder.decode(object1.getString("app_icon"), "UTF-8");
                            Picasso.get().load(afterDecode).into(imageView);

                        }else if(j==1){

                            title1.setText(object1.getString("title"));
                            description1.setText(object1.getString("description"));
                            String afterDecode = URLDecoder.decode(object1.getString("app_icon"), "UTF-8");
                            Picasso.get().load(afterDecode).into(imageView1);
                        }else if(j==2){

                            title2.setText(object1.getString("title"));
                            description2.setText(object1.getString("description"));
                            String afterDecode = URLDecoder.decode(object1.getString("app_icon"), "UTF-8");
                            Picasso.get().load(afterDecode).into(imageView2);

                        }else if(j==3){

                            title3.setText(object1.getString("title"));
                            description3.setText(object1.getString("description"));
                            String afterDecode = URLDecoder.decode(object1.getString("app_icon"), "UTF-8");
                            Picasso.get().load(afterDecode).into(imageView3);

                        }

                        model.setId(object1.getInt("id"));

                        model.setPdf_total(object1.getInt("pdf_total"));

                        model.setTitle(object1.getString("title"));

                        System.out.println("TITEL" +object1.getString("title"));

                        model.setDescription(object1.getString("description"));

                        model.setApp_icon(object1.getString("app_icon"));

                        list.add(model);
                    }

                } catch (JSONException ex) {

                    ex.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Throwable throwable, String s) {
                super.onFailure(throwable, s);

                if (isInternetPresent) {


                } else {

                    showAlertDialog(DashBoard.this);
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                progressDialog.dismiss();
            }
        });
    }

    //*** GCM Register API ***//

    public void PostGcm() {

        RequestParams params = new RequestParams();

         String android_id = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);

        params.put("device_id", android_id);

        params.put("device_model", "23");

        params.put("device_type", "Android");

        System.out.println("RegId" +sessionManager.getRegId());

        params.put("device_token", sessionManager.getRegId());

        params.put("device_version", "1.0");

        client.addHeader("Content-Type", "application/x-www-form-urlencoded");

        client.addHeader("Authorization", constant.auth_value);

        System.out.println("GCMResponse" + constant.common + "register_device?" + params);

        client.post(getApplicationContext(), constant.common + "register_device?", params, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onSuccess(int i, String s) {
                super.onSuccess(i, s);

                try {

                    JSONObject jsonObject = new JSONObject(s.toString());

                    String s1= jsonObject.getString("success");

                    System.out.println("GCMDATA" +s1+jsonObject);

                } catch (JSONException e) {

                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Throwable throwable, String s) {
                super.onFailure(throwable, s);
            }

            @Override
            public void onFinish() {
                super.onFinish();
            }
        });

    }

    //** Finish **//


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private class RenderView extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... arg0) {

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            AlertDialog.Builder builder = new AlertDialog.Builder(DashBoard.this);

            builder.setMessage("Are you sure you want to logout?");

            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {

                    sessionManager.logoutUser();
                }
            });

            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.dismiss();
                }
            });

            AlertDialog alert = builder.create();

            alert.show();

        }
    }

    public void showAlertDialog(final Context context) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        alertDialogBuilder.setMessage("Please check your internet connection and try again.");

        alertDialogBuilder.setIcon(R.drawable.fail);

        alertDialogBuilder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                getApplicationContext();
            }
        });

        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alert = alertDialogBuilder.create();

        alert.show();

        alert.getWindow().getAttributes();

        TextView textView = (TextView) alert.findViewById(android.R.id.message);

        textView.setTextSize(16);

        textView.setPadding(15, 15, 15, 30);

        textView.setLineSpacing(1, 1);

        textView.setBackgroundColor(Color.parseColor("#ffffff"));

        textView.setTextColor(Color.parseColor("#000000"));

        textView.setGravity(Gravity.CENTER);

        Button btn1 = alert.getButton(DialogInterface.BUTTON_POSITIVE);

        Button btn2 = alert.getButton(DialogInterface.BUTTON_NEGATIVE);

        btn1.setTextSize(16);

        btn2.setTextSize(16);

        btn1.setPadding(10, 10, 10, 20);

        btn2.setPadding(10, 10, 10, 20);

        btn1.setTextColor(Color.parseColor("#73C14A"));

        btn2.setTextColor(Color.parseColor("#EC4444"));

    }

}
