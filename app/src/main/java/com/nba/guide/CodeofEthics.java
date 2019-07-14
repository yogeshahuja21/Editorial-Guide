package com.nba.guide;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class CodeofEthics extends AppCompatActivity {

    ImageView notifi,title_text,back;

    TextView logo_text;

    android.support.v7.app.ActionBar mActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codeof_ethics);

        mActionBar = getSupportActionBar();

        mActionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F4F5F7")));

        mActionBar.setDisplayShowHomeEnabled(false);

        mActionBar.setDisplayShowTitleEnabled(false);

        LayoutInflater mInflater = LayoutInflater.from(this);

        View mCustomView = mInflater.inflate(R.layout.custombar, null);

        logo_text = (TextView) mCustomView.findViewById(R.id.logo_text);

        logo_text.setText("Code Of Ethics");

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

                Intent intent = new Intent(CodeofEthics.this,Notification.class);
                startActivity(intent);
            }
        });


        mActionBar.setCustomView(mCustomView, new android.support.v7.app.ActionBar.LayoutParams

                (android.support.v7.app.ActionBar.LayoutParams.MATCH_PARENT, android.support.v7.app.ActionBar.LayoutParams.MATCH_PARENT));

        mActionBar.setDisplayShowCustomEnabled(true);


    }

}
