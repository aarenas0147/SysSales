package com.aarenas.syssales;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class OnboardingScreenPage1Fragment extends Fragment {

    TextView tvTitle_OnboardingScreenPage1;

    public OnboardingScreenPage1Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_onboarding_screen_page_1, container, false);
        tvTitle_OnboardingScreenPage1 = rootView.findViewById(R.id.tvTitle_OnboardingScreenPage1);

        tvTitle_OnboardingScreenPage1.setText(String.format(tvTitle_OnboardingScreenPage1.getText().toString(), getString(R.string.app_name)));

        return rootView;
    }
}