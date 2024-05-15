package com.example.project_samsung;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GameLevel extends AppCompatActivity {

    private TextView timerTextView;
    private TextView displayTextView, displayBonusWord;
    private EditText inputEditText;
    private String[] words = {"apple", "banana", "orange", "grape", "melon"};
    private final int minValue = 0;
    private final int maxValue = words.length;
    public static int score = 0;
    private Handler handler = new Handler();
    private Handler handlerBonus = new Handler();
    private Runnable changeWordRunnable, changeBonusWordRunnable;
    final int COUNT_TIMER_SEC = 15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_level_run);

        displayBonusWord = findViewById(R.id.qest_bonus);
        timerTextView = findViewById(R.id.timeOut);
        displayTextView = findViewById(R.id.qest);
        inputEditText = findViewById(R.id.ans);

        registerForContextMenu(displayTextView);
        registerForContextMenu(displayBonusWord);

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        score = 0;

        // Initialize the display with the first word
        animati();
        animatiBonus();

        displayTextView.setText(words[(int)(minValue + Math.random() * (maxValue - minValue + 1))]);
        displayBonusWord.setText(words[(int)(minValue + Math.random() * (maxValue - minValue + 1))]);

        // Setup onClick listener for the submit button
        inputEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction() == KeyEvent.ACTION_DOWN && (keyCode == KeyEvent.KEYCODE_ENTER)){
                    checkInput();
                }
                return true;
            }
        });

        // Initialize and start the timer
        startTimer();
        startTimerBonus();
    }

    public void animati(){
        Animation animation = null;
        animation = AnimationUtils.loadAnimation(displayTextView.getContext(), R.anim.movedown);
        displayTextView.startAnimation(animation);
    }

    public void animatiBonus(){
        Animation animation = null;
        animation = AnimationUtils.loadAnimation(displayBonusWord.getContext(), R.anim.move_down_bonus);
        displayBonusWord.startAnimation(animation);
    }

    private void startTimer() {
        changeWordRunnable = new Runnable() {
            @Override
            public void run() {
                try {
                    changeDisplayedWord();
                }catch (Exception e){startTimer();}
            }
        };

        handler.postDelayed(changeWordRunnable, 7000); // Change word after 8 seconds
    }

    private void startTimerBonus() {
        changeBonusWordRunnable = new Runnable() {
            @Override
            public void run() {
                try {
                    changeDisplayedBonusWord();
                }catch (Exception e){startTimerBonus();}
            }
        };

        handlerBonus.postDelayed(changeBonusWordRunnable,4000);
    }


    private void changeDisplayedWord() {
        animati();
        displayTextView.setText(words[(int)(minValue + Math.random() * (maxValue - minValue + 1))]);
       // score--; // Decrement score when word changes automatically
        resetTimer();
    }
    private void changeDisplayedBonusWord() {
        animatiBonus();
        displayBonusWord.setText(words[(int)(minValue + Math.random() * (maxValue - minValue + 1))]);
       // score--; // Decrement score when word changes automatically
        resetTimerBonus();
    }

    private void checkInput(){
        try {
            String userInput = inputEditText.getText().toString().trim().toLowerCase();
            String displayedWord = displayTextView.getText().toString().trim().toLowerCase();
            String displayedBonusW = displayBonusWord.getText().toString().trim().toLowerCase();

            if (userInput.trim().equals(displayedBonusW.toLowerCase())){
                animatiBonus();
                displayBonusWord.setText(words[(int) (minValue + Math.random() * (maxValue - minValue + 1))]);
                score = score + 4; // Increment score when input matches displayed word
                inputEditText.setText(""); // Clear input field
                displayTextView.setText(words[(int) (minValue + Math.random() * (maxValue - minValue + 1))]);
                inputEditText.setText("");
                animati();
                resetTimerBonus();
            }

            else if (userInput.trim().equals(displayedWord.toLowerCase())) {
                animati();
                displayTextView.setText(words[(int) (minValue + Math.random() * (maxValue - minValue + 1))]);
                score++; // Increment score when input matches displayed word
                inputEditText.setText(""); // Clear input field
                resetTimer();
            }
            else {
                animati();
                animatiBonus();
                displayTextView.setText(words[(int)(minValue + Math.random() * (maxValue - minValue + 1))]);
                displayBonusWord.setText(words[(int) (minValue + Math.random() * (maxValue - minValue + 1))]);
                score--;
                inputEditText.setText("");
                resetTimer();
                resetTimerBonus();
            }
        }catch (Exception e){checkInput();}
    }


    private void resetTimer() {
        handler.removeCallbacks(changeWordRunnable);
        startTimer(); // Restart timer for next word change
    }
    private void resetTimerBonus() {
        handlerBonus.removeCallbacks(changeBonusWordRunnable);
        startTimerBonus(); // Restart timer for next bonus word change
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        countDownTimer.cancel();
        handler.removeCallbacks(changeWordRunnable);
        handlerBonus.removeCallbacks(changeBonusWordRunnable);// Clean up handler on activity destroy
    }


    CountDownTimer countDownTimer = new CountDownTimer(COUNT_TIMER_SEC *1000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            timerTextView.setText("Время: "+millisUntilFinished/1000);
        }

        @Override
        public void onFinish() {
            try {
                handler.removeCallbacks(changeWordRunnable);
                handlerBonus.removeCallbacks(changeBonusWordRunnable);
                Intent intent = new Intent(GameLevel.this, ResultWindow.class);
                startActivity(intent);
            }catch (Exception e){onFinish();}
        }
    }.start();

}