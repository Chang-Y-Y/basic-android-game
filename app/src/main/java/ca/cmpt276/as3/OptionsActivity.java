package ca.cmpt276.as3;

import android.content.Context;
import android.content.Intent;
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

public class OptionsActivity extends AppCompatActivity {

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

                }
            });

            // Add to radio group
            radioGroup.addView(button);

            if (i == 0) {
                radioGroup.check(button.getId());
            }
        }
    }

    private void setupBoardDimensionRadioButtons() {
        RadioGroup radioGroup = findViewById(R.id.radio_group_board_dimensions);

        int[] numCols = getResources().getIntArray(R.array.num_cols);
        int[] numRows = getResources().getIntArray(R.array.num_rows);

        for (int i = 0; i < numCols.length; i++) {
            int width = numCols[i];
            int height = numRows[i];

            RadioButton button = new RadioButton(this);
            button.setText(getString(R.string.board_dimension, height, width));
            button.setTextColor(Color.parseColor("#15ff00"));

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });


            radioGroup.addView(button);

            if (i == 0) {
                radioGroup.check(button.getId());
            }
        }

    }
}