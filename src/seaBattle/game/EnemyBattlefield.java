package seaBattle.game;

import java.awt.*;
import java.util.ArrayList;

public class EnemyBattlefield extends Battlefield {

    ArrayList<EnemyCell> enemyCells = new ArrayList<>();
    ArrayList<Cell> enemyBarrierPoints = new ArrayList<>();

    protected Image enemyMiss;
    protected Image enemyDeck;

    public EnemyBattlefield() {
        setBattlefieldImage(ASSET.enemyBattlefield);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBattlefield(g);
        drawEnemyCells(g);
        drawBarrierPoints(g);
        paintCurrentCell(g,currentCell, Color.GREEN);

    }

    protected void drawEnemyCells(Graphics g) {
        for (EnemyCell enemyCell : enemyCells) {
            if (enemyCell.getIsShip())
                g.drawImage(enemyDeck,multiplier+multiplier* enemyCell.x,multiplier+multiplier* enemyCell.y,null);
            else
                g.drawImage(enemyMiss,multiplier+multiplier* enemyCell.x,multiplier+multiplier* enemyCell.y,null);
        }
    }

    protected void drawBarrierPoints(Graphics g) {
        for (Cell b : enemyBarrierPoints) {
            g.drawImage(enemyMiss,multiplier+multiplier*b.x,multiplier+multiplier*b.y,null);
        }
    }

    public ArrayList<EnemyCell> getEnemyCells() {
        return enemyCells;
    }

    public void addEnemyHit(Cell enemyCell) {
    }

    public void addMiss(Cell enemyCell) {
    }

    public void makeEnemyShipBarriers(String name, int deck) {
    }

    public void addEnemyCell(EnemyCell enemyCell) {
    }
}
