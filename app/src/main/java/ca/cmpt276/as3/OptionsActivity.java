package ca.cmpt276.as3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import ca.cmpt276.as3.model.OptionsConfig;

/**
 *  This activity is the screen that lets the user config the game settings
 *  Creates the radio buttons and saves the options in the OptionsConfig class object
 *  and shared preferences
 */
public class OptionsActivity extends AppCompatActivity {


    private static final String NUM_ROWS_KEY = "Num rows for game board";
    private static final String NUM_COLS_KEY = "Num cols for game board";
    private static final int DEFAULT_NUM_BUGS = 6;
    private static final int DEFAULT_NUM_ROWS = 6;
    private static final int DEFAULT_NUM_COLS = 4;
    public static final String NUM_BUGS_KEY = "Num bugs in game";
    private OptionsConfig optionsConfig;

    // Setting color of the radio buttons https://stackoverflow.com/questions/17120199/change-circle-color-of-radio-button/41516331
    private ColorStateList colorStateList = new ColorStateList(
            new int[][]{
                    new int[]{-android.R.attr.state_checked}, //disabled
                    new int[]{android.R.attr.state_checked} //enabled
            },
            new int[] {
                    Color.WHITE //disabled
                    ,Color.RED //enabled
            }
    );

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

    }

    private void setupNumBugsRadioButtons() {
        RadioGroup radioGroup = findViewById(R.id.radio_group_num_bugs);

        // Create the buttons
        int[] numBugs = getResources().getIntArray(R.array.num_hack_bugs);
        for (int i = 0; i < numBugs.length; i++) {
            final int numBug = numBugs[i];

            RadioButton button = new RadioButton(this);
            button.setText(getString(R.string.hacker_bugs, numBug));
            button.setTextColor(Color.parseColor("#15ff00"));

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveNumBug(numBug);
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
        SharedPreferences prefs = this.getSharedPreferences("AppPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(NUM_BUGS_KEY, numBug);
        editor.apply();
        optionsConfig.setNumBug(numBug);
    }

    static public int getNumBugInGame(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("AppPrefs", MODE_PRIVATE);
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
                }
            });

            radioGroup.addView(button);

            if (height == getNumRowsForBoard(this) &&
                    width == getNumColsForBoard(this)) {
                button.setChecked(true);
            }
        }
    }

    private void saveBoardSize(int numRows, int numCols) {
        SharedPreferences sharedPreferences = this.getSharedPreferences("AppPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(NUM_ROWS_KEY, numRows);
        editor.putInt(NUM_COLS_KEY, numCols);
        editor.apply();
        optionsConfig.setNumRow(numRows);
        optionsConfig.setNumCol(numCols);
    }

    static public int getNumRowsForBoard(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("AppPrefs", MODE_PRIVATE);

        return preferences.getInt(NUM_ROWS_KEY,
                DEFAULT_NUM_ROWS);
    }

    static public int getNumColsForBoard(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("AppPrefs", MODE_PRIVATE);

        return preferences.getInt(NUM_COLS_KEY,
                DEFAULT_NUM_COLS);
    }
}