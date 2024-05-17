package com.example.project_samsung;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.text.SpannableString;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class Stats extends AppCompatActivity {
    DBHelper dbHelper;
    private TextView midRes, countTests, bestResult, underMidRes, underCountTests, underBestResult;
    LinearLayout plateMid, plateCount, plateBest;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stats_layout);

        plateMid = findViewById(R.id.mid_layoput);
        plateBest = findViewById(R.id.best_layoput);
        plateCount = findViewById(R.id.count_layoput);
        ani(plateCount);
        ani(plateMid);
        ani(plateBest);
        plateCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ani(plateCount);
            }
        });
        plateMid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ani(plateMid);
            }
        });
        plateBest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ani(plateBest);
            }
        });


        dbHelper = new DBHelper(this);
        midRes = findViewById(R.id.mid_res);
        underMidRes = findViewById(R.id.under_mid_result);
        countTests = findViewById(R.id.count_tests);
        underCountTests = findViewById(R.id.under_count_tests);
        bestResult = findViewById(R.id.best_result);
        underBestResult = findViewById(R.id.under_best_result);



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

    public void ani(LinearLayout plate){
        Animation animation = null;
        animation = AnimationUtils.loadAnimation(plate.getContext(), R.anim.on_press_scale_anim);
        plate.startAnimation(animation);
    }

}
