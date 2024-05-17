package com.example.project_samsung;

import static androidx.navigation.Navigation.findNavController;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    private Button buttonStart, btnStats;
    private TextView textView;
    private long backPressedTime;


    DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DBHelper(this);

        textView = findViewById(R.id.LevelNum);
        buttonStart = findViewById(R.id.buttonStart);
        btnStats =findViewById(R.id.button_stats);


        textView.setText("Последний результат: " + String.valueOf(dbHelper.getLastNumber()));

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ani(buttonStart);
                try {
                    Intent intent = new Intent(MainActivity.this, GameLevel.class);
                    startActivity(intent);
                }catch (Exception e){onClick(v);}
            }
        });
        btnStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ani(btnStats);
                Intent intent = new Intent(MainActivity.this, Stats.class);
                startActivity(intent);
            }
        });

       // BottomNavigationView navView = findViewById(R.id.bottomNavigationView);
       // AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.homeFragment, R.id.storyFragment, R.id.settingsFragment).build();
       // NavController navController = Navigation.findNavController(this,R.id.fragmentContainerView);
      //  NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
       // NavigationUI.setupWithNavController(binding.bottomNavigationView, navController);
    }

    @Override
    public void onBackPressed() {
        if (backPressedTime + 1500>System.currentTimeMillis()){
            super.onBackPressed();
            return;
        }else {
            Toast.makeText(getBaseContext(),"Нажмите ещё раз, чтобы выйти", Toast.LENGTH_SHORT).show();
        }
        backPressedTime = System.currentTimeMillis();
    }
    public void ani(Button plate){
        Animation animation = null;
        animation = AnimationUtils.loadAnimation(plate.getContext(), R.anim.on_press_scale_anim);
        plate.startAnimation(animation);
    }
}