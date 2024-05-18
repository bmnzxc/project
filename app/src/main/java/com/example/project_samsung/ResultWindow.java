package com.example.project_samsung;


import static com.example.project_samsung.MainActivity.*;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ResultWindow extends AppCompatActivity {
    private TextView result, rightWView, wrongWView, maxComboView;
    private Button button;
    DBHelper dbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_window);
        dbHelper = new DBHelper(this);
        result = findViewById(R.id.results);
        result.setText(String.valueOf(GameLevel.score));
        button = findViewById(R.id.tb);
        rightWView = findViewById(R.id.right_w_view);
        wrongWView = findViewById(R.id.wrong_w_view);
        maxComboView = findViewById(R.id.max_combo_view);

        rightWView.setText("Правильно слов: " + String.valueOf(GameLevel.rightW));
        wrongWView.setText("Неправильно слов: " + String.valueOf(GameLevel.wrongW));
        maxComboView.setText("Максимальное комбо:" + String.format("%.1f",GameLevel.maxCombo));

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dbHelper.addData(GameLevel.score, getCurrentDateTime());

        try {
            Thread.sleep(500);
        }catch (Exception e){}

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ani(button);
                    countDownTimer.cancel();
                    Intent intent = new Intent(ResultWindow.this, MainActivity.class);
                    startActivity(intent);
                }catch (Exception e){onClick(v);}
            }
        });
    }

    public static String getCurrentDateTime() {
        String format = "dd-MM-yyyy HH:mm";
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.getDefault());
        return dateFormat.format(currentDate);
    }

    CountDownTimer countDownTimer = new CountDownTimer(10000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {
            try {
                Intent intent = new Intent(ResultWindow.this, MainActivity.class);
                startActivity(intent);
            }catch (Exception e){onFinish();}
        }
    }.start();
    public void ani(Button plate){
        Animation animation = null;
        animation = AnimationUtils.loadAnimation(plate.getContext(), R.anim.on_press_scale_anim);
        plate.startAnimation(animation);
    }

}
