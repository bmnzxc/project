package com.example.project_samsung;


import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

public class StatisticsFragment extends Fragment {
    DBHelper dbHelper;
    private TextView midRes, countTests, bestResult, underMidRes, underCountTests, underBestResult;
    LinearLayout plateMid, plateCount, plateBest;

    public StatisticsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);

        plateMid = view.findViewById(R.id.mid_layoput);
        plateBest = view.findViewById(R.id.best_layoput);
        plateCount = view.findViewById(R.id.count_layoput);
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
        midRes = view.findViewById(R.id.mid_res);
        underMidRes = view.findViewById(R.id.under_mid_result);
        countTests = view.findViewById(R.id.count_tests);
        underCountTests = view.findViewById(R.id.under_count_tests);
        bestResult = view.findViewById(R.id.best_result);
        underBestResult = view.findViewById(R.id.under_best_result);



        midRes.setText(dbHelper.getMidCount());
        underMidRes.setText("Средний результат");
        bestResult.setText(String.valueOf(dbHelper.bestResult()));
        underBestResult.setText("Лучший результат");
        countTests.setText(String.valueOf(dbHelper.countOfTests()));
        underCountTests.setText("Поройденнные тесты");

        return view;
    }

    public void ani(LinearLayout plate){
        Animation animation = null;
        animation = AnimationUtils.loadAnimation(plate.getContext(), R.anim.on_press_scale_anim);
        plate.startAnimation(animation);
    }
}