package seaBattle.game;

import java.awt.*;
import java.util.LinkedHashSet;
import java.util.Set;

public class ShipSelectionBattlefield extends PlayerBattlefield {
    //динамический список подсвечиевамыех квадратиков
    private Set<Point> backlights;
    private boolean isEraseMod = false;

    public boolean isEraseMod() {
        return isEraseMod;
    }

    public void setEraseMod(boolean eraseMod) {
        isEraseMod = eraseMod;
    }

    public ShipSelectionBattlefield() {
        setBattlefieldImage(ASSET.field_s_edit);
        backlights = new LinkedHashSet<>();
    }

    public Set<Point> getBacklights() {
        return backlights;
    }

    public void addBacklight(Point point) {
        backlights.add(point);
    }

    public void clearBacklights() {
        backlights.clear();
    }

    public void changeIcon(int i) {
        ASSET.switchImageFromToolbar(i,"50");
    }

    @Override
    protected void paintComponent(Graphics g) {
        drawBattlefield(g);
        drawShips(g);
        drawBacklight(g);
        paintCurrentCell(g,currentCell,Color.GREEN);
    }

    @Override
    protected void paintCurrentCell(Graphics g, Cell point, Color cellColor) {
        try {
            if (isEraseMod) {
                g.drawImage(ASSET.eraser50, multiplier + multiplier * point.x, multiplier + multiplier * point.y, null);
            } else {
                g.setColor(cellColor);
                for (Cell cell : getBarrierPoints()) {
                    if (cell.equals(point)) {
                        g.setColor(Color.red);
                        break;
                    }
                }
                try {
                    g.drawRect(multiplier + multiplier * point.x, multiplier + multiplier * point.y, multiplier, multiplier);
                } catch (Exception exception) {
                    System.out.println("Такой клетки нету");
                }
            }
        }
        catch (NullPointerException nullPointerException) {
            System.out.println("Null *свечка*");
        }
    }

    private void drawBacklight(Graphics g) {
        g.setColor(Color.red);
        for (seaBattle.game.Point point : backlights) {
            g.fillRect(multiplier + point.x * multiplier, multiplier + point.y * multiplier, multiplier, multiplier);
        }
    }

}
