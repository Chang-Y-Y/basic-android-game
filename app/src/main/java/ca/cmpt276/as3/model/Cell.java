package ca.cmpt276.as3.model;

/**
 * Represnts the spot to be clicked for the game logic
 */
public class Cell {
    private boolean isBug;
    private boolean isClicked;
    private boolean isScanned;

    public Cell(boolean isBug, boolean isClicked, boolean isScanned) {
        this.isBug = isBug;
        this.isClicked = isClicked;
        this.isScanned = isScanned;
    }

    public boolean isBug() {
        return isBug;
    }

    public void setBug(boolean bug) {
        isBug = bug;
    }

    public boolean isClicked() {
        return isClicked;
    }

    public void setClicked(boolean clicked) {
        isClicked = clicked;
    }

    public boolean isScanned() {
        return isScanned;
    }

    public void setScanned(boolean scanned) {
        isScanned = scanned;
    }
}
