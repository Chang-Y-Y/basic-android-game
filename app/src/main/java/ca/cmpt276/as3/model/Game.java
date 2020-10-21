package ca.cmpt276.as3.model;

public class Game {
    private boolean isFinished;
    private int numBugs;
    private int numBugsFound;
    private int numScans;
    private Board board;
    private OptionsConfig optionsConfig;

    public Game() {
        isFinished = false;
        optionsConfig = OptionsConfig.getInstance();
        numBugs = optionsConfig.getNumBug();
        board = new Board(optionsConfig.getNumCol(), optionsConfig.getNumRow(),numBugs);
        numScans = 0;
        numBugsFound = 0;
    }

    public GameAction updateGame(int row, int col) {
        GameAction action = board.updateBoard(row, col);
        switch (action) {
            case SCANNED:
                numScans++;
                break;
            case FOUND:
                numBugsFound++;
                break;
            case CLICKED:
                break;
        }
        if (numBugsFound == numBugs) {
            isFinished = true;
        }
        return action;
    }

    public int getNumBugs() {
        return numBugs;
    }

    public int getNumBugsFound() {
        return numBugsFound;
    }

    public int getNumScans() {
        return numScans;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public Board getBoard() {
        return board;
    }
}
