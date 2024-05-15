package com.example.project_samsung;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Stats extends AppCompatActivity {
    DBHelper dbHelper;
    private TextView midRes, countTests, bestResult, underMidRes, underCountTests, underBestResult;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stats_layout);
        dbHelper = new DBHelper(this);
        midRes = findViewById(R.id.mid_res);
        underMidRes = findViewById(R.id.under_mid_result);
        countTests = findViewById(R.id.count_tests);
        underCountTests = findViewById(R.id.under_count_tests);
        bestResult = findViewById(R.id.best_result);
        underBestResult = findViewById(R.id.under_best_result);


        SpannableString ss = new SpannableString(String.valueOf(dbHelper.bestResult()) + "\n");


        midRes.setText(dbHelper.getMidCount());
        underMidRes.setText("Средний результат");
        bestResult.setText(String.valueOf(dbHelper.bestResult()));
        underBestResult.setText("Лучший результат");
        countTests.setText(String.valueOf(dbHelper.countOfTests()));
        underCountTests.setText("Поройденнные тесты");

    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Stats.this,MainActivity.class);
        startActivity(intent);
    }

}
