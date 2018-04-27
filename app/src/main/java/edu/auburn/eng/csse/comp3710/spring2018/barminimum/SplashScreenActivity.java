package edu.auburn.eng.csse.comp3710.spring2018.barminimum;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;

/**
 * Created by Tanner Oakes on 4/17/18.
 */

public class SplashScreenActivity extends Activity {

    private static final int SPLASH_SCREEN_DURATION = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_SCREEN_DURATION);
    }
}
