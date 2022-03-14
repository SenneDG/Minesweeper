package model;

import notifier.IGameStateNotifier;
import model.Minesweeper;

public interface PlayableMinesweeper {
    int getWidth();
    int getHeight();
    void startNewGame(Difficulty level);
    void startNewGame(int row, int col, int explosionCount);
    void open(int x, int y);
    void toggleFlag(int x, int y);
    void setGameStateNotifier(IGameStateNotifier notifier);
    AbstractTile[][] getField();
}
