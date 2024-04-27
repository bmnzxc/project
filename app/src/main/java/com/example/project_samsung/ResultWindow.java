package com.example.project_samsung;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ResultWindow extends AppCompatActivity {
    private TextView result;
    private Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_window);
        result = findViewById(R.id.results);
        result.setText(String.valueOf(GameLevel.p));
        button = findViewById(R.id.tb);
        try {
            Thread.sleep(1000);
        }catch (Exception e){}
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultWindow.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
