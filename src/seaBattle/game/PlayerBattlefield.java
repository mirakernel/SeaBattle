package seaBattle.game;

import java.awt.*;
import java.util.ArrayList;

public class PlayerBattlefield extends Battlefield {
    private ArrayList<Ship> ships;

    private ArrayList<Cell> missesPoints;
    private ArrayList<Cell> barrierPoints;
    private ArrayList<Cell> enemyHits;

    private Image miss;
    private Image enemyHit;

    public ArrayList<Cell> getMissesPoints() {
        return missesPoints;
    }

    public ArrayList<Cell> getBarrierPoints() {
        return barrierPoints;
    }

    public void setShips(ArrayList<Ship> ships) {
        this.ships = ships;
        updateBarriersPoints();
    }

    public ArrayList<Ship> getShips() {
        return ships;
    }

    public void addShip(Ship ship) {
        ships.add(ship);
        updateBarriersPoints();
    }

    public void removeShip(Ship ship) {
        ships.remove(ship);
        updateBarriersPoints();
    }

    public void clearShips() {
        ships.clear();
        updateBarriersPoints();
    }

    public void addEnemyHit(Cell point) {
        enemyHits.add(point);
    }

    public void addMiss(Cell miss) {
        missesPoints.add(miss);
    }

    public PlayerBattlefield() {
        ships = new ArrayList<>();
        missesPoints = new ArrayList<>();
        barrierPoints = new ArrayList<>();
        enemyHits = new ArrayList<>();
        initImg();
    }

    public void initImg() {
        battlefield = ASSET.playerBattlefield;
        miss = ASSET.miss;
        enemyHit = ASSET.shipHole;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBattlefield(g);
        drawShips(g);
        drawEnemyHits(g);
        drawEnemyMisses(g);
        drawShipsBarriers(g);
        try {
            paintCurrentCell(g, currentCell, Color.BLACK);
        }
        catch (Exception exception) {
            System.out.println("произошла ошибка в отрисовки");
        }
    }

    protected void drawShips(Graphics g) {
        for(Ship ship:ships) {
            ship.setupImage();
            g.drawImage(ship.image,multiplier+multiplier*ship.startPoint.x,multiplier+multiplier*ship.startPoint.y, null);
        }
    }

    private void drawEnemyHits(Graphics g) {
        for (Cell cell : enemyHits) {
            g.drawImage(enemyHit,multiplier+multiplier* cell.x,multiplier+multiplier* cell.y,null);
        }
    }

    private void drawShipsBarriers(Graphics g) {
        for(Ship ship:ships) {
            g.drawImage(ship.image,multiplier+multiplier*ship.startPoint.x,multiplier+multiplier*ship.startPoint.y, null);
            ship.setupImage();
            if (ship.hp==0) {
                for (Cell missesPoint : ship.barriers) {
                    g.drawImage(miss,multiplier+multiplier*missesPoint.x,multiplier+multiplier*missesPoint.y,null);
                }
            }
        }
    }

    private void drawEnemyMisses(Graphics g) {
        for (Cell missesPoint : missesPoints) {
            g.drawImage(miss,multiplier+multiplier*missesPoint.x,multiplier+multiplier*missesPoint.y,null);
        }
    }

    public void updateBarriersPoints() {
        barrierPoints.clear();
        for (Ship ship: ships) {
            barrierPoints.addAll(ship.barriers);
        }
    }
}
