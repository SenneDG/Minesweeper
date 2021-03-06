package notifier;

import model.Difficulty;
import model.Minesweeper;

import java.time.Duration;

public interface IGameStateNotifier {
    void notifyNewGame(int row, int col);
    void notifyGameLost();
    void notifyGameWon();
    void notifyFlagCountChanged(int newFlagCount);
    void notifyBombLeftChanged(int newBombLeft);
    void notifyTimeElapsedChanged(Duration newTimeElapsed);
    void notifyOpened(int x, int y, int explosiveNeighbourCount);
    void notifyFlagged(int x, int y);
    void notifyUnflagged(int x, int y);
    void notifyExploded(int x, int y);
    void resetTimer();

    Minesweeper returnMinesweeper();
}
