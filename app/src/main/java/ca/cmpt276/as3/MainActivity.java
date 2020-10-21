package ca.cmpt276.as3;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import ca.cmpt276.as3.model.OptionsConfig;

/**
 * This will be the main menu activity where the "play", "help", and "options" buttons
 * will be. The manifest has been changed so that the welcome activity will launch first
 */

public class MainActivity extends AppCompatActivity {

    public static Intent makeIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set up options config
        populateOptionsConfig();

        // Set up buttons
        setUpPlayButton();
        setUpOptionsButton();
        setUpHelpButton();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public void setUpPlayButton() {
        Button button = findViewById(R.id.button_play);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OptionsConfig optionsConfig = OptionsConfig.getInstance();
                optionsConfig.incrementNumGamesStarted();
                OptionsActivity.saveNumGamesStarted(MainActivity.this, optionsConfig.getNumGamesStarted());

                Intent intent = GameActivity.makeIntent(MainActivity.this);
                startActivity(intent);
            }
        });
    }

    public void setUpOptionsButton() {
        Button button = findViewById(R.id.button_options);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = OptionsActivity.makeIntent(MainActivity.this);
                startActivity(intent);
            }
        });
    }

    public void setUpHelpButton() {
        Button button = findViewById(R.id.button_help);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = HelpActivity.makeIntent(MainActivity.this);
                startActivity(intent);
            }
        });
    }

    public void populateOptionsConfig() {
        OptionsConfig optionsConfig = OptionsConfig.getInstance();
        optionsConfig.setNumBug(OptionsActivity.getNumBugInGame(this));
        optionsConfig.setNumCol(OptionsActivity.getNumColsForBoard(this));
        optionsConfig.setNumRow(OptionsActivity.getNumRowsForBoard(this));
        optionsConfig.setNumGamesStarted(OptionsActivity.getNumGamesStarted(this));
        optionsConfig.setHighScore(OptionsActivity.getHighscoreForCurrentConfig(this,
                optionsConfig.constructConfigString()));
    }

}