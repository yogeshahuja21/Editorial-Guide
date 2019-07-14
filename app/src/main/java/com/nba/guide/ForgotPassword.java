package com.nba.guide;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

public class ForgotPassword extends AppCompatActivity {

    ImageView notifi,title_text,logout,back,menuitem;

    TextView logo_text,feedback_count;

    EditText email;

    String mail_id;

    Button send;

    AsyncHttpClient client;

    Constant constant;

    ProgressDialog progressDialog;

    android.support.v7.app.ActionBar mActionBar;

    Boolean isInternetPresent;

    ConnectionDetector cd;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_forgot_password);

        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        client = new AsyncHttpClient();

        constant = new Constant(getApplicationContext());

        mActionBar = getSupportActionBar();

        mActionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F4F5F7")));

        mActionBar.setDisplayShowHomeEnabled(false);

        mActionBar.setDisplayShowTitleEnabled(false);

        LayoutInflater mInflater = LayoutInflater.from(this);

        View mCustomView = mInflater.inflate(R.layout.custombar, null);

        logo_text = (TextView) mCustomView.findViewById(R.id.logo_text);

        logo_text.setText("Forgot Password");

        logo_text.setTypeface(Typeface.DEFAULT_BOLD);

        notifi = (ImageView)mCustomView.findViewById(R.id.notifi);

        menuitem = (ImageView)mCustomView.findViewById(R.id.menu_item);

        menuitem.setVisibility(View.GONE);


        title_text = (ImageView)mCustomView.findViewById(R.id.title_text);

        feedback_count = (TextView)mCustomView.findViewById(R.id.feedback_count);

        cd = new ConnectionDetector(getApplicationContext());

        isInternetPresent = cd.isConnectingToInternet();

        if (isInternetPresent) {


        } else {

            showAlertDialog(ForgotPassword.this);
        }

        back = (ImageView)mCustomView.findViewById(R.id.back);

        logo_text.setVisibility(View.VISIBLE);

        title_text.setVisibility(View.GONE);

        //logout.setVisibility(View.GONE);

        notifi.setVisibility(View.GONE);

        email = (EditText)findViewById(R.id.email);

        send = (Button)findViewById(R.id.send);

        feedback_count.setVisibility(View.GONE);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email.getText().toString().matches(emailPattern)){
                    login();
                }else {
                    email.setError("Please enter valid email Id");

                }

            }
        });


        mActionBar.setCustomView(mCustomView, new android.support.v7.app.ActionBar.LayoutParams

                (android.support.v7.app.ActionBar.LayoutParams.MATCH_PARENT, android.support.v7.app.ActionBar.LayoutParams.MATCH_PARENT));

        mActionBar.setDisplayShowCustomEnabled(true);

    }

    public void login() {

        mail_id = email.getText().toString();

        RequestParams params = new RequestParams();

        params.put("email_id", mail_id);

        client.addHeader("Content-Type", "application/x-www-form-urlencoded");

        client.addHeader("Authorization", constant.auth_value);

        System.out.println("response" + constant.common + "forget_password" + params);

        client.post(getApplicationContext(), constant.common + "forget_password", params, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                progressDialog = new ProgressDialog(ForgotPassword.this);

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

                    System.out.println("FORGOT" + jsonObject);

                    String s1= jsonObject.getString("success");

                    String msg= jsonObject.getString("message");

                    if (s1.equalsIgnoreCase("success")){
                        Toast.makeText(getApplicationContext(),s1,Toast.LENGTH_SHORT).show();
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
                if (isInternetPresent) {


                } else {

                    showAlertDialog(ForgotPassword.this);
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                progressDialog.dismiss();
            }
        });

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
