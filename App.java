import model.Difficulty;
import model.Minesweeper;
import model.PlayableMinesweeper;
import view.MinesweeperView;

public class App {
    public static void main(String[] args) throws Exception {
        //Minesweeper test = new Minesweeper();
        //test.startNewGame(Difficulty.MEDIUM);
        //MinesweeperView view = new MinesweeperView();
        //Uncomment the lines below once your game model code is ready; don't forget to import your game model 
        PlayableMinesweeper model = new Minesweeper();
        MinesweeperView mine = new MinesweeperView(model);
        
        
        /**
            Your code to bind your game model to the game user interface
        */
        
        
        model.startNewGame(Difficulty.EASY);
        int h = model.getHeight();
        int w = model.getWidth();
        mine.notifyNewGame(w, h);
    }
}
