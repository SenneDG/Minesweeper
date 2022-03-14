package model;
import model.AbstractTile;
import java.util.Random;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Minesweeper extends AbstractMineSweeper {

    private AbstractTile[][] field;
    private boolean firstClick;
    private boolean firstTileRule;
    public int explosionCount;
    public Minesweeper()
    {
        firstTileRule = true;
    }


    public int getExplosionCount(){
        return explosionCount;
    }

    @Override
    public int getWidth()
    {
        return field[0].length;
    }

    @Override
    public int getHeight()
    {
        return field.length;
    }

    @Override
    public void startNewGame(Difficulty level)
    {
        if(level == Difficulty.EASY){
            int row = 8;
            int col = 8;
            int explosionCount = 10;
            this.explosionCount = explosionCount;
            startNewGame(row, col, explosionCount);
        }
        else if(level == Difficulty.MEDIUM){
            int row = 16;
            int col = 16;
            int explosionCount = 40;
            this.explosionCount = explosionCount;
            startNewGame(row, col, explosionCount);
        }
        else if(level == Difficulty.HARD){
            int row = 16;
            int col = 30;
            int explosionCount = 99;
            this.explosionCount = explosionCount;
            startNewGame(row, col, explosionCount);
        }
    }

    @Override
    public void startNewGame(int row, int col, int explosionCount)
    {
        firstClick = true;
        field = new AbstractTile[row][col];
        for(int x = 0; x < row; x++)
        {
            for(int y = 0; y < col; y++)
            {
                field[x][y] = generateEmptyTile();
            }
        }
        int teller = 0;
        int rand_x;
        int rand_y;
        Random random = new Random();
        while(teller < explosionCount)
        {
            rand_x = random.nextInt(row);
            rand_y = random.nextInt(col);
            if(field[rand_x][rand_y].isExplosive() != true)
            {
                field[rand_x][rand_y] = generateExplosiveTile();
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
    public AbstractTile[][] getField() {
        return field;
    }

    @Override
    public AbstractTile getTile(int x, int y)
    {
        if(x < getHeight() && x>=0 && y < getWidth() && y>=0)
        {
            return field[x][y];
        }
        return generateEmptyTile();
    }

    @Override
    public void setWorld(AbstractTile[][] world)
    {
        field = world;
    }

    @Override
    public void open(int x, int y)
    {
        if (x < getHeight() && x >= 0 && y < getWidth() && y >= 0)
        {
            if (firstClick)
            {
                if (field[x][y].isExplosive())
                {
                    firstClick = false;
                    Random random = new Random();
                    while (field[x][y].isExplosive())
                    {
                        x = random.nextInt(getWidth());
                        y = random.nextInt(getHeight());
                    }

                }
            }
            field[x][y].open();
        }
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
        firstTileRule = false;
    }

    @Override
    public AbstractTile generateEmptyTile() {
        Tile tile = new Tile();
        return tile;
    }

    @Override
    public AbstractTile generateExplosiveTile() {
        Tile tile = new Tile();
        tile.setExplosive();
        return tile;
    }

    public int getAmountExplosive(int x, int y) {
        int teller = 0;
        if (x == 0 && y == 0) {
            if (field[x + 1][y].isExplosive()) {
                teller++;
            }
            if (field[x][y + 1].isExplosive()) {
                teller++;
            }
            if (field[x + 1][y + 1].isExplosive()) {
                teller++;
            }
        }
        if (x == 0 && y != 0 && y != getHeight()-1) {
            if (field[x][y - 1].isExplosive()) {
                teller++;
            }
            if (field[x + 1][y - 1].isExplosive()) {
                teller++;
            }
            if (field[x + 1][y].isExplosive()) {
                teller++;
            }
            if (field[x][y + 1].isExplosive()) {
                teller++;
            }
            if (field[x + 1][y + 1].isExplosive()) {
                teller++;
            }
        }
        if (x != 0 && y == 0 && x != getWidth()-1) {
            if (field[x - 1][y].isExplosive()) {
                teller++;
            }
            if (field[x - 1][y + 1].isExplosive()) {
                teller++;
            }
            if (field[x][y + 1].isExplosive()) {
                teller++;
            }
            if (field[x + 1][y + 1].isExplosive()) {
                teller++;
            }
            if (field[x + 1][y].isExplosive()) {
                teller++;
            }
        }
        if (x != 0 && y != 0 && x != getWidth()-1 && y != getHeight()-1) {
            if (field[x - 1][y - 1].isExplosive()) {
                teller++;
            }
            if (field[x][y - 1].isExplosive()) {
                teller++;
            }
            if (field[x + 1][y - 1].isExplosive()) {
                teller++;
            }
            if (field[x + 1][y].isExplosive()) {
                teller++;
            }
            if (field[x - 1][y].isExplosive()) {
                teller++;
            }
            if (field[x - 1][y + 1].isExplosive()) {
                teller++;
            }
            if (field[x][y + 1].isExplosive()) {
                teller++;
            }
            if (field[x + 1][y + 1].isExplosive()) {
                teller++;
            }
        }
        if(x==getWidth()-1 && y==0){
            if (field[x - 1][y].isExplosive()) {
                teller++;
            }
            if (field[x-1][y + 1].isExplosive()) {
                teller++;
            }
            if (field[x][y + 1].isExplosive()) {
                teller++;
            }
        }
        if(x==getWidth()-1 && y!=0 && y!=getHeight()-1){
            if (field[x][y-1].isExplosive()) {
                teller++;
            }
            if (field[x - 1][y - 1].isExplosive()) {
                teller++;
            }
            if (field[x-1][y].isExplosive()) {
                teller++;
            }
            if (field[x - 1][y + 1].isExplosive()) {
                teller++;
            }
            if (field[x][y+1].isExplosive()) {
                teller++;
            }
        }
        if(x==getWidth()-1 && y==getHeight()-1){
            if (field[x][y -1].isExplosive()) {
                teller++;
            }
            if (field[x-1][y - 1].isExplosive()) {
                teller++;
            }
            if (field[x-1][y].isExplosive()) {
                teller++;
            }
        }
        if(x!=0 && y==getHeight()-1 && x!=getWidth()-1){
            if (field[x-1][y].isExplosive()) {
                teller++;
            }
            if (field[x - 1][y - 1].isExplosive()) {
                teller++;
            }
            if (field[x][y-1].isExplosive()) {
                teller++;
            }
            if (field[x + 1][y - 1].isExplosive()) {
                teller++;
            }
            if (field[x+1][y].isExplosive()) {
                teller++;
            }
        }
        return teller;
    }
}






//        if(field[x][y].isExplosive() != true)
//        {
//            if (x > 0)
//            {
//                if(field[x-1][y].isExplosive())
//                {
//                    teller++;
//                }
//            }
//            if(x<getHeight() - 1)
//            {
//                if(field[x-1][y].isExplosive())
//                {
//                    teller++;
//                }
//            }
//            if(y>0)
//            {
//                if(field[x][y+1].isExplosive())
//                {
//                    teller++;
//                }
//            }
//            if(y<getWidth() - 1)
//            {
//                if(field[x][y-1].isExplosive())
//                {
//                    teller++;
//                }
//            }
//        }
//        return teller;
//    }
//}