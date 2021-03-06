package ca.cmpt276.as3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import ca.cmpt276.as3.model.OptionsConfig;

/**
 *  This activity is the screen that lets the user config the game settings
 *  Creates the radio buttons and saves the options in the OptionsConfig class object
 *  and shared preferences
 */
public class OptionsActivity extends AppCompatActivity {

    private static final String SHARED_PREFS_NAME = "AppPrefs";
    private static final String NUM_BUGS_KEY = "Num bugs in game";
    private static final String NUM_ROWS_KEY = "Num rows for game board";
    private static final String NUM_COLS_KEY = "Num cols for game board";
    private static final String NUM_GAMES_STARTED_KEY = "Num games started";
    private static final int DEFAULT_NUM_BUGS = 6;
    private static final int DEFAULT_NUM_ROWS = 4;
    private static final int DEFAULT_NUM_COLS = 6;

    private OptionsConfig optionsConfig;

    public static Intent makeIntent(Context context) {
        return new Intent(context, OptionsActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        optionsConfig = OptionsConfig.getInstance();

        // Set up Radio Options
        setupNumBugsRadioButtons();
        setupBoardDimensionRadioButtons();
        setupResetHighScoreButton();
        setupResetTimesPlayedButton();
    }

    private void setupNumBugsRadioButtons() {
        RadioGroup radioGroup = findViewById(R.id.radio_group_num_bugs);

        // Create the buttons
        int[] numBugs = getResources().getIntArray(R.array.num_hack_bugs);
        for (final int numBug : numBugs) {

            RadioButton button = new RadioButton(this);
            button.setText(getString(R.string.hacker_bugs, numBug));
            button.setTextColor(Color.parseColor("#15ff00"));

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveNumBug(numBug);
                    optionsConfig.setHighScore(getHighscoreForCurrentConfig(
                            OptionsActivity.this,
                            optionsConfig.constructConfigString()));
                }
            });

            // Add to radio group
            radioGroup.addView(button);

            if (numBug == getNumBugInGame(this)) {
                button.setChecked(true);
            }
        }
    }

    private void saveNumBug(int numBug) {
        SharedPreferences prefs = this.getSharedPreferences(SHARED_PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(NUM_BUGS_KEY, numBug);
        editor.apply();
        optionsConfig.setNumBug(numBug);
    }

    static public int getNumBugInGame(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREFS_NAME, MODE_PRIVATE);
        return prefs.getInt(NUM_BUGS_KEY, DEFAULT_NUM_BUGS);
    }

    private void setupBoardDimensionRadioButtons() {
        RadioGroup radioGroup = findViewById(R.id.radio_group_board_dimensions);

        final int[] numCols = getResources().getIntArray(R.array.num_cols);
        int[] numRows = getResources().getIntArray(R.array.num_rows);

        for (int i = 0; i < numCols.length; i++) {
            final int width = numCols[i];
            final int height = numRows[i];

            RadioButton button = new RadioButton(this);
            button.setText(getString(R.string.board_dimension, height, width));
            button.setTextColor(Color.parseColor("#15ff00"));

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveBoardSize(height, width);
                    optionsConfig.setHighScore(getHighscoreForCurrentConfig(
                            OptionsActivity.this,
                            optionsConfig.constructConfigString()));
                }
            });

            radioGroup.addView(button);

            if (height == getNumRowsForBoard(this) &&
                    width == getNumColsForBoard(this)) {
                button.setChecked(true);
            }
        }
    }

    private void setupResetHighScoreButton() {
        Button button = findViewById(R.id.button_reset_highscore);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                optionsConfig.setHighScore(0);
                saveHighScoreForCurrentConfig(OptionsActivity.this,
                        optionsConfig.constructConfigString(),
                        0);
            }
        });
    }

    private void setupResetTimesPlayedButton() {
        Button button = findViewById(R.id.button_reset_played_games);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                optionsConfig.setNumGamesStarted(0);
                saveNumGamesStarted(OptionsActivity.this, 0);
            }
        });
    }

    private void saveBoardSize(int numRows, int numCols) {
        SharedPreferences sharedPreferences = this.getSharedPreferences(SHARED_PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(NUM_ROWS_KEY, numRows);
        editor.putInt(NUM_COLS_KEY, numCols);
        editor.apply();
        optionsConfig.setNumRow(numRows);
        optionsConfig.setNumCol(numCols);
    }

    static public int getNumRowsForBoard(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFS_NAME, MODE_PRIVATE);

        return preferences.getInt(NUM_ROWS_KEY,
                DEFAULT_NUM_ROWS);
    }

    static public int getNumColsForBoard(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFS_NAME, MODE_PRIVATE);

        return preferences.getInt(NUM_COLS_KEY,
                DEFAULT_NUM_COLS);
    }

    static public void saveNumGamesStarted(Context context, int numGames) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(NUM_GAMES_STARTED_KEY, numGames);
        editor.apply();
    }


    static public int getNumGamesStarted(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFS_NAME, MODE_PRIVATE);

        return preferences.getInt(NUM_GAMES_STARTED_KEY, 0);
    }

    static public int getHighscoreForCurrentConfig(Context context, String configKey) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFS_NAME, MODE_PRIVATE);

        return preferences.getInt(configKey, 0);
    }

    static public void saveHighScoreForCurrentConfig(Context context, String configKey, int value) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(configKey, value);
        editor.apply();
    }
}
