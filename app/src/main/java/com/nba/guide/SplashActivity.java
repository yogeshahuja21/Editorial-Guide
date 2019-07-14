package com.nba.guide;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

public class SplashActivity extends AppCompatActivity {

    TextView copy,copy1,copy2,textView,textView2;
    Animation animFadeIn,animFadeOut,animBlink,animZoomIn,animZoomOut,animRotate
            ,animMove,animSlideUp,animSlideDown,animBounce,animSequential,animTogether,animCrossFadeIn,animCrossFadeOut;
    ImageView imageView2,imageView3;

    LinearLayout linearLayout5;

    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        sessionManager = new SessionManager(getApplicationContext());
        linearLayout5 = (LinearLayout)findViewById(R.id.linearLayout5);
        animFadeIn = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.move);

        animZoomOut = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fade_in);

        FirebaseMessaging.getInstance().subscribeToTopic("news");

        Toast.makeText(SplashActivity.this, "Connected!", Toast.LENGTH_SHORT).show();

        textView = (TextView)findViewById(R.id.textView);

        textView2 = (TextView)findViewById(R.id.textView2);

        imageView2 = (ImageView)findViewById(R.id.imageView2);

        imageView3 = (ImageView)findViewById(R.id.imageView3);

        imageView2.startAnimation(animZoomOut);

        imageView3.startAnimation(animZoomOut);
        System.out.println("GCMResponse" + FirebaseInstanceId.getInstance().getToken() + "register_device?" );
        sessionManager.setRegId(FirebaseInstanceId.getInstance().getToken());

        Thread background1 = new Thread() {
            public void run() {

                try {
                    linearLayout5.setVisibility(View.INVISIBLE);

                    sleep(2000);

                    linearLayout5.startAnimation(animFadeIn);


                } catch (Exception e) {

                }
            }
        };
        background1.start();

        Thread background = new Thread() {
            public void run() {

                try {

                    sleep(5 * 1000);

                        Intent i = new Intent(getBaseContext(), DashBoard.class);
                        startActivity(i);
                        overridePendingTransition(0, 0);
                        finish();

                } catch (Exception e) {

                }
            }
        };

        background.start();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_splash, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
