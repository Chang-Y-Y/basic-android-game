package ca.cmpt276.as3.model;

public class OptionsConfig {

    private int numBug;
    private int numRow;
    private int numCol;

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
}
