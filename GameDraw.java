package com.example.hacking.tolibals10;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import java.util.Random;


public class GameDraw extends View {

    private float ballRadius = 35;  // Ball's radius
    private float ballY = ballRadius + 40;  // Ball's center y
    private float ballSpeedY = 10;   // Ball's speed y
    private RectF ballBounds;   // Needed for Canvas.drawOval for position of object
    private Paint paint;    // The paint (e.g. style, color) used for drawing
    private final Context context = GameDraw.this.getContext();   // Need for Window manager
    private WindowManager windowManager =
            (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);   // Need for Display
    private Display display = windowManager.getDefaultDisplay();    // need for Point under
    private Point size = new Point();   // need for getting size of the screen
    private int colorBall = Color.RED;  // color of the paint for ball
    private int colorLine = Color.RED;  // color of the paint for the line
    public int score = 0;  // score
    private int nextBig = 12;   // next big score to speed up the ball
    private Random random = new Random();   // random for color
    private boolean lost = false;

    // Constructor
    public GameDraw(final Context context) {
        super(context);

        ballBounds = new RectF();
        paint = new Paint();
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int currPos = (int) ballY;
                int nextPos = (int) ballY + 40;
                while (currPos < nextPos) {
                    ballSpeedY = -ballSpeedY;
                    ballY = ballY - ballRadius;
                    currPos += 10;
                }
                randomColor();
            }
        });


    }

    // Called back to draw the view. Also called by invalidate().
    @Override
    protected void onDraw(Canvas canvas) {
        // Draw the ball
        display.getSize(size);  // Get size of the screen
        int width = size.x;
        int height = size.y;

        ballBounds.set(width / 2 - ballRadius, ballY - ballRadius, width / 2 + ballRadius, ballY + ballRadius);//Position
        paint.setColor(colorBall);
        canvas.drawOval(ballBounds, paint);   //draw it actually

        // Update the position of the ball, including collision detection and reaction.
        update();

        // Delay
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        invalidate();  // Force a re-draw

        // Drawing the line over the screen on the bottom
        paint.setColor(colorLine);
        paint.setStrokeWidth(7);
        canvas.drawLine(width, height - 150,-width, height - 150,paint);

        paint.setColor(Color.RED);
        paint.setTextSize(20);
        canvas.drawText("Score: " + score,15,25,paint);

        if (lost) {
            this.setBackgroundColor(Color.RED);
            colorBall = Color.BLACK;
            colorLine = Color.RED;
            ballY = 30 + ballRadius;
            ballSpeedY = 0;
            paint.setColor(Color.BLACK);
            paint.setTextSize(50);
            canvas.drawText("Score: " + score,15,100,paint);

            paint.setColor(Color.BLACK);
            paint.setTextSize(130);

            canvas.drawText("Game Over!",50,height/2,paint);

            paint.setColor(Color.LTGRAY);
            paint.setTextSize(20);
            canvas.drawText("Tap anywhere on screen",width/3,height-50,paint);
            setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent lose = new Intent(context, MainActivity.class);
                    lose.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(lose);
                }
            });
        }
    }

    // Detect collision and update the position of the ball.
    private void update() {
        // Get new y position
        ballY += ballSpeedY;

        int yMin = 0;
        if (ballY - ballRadius < yMin){
            ballY = yMin + ballRadius;
            ballSpeedY += 0.02;
        }

        display.getSize(size);  // Get size of the screen
        int height = size.y;
        // Detect collision and react
        if (ballY + ballRadius > height - 140) {
            if (colorBall == colorLine) {
                score++;
                ballSpeedY += 0.1;
                ballY = 1;
                randomColor();
                setColorLine();
                if (score >= nextBig) {
                    ballSpeedY += 0.3;
                    nextBig += 10;
                }
            }else{
                lost = true;
            }

        }
    }


    public void randomColor(){

        switch (random.nextInt(8)) {
            case 0:
                colorBall = Color.WHITE;
                break;
            case 1:
                colorBall = Color.RED;
                break;
            case 2:
                colorBall = Color.GREEN;
                break;
            case 3:
                colorBall = Color.BLUE;
                break;
            case 4:
                colorBall = Color.MAGENTA;
                break;
            case 5:
                colorBall = Color.YELLOW;
                break;
            case 6:
                colorBall = Color.GRAY;
                break;

            default:
                colorBall = Color.BLACK;
                break;
        }
    }

    public void setColorLine(){
        switch (random.nextInt(8)){
            case 0:
                colorLine = Color.BLUE;
                break;
            case 1:
                colorLine = Color.YELLOW;
                break;
            case 2:
                colorLine = Color.GREEN;
                break;
            case 3:
                colorLine = Color.WHITE;
                break;
            case 4:
                colorLine = Color.BLACK;
                break;
            case 5:
                colorLine = Color.RED;
                break;
            case 6:
                colorLine = Color.GRAY;
                break;

            default:
                colorLine = Color.MAGENTA;
                break;
        }
    }


}