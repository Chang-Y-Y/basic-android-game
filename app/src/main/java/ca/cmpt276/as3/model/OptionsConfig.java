package ca.cmpt276.as3.model;

/**
 * Singleton object container for options configuration
 * Gets initalized in the main activity
 */
public class OptionsConfig {

    private int numBug;
    private int numRow;
    private int numCol;
    private int highScore;
    private int numGamesStarted;

    private static OptionsConfig instance;

    private OptionsConfig() {

    }

    public static OptionsConfig getInstance() {
        if (instance == null) {
            instance = new OptionsConfig();
        }
        return instance;
    }

    public int getNumBug() {
        return numBug;
    }

    public void setNumBug(int numBug) {
        this.numBug = numBug;
    }

    public int getNumRow() {
        return numRow;
    }

    public void setNumRow(int numRow) {
        this.numRow = numRow;
    }

    public int getNumCol() {
        return numCol;
    }

    public void setNumCol(int numCol) {
        this.numCol = numCol;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    public int getNumGamesStarted() {
        return numGamesStarted;
    }

    public void setNumGamesStarted(int numGamesStarted) {
        this.numGamesStarted = numGamesStarted;
    }

    public void incrementNumGamesStarted() {
        numGamesStarted++;
    }

    public String constructConfigString() {
        return numRow + " rows " + numCol + " columns " + numBug + " bugs";
    }
}
