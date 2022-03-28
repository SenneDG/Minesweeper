package view;

import model.Difficulty;
import model.Minesweeper;
import model.PlayableMinesweeper;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.DimensionUIResource;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.util.concurrent.TimeUnit;


import notifier.IGameStateNotifier;

public class MinesweeperView implements IGameStateNotifier {
    public static final int MAX_TIME = 1;//in minutes
    public static final int TILE_SIZE = 50;

    public static final class AssetPath {
        public static final String CLOCK_ICON = "./assets/icons/clock.png";
        public static final String FLAG_ICON = "./assets/icons/flag.png";
        public static final String BOMB_ICON = "./assets/icons/bomb.png";
    }
    private PlayableMinesweeper gameModel;
    private JFrame window;
    private JMenuBar menuBar;
    private JMenu gameMenu;
    private JMenuItem easyGame, mediumGame, hardGame;
    private TileView[][] tiles;
    private JPanel world = new JPanel();
    private JPanel timerPanel = new JPanel();
    private JPanel flagPanel = new JPanel();
    private JLabel timerView = new JLabel();
    private JLabel flagCountView = new JLabel();
    private Minesweeper minesweeper;
    private int flagAmount = 0;
    private int correctAmount = 0;
    private boolean firstTileRule = false;
    private Difficulty difficulty = Difficulty.EASY;
    private boolean timer = true;
    long displayMinutes=0;
    long secondspassed = 0;
    long starttime=System.currentTimeMillis();


    public MinesweeperView() {
        minesweeper = new Minesweeper();
        minesweeper.startNewGame(difficulty);

        this.window = new JFrame("Minesweeper");
        timerPanel.setLayout(new FlowLayout());
        this.menuBar = new JMenuBar();
        this.gameMenu = new JMenu("New Game");
        this.menuBar.add(gameMenu);
        
        this.easyGame = new JMenuItem("Easy");
        this.gameMenu.add(this.easyGame);
        this.easyGame.addActionListener((ActionEvent e) -> {
            if (gameModel != null) 
                gameModel.startNewGame(Difficulty.EASY);
                notifyNewGame(8,8);
        });
        this.mediumGame = new JMenuItem("Medium");
        this.gameMenu.add(this.mediumGame);
        this.mediumGame.addActionListener((ActionEvent e) -> {
            if (gameModel != null)
                gameModel.startNewGame(Difficulty.MEDIUM);
                notifyNewGame(16,16);
        });
        this.hardGame = new JMenuItem("Hard");
        this.gameMenu.add(this.hardGame);
        this.hardGame.addActionListener((ActionEvent e) -> {
            if (gameModel != null)
                gameModel.startNewGame(Difficulty.HARD);
                notifyNewGame(24,24);
        });
        
        this.window.setJMenuBar(this.menuBar);

        try {
            JLabel clockIcon = new JLabel(new ImageIcon(ImageIO.read(new File(AssetPath.CLOCK_ICON))));
            clockIcon.setSize(new DimensionUIResource(1, 1));
            timerPanel.add(clockIcon);
            //timerPanel.add(new JLabel("TIME ELAPSED: " + timeElapsed));
            timerPanel.add(this.timerView);
        } catch (IOException e) {
            System.out.println("Unable to locate clock resource");
        }
        flagPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        try {
            JLabel clockIcon = new JLabel(new ImageIcon(ImageIO.read(new File(AssetPath.FLAG_ICON))));
            clockIcon.setSize(new DimensionUIResource(1, 1));
            flagPanel.add(clockIcon);
            flagPanel.add(new JLabel("FLAG: "));
            flagPanel.add(this.flagCountView);
        } catch (IOException e) {
            System.out.println("Unable to locate flag resource");
        }

        this.window.setLayout(new GridBagLayout());
        GridBagConstraints layoutConstraints = new GridBagConstraints();
        layoutConstraints.gridwidth = java.awt.GridBagConstraints.RELATIVE;
        layoutConstraints.fill = GridBagConstraints.HORIZONTAL;
        layoutConstraints.gridx = 0;
        layoutConstraints.gridy = 0;
        this.window.add(timerPanel, layoutConstraints);
        layoutConstraints.gridx = 1;
        layoutConstraints.gridy = 0;
        this.window.add(flagPanel, layoutConstraints);
        layoutConstraints.fill = GridBagConstraints.BOTH;
        layoutConstraints.gridx = 0;
        layoutConstraints.gridy = 1;
        layoutConstraints.gridwidth = 2;
        layoutConstraints.weightx = 1.0;
        layoutConstraints.weighty = 1.0;
        this.window.add(world, layoutConstraints);
        this.window.setSize(500, 1);
        this.window.setVisible(true);
        this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        this.world.setVisible(true);
    }

