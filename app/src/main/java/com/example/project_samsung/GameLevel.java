package com.example.project_samsung;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class GameLevel extends AppCompatActivity{
    private EditText editText;
    private TextView textView, timeOut, points;
    static volatile boolean f = false;
    static String[] arr = new String[]{"бингус","гол", "спам", "вымпел","гриб","стоп"};
    static int si = arr.length;
    final int TIME_FOR_ANS = 4;
    public static Integer p = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_level_run);
        editText = findViewById(R.id.ans);
        timeOut = findViewById(R.id.timeOut);
        textView = findViewById(R.id.qest);



        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ///сделать через async task

        Words words = new Words();
        words.execute();

        Runnable runnable = new Runnable() {
            @Override
            public synchronized void run() {
                int i = 0;
                while (true){
                    f = false;
                    i = 0;
                    try {
                        textView.append(arr[(int) (0 + Math.random() * (si - 0 + 1))]);
                       // textView.setText(arr[(int) (0 + Math.random() * (si - 0 + 1))]);
                        while(!f || i != TIME_FOR_ANS){
                            Thread.sleep(500);
                            i++;
                        }
                        Thread.sleep(100);
                    } catch (Exception e) {}
                }
            }
        };


        Thread thread = new Thread(runnable);
        thread.start();


        CountDownTimer countDownTimer = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long ti) {
                timeOut.setText("soconds: " + (int) (ti / 1000));
            }

            @Override
            public void onFinish() {
                Intent intent = new Intent(GameLevel.this, ResultWindow.class);
                startActivity(intent);
            }
        }.start();


        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction() == KeyEvent.ACTION_DOWN &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    if (editText.getText().toString().trim().equals(textView.getText().toString())) {
                        p++;
                        f = true;
                    } else {
                        p--;
                        f = false;
                    }
                }
                return true;
            }
        });
    }
    protected class Words extends AsyncTask<Void, Void, String>{


        @Override
        protected String doInBackground(Void... booleans) {
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            while (true){
                f = false;
                int i = 0;
                try {
                    textView.append(arr[(int) (0 + Math.random() * (si - 0 + 1))]);
                    // textView.setText(arr[(int) (0 + Math.random() * (si - 0 + 1))]);
                    while(!f || i != TIME_FOR_ANS){
                        Thread.sleep(500);
                        i++;
                    }
                    Thread.sleep(100);
                } catch (Exception e) {}
            }
        }
    }

}