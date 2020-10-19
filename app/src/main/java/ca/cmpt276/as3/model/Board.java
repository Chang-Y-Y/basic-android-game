package ca.cmpt276.as3.model;

public class Board {

    private Cell board[][];
    private int width;
    private int height;
    private int[] rowSums;
    private int[] colSums;

    public Board(int width, int height, int numBugs) {
        this.width = width;
        this.height = height;
        board = new Cell[height][width];
        rowSums = new int[height];
        colSums = new int[width];

        initializeBoard(numBugs);
        calculateSums();

    }

    private void initializeBoard(int numBugs) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                board[i][j] = new Cell(false, false, false);
                if (numBugs > 0) {
                    board[i][j].setBug(true);
                    numBugs--;
                }
            }
        }
    }

    private void calculateSums() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if(board[i][j].isBug() && !board[i][j].isClicked()) {
                    rowSums[i]++;
                    colSums[j]++;
                }
            }
        }
    }

    public int getNumOfBugsInPos(int row, int col) {
        return rowSums[row] + colSums[col];
    }

    public GameAction updateBoard(int row, int col) {
        Cell cell = board[row][col];

        if (cell.isBug() && !cell.isClicked()) {
            cell.setClicked(true);
            updateSums(row, col);
            return GameAction.FOUND;
        }

        else if (!cell.isScanned()) {
            cell.setScanned(true);
            return GameAction.SCANNED;
        }

        return GameAction.CLICKED;
    }

    private void updateSums(int row, int col) {
        rowSums[row]--;
        colSums[col]--;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Cell getCellAt(int row, int col) {
        return board[row][col];
    }

}
