package ca.cmpt276.as3;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import ca.cmpt276.as3.model.Cell;
import ca.cmpt276.as3.model.Game;
import ca.cmpt276.as3.model.GameAction;
import ca.cmpt276.as3.model.OptionsConfig;

/**
 * Activity class for playing the game. This activity is where the main game is played
 */
public class GameActivity extends AppCompatActivity {

    private OptionsConfig optionsConfig;
    private Game game;

    Button buttons[][];

    public static Intent makeIntent(Context context) {
        return new Intent(context, GameActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Setting up variables
        optionsConfig = OptionsConfig.getInstance();
        buttons = new Button[optionsConfig.getNumRow()][optionsConfig.getNumCol()];
        game = new Game();

        // Setting up ui components
        populateButtons();
        lockButtonSizes();
        initGameText();

    }

    private void populateButtons() {
        int rows = optionsConfig.getNumRow();
        int cols = optionsConfig.getNumCol();

        TableLayout tableLayout = findViewById(R.id.table_layout_game_board);
        for (int row = 0; row < rows; row++) {
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.MATCH_PARENT,
                1.0f));
            tableLayout.addView(tableRow);

            for (int col = 0; col < cols; col++) {
                final int FINAL_COL = col;
                final int FINAL_ROW = row;

                Button button = new Button(this);
                button.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT,
                        1.0f
                ));

                button.setPadding(0, 0, 0, 0);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gridButtonClicked(FINAL_ROW, FINAL_COL);
                        checkGameFinished();
                    }
                });

                tableRow.addView(button);
                buttons[row][col] = button;
            }
        }
    }

    private void changeButtonIcons(Button button, int iconID) {
        int newWidth = button.getWidth();
        int newHeight = button.getHeight();
        Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), iconID);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, newWidth, newHeight, true);
        Resources resources = getResources();
        button.setBackground(new BitmapDrawable(resources, scaledBitmap));
    }

    private void gridButtonClicked(int row, int col) {

        Button button = buttons[row][col];

        // Lock Button Sizes
        lockButtonSizes();

        GameAction action = game.updateGame(row, col);
        switch (action) {
            case FOUND:
                changeButtonIcons(button, R.drawable.found_bug);
                break;
            case SCANNED:
                button.setText(getString(R.string.number, game.getNumScans()));
                if (game.getBoard().getCellAt(row, col).isBug()) {
                    button.setTextColor(Color.parseColor("#FFFFFF"));
                }
        }
        refreshScreen(row, col);
    }

    private void lockButtonSizes() {
        for (int row = 0; row < optionsConfig.getNumRow(); row++) {
            for (int col = 0; col < optionsConfig.getNumCol(); col++) {
                Button button = buttons[row][col];

                int width = button.getWidth();
                button.setMinWidth(width);
                button.setMaxWidth(width);

                int height = button.getHeight();
                button.setMaxHeight(height);
                button.setMinHeight(height);
            }
        }
    }

    private void refreshScreen(int row, int col) {

        for (int i = 0; i < game.getBoard().getHeight(); i++) {
            Cell cell = game.getBoard().getCellAt(i, col);
            if (cell.isScanned()) {
                buttons[i][col].setText(getString(R.string.number, game.getBoard().getNumOfBugsInPos(i, col)));
            }
            int duration = 2500 * Math.abs(i - row) / game.getBoard().getHeight();
            animateButton(i, col, duration);

        }
        for (int j = 0; j < game.getBoard().getWidth(); j++) {
            Cell cell = game.getBoard().getCellAt(row, j);
            if (cell.isScanned()) {
                buttons[row][j].setText(getString(R.string.number, game.getBoard().getNumOfBugsInPos(row, j)));
            }
            int duration = 2500 * (Math.abs(j - col)) / game.getBoard().getWidth();
            animateButton(row, j, duration);
        }
        updateGameTextState();
    }

    private void checkGameFinished() {
        if (game.isFinished()) {
            if (optionsConfig.getHighScore() > game.getNumScans()
                    || optionsConfig.getHighScore() == 0) {

                optionsConfig.setHighScore(game.getNumScans());
                OptionsActivity.saveHighScoreForCurrentConfig(this,
                                        optionsConfig.constructConfigString(),
                                        optionsConfig.getHighScore());
            }

            FragmentManager manager = getSupportFragmentManager();
            WinMessageFragment dialog = new WinMessageFragment();
            dialog.show(manager, "MessageDialog");
        }
    }

    private void updateGameTextState() {
        TextView textView = findViewById(R.id.text_num_bugs_found);
        textView.setText(getString(R.string.number_of_hacker_bugs_found,
                game.getNumBugsFound(),
                game.getNumBugs()));
        textView = findViewById(R.id.text_num_scans_used);
        textView.setText(getString(R.string.number_of_scans_used, game.getNumScans()));
    }

    private void initGameText() {
        updateGameTextState();
        TextView textView = findViewById(R.id.text_num_games_played);
        textView.setText(getString(R.string.num_games_started, optionsConfig.getNumGamesStarted()));
        textView = findViewById(R.id.text_high_score);
        textView.setText(getString(R.string.highscore_text, optionsConfig.getHighScore()));
    }

    private void animateButton(int row,int col, int duration) {
        Animation fadeOut = new AlphaAnimation(1f, 0.4f);
        fadeOut.setDuration(duration);
        fadeOut.setInterpolator(new AccelerateInterpolator());

        Button button = buttons[row][col];
        button.startAnimation(fadeOut);

    }
}
