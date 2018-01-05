package com.example.hacking.tolibals10;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;


public class GameActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View ballView = new GameDraw(this);
        setContentView(ballView);
        ballView.setBackgroundColor(Color.argb(140,15,105,15));
        // Hamza je car

    }
}

