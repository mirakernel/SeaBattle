package seaBattle.game.scenes;

import seaBattle.game.ASSET;
import seaBattle.game.Cell;
import seaBattle.game.Ship;
import seaBattle.game.ShipSelectionBattlefield;
import seaBattle.game.generators.RandomShipGenerator;
import seaBattle.game.generators.ShipsGenerator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Arrays;

/*
    открываетс€ окно с инструкцией постоновки кораблей, корабли и поле
    »грок может поставить (пока) только стоко кораблей скоко предусмотрено игрой
    „тобы разместить корабль:
    1) ¬ыбрать корабль
    2) –азместить его на поле
    3) Ќажать старт

    »зменение изображени€ выбранного корабл€
    »зменить
    ѕод мышкой если включена резинка - по€лв€етс€ полупрозрачна€ резинка
    –андом все сбрасывает
 */

public class ShipSelectionScene {
    private JFrame frame;
    private JButton start;
    private JLabel toolbar;
    private ShipSelectionBattlefield battlefield;

    ShipsGenerator generator;

    SelectMouseListener ml;

    public ShipSelectionScene() {
        generator = new RandomShipGenerator();
    }

    public void close() {
        frame.dispose();
        BattleScene battleScene = new BattleScene(battlefield.getShips());
        battleScene.start();
    }

    public void start() {
        initFrame();

        initBattlefield();
        initToolbar();
        initStartBtn();

        initComponentStructure();
    }

    private void initStartBtn() {
        start = ASSET.start;
        start.addActionListener(e -> close());
    }

    private void initBattlefield() {
        battlefield = new ShipSelectionBattlefield();
        battlefield.setBounds(21,21,600,600);
        ml = new SelectMouseListener();
        battlefield.addMouseListener(ml);
    }

    private void initFrame() {
        frame = new JFrame();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension dimension = kit.getScreenSize();
        frame.setBounds((dimension.width / 2 - 150), (dimension.height / 2 - 150), (int) (854*ASSET.extension), (int) (860*ASSET.extension));
        frame.setResizable(false);
    }

    private void initComponentStructure() {
        frame.add(ASSET.selector_background);
        ASSET.selector_background.add(ASSET.under2Selector);
        ASSET.under2Selector.add(ASSET.underSelector);
        ASSET.underSelector.add(ASSET.underField);
        ASSET.underField.add(battlefield);
        ASSET.underSelector.add(start);
        ASSET.underSelector.add(toolbar);
        frame.repaint();
        battlefield.repaint();
        battlefield.revalidate();
    }

