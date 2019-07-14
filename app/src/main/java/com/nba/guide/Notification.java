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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;


import java.util.ArrayList;
import java.util.List;


public class Notification extends AppCompatActivity {

    ListView noti_listr;

    Context context;

    ArrayList prgmName;

    ImageView notifi,title_text,back,menu_item;

    TextView logo_text,noti_ad;

    AsyncHttpClient client;

    Constant constant;

    TextView feedback_count;

    ProgressDialog progressDialog;

    SessionManager sessionManager;

    LinearLayout change_logout;

    TextView logout,change_pass;

    List<NotificationModel> list;

    NotificationAdapter notificationAdapter;

    android.support.v7.app.ActionBar mActionBar;
    NotificationAdapter categories2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_notification);

        client = new AsyncHttpClient();

        constant = new Constant(getApplicationContext());

        sessionManager = new SessionManager(getApplicationContext());

        sessionManager.setList_Size("0");

        mActionBar = getSupportActionBar();

        mActionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F4F5F7")));

        mActionBar.setDisplayShowHomeEnabled(false);

        mActionBar.setDisplayShowTitleEnabled(false);

        LayoutInflater mInflater = LayoutInflater.from(this);

        View mCustomView = mInflater.inflate(R.layout.custombar, null);

        logo_text = (TextView) mCustomView.findViewById(R.id.logo_text);

        logo_text.setText("Notification");

        logo_text.setTypeface(Typeface.DEFAULT_BOLD);

        noti_listr=(ListView) findViewById(R.id.noti_listr);

        notifi = (ImageView)mCustomView.findViewById(R.id.notifi);

        title_text = (ImageView)mCustomView.findViewById(R.id.title_text);

        back = (ImageView)mCustomView.findViewById(R.id.back);

        noti_ad = (TextView)findViewById(R.id.noti_ad);

        logo_text.setVisibility(View.VISIBLE);

        title_text.setVisibility(View.GONE);

        notifi.setVisibility(View.GONE);

        feedback_count = (TextView)mCustomView.findViewById(R.id.feedback_count);

        menu_item = (ImageView)mCustomView.findViewById(R.id.menu_item);

        logout = (TextView)findViewById(R.id.logout);

        change_pass = (TextView)findViewById(R.id.change_pass);

        feedback_count.setVisibility(View.GONE);

        list = new ArrayList<>();

        final DataHandler db = new DataHandler(getApplicationContext());


        list = db.getAllContacts();
        System.out.println("listsize"+list.size());

        if(list!=null&&list.size()>0){
            db.deleteAll();
            for (int i=0;i<list.size();i++){
                NotificationModel notificationModel = new NotificationModel();
                notificationModel.setId("0");
                notificationModel.setDescription(list.get(i).getDescription());
                notificationModel.setNotification_date(list.get(i).getNotification_date());
                db.addContact(notificationModel);
            }
            categories2 = new NotificationAdapter(Notification.this, list);
            noti_listr.setAdapter(categories2);
        }
        if (list.size()>0){
            noti_ad.setVisibility(View.GONE);
        }else {
            noti_ad.setVisibility(View.VISIBLE);
        }


        SwipeDismissListViewTouchListener touchListener =
                new SwipeDismissListViewTouchListener(
                        noti_listr,
                        new SwipeDismissListViewTouchListener.DismissCallbacks() {
                            @Override
                            public boolean canDismiss(int position) {

                                return true;
                            }

                            @Override
                            public void onDismiss(ListView listView, int[] reverseSortedPositions) {
                                for (final int position : reverseSortedPositions) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(Notification.this);

                                    builder.setMessage("Are you sure you want to delete ?")

                                            .setCancelable(false)

                                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                                                public void onClick(DialogInterface dialog, int id) {

                                                    NotificationModel notificationModel = list.get(position);
                                                    db.deleteContact(notificationModel);
                                                    Intent intent = new Intent(Notification.this,Notification.class);
                                                    startActivity(intent);
                                                    finish();


                                                }
                                            })
                                            .setNegativeButton("No", new DialogInterface.OnClickListener() {

                                                public void onClick(DialogInterface dialog, int id) {
                                                    dialog.cancel();

                                                }
                                            });

                                    AlertDialog alert = builder.show();

                                    TextView textView = (TextView) alert.findViewById(android.R.id.message);

                                    textView.setGravity(Gravity.CENTER);

                                    alert.show();
                                }
                                categories2.notifyDataSetChanged();
                            }
                        });
        noti_listr.setOnTouchListener(touchListener);
        // Setting this scroll listener is required to ensure that during ListView scrolling,
        // we don't look for swipes.
        noti_listr.setOnScrollListener(touchListener.makeScrollListener());





        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // change_logout.setVisibility(View.GONE);
                onBackPressed();
            }
        });

        mActionBar.setCustomView(mCustomView, new android.support.v7.app.ActionBar.LayoutParams

                (android.support.v7.app.ActionBar.LayoutParams.MATCH_PARENT, android.support.v7.app.ActionBar.LayoutParams.MATCH_PARENT));

        mActionBar.setDisplayShowCustomEnabled(true);

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

            AlertDialog.Builder builder = new AlertDialog.Builder(Notification.this);

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

}