    public MinesweeperView(PlayableMinesweeper gameModel) {
        this();
        this.setGameModel(gameModel);
    }

    public void setGameModel(PlayableMinesweeper newGameModel) {
        this.gameModel = newGameModel;
        this.gameModel.setGameStateNotifier(this);
    }

    public void timer() throws InterruptedException
    {
        boolean minutes = false;
        while(timer)
        {
            TimeUnit.SECONDS.sleep(1);
            long timepassed=System.currentTimeMillis()-starttime;
            long secondspassed=timepassed/1000;
            if(secondspassed==60)
            {
                secondspassed=0;
                minutes = true;
                starttime=System.currentTimeMillis();
            }
            if((secondspassed%60)==0)
                displayMinutes++;

            if(minutes) {
                if (secondspassed == 1) {
                    if(displayMinutes == 1) {
                        timerView.setText("TIME ELAPSED: " + displayMinutes + " minute and " + secondspassed + " second");
                    }
                    else {
                        timerView.setText("TIME ELAPSED: " + displayMinutes + " minutes and " + secondspassed + " second");
                    }
                }
                else {
                    timerView.setText("TIME ELAPSED: " + displayMinutes + " minutes and " + secondspassed + " seconds");
                }
            }
            else {
                if(secondspassed == 1) {
                    timerView.setText("TIME ELAPSED: " + secondspassed + " second");
                }
                else {
                    timerView.setText("TIME ELAPSED: " + secondspassed + " seconds");
                }
            }
        }
        timer();
    }

