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

    private TextView timerTextView, displayTextView, displayBonusWord, comboView;
    private EditText inputEditText;
    private String[] words = {"apple", "banana", "orange", "grape", "melon"};
    public static int score, rightW, wrongW;
    private Handler handlerBonus = new Handler(), handler = new Handler();
    private Runnable changeWordRunnable, changeBonusWordRunnable;
    private final int COUNT_TIMER_SEC = 20, TIME_FOR_BONUS_W_SEC = 5, minValue = 0, maxValue = words.length;
    int decrease = 0;
    public static double combo = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_level_run);
        displayBonusWord = findViewById(R.id.qest_bonus);
        timerTextView = findViewById(R.id.timeOut);
        displayTextView = findViewById(R.id.qest);
        inputEditText = findViewById(R.id.ans);
        comboView = findViewById(R.id.comboView);

        registerForContextMenu(displayTextView);
        registerForContextMenu(displayBonusWord);

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        score = 0;
        combo = 1;
        rightW = 0;
        wrongW = 0;
        try {
            Thread.sleep(500);
            displayTextView.setText(words[(int) (minValue + Math.random() * (maxValue - minValue + 1))]);
            displayBonusWord.setText(words[(int) (minValue + Math.random() * (maxValue - minValue + 1))]);
            animati();
            animatiBonus();
        }catch (Exception e){onBackPressed();}

        inputEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction() == KeyEvent.ACTION_DOWN && (keyCode == KeyEvent.KEYCODE_ENTER)){
                    checkInput();
                }
                return true;
            }
        });
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
        animation.setDuration((TIME_FOR_BONUS_W_SEC*1000) - decrease);
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

        handlerBonus.postDelayed(changeBonusWordRunnable, TIME_FOR_BONUS_W_SEC *1000 - decrease);
    }


    private void changeDisplayedWord() {
        displayTextView.setText(words[(int)(minValue + Math.random() * (maxValue - minValue + 1))]);
        animati();
        resetTimer();
    }
    private void changeDisplayedBonusWord() {
        displayBonusWord.setText(words[(int)(minValue + Math.random() * (maxValue - minValue + 1))]);
        animatiBonus();
        resetTimerBonus();
    }

    private void checkInput(){
        try {
            String userInput = inputEditText.getText().toString().trim().toLowerCase();
            String displayedWord = displayTextView.getText().toString().trim().toLowerCase();
            String displayedBonusW = displayBonusWord.getText().toString().trim().toLowerCase();


            // Бонусное слово
            if (userInput.trim().toLowerCase().equals(displayedBonusW.trim().toLowerCase())) {
                if (decrease < 1500){
                    decrease += 300;
                }
                combo += 0.2;
                rightW++;
                ani(comboView);
                comboView.setText(String.format("%.1f",combo));
                score = score + 3; // Increment score when input matches displayed word
                inputEditText.setText(""); // Clear input field
                changeDisplayedBonusWord();
                changeDisplayedWord();
            }
            // обычное слово
            else if (userInput.trim().toLowerCase().equals(displayedWord.trim().toLowerCase())) {
                combo += 0.2;
                rightW++;
                decrease = 0;
                ani(comboView);
                animati();
                comboView.setText(String.format("%.1f",combo));
                score++; // Increment score when input matches displayed word
                inputEditText.setText(""); // Clear input field
                changeDisplayedWord();
            }
            else {
                combo = 1.0;
                decrease = 0;
                wrongW--;
                ani(comboView);
                comboView.setText(String.format("%.1f",combo));
                score--;
                inputEditText.setText("");
                changeDisplayedWord();
                changeDisplayedBonusWord();
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
                if (wrongW<0)
                    wrongW = 0;
                score = score * (int)combo;
                handler.removeCallbacks(changeWordRunnable);
                handlerBonus.removeCallbacks(changeBonusWordRunnable);
                Intent intent = new Intent(GameLevel.this, ResultWindow.class);
                startActivity(intent);
            }catch (Exception e){onFinish();}
        }
    }.start();

    public void ani(TextView text){
        Animation animation = null;
        animation = AnimationUtils.loadAnimation(text.getContext(), R.anim.combo_count_anim);
        text.startAnimation(animation);
    }
}