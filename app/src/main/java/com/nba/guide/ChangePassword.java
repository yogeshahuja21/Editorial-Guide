package com.nba.guide;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

public class ChangePassword extends AppCompatActivity {

    EditText curr_pass,new_pass,re_enterpass;

    Button submit;

    SessionManager sessionManager;

    AsyncHttpClient client;

    Constant constant;

    ProgressDialog progressDialog;

    String current,newpass;

    ImageView notifi,title_text,back,menu_item;

    TextView logo_text,noti_ad,feedback_count;

    LinearLayout change_logout;

    TextView logout,change_pass;

    android.support.v7.app.ActionBar mActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_change_password);

        sessionManager = new SessionManager(getApplicationContext());

        constant = new Constant(getApplicationContext());

        client = new AsyncHttpClient();

        mActionBar = getSupportActionBar();

        mActionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F4F5F7")));

        mActionBar.setDisplayShowHomeEnabled(false);

        mActionBar.setDisplayShowTitleEnabled(false);

        LayoutInflater mInflater = LayoutInflater.from(this);

        View mCustomView = mInflater.inflate(R.layout.custombar, null);

        logo_text = (TextView) mCustomView.findViewById(R.id.logo_text);

        logo_text.setText("Change Password");

        logo_text.setTypeface(Typeface.DEFAULT_BOLD);

        notifi = (ImageView)mCustomView.findViewById(R.id.notifi);

        title_text = (ImageView)mCustomView.findViewById(R.id.title_text);

        back = (ImageView)mCustomView.findViewById(R.id.back);

        logo_text.setVisibility(View.VISIBLE);

        title_text.setVisibility(View.GONE);

        notifi.setVisibility(View.GONE);

        curr_pass = (EditText)findViewById(R.id.curr_pass);

        new_pass = (EditText)findViewById(R.id.new_pass);

        re_enterpass = (EditText)findViewById(R.id.re_enterpass);

        feedback_count = (TextView)mCustomView.findViewById(R.id.feedback_count);

        menu_item = (ImageView)mCustomView.findViewById(R.id.menu_item);

        logout = (TextView)findViewById(R.id.logout);

        change_logout = (LinearLayout)findViewById(R.id.change_logout);

        change_logout.setVisibility(View.GONE);

        feedback_count.setVisibility(View.GONE);

        submit = (Button)findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(curr_pass.getText().toString().isEmpty()){
                    curr_pass.setError(" Please enter old password.");
                }
                else if(new_pass.getText().toString().isEmpty()){
                    new_pass.setError("Please enter new password.");
                }
                else if(re_enterpass.getText().toString().isEmpty()){
                    re_enterpass.setError("Please re-enter new password.");
                }
                else if(!new_pass.getText().toString().equalsIgnoreCase(re_enterpass.getText().toString())){
                    //new_pass.setError("Password doesn't match");
                    re_enterpass.setError("Password doesn't match");

                }else {
                        changepass();
                }

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                change_logout.setVisibility(View.GONE);
                onBackPressed();
            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                change_logout.setVisibility(View.GONE);
                new RenderView().execute();
            }

        });

        menu_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(change_logout.getVisibility()==View.GONE){
                    change_logout.setVisibility(View.VISIBLE);
                }else {
                    change_logout.setVisibility(View.GONE);
                }
            }
        });

        mActionBar.setCustomView(mCustomView, new android.support.v7.app.ActionBar.LayoutParams

                (android.support.v7.app.ActionBar.LayoutParams.MATCH_PARENT, android.support.v7.app.ActionBar.LayoutParams.MATCH_PARENT));

        mActionBar.setDisplayShowCustomEnabled(true);

    }

    public void changepass() {

        current = curr_pass.getText().toString();

        newpass = new_pass.getText().toString();

        RequestParams params = new RequestParams();

        params.put("current_password", current);

        params.put("new_password", newpass);

        params.put("user_id",sessionManager.getUserId());

        client.addHeader("Content-Type", "application/x-www-form-urlencoded");

        client.addHeader("Authorization", constant.auth_value);

        System.out.println("response" + constant.common + "change_password" + params);

        client.post(getApplicationContext(), constant.common + "change_password", params, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                progressDialog = new ProgressDialog(ChangePassword.this);

                progressDialog.setMessage("Please Wait...");

                progressDialog.setIndeterminate(false);

                progressDialog.show();

                super.onStart();
            }

            @Override
            public void onSuccess(int i, String s) {
                super.onSuccess(i, s);

                try {

                    JSONObject jsonObject = new JSONObject(s.toString());

                    System.out.println("Change" + jsonObject);

                    String s1= jsonObject.getString("success");

                    String msg= jsonObject.getString("message");
                    System.out.println("Change" + s1);


                    if (s1.equalsIgnoreCase("1")){
                        Intent intent = new Intent(ChangePassword.this,DashBoard.class);
                        startActivity(intent);
                        finish();
                        Toast.makeText(getApplicationContext(), "Password change successfully", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                    }


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

            AlertDialog.Builder builder = new AlertDialog.Builder(ChangePassword.this);

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