    @Override
    public void notifyNewGame(int row, int col) {
        this.flagCountView.setText("0");
        this.window.setSize(col * TILE_SIZE, row * TILE_SIZE + 30);
        this.world.removeAll();
        timer = true;
        secondspassed = 0;
        displayMinutes = 0;


        this.tiles = new TileView[row][col];
        for (int i=0; i<row; ++i) {
            for (int j=0; j<col; ++j) {
                TileView temp = new TileView(j, i); 
                temp.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent arg0) {
                        if (arg0.getButton() == MouseEvent.BUTTON1){
                            if (gameModel!=null)
                                gameModel.open(temp.getPositionX(), temp.getPositionY());
                                System.out.println("coordinate: "+"("+ temp.getPositionX()+ "," + temp.getPositionY() + ")");

                                //eerste click mag geen bom zijn
                                if(minesweeper.getTile(temp.getPositionX(), temp.getPositionY()).isExplosive() && firstTileRule && !minesweeper.getTile(temp.getPositionX(), temp.getPositionY()).isFlagged()) {
                                    minesweeper.getTile(temp.getPositionX(), temp.getPositionY()).setNotExplosive();
                                    int b = minesweeper.getAmountExplosive(temp.getPositionX(), temp.getPositionY());
                                    notifyOpened(temp.getPositionX(), temp.getPositionY(), b);
                                    boolean broke = false;
                                    for (int i = 0; i < row && !broke; ++i) {
                                        for (int j = 0; j < col; ++j) {
                                            if (!minesweeper.getTile(temp.getPositionX(), temp.getPositionY()).isExplosive()) {
                                                minesweeper.getTile(i, j).isExplosive();
                                                broke = true;
                                                break;
                                            }
                                        }
                                    }
                                }
                                //als de click een bom is stopt de game -> verloren
                                if(minesweeper.getTile(temp.getPositionX(), temp.getPositionY()).isExplosive() && !minesweeper.getTile(temp.getPositionX(), temp.getPositionY()).isFlagged()){
                                    notifyExploded(temp.getPositionX(), temp.getPositionY());
                                    notifyGameLost();
                                }
                                //als de click geen bom is komt er een getal met het aantal omliggende bommen
                                else if(!minesweeper.getTile(temp.getPositionX(), temp.getPositionY()).isFlagged()){
                                        int b = minesweeper.getAmountExplosive(temp.getPositionX(), temp.getPositionY());
                                        if(b == 0) {
                                            notifyOpened(temp.getPositionX(), temp.getPositionY(), b);
                                            //omliggende vakjes ook laten openen wanneer er geen bommen in de buurt liggen
                                            if(temp.getPositionX() == 0){
                                                if(temp.getPositionY() == 0){
                                                    b = minesweeper.getAmountExplosive(temp.getPositionX()+1, temp.getPositionY());
                                                    notifyOpened(temp.getPositionX()+1, temp.getPositionY(), b);
                                                    b = minesweeper.getAmountExplosive(temp.getPositionX(), temp.getPositionY()+1);
                                                    notifyOpened(temp.getPositionX(), temp.getPositionY()+1, b);
                                                    b = minesweeper.getAmountExplosive(temp.getPositionX()+1, temp.getPositionY()+1);
                                                    notifyOpened(temp.getPositionX()+1, temp.getPositionY()+1, b);
                                                }
                                            }
                                            if(temp.getPositionX() == 0){
                                                if (temp.getPositionY() != 0){
                                                    if(temp.getPositionY() != minesweeper.getHeight()-1){
                                                        b = minesweeper.getAmountExplosive(temp.getPositionX(), temp.getPositionY()-1);
                                                        notifyOpened(temp.getPositionX(), temp.getPositionY()-1, b);
                                                        b = minesweeper.getAmountExplosive(temp.getPositionX()+1, temp.getPositionY()-1);
                                                        notifyOpened(temp.getPositionX()+1, temp.getPositionY()-1, b);
                                                        b = minesweeper.getAmountExplosive(temp.getPositionX()+1, temp.getPositionY());
                                                        notifyOpened(temp.getPositionX()+1, temp.getPositionY(), b);
                                                        b = minesweeper.getAmountExplosive(temp.getPositionX(), temp.getPositionY()+1);
                                                        notifyOpened(temp.getPositionX(), temp.getPositionY()+1, b);
                                                        b = minesweeper.getAmountExplosive(temp.getPositionX()+1, temp.getPositionY()+1);
                                                        notifyOpened(temp.getPositionX()+1, temp.getPositionY()+1, b);
                                                    }
                                                }
                                            }
                                            if(temp.getPositionX() != 0){
                                                if(temp.getPositionY() == 0){
                                                    if(temp.getPositionX() != minesweeper.getWidth()-1){
                                                        b = minesweeper.getAmountExplosive(temp.getPositionX()-1, temp.getPositionY());
                                                        notifyOpened(temp.getPositionX()-1, temp.getPositionY(), b);
                                                        b = minesweeper.getAmountExplosive(temp.getPositionX()+1, temp.getPositionY());
                                                        notifyOpened(temp.getPositionX()+1, temp.getPositionY(), b);
                                                        b = minesweeper.getAmountExplosive(temp.getPositionX()-1, temp.getPositionY()+1);
                                                        notifyOpened(temp.getPositionX()-1, temp.getPositionY()+1, b);
                                                        b = minesweeper.getAmountExplosive(temp.getPositionX(), temp.getPositionY()+1);
                                                        notifyOpened(temp.getPositionX(), temp.getPositionY()+1, b);
                                                        b = minesweeper.getAmountExplosive(temp.getPositionX()+1, temp.getPositionY()+1);
                                                        notifyOpened(temp.getPositionX()+1, temp.getPositionY()+1, b);
                                                    }
                                                }
                                            }
                                            if(temp.getPositionX() != 0){
                                                if(temp.getPositionX() != minesweeper.getWidth()-1){
                                                    if(temp.getPositionY() != 0){
                                                        if(temp.getPositionY() != minesweeper.getHeight()-1){
                                                            b = minesweeper.getAmountExplosive(temp.getPositionX()-1, temp.getPositionY()-1);
                                                            notifyOpened(temp.getPositionX()-1, temp.getPositionY()-1, b);
                                                            b = minesweeper.getAmountExplosive(temp.getPositionX(), temp.getPositionY()-1);
                                                            notifyOpened(temp.getPositionX(), temp.getPositionY()-1, b);
                                                            b = minesweeper.getAmountExplosive(temp.getPositionX()+1, temp.getPositionY()-1);
                                                            notifyOpened(temp.getPositionX()+1, temp.getPositionY()-1, b);
                                                            b = minesweeper.getAmountExplosive(temp.getPositionX()-1, temp.getPositionY());
                                                            notifyOpened(temp.getPositionX()-1, temp.getPositionY(), b);
                                                            b = minesweeper.getAmountExplosive(temp.getPositionX()+1, temp.getPositionY());
                                                            notifyOpened(temp.getPositionX()+1, temp.getPositionY(), b);
                                                            b = minesweeper.getAmountExplosive(temp.getPositionX()-1, temp.getPositionY()+1);
                                                            notifyOpened(temp.getPositionX()-1, temp.getPositionY()+1, b);
                                                            b = minesweeper.getAmountExplosive(temp.getPositionX(), temp.getPositionY()+1);
                                                            notifyOpened(temp.getPositionX(), temp.getPositionY()+1, b);
                                                            b = minesweeper.getAmountExplosive(temp.getPositionX()+1, temp.getPositionY()+1);
                                                            notifyOpened(temp.getPositionX()+1, temp.getPositionY()+1, b);
                                                        }
                                                    }
                                                }
                                            }
                                            if(temp.getPositionX() == minesweeper.getWidth()-1){
                                                if(temp.getPositionY() == 0){
                                                    b = minesweeper.getAmountExplosive(temp.getPositionX()-1, temp.getPositionY());
                                                    notifyOpened(temp.getPositionX()-1, temp.getPositionY(), b);
                                                    b = minesweeper.getAmountExplosive(temp.getPositionX()-1, temp.getPositionY()+1);
                                                    notifyOpened(temp.getPositionX()-1, temp.getPositionY()+1, b);
                                                    b = minesweeper.getAmountExplosive(temp.getPositionX(), temp.getPositionY()+1);
                                                    notifyOpened(temp.getPositionX(), temp.getPositionY()+1, b);
                                                }
                                            }
                                            if(temp.getPositionX() == minesweeper.getWidth()-1){
                                                if(temp.getPositionY() != 0){
                                                    if(temp.getPositionY() != minesweeper.getHeight()-1){
                                                        b = minesweeper.getAmountExplosive(temp.getPositionX()-1, temp.getPositionY()-1);
                                                        notifyOpened(temp.getPositionX()-1, temp.getPositionY()-1, b);
                                                        b = minesweeper.getAmountExplosive(temp.getPositionX(), temp.getPositionY()-1);
                                                        notifyOpened(temp.getPositionX(), temp.getPositionY()-1, b);
                                                        b = minesweeper.getAmountExplosive(temp.getPositionX()-1, temp.getPositionY());
                                                        notifyOpened(temp.getPositionX()-1, temp.getPositionY(), b);
                                                        b = minesweeper.getAmountExplosive(temp.getPositionX()-1, temp.getPositionY()+1);
                                                        notifyOpened(temp.getPositionX()-1, temp.getPositionY()+1, b);
                                                        b = minesweeper.getAmountExplosive(temp.getPositionX(), temp.getPositionY()+1);
                                                        notifyOpened(temp.getPositionX(), temp.getPositionY()+1, b);
                                                    }
                                                }
                                            }
                                            if(temp.getPositionX() == minesweeper.getWidth()-1){
                                                if(temp.getPositionY() == minesweeper.getHeight()-1){
                                                    b = minesweeper.getAmountExplosive(temp.getPositionX()-1, temp.getPositionY()-1);
                                                    notifyOpened(temp.getPositionX()-1, temp.getPositionY()-1, b);
                                                    b = minesweeper.getAmountExplosive(temp.getPositionX(), temp.getPositionY()-1);
                                                    notifyOpened(temp.getPositionX(), temp.getPositionY()-1, b);
                                                    b = minesweeper.getAmountExplosive(temp.getPositionX()-1, temp.getPositionY());
                                                    notifyOpened(temp.getPositionX()-1, temp.getPositionY(), b);
                                                }
                                            }
                                            if(temp.getPositionX() != 0){
                                                if(temp.getPositionY() == minesweeper.getHeight()-1){
                                                    if(temp.getPositionX() != minesweeper.getWidth()-1){
                                                        b = minesweeper.getAmountExplosive(temp.getPositionX()-1, temp.getPositionY()-1);
                                                        notifyOpened(temp.getPositionX()-1, temp.getPositionY()-1, b);
                                                        b = minesweeper.getAmountExplosive(temp.getPositionX(), temp.getPositionY()-1);
                                                        notifyOpened(temp.getPositionX(), temp.getPositionY()-1, b);
                                                        b = minesweeper.getAmountExplosive(temp.getPositionX()+1, temp.getPositionY()-1);
                                                        notifyOpened(temp.getPositionX()+1, temp.getPositionY()-1, b);
                                                        b = minesweeper.getAmountExplosive(temp.getPositionX()-1, temp.getPositionY());
                                                        notifyOpened(temp.getPositionX()-1, temp.getPositionY(), b);
                                                        b = minesweeper.getAmountExplosive(temp.getPositionX()+1, temp.getPositionY());
                                                        notifyOpened(temp.getPositionX()+1, temp.getPositionY(), b);
                                                    }
                                                }
                                            }
                                            if(temp.getPositionX() == 0){
                                                if(temp.getPositionY() == minesweeper.getHeight()-1){
                                                    b = minesweeper.getAmountExplosive(temp.getPositionX(), temp.getPositionY()-1);
                                                    notifyOpened(temp.getPositionX(), temp.getPositionY()-1, b);
                                                    b = minesweeper.getAmountExplosive(temp.getPositionX()+1, temp.getPositionY()-1);
                                                    notifyOpened(temp.getPositionX()+1, temp.getPositionY()-1, b);
                                                    b = minesweeper.getAmountExplosive(temp.getPositionX()+1, temp.getPositionY());
                                                    notifyOpened(temp.getPositionX()+1, temp.getPositionY(), b);
                                                }
                                            }
                                        }
                                        else{
                                            notifyOpened(temp.getPositionX(), temp.getPositionY(), b);
                                        }
                                }
                        }
                        else if (arg0.getButton() == MouseEvent.BUTTON3) {
                            if (gameModel!=null)
                                gameModel.toggleFlag(temp.getPositionX(), temp.getPositionY());
                                //als de tile nog geen flag heeft en het maximum flags nog niet gehaald is -> flag de tile
                                if(!minesweeper.getTile(temp.getPositionX(), temp.getPositionY()).isFlagged() && flagAmount < minesweeper.getExplosionCount()) {
                                    notifyFlagged(temp.getPositionX(), temp.getPositionY());
                                    minesweeper.flag(temp.getPositionX(), temp.getPositionY());
                                    flagAmount++;
                                    notifyFlagCountChanged(flagAmount);

                                    //als alle flags op de bommen liggen stopt de game -> gewonnen
                                    if(minesweeper.getTile(temp.getPositionX(), temp.getPositionY()).isExplosive()){
                                        correctAmount++;
                                        if(correctAmount == minesweeper.getExplosionCount()){
                                            notifyGameWon();
                                        }
                                    }
                                }
                                //toggle
                                else if(minesweeper.getTile(temp.getPositionX(), temp.getPositionY()).isFlagged()){
                                    notifyUnflagged(temp.getPositionX(), temp.getPositionY());
                                    minesweeper.unflag(temp.getPositionX(), temp.getPositionY());
                                    flagAmount--;
                                    notifyFlagCountChanged(flagAmount);
                                }


                        } 
                    }
                });
                this.tiles[i][j] = temp;
                this.world.add(temp);
            }
        }
        this.world.setLayout(new GridLayout(row, col));
        this.world.setVisible(false);
        this.world.setVisible(true);
        this.world.repaint();
    }

    @Override
    public void notifyGameLost() {
        this.removeAllTileEvents();
        for (int i=0; i<this.tiles.length; ++i)
            for (int j=0; j<this.tiles[i].length; ++j) {
                int b = minesweeper.getAmountExplosive(i, j);
                this.tiles[j][i].notifyOpened(b);
                if(minesweeper.getTile(i,j).isExplosive()){
                    this.tiles[j][i].notifyExplode();
                }
            }
        System.out.println("You lost");
        timer = false;
        JOptionPane.showMessageDialog(null, "You lost!");
        //throw new UnsupportedOperationException();
    }
    @Override
    public void notifyGameWon() {
        this.removeAllTileEvents();
        for (int i=0; i<this.tiles.length; ++i) {
            for (int j=0; j<this.tiles[i].length; ++j) {
                if(!minesweeper.getTile(i,j).isExplosive()){
                    int b = minesweeper.getAmountExplosive(i, j);
                    this.tiles[j][i].notifyOpened(b);
                }
                if(minesweeper.getTile(i,j).isExplosive()) {
                    this.tiles[j][i].notifyFlagged();
                }
            }
        }
        System.out.println("You won!");
        JOptionPane.showMessageDialog(null, "You won!");
        timer = false;
        //throw new UnsupportedOperationException();
    }

    private void removeAllTileEvents() {
        for (int i=0; i<this.tiles.length; ++i)
            for (int j=0; j<this.tiles[i].length; ++j)
                this.tiles[i][j].removalAllMouseListeners();
    }

    @Override
    public void notifyFlagCountChanged(int newFlagCount) {
        this.flagCountView.setText(Integer.toString(newFlagCount));
    }

    @Override
    public void notifyBombLeftChanged(int newBombLeft) {

    }

    @Override
    public void notifyTimeElapsedChanged(Duration newTimeElapsed) {
        timerView.setText(
                    String.format("%d:%02d", newTimeElapsed.toMinutesPart(), newTimeElapsed.toSecondsPart()));  
        
    }

    @Override
    public void notifyOpened(int x, int y, int explosiveNeighbourCount) {
        this.tiles[y][x].notifyOpened(explosiveNeighbourCount);
    }

    @Override
    public void notifyFlagged(int x, int y) {
        this.tiles[y][x].notifyFlagged();
    }

    @Override
    public void notifyUnflagged(int x, int y) {
        this.tiles[y][x].notifyUnflagged();
    }

    @Override
    public void notifyExploded(int x, int y) {
        this.tiles[y][x].notifyExplode();
    }

    @Override
    public Minesweeper returnMinesweeper() {
        return minesweeper;
    }

    public void resetTimer() {
        secondspassed = 0;
        displayMinutes = 0;
        timer = true;


    }

}