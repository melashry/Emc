package com.semicolon.emcmisir.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.semicolon.emcmisir.Fragment.AppIntroSampleSlider;
import com.semicolon.emcmisir.R;
import com.github.paolorotolo.appintro.AppIntro;


public class MyIntro extends AppIntro {
    // Please DO NOT override onCreate. Use init
    @Override
    public void init(Bundle savedInstanceState) {

        //adding the three slides for introduction app you can ad as many you needed
        addSlide(AppIntroSampleSlider.newInstance(R.layout.app_intro1));
        addSlide(AppIntroSampleSlider.newInstance(R.layout.app_intro2));
        addSlide(AppIntroSampleSlider.newInstance(R.layout.app_intro3));
        addSlide(AppIntroSampleSlider.newInstance(R.layout.app_intro4));
        addSlide(AppIntroSampleSlider.newInstance(R.layout.app_intro5));

        // Show and Hide Skip and Done buttons
        showStatusBar(false);
        showSkipButton(false);

        // Turn vibration on and set intensity
        // You will need to add VIBRATE permission in Manifest file
        setVibrate(true);
        setVibrateIntensity(50);

        //Add animation to the intro slider
        setDepthAnimation();
    }


    @Override
    public void onDonePressed(Fragment currentFragment) {

        Intent i = new Intent(getApplicationContext(), SlideUpActivity.class);
        startActivity(i);
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        Toast.makeText(getApplicationContext(),
                "skip", Toast.LENGTH_SHORT).show();
    }


}