package seaBattle.game;

public class Point extends Cell {

    private boolean isBroken;

    private Cell[] barrier;

    public Point(int x, int y) {
        super(x, y);
        isBroken = false;
    }

    public void broke() {
        isBroken = true;
    }

    public boolean isBroken() {
        return isBroken;
    }

    public Cell[] getBarrier() {
        return barrier;
    }

    public void makeBarrier() {
        barrier = new Cell[9];
        barrier[0] = new Cell(x - 1, y - 1);
        barrier[1] = new Cell(x - 1, y);
        barrier[2] = new Cell(x, y - 1);
        barrier[3] = new Cell(x, y);
        barrier[4] = new Cell(x + 1, y);
        barrier[5] = new Cell(x, y + 1);
        barrier[6] = new Cell(x + 1, y + 1);
        barrier[7] = new Cell(x - 1, y + 1);
        barrier[8] = new Cell(x + 1, y - 1);
    }

    public static Point parsePoint(EnemyCell enemyCell) {
        return new Point(enemyCell.x, enemyCell.y);
    }

}
