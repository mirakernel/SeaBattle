package seaBattle.game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;

public class Ship {
public int hp;
public int deck; //Количество палуб корабля
public Point startPoint;
public String name;
public String position;

public HashSet<Cell> barriers;
public Point[] points;
public Image image;


	public void setupImage() {
		try {
			image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/seaBattle/game/assets/img/ships/d" + deck + "/" + position + "/" + "k" + hp + ".png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		int width = ASSET.gameMulti;
		int height = ASSET.gameMulti;
		if (position.equals("ver")) {
			height*=deck;
		}
		else {
			width*=deck;
		}
		width *= ASSET.extension;
		height *= ASSET.extension;
		image = image.getScaledInstance(width, height, Image.SCALE_DEFAULT);
	}
	

	public Ship(int deck, String name, Point[] points, Point startPoint, String position) {
		barriers = new HashSet<>();
		this.name = name;
		this.deck = deck;
		this.startPoint = startPoint;
		this.position = position;
		hp = deck;
		this.points = points;
		if (deck==1) {
			this.position = "hor";
		}
		setupImage();
		setupBarriers();
		System.out.println(name);
	}

	public Cell getDifferenceDeStartPoint(Cell sp) { //отспуп от startPoint
		return new Cell(sp.x-startPoint.x,sp.y-startPoint.y);
	}
	
	public String getDamage(String input) {
		for(int i=0;i<deck;i++)	{
			if(input.equals(points[i].toString())&&!(points[i].isBroken()))
			{
				points[i].broke();
				hp--;
				if(hp==0) {
					return "Потопил!";
				}
				else {
					return "Попал!";
				}
			}
		}
		return "Промах";
	}

	private void setupBarriers() {
		for(Point point : points) {
			point.makeBarrier();
			barriers.addAll(Arrays.asList(point.getBarrier()));
		}
		System.out.println("Барьеры добавлены к кораблю "+name);
	}
}