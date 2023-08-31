package com.aarenas.syssales;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class OnboardingScreenActivity extends AppCompatActivity {

    //Controls:
    FrameLayout container;
    Button btnNext_OnboardingScreen;

    //Variables:
    SharedPreferences preferences;
    boolean isShowingAssistant = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        //setTheme(R.style.Theme_SysSales_NoActionBar);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding_screen);

        container = findViewById(R.id.container);
        btnNext_OnboardingScreen = findViewById(R.id.btnNext_OnboardingScreen);

        btnNext_OnboardingScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isShowingAssistant)
                {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.container, new OnboardingScreenPage2Fragment())
                            .commit();
                    isShowingAssistant = true;
                    btnNext_OnboardingScreen.setText(R.string.finalize);
                }
                else
                {
                    String hostname = preferences.getString("hostname", "");
                    if (!hostname.isEmpty())
                    {
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putBoolean("first_run", false);
                        editor.apply();

                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        finish();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), R.string.message_incomplete_data, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, new OnboardingScreenPage1Fragment())
                .commit();
    }
}