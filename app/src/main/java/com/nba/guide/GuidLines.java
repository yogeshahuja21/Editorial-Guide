package com.nba.guide;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GuidLines extends AppCompatActivity {


    ListView guidline_view;

    Context context;

    ArrayList prgmName;

    ImageView notifi,title_text,back,menu_item;

    TextView logo_text,notificount;

    Constant constant;

    SessionManager sessionManager;

    AsyncHttpClient client;

    ProgressDialog progressDialog;

    GuidLineAdapter guidLineAdapter;

    public static LinearLayout change_logout;

    TextView logout,change_pass;

    String data ="0";

    String id_cat;

    String str = null;

    Boolean isInternetPresent;

    ConnectionDetector cd;

    List<PdfDataModel> list;

    android.support.v7.app.ActionBar mActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_guid_lines);

        sessionManager = new SessionManager(getApplicationContext());

        constant = new Constant(getApplicationContext());

        client = new AsyncHttpClient();

        list = new ArrayList<>();

        mActionBar = getSupportActionBar();

        mActionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F4F5F7")));

        mActionBar.setDisplayShowHomeEnabled(false);

        mActionBar.setDisplayShowTitleEnabled(false);

        LayoutInflater mInflater = LayoutInflater.from(this);

        View mCustomView = mInflater.inflate(R.layout.custombar, null);

        logo_text = (TextView) mCustomView.findViewById(R.id.logo_text);

        Intent intent = getIntent();

        id_cat = String.valueOf(intent.getExtras().getInt("cat", 0));

        System.out.println("back"+ intent.getExtras().getInt("cat",0));

        guiline();

        logo_text.setText(intent.getStringExtra("tit"));

        System.out.println("MODELTEXT" + intent.getStringExtra("tit"));

        data = intent.getStringExtra("data");
        logo_text.setTypeface(Typeface.DEFAULT_BOLD);

        notifi = (ImageView)mCustomView.findViewById(R.id.notifi);

        title_text = (ImageView)mCustomView.findViewById(R.id.title_text);

        back = (ImageView)mCustomView.findViewById(R.id.back);

        logo_text.setVisibility(View.VISIBLE);

        title_text.setVisibility(View.GONE);

        notifi = (ImageView)mCustomView.findViewById(R.id.notifi);

        menu_item = (ImageView)mCustomView.findViewById(R.id.menu_item);

        notificount = (TextView)mCustomView.findViewById(R.id.feedback_count);

        logout = (TextView)findViewById(R.id.logout);

       // change_logout = (LinearLayout)findViewById(R.id.change_logout);

        change_pass = (TextView)findViewById(R.id.change_pass);

        cd = new ConnectionDetector(getApplicationContext());

        isInternetPresent = cd.isConnectingToInternet();

        if (isInternetPresent) {


        } else {

            showAlertDialog(GuidLines.this);
        }

        back.setVisibility(View.VISIBLE);

        //change_logout.setVisibility(View.GONE);


        int size = Integer.parseInt(sessionManager.getList_Size());



        if(size>0){
            notificount.setVisibility(View.VISIBLE);
            notificount.setText(String.valueOf(size));
        }else {
            notificount.setVisibility(View.GONE);

        }


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // change_logout.setVisibility(View.GONE);
                onBackPressed();
            }
        });

        menu_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(change_logout.getVisibility()==View.VISIBLE){
//                    change_logout.setVisibility(View.GONE);
//
//                }

                Intent intent = new Intent(GuidLines.this, Notification.class);
                startActivity(intent);
            }
        });

//        logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                change_logout.setVisibility(View.GONE);
//                new RenderView().execute();
//            }
//
//        });
//
//        change_pass.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                change_logout.setVisibility(View.GONE);
//                Intent intent = new Intent(GuidLines.this, ChangePassword.class);
//                startActivity(intent);
//
//            }
//        });

//        menu_item.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(change_logout.getVisibility()==View.GONE){
//                    change_logout.setVisibility(View.VISIBLE);
//                }else {
//                    change_logout.setVisibility(View.GONE);
//                }
//            }
//        });



        guidline_view=(ListView) findViewById(R.id.guidline_view);



        mActionBar.setCustomView(mCustomView, new android.support.v7.app.ActionBar.LayoutParams

                (android.support.v7.app.ActionBar.LayoutParams.MATCH_PARENT, android.support.v7.app.ActionBar.LayoutParams.MATCH_PARENT));

        mActionBar.setDisplayShowCustomEnabled(true);


    }

    public void guiline() {

        RequestParams params = new RequestParams();

        params.put("category_id", id_cat);

        System.out.println("IDCAT" +id_cat);

        client.addHeader("Content-Type", "application/x-www-form-urlencoded");

        client.addHeader("Authorization", "Basic c2lkZGhhcnRoc0BuZXVyb25pbWJ1cy5jb206TmV1cm9AMTIz");

        System.out.println("response"+ constant.common + "pdf_documents");

        client.post(GuidLines.this, constant.common + "pdf_documents", params, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                progressDialog = new ProgressDialog(GuidLines.this);

                progressDialog.setMessage("Please Wait...");

                progressDialog.setIndeterminate(false);

                progressDialog.show();

                super.onStart();
            }

            @Override
            public void onSuccess(int i, String jsonObject) {
                super.onSuccess(i, jsonObject);

                System.out.println("DATAPDF" + jsonObject);

                try {
                    JSONObject object = new JSONObject(jsonObject);

                    JSONArray jsonArray = object.getJSONArray("pdf_documents");


                    for(int j=0;j<jsonArray.length();j++){

                        PdfDataModel pdfDataModel = new PdfDataModel();

                        JSONObject object1 = jsonArray.getJSONObject(j);

                        System.out.println("PDFHINDI" + object1);

                        pdfDataModel.setId(object1.getInt("id"));

                        pdfDataModel.setTitle(object1.getString("title"));

                        parseDateToddMMyyyy(object1.getString("pdf_date"));

                        pdfDataModel.setDate_time(str);

                        pdfDataModel.setDescription(object1.getString("pdf_english"));

                        pdfDataModel.setHindi(object1.getString("pdf_hindi"));

                        pdfDataModel.setCategory_title(object1.getString("category_title"));

                        list.add(pdfDataModel);
                    }

                    guidLineAdapter = new GuidLineAdapter(GuidLines.this,list,data);

                    guidline_view.setAdapter(guidLineAdapter);

                } catch (JSONException ex) {

                    ex.printStackTrace();
                }

            }

            @Override
            public void onFailure(Throwable throwable, String s) {
                super.onFailure(throwable, s);
                if (isInternetPresent) {


                } else {

                    showAlertDialog(GuidLines.this);
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                progressDialog.dismiss();
            }
        });

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

            AlertDialog.Builder builder = new AlertDialog.Builder(GuidLines.this);

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

    public String parseDateToddMMyyyy(String time) {

        String inputPattern = "yyyy-MM-dd";

        String outputPattern = "dd MMMM yyyy";

        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);

        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;

        try {
            date = inputFormat.parse(time);

            str = outputFormat.format(date);

        } catch (ParseException e) {

            e.printStackTrace();
        }
        return str;
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
