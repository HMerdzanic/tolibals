package com.example.hacking.tolibals10;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

public class WelcomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideStatusBar();
        setContentView(R.layout.activity_welcome);

        Thread time = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(2000);
                    startMain();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        time.start();
    }

    // Go to the main acc when user resume the app. Because we do not want to spend user's time
    @Override
    protected void onResume() {
        super.onResume();
        hideStatusBar();
    }

    // Hide the status bar when user restart the app
    @Override
    protected void onRestart() {
        super.onRestart();
        startMain();
    }


    // Hide the status bar
    public void hideStatusBar(){
        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        }

        decorView.setSystemUiVisibility(uiOptions);

        // Remember that you should never show the action bar if the
        // status bar is hidden, so hide that too if necessary.
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        // If the Android version is lower than Jellybean, use this call to hide
        // the status bar.
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

    }

    // Start the main acc
    private void startMain(){
        Intent start = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(start);
        finish();
    }

}
