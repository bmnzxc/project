package com.example.project_samsung;


import static com.example.project_samsung.MainActivity.*;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ResultWindow extends AppCompatActivity {
    private TextView result;
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

}
