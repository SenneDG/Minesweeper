package model;
import model.AbstractTile;

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

    }

    @Override
    public void startNewGame(int row, int col, int explosionCount)
    {
        field = new AbstractTile[row][col];
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
}