package model;
import model.AbstractTile;
import java.util.Random;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Minesweeper extends AbstractMineSweeper {

    private AbstractTile[][] field;



    @Override
    public int getWidth()
    {
        return field.length;
    }

    @Override
    public int getHeight()
    {
        return field[0].length;
    }

    @Override
    public void startNewGame(Difficulty level)
    {
        if(level == Difficulty.EASY){
            int row = 8;
            int col = 8;
            int explosionCount = 10;
            startNewGame(row, col, explosionCount);
        }
        else if(level == Difficulty.MEDIUM){
            int row = 16;
            int col = 16;
            int explosionCount = 40;
            startNewGame(row, col, explosionCount);
        }
        else if(level == Difficulty.HARD){
            int row = 16;
            int col = 30;
            int explosionCount = 99;
            startNewGame(row, col, explosionCount);
        }
    }

    @Override
    public void startNewGame(int row, int col, int explosionCount)
    {
        field = new AbstractTile[row][col];
        for(int x = 0; x < row; x++)
        {
            for(int y = 0; y < col; y++)
            {
                field[x][y] = new Tile();
            }
        }
        int teller = 0;
        int rand_x = 0;
        int rand_y = 0;
        Random random = new Random();
        while(teller < explosionCount)
        {
            for(int x = 0; x < row; x++)
            {
                rand_x = random.nextInt(row);
                for(int y = 0; y < col; y++)
                {
                    rand_y = random.nextInt(col);
                }
            }
            if(field[rand_x][rand_y].isFlagged() != true)
            {
                field[rand_x][rand_y].flag();
                teller++;
            }
        }

    }

    @Override
    public void toggleFlag(int x, int y)
    {
        if(field[x][y].isFlagged())
        {
            field[x][y].unflag();
        }
        else
        {
            field[x][y].flag();
        }
    }

    @Override
    public AbstractTile getTile(int x, int y)
    {
        return field[x][y];
    }

    @Override
    public void setWorld(AbstractTile[][] world)
    {
        field = world;
    }

    @Override
    public void open(int x, int y) {
        field[x][y].open();
    }

    @Override
    public void flag(int x, int y) {
        field[x][y].flag();
    }

    @Override
    public void unflag(int x, int y) {
        field[x][y].unflag();
    }

    @Override
    public void deactivateFirstTileRule() {

    }

    @Override
    public AbstractTile generateEmptyTile() {
        Tile tile = new Tile();
        return tile;
    }

    @Override
    public AbstractTile generateExplosiveTile() {
        return null;
    }


    public int returnAmountFlagged()
    {
        int teller = 0;
        for(int x = 0; x < getWidth(); x++)
        {
            for(int y = 0; y < getHeight(); y++)
            {
                if(field[x][y].isFlagged())
                {
                    teller++;
                }
            }
        }
        return teller;
    }
}