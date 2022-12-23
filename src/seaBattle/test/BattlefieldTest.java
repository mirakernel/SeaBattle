package seaBattle.test;

import seaBattle.game.Point;
import seaBattle.game.Ship;
import seaBattle.game.ShipSelectionBattlefield;

import javax.swing.*;

public class BattlefieldTest {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(0,0,1920,1080);
        //frame.setResizable(false);
        ShipSelectionBattlefield battlefield = new ShipSelectionBattlefield();
        battlefield.setBounds(21,21,600,600);
        frame.add(battlefield);
        Point[] points = new Point[1];
        points[0] = new Point(2,2);
        battlefield.addShip(new Ship(1,"one",points, new Point(2,2),"hor"));
        battlefield.repaint();
    }
}
