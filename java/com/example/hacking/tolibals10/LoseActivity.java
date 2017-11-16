package com.example.hacking.tolibals10;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class LoseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lose);

        Intent getData = getIntent();
        SharedPreferences saveScore = getSharedPreferences("save_score",MODE_PRIVATE);
        SharedPreferences.Editor editor = saveScore .edit();
        TextView showScore = findViewById(R.id.show_loseScore);
        RelativeLayout view = findViewById(R.id.lose_layout);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToMain();
            }
        });

        int getScore = getData.getIntExtra("score_value",0);
        editor.putInt("score_saved",getScore);
        editor.apply();
        showScore.setText(String.valueOf(saveScore.getInt("score_saved",0)));
    }

    @Override
    public void onBackPressed() {
        backToMain();
        super.onBackPressed();
    }

    private void backToMain(){
        Intent startMain = new Intent(LoseActivity.this,MainActivity.class);
        startActivity(startMain);
        finish();
    }

    public void repeatGame(View view){
        Intent startGame = new Intent(LoseActivity.this,GameActivity.class);
        startActivity(startGame);
        finish();
    }
}