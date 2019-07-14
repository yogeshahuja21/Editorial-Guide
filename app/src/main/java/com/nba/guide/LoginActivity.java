package com.nba.guide;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gcm.GCMRegistrar;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    LinearLayout linearLayout,put_email,put_password;

    Button login,register1 ,login_button;

    TextView forgot,newuser,text;

    EditText enter_email,enter_pass;

    CheckBox checkBox;

    Constant constant;

    AsyncHttpClient client;

    ProgressDialog progressDialog;

    String email,pass;

    String userId;

    Boolean aBoolean = false;

    SessionManager sessionManager;

    SharedPreferences pref;

    String android_id;

    String version;

    String verCode;

    Boolean isInternetPresent;

    ConnectionDetector cd;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    AsyncTask<Void, Void, Void> mRegisterTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        GCMRegistrar.checkManifest(this);
        setContentView(R.layout.content_login);

        client = new AsyncHttpClient();

        constant = new Constant(getApplicationContext());

        sessionManager = new SessionManager(getApplicationContext());

        linearLayout = findViewById(R.id.linearLayout);

        login = findViewById(R.id.login);

        register1 = findViewById(R.id.register1);

        login_button = findViewById(R.id.login_button);

        forgot = findViewById(R.id.forgot);

        put_email = findViewById(R.id.put_email);

        enter_email = findViewById(R.id.enter_email);

        enter_pass = findViewById(R.id.enter_pass);

        newuser = findViewById(R.id.newuser);

        checkBox = findViewById(R.id.checkBox);

        cd = new ConnectionDetector(getApplicationContext());

        isInternetPresent = cd.isConnectingToInternet();

        if (isInternetPresent) {


        } else {

            showAlertDialog(LoginActivity.this);
        }

        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);

        String username=pref.getString("username", "");

        final String password=pref.getString("password","");

        Boolean check=pref.getBoolean("check",false);
        enter_email.setText(sessionManager.getEmail());
        enter_pass.setText(sessionManager.getPassword());

        register1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                overridePendingTransition(0,0);
                finish();
            }
        });


        newuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                overridePendingTransition(0,0);
                finish();

            }
        });

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!enter_email.getText().toString().matches(emailPattern)) {
                    enter_email.setError("Please enter valid email");

                } else if (enter_pass.getText().toString().isEmpty()) {
                    enter_pass.setError("Please enter password");

                }
               else {
                    if(checkBox.isChecked()==true){
                        sessionManager.setEmail_ID(enter_email.getText().toString());
                        sessionManager.setPassword(enter_pass.getText().toString());
                        login();
                        //Toast.makeText(LoginActivity.this,"Password",Toast.LENGTH_LONG).show();
                    }
                    else {
                        login();
                    }


                }
                }

        });

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgotPassword.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_out_left, R.anim.slide_out_right);
            }
        });


//        checkBox.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (enter_email.getText().toString().isEmpty() && enter_pass.getText().toString().isEmpty()) {
//                        checkBox.setChecked(false);
//                    }
//                else
//                {
//                    if(checkBox.isChecked()) {
//                        checkBox.setChecked(true);
//                    }
//                    else{
//                        checkBox.setChecked(false);
//
//                    }
//                }
//
//            }
//        });

//        final String regId = GCMRegistrar.getRegistrationId(this);
//
//        android_id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
//
//        System.out.println("DeviceId--->" + android_id);
//
//        System.out.println("regID--->" + regId);
//
//        PackageInfo pInfo = null;
//
//        try {
//
//            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
//
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }
//        version = String.valueOf(pInfo.versionCode);
//
//        System.out.println("DeviceId--->" + pInfo.versionName + "-----" + pInfo.versionCode);
//
//        pInfo = null;
//        try {
//            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }
//        version = pInfo.versionName;
//
//        verCode = String.valueOf(pInfo.versionCode);
//
//        System.out.println("version" + version + "----"+verCode);
//
//        if (regId != null){
//            sessionManager.setRegId(regId);
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

    }

    public void login() {

        email = enter_email.getText().toString();

        pass = enter_pass.getText().toString();

        RequestParams params = new RequestParams();

        params.put("email_id", email);

        params.put("password", pass);

        client.addHeader("Content-Type", "application/x-www-form-urlencoded");

        client.addHeader("Authorization", constant.auth_value);

        System.out.println("response" + constant.common + "login?" + params);

        client.post(getApplicationContext(), constant.common + "login?", params, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                progressDialog = new ProgressDialog(LoginActivity.this);

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

                    String s1= jsonObject.getString("success");

                    String msg= jsonObject.getString("message");

                    if(s1.equalsIgnoreCase("1"))
                    {
                    String jsonObject1 = jsonObject.getString("user");

                    JSONObject object = new JSONObject(jsonObject1);

                    System.out.println("Register" + object);

                    userId = object.getString("user_id");

                        System.out.println("USERID" + userId);

                        sessionManager.createLoginSession(userId);

                        sessionManager.setLogin(true);
                        Intent intent = new Intent(getApplicationContext(),DashBoard.class);
                        startActivity(intent);

                    }
                    else {
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

                    showAlertDialog(LoginActivity.this);
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                progressDialog.dismiss();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();

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
