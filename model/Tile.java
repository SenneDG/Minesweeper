package model;

public class Tile extends AbstractTile{
    private boolean flagged;
    private boolean isOpened;
    private boolean explosive;

    public Tile() {
        isOpened = false;
        flagged = false;
        explosive = false;
    }

    @Override
    public boolean open() {
        isOpened = true;
        return flagged;
    }

    @Override
    public void flag() {
        flagged = true;
    }

    @Override
    public void unflag() {
        flagged = false;
    }

    @Override
    public boolean isFlagged() {
        return flagged;
    }

    @Override
    public boolean isExplosive() {
        return explosive;
    }

    @Override
    public boolean isOpened() {
        return isOpened;
    }
}
