package seaBattle.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public abstract class Battlefield extends JLabel {
    protected Image battlefield = ASSET.playerBattlefield;
    public Cell currentCell = null;

    protected int multiplier = ASSET.gameMulti;

    public Battlefield() {
        addCellOutline();
    }

    public Cell getCurrentCell() {
        return currentCell;
    }

    public void setBattlefieldImage(Image battlefield) {
        this.battlefield = battlefield;
    }

    protected void drawBattlefield(Graphics g) {
        g.drawImage(battlefield, 0, 0, null);
    }

    protected void paintCurrentCell(Graphics g, Cell point, Color cellColor) {
        try {
            g.setColor(cellColor);
            g.drawRect(multiplier + multiplier * point.x, multiplier + multiplier * point.y, multiplier, multiplier);
        }
        catch (NullPointerException nullPointerException) {
            System.out.println("Ну и хрен с этой клеткой");
        }
    }

    protected void addCellOutline() {
        Battlefield thisBattlefield = this;
        thisBattlefield.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
            }
            @Override
            public void mouseMoved(MouseEvent me) { //
                int x = (int)(((double)me.getX()-(50.0* ASSET.extension))/(50.0*ASSET.extension));
                int y = (int)(((double)me.getY()-(50.0*ASSET.extension))/(50.0*ASSET.extension));
                if (!(x<0||x>9||y<0||y>9)) {
                    thisBattlefield.currentCell = new Cell(x,y);
                }
                else {thisBattlefield.currentCell = null;
                }
                thisBattlefield.repaint();
            }
        });
    }


}
