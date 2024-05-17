package com.example.project_samsung;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class HomeFragment extends Fragment {

    private DBHelper dbHelper;
    private TextView textView;
    private Button buttonStart;

    public HomeFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        dbHelper = new DBHelper(getActivity()); // Используйте getActivity() для получения контекста

        textView = view.findViewById(R.id.LevelNum);
        buttonStart = view.findViewById(R.id.buttonStart);

        textView.setText("Последний результат: " + dbHelper.getLastNumber());

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ani(buttonStart);
                try {
                    Intent intent = new Intent(getActivity(), GameLevel.class);
                    startActivity(intent);
                } catch (Exception e) {
                    onClick(v);
                }
            }
        });


        return view;
    }
    public void ani(Button plate){
        Animation animation = null;
        animation = AnimationUtils.loadAnimation(plate.getContext(), R.anim.on_press_scale_anim);
        plate.startAnimation(animation);
    }
}