    private void initToolbar() {
        ASSET.initToolbarImages();

        toolbar = ASSET.underExShipBox;
        JLabel lEraser = ASSET.eraser;
        lEraser.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent me) {
                ASSET.fifAllExampleShips();
                ml.maxDeck = 0;

                battlefield.setEraseMod(!battlefield.isEraseMod());
                ASSET.switchEraserImage();
            }
            @Override
            public void mouseExited(MouseEvent me) {}
            @Override
            public void mouseEntered(MouseEvent me) {}
            @Override
            public void mousePressed(MouseEvent me) {}
            @Override
            public void mouseReleased(MouseEvent me) {}
        });
        JButton randomBtn = ASSET.randomBtn;
        randomBtn.addActionListener(e -> {
            battlefield.clearShips();
            battlefield.setShips(generator.generate());
            scanShips();
            ml.setMaxDeck(0);
            ASSET.fifAllExampleShips();
            ASSET.eraser50();
            battlefield.repaint();
        });

        for (int i = 0; i < ASSET.exampleShips.length; i++) {
            ASSET.exampleShips[i].addMouseListener(new ToolbarMouseListener(i+1, ASSET.exampleShips[i]));
        }
        System.out.println(Arrays.toString(ASSET.toolsLight));
        for (JComponent component:
             ASSET.toolsLight) {
            toolbar.add(component);
        }
    }

    private boolean isPointRight(Cell sp) {
        for (Cell barrier : battlefield.getBarrierPoints()) {
            if (barrier.equals(sp)) {
                return false;
            }
        }
        return true;
    }

    private void scanShips() {
        int one = 0;
        int two = 0;
        int tree = 0;
        int four = 0;
        for (int i = 0; i < battlefield.getShips().size(); i++) {
            switch (battlefield.getShips().get(i).deck) {
                case 1: {
                    one++;
                    System.out.println("скоко у нас однопалубников "+one);
                    if (one==4) {
                        battlefield.changeIcon(1);
                        ShipSelectionScene.ToolbarMouseListener listener =  (ShipSelectionScene.ToolbarMouseListener) ASSET.exampleShips[0].getMouseListeners()[0];
                        listener.changed = false;
                        ASSET.setRedLight(1);
                    } else
                        ASSET.setGreenLight(1);
                    if (one > 4) {
                        battlefield.removeShip(battlefield.getShips().get(i));
                        i--;
                    }
                    ASSET.toolsLight[0].repaint();
                    break;
                }
                case 2: {
                    two++;
                    if (two==3) {
                        battlefield.changeIcon(2);
                        ShipSelectionScene.ToolbarMouseListener listener =  (ShipSelectionScene.ToolbarMouseListener) ASSET.exampleShips[1].getMouseListeners()[0];
                        listener.changed = false;
                        ASSET.setRedLight(2);
                    } else
                        ASSET.setGreenLight(2);
                    if (two > 3) {
                        battlefield.changeIcon(2);
                        battlefield.removeShip(battlefield.getShips().get(i));
                        i--;
                    }
                    ASSET.toolsLight[1].repaint();
                    break;
                }
                case 3: {
                    tree++;
                    if (tree==2) {
                        battlefield.changeIcon(3);
                        ShipSelectionScene.ToolbarMouseListener listener =  (ShipSelectionScene.ToolbarMouseListener) ASSET.exampleShips[2].getMouseListeners()[0];
                        listener.changed = false;
                        ASSET.setRedLight(3);
                    } else
                        ASSET.setGreenLight(3);
                    if (tree > 2) {
                        battlefield.changeIcon(3);
                        battlefield.removeShip(battlefield.getShips().get(i));
                        i--;
                    }
                    ASSET.toolsLight[2].repaint();
                    break;
                }
                case 4: {
                    four++;
                    if (four==1) {
                        battlefield.changeIcon(4);
                        ShipSelectionScene.ToolbarMouseListener listener =  (ShipSelectionScene.ToolbarMouseListener) ASSET.exampleShips[3].getMouseListeners()[0];
                        listener.changed = false;
                        ASSET.setRedLight(4);
                    } else
                        ASSET.setGreenLight(4);
                    if (four > 1) {
                        battlefield.removeShip(battlefield.getShips().get(i));
                        i--;
                    }
                    ASSET.toolsLight[3].repaint();
                    break;
                }
            }
        }
        if (one == 4 && two == 3 && tree == 2 && four == 1) {
            start.setEnabled(true);
        }
    }

    public class ToolbarMouseListener implements MouseListener {
        int deck;
        JLabel exampleShip;
        public boolean changed;

        public ToolbarMouseListener(int deck, JLabel uLabel) {
            this.deck = deck;
            this.exampleShip = uLabel;
            this.changed = false;
        }

        @Override
        public void mouseClicked(MouseEvent me) {
            battlefield.setEraseMod(false);
            ASSET.fifAllExampleShips();
            ml.setMaxDeck(deck);
            changed = !changed;
            if (changed)
            ASSET.switchImageFromToolbar(deck,"");
            else
                ASSET.switchImageFromToolbar(deck,"50");
            exampleShip.repaint();
        }

        @Override
        public void mouseExited(MouseEvent me) {}
        @Override
        public void mouseEntered(MouseEvent me) {}
        @Override
        public void mousePressed(MouseEvent me) {}
        @Override
        public void mouseReleased(MouseEvent me) {}
    }

    class SelectMouseListener implements MouseListener {
        private seaBattle.game.Point startPoint;
        private seaBattle.game.Point pastPoint;
        private String position;
        private int maxDeck;

        public void setMaxDeck(int maxDeck) {
            this.maxDeck = maxDeck;
        }

        public SelectMouseListener() {
            battlefield.addMouseMotionListener(new MouseMotionListener() {
                public void mouseDragged(MouseEvent me) {
                    seaBattle.game.Point point = makePoint(me);
                    if (position == null && startPoint != null && !(startPoint.equals(point)) && pastPoint != null) {
                        if (point.y > startPoint.y || point.y < startPoint.y)
                            position = "ver";
                        else
                            position = "hor";
                    }
                    if (startPoint == null) {
                        startPoint = point;
                        pastPoint = point;
                    }
                    if (isPointRight(point) && point != null && battlefield.getBacklights().size() < maxDeck) {
                        if (point.x < startPoint.x || point.y < startPoint.y) {
                            startPoint = point;
                        }
                        battlefield.addBacklight(point);
                        battlefield.repaint();
                    }
                    battlefield.repaint();
                }
                public void mouseMoved(MouseEvent me) {}
            });
        }

        @Override
        public void mouseClicked(MouseEvent me) {
            if (battlefield.isEraseMod()) {
                Ship eraseShip = deleteSelectedShip(makePoint(me));
                battlefield.removeShip(eraseShip);
                scanShips();
            }
            if (maxDeck==1) {
                Cell point = makePoint(me);
                if (isPointRight(point) && point != null) {
                    battlefield.addBacklight(makePoint(me));
                    makeShip();
                    position = null;
                    startPoint = null;
                    battlefield.clearBacklights();
                    battlefield.repaint();
                }
            }
            scanShips();
        }
        @Override
        public void mouseExited(MouseEvent me) {
        }
        @Override
        public void mouseEntered(MouseEvent me) {
        }
        @Override
        public void mousePressed(MouseEvent me) {
            //System.out.println(makePoint(me));
        }

        /*
        Ѕаг1:  огда поставлено максимальное количесво кораблей с n палубой, ставить корабли возможно,
        они не будут отображатьс€, но их барьеры не позвол€т поставить другой корабль на их место //исправлено
        Ѕаг2:  огда начинаешь первую точку с барьера и заполн€ешь нужное количество клеток, корабль все равно ставитс€ от первой точки //исправлено
        */

        @Override
        public void mouseReleased(MouseEvent me) {
            if (maxDeck>1) {
                makeShip();
                position = null;
                startPoint = null;
                battlefield.clearBacklights();
                scanShips();
                battlefield.repaint();
            }
        }

        private Ship deleteSelectedShip(Cell cell) {
            for (Ship ship : battlefield.getShips()) {
                for (seaBattle.game.Point point : ship.points) {
                    if (point.equals(cell)) {
                        scanShips();
                        return ship;
                    }
                }
            }
            scanShips();
            return null;
        }

        private void makeShip() {
            if (battlefield.getBacklights().size() == maxDeck&&!checkMaxShipOfDeck(maxDeck)) {
                seaBattle.game.Point[] points = new seaBattle.game.Point[battlefield.getBacklights().size()];
                int i = 0;
                for (seaBattle.game.Point p : battlefield.getBacklights()) {
                    points[i] = p;
                    i++;
                }

                Ship newShip;

                if (!(maxDeck>1)||(position.equals("ver")&&points[0].y<points[maxDeck-1].y)||(position.equals("hor")&&points[0].x<points[maxDeck-1].x)) {
                    newShip = new Ship(points.length, points[0].toString(), points, points[0], position);
                }
                else {
                    newShip = new Ship(points.length, points[maxDeck - 1].toString(), points, points[maxDeck - 1], position);
                }
                battlefield.addShip(newShip);
            }
            scanShips();
        }

        private boolean checkMaxShipOfDeck(int deck) {
            int maxShipCount = 5-deck;
            int shipCount = 0;

            for (Ship s: battlefield.getShips()
            ) {
                if (s.deck==deck)
                    shipCount++;
                if (shipCount==maxShipCount)
                    return true;
            }
            return false;
        }

        private seaBattle.game.Point makePoint(MouseEvent me) {
            seaBattle.game.Point point = null;

            int meX = me.getX();
            int meY = me.getY();

            int x = (int)(((double)meX-(50.0*ASSET.extension))/(50.0*ASSET.extension));
            int y = (int)(((double)meY-(50.0*ASSET.extension))/(50.0*ASSET.extension));

            if (!(x < 0 || x > 9 || y < 0 || y > 9)) {
                point = new seaBattle.game.Point(x, y);
            }
            return point;
        }
    }
}
