package com.aarenas.syssales;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class SplashScreenActivity extends AppCompatActivity {

    //Constants:
    //private static final int SPLASH_SCREEN_DELAY = 2500;
    private static final int SPLASH_SCREEN_DELAY = 1000;

    //Controls:
    ImageView imgLogo_SplashScreenActivity;
    TextView tvAppName_SplashScreenActivity;
    ProgressBar progressBar_SplashScreenActivity;

    //Variables:
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        imgLogo_SplashScreenActivity = findViewById(R.id.imgLogo_SplashScreenActivity);
        tvAppName_SplashScreenActivity = findViewById(R.id.tvAppName_SplashScreenActivity);
        progressBar_SplashScreenActivity = findViewById(R.id.progressBar_SplashScreenActivity);

        imgLogo_SplashScreenActivity.setImageResource(R.drawable.icon);

        ObjectAnimator.ofInt(progressBar_SplashScreenActivity, "progress", 100)
                .setDuration(SPLASH_SCREEN_DELAY).start();

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        defaultSkin();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable(){
            public void run(){
                boolean isFirstRun = preferences.getBoolean("first_run", true);
                if (isFirstRun)
                {
                    startActivity(new Intent(getApplicationContext(), OnboardingScreenActivity.class));
                }
                else
                {
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                }

                finish();
            };
        }, SPLASH_SCREEN_DELAY);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    private void defaultSkin()
    {
        boolean isFirstRun = preferences.getBoolean("first_run", true);
        if (isFirstRun)
        {
            editor = preferences.edit();
            if (BuildConfig.BUILD_TYPE.equals("sales")) {
                editor.putString("skin", getString(R.string.skin_style_1_value));
            }
            else if (BuildConfig.BUILD_TYPE.equals("distributions")) {
                editor.putString("skin", getString(R.string.skin_style_2_value));
            }
            else {
                editor.putString("skin", getString(R.string.skin_default_value));
            }
            editor.apply();
            editor = null;
        }
    }
}