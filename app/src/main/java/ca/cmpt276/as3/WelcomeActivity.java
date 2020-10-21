package ca.cmpt276.as3;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.view.Display;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class WelcomeActivity extends AppCompatActivity {

    Handler handler;
    Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set up button to skip to main menu
        setUpMainMenuButton();
        setUpAnimations();

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                gotoMainMenu();
            }
        };
        handler.postDelayed(runnable, 5000);

    }

    public void gotoMainMenu() {
        Intent intent = MainActivity.makeIntent(this);
        finish();
        startActivity(intent);
    }

    public void setUpMainMenuButton() {
        Button button = findViewById(R.id.button_main_menu);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.removeCallbacks(runnable);
                gotoMainMenu();
            }
        });
    }

    public void setUpAnimations() {
        TextView textView = findViewById(R.id.text_game_title);
        AlphaAnimation fadeIn = new AlphaAnimation(0.0f, 1.0f);
        textView.startAnimation(fadeIn);
        fadeIn.setDuration(5000);
        fadeIn.setFillAfter(true);;


        ImageView imageView = findViewById(R.id.image_welcome_image);
        RotateAnimation rotateAnimation = new RotateAnimation(0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        imageView.startAnimation(rotateAnimation);
        rotateAnimation.setDuration(5000);
    }

}