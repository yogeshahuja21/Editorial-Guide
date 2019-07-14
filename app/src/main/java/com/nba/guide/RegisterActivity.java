package com.nba.guide;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {

    LinearLayout linearLayout;

    Button login,register,submit_register;

    AsyncHttpClient client;

    Constant constant;

    ProgressDialog progressDialog;

    String fname,degination,email,phone,comany,chanel,pass;

    String device_type = "Android";

    Spinner company_name,chanel_name;

    TextView email_text;

    EditText full_name,degi_type,email_id,mobile_no,password,confirm_pass;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    public ArrayList<CompanyModel>  company = new ArrayList<>();

    CompanyModel companyModel;

    ChanelModel chanelModel;

    CompanyAdapter adapter;

    JSONArray comapny_name;

    String comapnyName;

    String chanels_name;

    Boolean isInternetPresent;

    ConnectionDetector cd;

    public ArrayList<ChanelModel>  chanels = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_register);

        client = new AsyncHttpClient();

        constant = new Constant(getApplicationContext());

        linearLayout = (LinearLayout)findViewById(R.id.linearLayout);

        login = (Button)findViewById(R.id.login);

        register = (Button)findViewById(R.id.register);

        submit_register = (Button)findViewById(R.id.submit_register);

        full_name = (EditText)findViewById(R.id.full_name);

        degi_type = (EditText)findViewById(R.id.degi_type);

        email_id = (EditText)findViewById(R.id.email_id);

        mobile_no = (EditText)findViewById(R.id.mobile_no);

        company_name = (Spinner)findViewById(R.id.company_name);

        chanel_name = (Spinner)findViewById(R.id.chanel_name);

        password = (EditText)findViewById(R.id.password);

        confirm_pass = (EditText)findViewById(R.id.confirm_pass);

        cd = new ConnectionDetector(getApplicationContext());

        isInternetPresent = cd.isConnectingToInternet();

        comanylist();

        if (isInternetPresent) {


        } else {

            showAlertDialog(RegisterActivity.this);
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });

        submit_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isInternetPresent) {

                    if (full_name.getText().toString().isEmpty()) {
                        full_name.setError("Please Enter Your Name");
                    } else if (degi_type.getText().toString().isEmpty()) {
                        degi_type.setError("Please Enter Degination");
                    } else if (!email_id.getText().toString().matches(emailPattern)) {
                        email_id.setError("Please Enter valid Email");
                    } else if (mobile_no.getText().toString().length()<=9) {
                        mobile_no.setError("Please Enter valid Mobile Number");
                    } else if (password.getText().toString().isEmpty()) {
                        password.setError("Please Enter your Password");
                    }
                    else if (confirm_pass.getText().toString().isEmpty()){
                        confirm_pass.setError("Please Enter Password Again");
                    }
                    else if(!password.getText().toString().equalsIgnoreCase(confirm_pass.getText().toString())){
                        password.setError("Password don't match");
                        confirm_pass.setError("Password don't match");
                    }else {
                        postRegData();

                    }

                }


            }
        });

        company_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                companyModel = company.get(position);
                try {
                    comapnyName = companyModel.getComapny_name();
                    comapny_name = new JSONArray(companyModel.getChannel_name());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (chanels.size()>0){
                    chanels.clear();
                }
                chanellist(comapny_name);

                System.out.println("company name" + comapny_name);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        chanel_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                chanelModel = chanels.get(position);
                chanels_name = companyModel.getChannel_name();

                System.out.println("company name" + chanels_name);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }

    public void postRegData() {

        fname = full_name.getText().toString();

        degination = degi_type.getText().toString();

        email = email_id.getText().toString();

        phone = mobile_no.getText().toString();

        pass = password.getText().toString();

        RequestParams params = new RequestParams();

        params.put("full_name", fname);

        params.put("designation", degination);

        params.put("email_id", email);

        params.put("contact_no", phone);

        params.put("company_name", comapnyName.trim());

        params.put("channel_name", chanels_name.trim());

        params.put("password", pass);

        params.put("device", device_type);

        client.addHeader("Content-Type", "application/x-www-form-urlencoded");

        client.addHeader("Authorization", constant.auth_value);

        System.out.println("response" + constant.common + "register?" + params);

        client.post(getApplicationContext(), constant.common + "register?", params, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                progressDialog = new ProgressDialog(RegisterActivity.this);

                progressDialog.setMessage("Please Wait...");

                progressDialog.setIndeterminate(false);

                progressDialog.show();

                super.onStart();
            }

            @Override
            public void onSuccess(int i, String s) {
                super.onSuccess(i, s);

                System.out.println("Register" + s);

                try {

                    JSONObject jsonObject = new JSONObject(s.toString());

                    String s1 = jsonObject.getString("success");

                    if (s1.equalsIgnoreCase("0")) {

                        Toast.makeText(RegisterActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                    } else {

                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        Toast.makeText(RegisterActivity.this, "Your account is registered successfully", Toast.LENGTH_SHORT).show();

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

                    showAlertDialog(RegisterActivity.this);
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                progressDialog.dismiss();
            }
        });

    }

    public void comanylist() {

        client.addHeader("Content-Type", "application/x-www-form-urlencoded");

        client.addHeader("Authorization", "Basic c2lkZGhhcnRoc0BuZXVyb25pbWJ1cy5jb206TmV1cm9AMTIz");

        System.out.println("response visatype" + constant.common + "companies");

        client.get(RegisterActivity.this, constant.common + "companies", null, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                progressDialog = new ProgressDialog(RegisterActivity.this);

                progressDialog.setMessage("Please Wait...");

                progressDialog.setIndeterminate(false);

                progressDialog.show();

                super.onStart();
            }

            @Override
            public void onSuccess(int i, String jsonObject) {
                super.onSuccess(i, jsonObject);

                System.out.println("Comany" + jsonObject);


                try {
                    chanels.clear();
                    JSONObject object = new JSONObject(jsonObject);

                    JSONArray jsonArray = object.getJSONArray("companies");

                    for (int k = 0; k < jsonArray.length(); k++) {

                        JSONObject obj = jsonArray.getJSONObject(k);

                        System.out.println("OBJECT" + obj);

                        CompanyModel companyModel = new CompanyModel();

                        if (obj.has("company_name")) {

                            companyModel.setComapny_name(obj.getString("company_name"));

                        }

                        if (obj.has("id")) {

                            companyModel.setId(obj.getInt("id"));

                        }

                        if (obj.has("channels")) {

                            companyModel.setChannel_name(obj.getString("channels"));

                        }
                        company.add(companyModel);
                    }

                    Resources res = getResources();

                    CompanyAdapter companyAdapter = new CompanyAdapter(RegisterActivity.this, R.layout.spinner_rows, company, res);

                    company_name.setAdapter(companyAdapter);

                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable throwable, String s) {
                super.onFailure(throwable, s);
                if (isInternetPresent) {


                } else {

                    showAlertDialog(RegisterActivity.this);
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                progressDialog.dismiss();
            }
        });
    }

    public void chanellist(final JSONArray chanelname) {

                  try{
                    for (int k = 0; k < chanelname.length(); k++) {

                        JSONObject obj = chanelname.getJSONObject(k);

                        ChanelModel model = new ChanelModel();

                            if(obj.has("channel_name")){

                               model.setChannel_name(obj.getString("channel_name"));

                            }

                        if(obj.has("id")){

                            model.setId(obj.getInt("id"));

                        }

                        chanels.add(model);

                    }

                    Resources res = getResources();

                    ChanelAdapter chanelAdapter = new ChanelAdapter(RegisterActivity.this, R.layout.spinner_rows, chanels, res);

                    chanel_name.setAdapter(chanelAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
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
