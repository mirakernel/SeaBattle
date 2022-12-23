package seaBattle.game;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;


public class FieldVisualization extends JLabel {
	
	public ArrayList<Ship> ships;
	ArrayList<EnemyCell> enemyCells;

	public ArrayList<EnemyCell> getEnemyPoints() {
		return enemyCells;
	}
	public ArrayList<Cell> getMissesPoints() {
		return missesPoints;
	}
	public ArrayList<Cell> getBarrierPoints() {
		return barrierPoints;
	}

	ArrayList<Cell> missesPoints;
	ArrayList<Cell> barrierPoints;
	ArrayList<Cell> enemyHits;

	protected Image miss;
	protected Image enemyMiss;
	protected Image playerBattlefield;
	protected Image enemyBattlefield;
	protected Image enemyHit;
	protected Image enemyDeck;

	private int multiplier;

	public Cell currentCell = null;

   
   public FieldVisualization() {
		ships = new ArrayList<>();
		enemyCells = new ArrayList<>();
		missesPoints = new ArrayList<>();
		barrierPoints = new ArrayList<>();
		enemyHits = new ArrayList<>();
	   	multiplier = ASSET.gameMulti;
	   	multiplier *= ASSET.extension;

		initImg();
   }
	
	public FieldVisualization(ArrayList<Ship> ships) {
		this.ships = ships;
		enemyCells = new ArrayList<>();
		missesPoints = new ArrayList<>();
		barrierPoints = new ArrayList<>();
		enemyHits = new ArrayList<>();
		multiplier = 50;
		multiplier *= ASSET.extension;

		initImg();
	}

	public void initImg() {
		String way = "/seaBattle/game/assets/img";
		playerBattlefield = ASSET.field;
		miss = ASSET.miss;
		enemyHit = ASSET.shipHole;
		enemyBattlefield = ASSET.enemyField;
		enemyMiss = ASSET.enemyMiss;
		enemyDeck = ASSET.enemyShip;
	}
	
	public void makeEnemyShipBarriers(String shipName, int deckCount) {
			ArrayList<Cell> barriers = new ArrayList<>();
			for (EnemyCell enemyCell : enemyCells) {
				if(enemyCell.getName().equals(shipName)) {
					Point point = Point.parsePoint(enemyCell);
					point.makeBarrier();
					Collections.addAll(barriers, point.getBarrier());
				}
			}
			
			for(Cell b : barriers) {
			addEnemyShipsBarrier(b);
			}
		}

	public void addEnemyHit(Cell point) {
		enemyHits.add(point);
	}
	
	public void addEnemyPoint(EnemyCell enemyCell) {
		enemyCells.add(enemyCell);
	}
	
	public void addMiss(Cell miss) {
		missesPoints.add(miss);
	}
	
	public void addEnemyShipsBarrier(Cell b) {
		barrierPoints.add(b);
	}


	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawMyField(g);
		drawEnemyField(g);
		try {
			paintCurrentCell(g,currentCell);
		} catch (Exception exception) {
			System.out.println("Чето не так с клеткой");
		}
		setBackground(Color.gray);
	}




	protected void drawMyField(Graphics g) {
		g.drawImage(playerBattlefield, 0, 0, null);
		drawMyShips(g);
		drawEnemyHits(g);
		drawMisses(g);
	}

	protected void drawEnemyHits(Graphics g) {
		for (Cell cell : enemyHits) {
			g.drawImage(enemyHit,multiplier+multiplier* cell.x,multiplier+multiplier* cell.y,null);
		}
	}

	protected void drawMyShips(Graphics g) {
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

	protected void drawMisses(Graphics g) {
		for (Cell missesPoint : missesPoints) {
			g.drawImage(miss,multiplier+multiplier*missesPoint.x,multiplier+multiplier*missesPoint.y,null);
		}
	}

	protected void drawBarrierPoints(Graphics g) {
		for (Cell b : barrierPoints) {
			g.drawImage(enemyMiss,13*multiplier+multiplier*b.x,multiplier+multiplier*b.y,null);
		}
	}

	protected void drawEnemyField(Graphics g) {
		
		g.drawImage(enemyBattlefield, 12*multiplier, 0, null);
		
		drawBarrierPoints(g);
		
		for (EnemyCell enemyCell : enemyCells) {
			if (enemyCell.getIsShip())
				g.drawImage(enemyDeck,13*multiplier+multiplier* enemyCell.x,multiplier+multiplier* enemyCell.y,null);
			else
				g.drawImage(enemyMiss,13*multiplier+multiplier* enemyCell.x,multiplier+multiplier* enemyCell.y,null);
		}
	}

	protected void paintCurrentCell(Graphics g, Cell point) {
		g.setColor(Color.red);
		for (Cell cell :
				enemyCells) {
			if (cell.equals(point)) {
				g.setColor(Color.BLACK);
				break;
			}
		}
		for (Cell cell :
				barrierPoints) {
			if (cell.equals(point)) {
				g.setColor(Color.BLACK);
				break;
			}
		}
		g.drawRect(13*multiplier+multiplier*point.x,multiplier+multiplier*point.y,multiplier,multiplier);
	}
}

