import model.Difficulty;
import model.Minesweeper;
import model.PlayableMinesweeper;
import view.MinesweeperView;

public class App {
    public static void main(String[] args) throws Exception {
        Minesweeper test = new Minesweeper();
        test.startNewGame(Difficulty.EASY);
        MinesweeperView view = new MinesweeperView();
        //Uncomment the lines below once your game model code is ready; don't forget to import your game model 
        PlayableMinesweeper model = new Minesweeper();
        
        
        /**
            Your code to bind your game model to the game user interface
        */
        test.startNewGame(Difficulty.EASY);
        int x = test.getHeight();
        int y = test.getWidth();
        //view.setGameModel(test);
        view.notifyNewGame(x, y);





        //model.startNewGame(Difficulty.EASY);
    }
}
