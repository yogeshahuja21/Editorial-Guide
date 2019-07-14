package com.nba.guide;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Advisery extends AppCompatActivity {

    ListView ad_list;

    Context context;

    android.support.v7.app.ActionBar mActionBar;

    ArrayList prgmName;

    ImageView notifi,title_text,back;

    TextView logo_text;


    public static String [] prgmNameList={"28 March,2016","30 March,2016","31 March,2016","1 April,2016","28 April,2016"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advisery);

        mActionBar = getSupportActionBar();

        mActionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F4F5F7")));

        mActionBar.setDisplayShowHomeEnabled(false);

        mActionBar.setDisplayShowTitleEnabled(false);

        LayoutInflater mInflater = LayoutInflater.from(this);

        View mCustomView = mInflater.inflate(R.layout.custombar, null);

        logo_text = (TextView) mCustomView.findViewById(R.id.logo_text);

        logo_text.setText("Advisories");

        logo_text.setTypeface(Typeface.DEFAULT_BOLD);

        notifi = (ImageView)mCustomView.findViewById(R.id.notifi);

        title_text = (ImageView)mCustomView.findViewById(R.id.title_text);

        back = (ImageView)mCustomView.findViewById(R.id.back);

        logo_text.setVisibility(View.VISIBLE);

        title_text.setVisibility(View.GONE);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });

        notifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Advisery.this,Notification.class);
                startActivity(intent);
            }
        });

        context=this;

        ad_list=(ListView) findViewById(R.id.ad_list);

        ad_list.setAdapter(new AdviseryAdapter(this, prgmNameList));

        mActionBar.setCustomView(mCustomView, new android.support.v7.app.ActionBar.LayoutParams

                (android.support.v7.app.ActionBar.LayoutParams.MATCH_PARENT, android.support.v7.app.ActionBar.LayoutParams.MATCH_PARENT));

        mActionBar.setDisplayShowCustomEnabled(true);


    }

}
