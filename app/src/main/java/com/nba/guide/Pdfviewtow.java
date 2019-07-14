package com.nba.guide;

import android.app.AlertDialog;
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

public class Pdfviewtow extends AppCompatActivity {

    String eng;

    String hind;

    TextView english, hindi,urltype,notificount;

    private WebView view_web;

    ImageView back, notifi,title_text,menu_item;

    TextView logo_text;

    String pdf_url;

    String googleDocs;

    String type=null;

    public static  LinearLayout change_logout;

    TextView logout,change_pass;

    SessionManager sessionManager;

    Boolean isInternetPresent;

    ConnectionDetector cd;

    String texttitle;

    android.support.v7.app.ActionBar mActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_pdfviewtow);

        mActionBar = getSupportActionBar();

        sessionManager = new SessionManager(getApplicationContext());

        mActionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F4F5F7")));

        mActionBar.setDisplayShowHomeEnabled(false);

        mActionBar.setDisplayShowTitleEnabled(false);

        view_web = (WebView) findViewById(R.id.view_web);

        english = (TextView) findViewById(R.id.english);

        hindi = (TextView) findViewById(R.id.hindi);


        LayoutInflater mInflater = LayoutInflater.from(this);

        View mCustomView = mInflater.inflate(R.layout.custombar, null);

        back = (ImageView) mCustomView.findViewById(R.id.back);

        notifi = (ImageView) mCustomView.findViewById(R.id.notifi);

        notificount = (TextView)mCustomView.findViewById(R.id.feedback_count);

        logo_text = (TextView) mCustomView.findViewById(R.id.logo_text);

        title_text = (ImageView)mCustomView.findViewById(R.id.title_text);

        menu_item = (ImageView)mCustomView.findViewById(R.id.menu_item);

        cd = new ConnectionDetector(getApplicationContext());

        isInternetPresent = cd.isConnectingToInternet();

        if (isInternetPresent) {


        } else {

            showAlertDialog(Pdfviewtow.this);
        }

        int size = Integer.parseInt(sessionManager.getList_Size());



        if(size>0){
            notificount.setVisibility(View.VISIBLE);
            notificount.setText(String.valueOf(size));
        }else {
            notificount.setVisibility(View.GONE);

        }
        menu_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(change_logout.getVisibility()==View.VISIBLE){
//                    change_logout.setVisibility(View.GONE);
//
//                }

                Intent intent = new Intent(Pdfviewtow.this, Notification.class);
                startActivity(intent);
            }
        });

        Intent intent = getIntent();

        if(intent.getExtras()!=null){

            eng = String.valueOf(intent.getExtras().getString("ENG"));

            hind = String.valueOf(intent.getExtras().getString("HIND"));

            logo_text.setText(intent.getStringExtra("texttit"));

            System.out.println("MODELTEXT" + intent.getStringExtra("tit"));

            System.out.println("MODELTEXT" + eng+"  "+ hind);

            if(eng.equalsIgnoreCase("") || hind.equalsIgnoreCase("")){
                english.setVisibility(View.GONE);
                hindi.setVisibility(View.GONE);
            }
            else {
                english.setVisibility(View.VISIBLE);
                hindi.setVisibility(View.VISIBLE);
            }


            type = intent.getExtras().getString("Type");
        }

        logo_text.setTypeface(Typeface.DEFAULT_BOLD);

        title_text.setVisibility(View.GONE);

        logo_text.setVisibility(View.VISIBLE);

        menu_item = (ImageView)mCustomView.findViewById(R.id.menu_item);

        //logout = (TextView)findViewById(R.id.logout);

        //change_logout = (LinearLayout)findViewById(R.id.change_logout);

       // change_pass = (TextView)findViewById(R.id.change_pass);

       // change_logout.setVisibility(View.GONE);
//
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
//                Intent intent = new Intent(Pdfviewtow.this, ChangePassword.class);
//                startActivity(intent);
//
//            }
//        });
//
//        menu_item.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (change_logout.getVisibility() == View.GONE) {
//                    change_logout.setVisibility(View.VISIBLE);
//                } else {
//                    change_logout.setVisibility(View.GONE);
//                }
//            }
//        });

        if(type.equalsIgnoreCase("H")){

            googleDocs = "https://docs.google.com/viewer?embedded=true&url=";

            pdf_url = hind;

            view_web.loadUrl(googleDocs + pdf_url);

            System.out.println("URLTYPE" + pdf_url);


            english.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#aaEDEDED")));
            hindi.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#012883")));
            hindi.setTextColor(Color.parseColor("#FFFFFF"));
            english.setTextColor(Color.parseColor("#868687"));


        }else {
            googleDocs = "https://docs.google.com/viewer?embedded=true&url=";

            pdf_url = eng;

            english.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#012883")));
            hindi.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#aaEDEDED")));
            hindi.setTextColor(Color.parseColor("#868687"));
            english.setTextColor(Color.parseColor("#FFFFFF"));

            view_web.loadUrl(googleDocs + pdf_url);

            System.out.println("URLTYPE" + pdf_url);

        }


        english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                english.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#012883")));
                hindi.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#aaEDEDED")));
                hindi.setTextColor(Color.parseColor("#868687"));
                english.setTextColor(Color.parseColor("#FFFFFF"));
                String googleDocs = "https://docs.google.com/viewer?embedded=true&url=";

                String pdf_url = eng;

                view_web.loadUrl(googleDocs + pdf_url);

                System.out.println("HINDIURL" + pdf_url);
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

                view_web.loadUrl(googleDocs + pdf_url);

                System.out.println("ENGLISHURL" + pdf_url);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // change_logout.setVisibility(View.GONE);
                onBackPressed();
            }
        });

        view_web = (WebView) findViewById(R.id.view_web);

        view_web.getSettings().setJavaScriptEnabled(true);

        view_web.getSettings().setBuiltInZoomControls(true);

        view_web.getSettings().setDisplayZoomControls(true);

        view_web.getSettings().setSupportZoom(true);

        increaseFont(view_web);

        mActionBar.setCustomView(mCustomView, new android.support.v7.app.ActionBar.LayoutParams

                (android.support.v7.app.ActionBar.LayoutParams.MATCH_PARENT, android.support.v7.app.ActionBar.LayoutParams.MATCH_PARENT));

        mActionBar.setDisplayShowCustomEnabled(true);

    }

    public void increaseFont(WebView webView) {
        int font = webView.getSettings().getTextZoom();
        int newFont = font + 35;
        webView.getSettings().setTextZoom(newFont);
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

            AlertDialog.Builder builder = new AlertDialog.Builder(Pdfviewtow.this);

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
