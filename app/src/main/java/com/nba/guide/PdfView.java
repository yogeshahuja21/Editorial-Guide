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
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class PdfView extends AppCompatActivity {

    private WebView webView;

    ImageView back, notifi,title_text,menu_item;

    TextView english, hindi,urltype;

    TextView logo_text,notificount;

    ProgressDialog progressDialog;

    String id_cat,url,englishurl,hindiurl;

    android.support.v7.app.ActionBar mActionBar;

    AsyncHttpClient client;

    Constant constant;

    String eng ,titel;

    String hind;

    CategriesModel model;

    LinearLayout change_logout;

    TextView logout,change_pass;

    SessionManager sessionManager;

    Boolean isInternetPresent;

    ConnectionDetector cd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_pdf_view);

        client = new AsyncHttpClient();

        sessionManager = new SessionManager(getApplicationContext());

        mActionBar = getSupportActionBar();

        mActionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F4F5F7")));

        mActionBar.setDisplayShowHomeEnabled(false);

        mActionBar.setDisplayShowTitleEnabled(false);

        constant = new Constant(getApplicationContext());

        cd = new ConnectionDetector(getApplicationContext());

        isInternetPresent = cd.isConnectingToInternet();

        if (isInternetPresent) {


        } else {

            showAlertDialog(PdfView.this);
        }


        model = new CategriesModel();

        Intent intent = getIntent();

        id_cat = String.valueOf(intent.getExtras().getInt("cat", 0));

        LayoutInflater mInflater = LayoutInflater.from(this);

        View mCustomView = mInflater.inflate(R.layout.custombar, null);

        back = (ImageView) mCustomView.findViewById(R.id.back);

        notifi = (ImageView) mCustomView.findViewById(R.id.notifi);

        english = (TextView) findViewById(R.id.english);

        hindi = (TextView) findViewById(R.id.hindi);

        urltype = (TextView) findViewById(R.id.urltype);

        logo_text = (TextView) mCustomView.findViewById(R.id.logo_text);

        title_text = (ImageView)mCustomView.findViewById(R.id.title_text);

        menu_item = (ImageView)mCustomView.findViewById(R.id.menu_item);

        notificount = (TextView)mCustomView.findViewById(R.id.feedback_count);

        logo_text.setText(intent.getStringExtra("tit"));

        System.out.println("MODELTEXT" + intent.getStringExtra("tit"));

        logo_text.setTypeface(Typeface.DEFAULT_BOLD);

        title_text.setVisibility(View.GONE);

        logo_text.setVisibility(View.VISIBLE);

        guiline();



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
               // change_logout.setVisibility(View.GONE);
                Intent intent = new Intent(PdfView.this, Notification.class);
                startActivity(intent);

            }
        });

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

//        change_pass.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(change_logout.getVisibility()==View.VISIBLE){
//                    change_logout.setVisibility(View.GONE);
//
//                }                Intent intent = new Intent(PdfView.this, ChangePassword.class);
//                startActivity(intent);
//
//            }
//        });



        english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                english.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#012883")));
                hindi.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#aaEDEDED")));
                hindi.setTextColor(Color.parseColor("#868687"));
                english.setTextColor(Color.parseColor("#FFFFFF"));
                String googleDocs = "https://docs.google.com/viewer?embedded=true&url=";

                String pdf_url = eng;

                webView.loadUrl(googleDocs + pdf_url);

                System.out.println("URLTYPE" + pdf_url);
            }
        });

        hindi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                english.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#aaEDEDED")));
                hindi.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#012883")));
                hindi.setTextColor(Color.parseColor("#FFFFFF"));
                english.setTextColor(Color.parseColor("#868687"));

                String googleDocs = "https://docs.google.com/viewer?embedded=true&url=";

                String pdf_url = hind;

                webView.loadUrl(googleDocs + pdf_url);

                System.out.println("URLTYPE" + pdf_url);
            }
        });

//        logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new RenderView().execute();
//            }
//
//        });

        String URL = intent.getStringExtra("IMG");

        webView = (WebView) findViewById(R.id.webview);

        webView.getSettings().setJavaScriptEnabled(true);

        webView.getSettings().setBuiltInZoomControls(true);

        webView.getSettings().setDisplayZoomControls(true);

        webView.getSettings().setSupportZoom(true);

        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);

        increaseFont(webView);

        mActionBar.setCustomView(mCustomView, new android.support.v7.app.ActionBar.LayoutParams

                (android.support.v7.app.ActionBar.LayoutParams.MATCH_PARENT, android.support.v7.app.ActionBar.LayoutParams.MATCH_PARENT));

        mActionBar.setDisplayShowCustomEnabled(true);

    }


    public void increaseFont(WebView webView) {
        int font = webView.getSettings().getTextZoom();
        int newFont = font + 35;
        webView.getSettings().setTextZoom(newFont);
    }



    public void guiline() {

        RequestParams params = new RequestParams();

        params.put("category_id", id_cat);

        System.out.println("IDCAT" + id_cat);

        client.addHeader("Content-Type", "application/x-www-form-urlencoded");

        client.addHeader("Authorization", "Basic c2lkZGhhcnRoc0BuZXVyb25pbWJ1cy5jb206TmV1cm9AMTIz");

        System.out.println("response" + constant.common + "pdf_documents");

        client.post(PdfView.this, constant.common + "pdf_documents", params, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                progressDialog = new ProgressDialog(PdfView.this);

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

                    for (int j = 0; j < jsonArray.length(); j++) {

                        PdfDataModel pdfDataModel = new PdfDataModel();

                        JSONObject object1 = jsonArray.getJSONObject(j);

                        System.out.println("DATAPDF11" + object1);

                        englishurl = object1.getString("pdf_english");

                        hindiurl = object1.getString("pdf_hindi");

                        try {
                            eng = URLDecoder.decode(englishurl, "UTF-8");
                            hind = URLDecoder.decode(hindiurl, "UTF-8");

                            String googleDocs = "https://docs.google.com/viewer?embedded=true&url=";

                            String pdf_url = eng;

                            webView.loadUrl(googleDocs + pdf_url);

                            System.out.println("URLTYPE"  +  pdf_url);

                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }


                } catch (JSONException ex) {

                    ex.printStackTrace();
                }

            }

            @Override
            public void onFailure(Throwable throwable, String s) {
                super.onFailure(throwable, s);
                if (isInternetPresent) {


                } else {

                    showAlertDialog(PdfView.this);
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

            AlertDialog.Builder builder = new AlertDialog.Builder(PdfView.this);

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
