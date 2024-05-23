package com.example.countdowntimer;

import android.os.CountDownTimer;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import android.app.Activity;

public class MainActivity extends Activity {
    private static final long TOTAL_TIME = 10_000;  //600_000; // 600,000 ms = 10 min

    private TextView countDownText;
    private Button countDownButton;
    private Button resetButton;

    private CountDownTimer timer;
    private long timeLeftInMilliSeconds;
    private boolean timerRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        countDownText = findViewById(R.id.countdown_text);
        countDownButton = findViewById(R.id.countdown_button);
        resetButton = findViewById(R.id.reset_button);

        countDownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startStop();
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetTimer();
            }
        });

        resetTimer();
    }

    public void startStop() {
        if (timerRunning) {
            pauseTimer();
        }
        else {
            startTimer();
        }
    }

    public void startTimer() {
        // build a new timer
        timer = new CountDownTimer(timeLeftInMilliSeconds, 100) {
            @Override
            public void onTick(long l) {
                timeLeftInMilliSeconds = l;
                updateTimer();
            }

            @Override
            public void onFinish() {
                timerRunning = false;
                countDownButton.setText("start");
                countDownButton.setVisibility(View.INVISIBLE);
                resetButton.setVisibility(View.VISIBLE);

                Toast.makeText(
                        getApplicationContext(),
                        "Time's Up!", Toast.LENGTH_LONG).show();
            }
        };

        // and start it immediately
        timer.start();

        countDownButton.setText("pause");
        resetButton.setVisibility(View.INVISIBLE);
        timerRunning = true;
    }

    public void pauseTimer() {
        timer.cancel();

        countDownButton.setText("start");
        resetButton.setVisibility(View.VISIBLE);
        timerRunning = false;
    }

    public void resetTimer() {
        timeLeftInMilliSeconds = TOTAL_TIME;
        updateTimer();
        resetButton.setVisibility(View.INVISIBLE);
        countDownButton.setVisibility(View.VISIBLE);
    }

    public void updateTimer() {
        // get the minutes that are left
        // 60_000 ms = 1 min, so we divide the total ms
        // left by one min to get the minutes left
        int minutes = (int) timeLeftInMilliSeconds / 60_000;

        // get the second that are left
        // get the ms that are left and divide them by 1000
        // to convert them into seconds
        int seconds = (int) (timeLeftInMilliSeconds % 60_000) / 1000;

        // build the updated timer text
        StringBuilder timeLeftTextBuilder = new StringBuilder();

        timeLeftTextBuilder.append(minutes);
        timeLeftTextBuilder.append(":");

        if (seconds < 10) {
            timeLeftTextBuilder.append(0);
        }

        timeLeftTextBuilder.append(seconds);

        countDownText.setText(timeLeftTextBuilder.toString());
    }

}