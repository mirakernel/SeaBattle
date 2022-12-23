package seaBattle.game.generators;

import seaBattle.game.Cell;
import seaBattle.game.Point;
import seaBattle.game.Ship;

import java.util.ArrayList;
import java.util.HashSet;

public class RandomShipGenerator implements ShipsGenerator {
    private HashSet<Cell> barriers;

    @Override
    public ArrayList<Ship> generate() {
        barriers = new HashSet<>();
        ArrayList<Ship> ships = new ArrayList<>();
        ships.add(generateShip(4,"������� �����(���������������)"));
        ships.add(generateShip(3,"������������ 2"));
        ships.add(generateShip(3,"������������ 2"));
        ships.add(generateShip(2,"������������ 1"));
        ships.add(generateShip(2,"������������ 2"));
        ships.add(generateShip(2,"������������ 3"));
        ships.add(generateShip(1,"������������ 1"));
        ships.add(generateShip(1,"������������ 2"));
        ships.add(generateShip(1,"������������ 3"));
        ships.add(generateShip(1,"������������ 4"));
        return ships;
    }

    //�������� ��������� �������
    /*
    ���������� �������� ������������ ��� �������������� �������
    ���������� ��������� ��������� �����
    ����������� �� �������� �� ��� ������ ��������� � �������� �����
    ���� ��� ���, �� ��������� ����� ������� � ��������� ������ �������
    � ����� ������ ����������� ������� �������
    ������� ����������� � ������ ��������
     */

    private Ship generateShip(int deck, String name) {
        Point startPoint;
        boolean choiceDirections = (int) (0+Math.random()*10)>5;
        if (choiceDirections) { // horizontal
            startPoint = createHorizontalStartPoint(deck);
            if (startPoint!=null) {
                return generateHorizontalShip(deck,startPoint);
            }
            else return generateShip(deck,name);
        }
        else { //vertical
            startPoint = createVerticalStartPoint(deck);
            if (startPoint!=null) {
                return generateVerticalShip(deck,startPoint);
            }
            else return generateShip(deck,name);
        }
    }

    private Ship generateHorizontalShip(int deck, Point startPoint) {
        String position = "hor";
        Point[] points = createHorizontalPoints(startPoint, deck);

        Ship resultShip = new Ship(deck,position+" "+deck+"��������� "+startPoint, points, startPoint, position);
        barriers.addAll(resultShip.barriers);
        return resultShip;
    }

    private Ship generateVerticalShip(int deck, Point startPoint) {
        String position = "ver";
        Point[] points = createVerticalPoints(startPoint, deck);

        Ship resultShip = new Ship(deck,position+" "+deck+"��������� "+startPoint, points, startPoint, position);
        barriers.addAll(resultShip.barriers);
        return resultShip;
    }

    private boolean isPointRight(Point startPoint) { //Right-����� - ����������.
        if(startPoint.x<0||startPoint.x>9||startPoint.y<0||startPoint.y>9) {
            return false;
        }
        try {
            for (Cell point : barriers) {
                if (point.equals(startPoint)) {
                    return false;
                }
            }
            return true;
        }
        catch (NullPointerException nullPointerException) {
            return true;
        }
    }

    private Point createVerticalStartPoint(int hp) {

        Point startPoint;

        for(int i =0; i<64; i++) {
            //��������� ��������� ����������������� �����
            startPoint = new Point((int)(0+Math.random()*9),(int)(0+Math.random()*9));
            //�����������: ���� ��� ������� �� ������ ��� ������ ������� �� ���� �����������
            boolean isContinue = true;
            int lastY = startPoint.y;

            for(int j =0; j<hp; j++) {
                startPoint.y = lastY+j;
                isContinue = isPointRight(startPoint);
                if (!isContinue) {
                    return null;
                }
                startPoint.y = lastY;
            }
            if(isContinue) {return startPoint;}
        }
        return null;
    }

    private Point[] createVerticalPoints(Point startPoint, int hp) {
        //���� ���������� ��������� ����������� ����� ��������
        //���� ��� x � y, �� ����� �������� �� 'hp' ������ � ���� �� ������
        Point[] points = new Point[hp];
        for (int i=0;i<hp;i++) {
            Point newPoint = new Point(startPoint.x,startPoint.y+i);
            points[i] = newPoint;
        }
        return points;
    }

    private Point createHorizontalStartPoint(int hp) {
        for(int i =0; i<64; i++) {
            //��������� ��������� ����������������� �����
            Point startPoint = new Point((int) (0 + Math.random() * 9), (int) (0 + Math.random() * 9));
            //�����������: ���� ��� ������� �� ������ ��� ������ ������� �� ���� �����������
            boolean isContinue = true;
            int lastX = startPoint.x;

            for(int j =0; j<hp; j++) {
                startPoint.x = lastX+j;
                isContinue = isPointRight(startPoint);
                if (!isContinue) {
                    System.out.println("�� ����������");
                    return null;
                }
                startPoint.x = lastX;
            }
            if(isContinue) {return startPoint;}
        }
        return null;
    }

    private Point[] createHorizontalPoints(Point startPoint, int hp) {
        //���� ���������� ��������� ����������� ����� ��������; ���� ��� x, � ����� �������� �� 'hp' ������ ������
        Point[] points = new Point[hp];
        for (int i=0;i<hp;i++) {
            Point newPoint = new Point(startPoint.x+i,startPoint.y);
            points[i] = newPoint;
        }
        return points;
    }
}
