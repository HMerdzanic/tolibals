package com.example.hacking.tolibals10;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.Random;

/**
 * this is checked
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar();


        SharedPreferences save = getSharedPreferences("save_name", Context.MODE_PRIVATE);
        String getNick = save.getString("new_nick",null);
        TextView nick = findViewById(R.id.showNickHere);
        nick.setText(getNick);
        if (getNick == null){
            newNick();
        }

        ImageView startBtn = findViewById(R.id.startGameBtn);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startGame = new Intent(MainActivity.this,GameActivity.class);
                startActivity(startGame);
                finish();
            }
        });

        changeBackground();
    }

    private void changeBackground() {
        Random ran = new Random();
        final RelativeLayout view = findViewById(R.id.activity_main);
        ObjectAnimator animator = ObjectAnimator.ofInt(view, "backgroundColor",
                Color.argb(255,ran.nextInt(255),ran.nextInt(250),ran.nextInt(255)),
                Color.argb(150,ran.nextInt(50),ran.nextInt(50),ran.nextInt(50)))
                .setDuration(5500);
        animator.setEvaluator(new ArgbEvaluator());
        animator.start();
    }

    @Override
    protected void onRestart() {
        changeBackground();
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        changeBackground();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        changeBackground();
        super.onResume();
    }

    private void newNick() {
        final TextView showNick = findViewById(R.id.showNickHere);

        final AlertDialog.Builder inputNick = new AlertDialog.Builder(this);
        inputNick.setTitle("Input nick");
        inputNick.setCancelable(false);

        final EditText input = new EditText(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);

        input.setLayoutParams(params);
        inputNick.setView(input);
        inputNick.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String text = input.getText().toString();

                if (text.isEmpty()){
                    newNick();
                }else {
                    SharedPreferences save = getSharedPreferences("save_name",MODE_PRIVATE);
                    SharedPreferences.Editor edit = save.edit();
                    edit.putString("new_nick",text);
                    edit.apply();

                    showNick.setText(save.getString("new_nick",null));
                    dialog.dismiss();
                }
            }
        });
        inputNick.show();
    }

    public void nick(View view){
        newNick();
    }

}
