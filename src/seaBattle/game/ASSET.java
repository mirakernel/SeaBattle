package seaBattle.game;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class ASSET {
    //глобальная пеерменная расширения
    public static double extension;
    public static String nick;

    static {
        extension = 1.0; // 0.8 - для 1600 на 900 0.625 - 720pi
        loadSettings();
    }

    public static void setNick(String nick) {
        ASSET.nick = nick;
    }

    public static void setExtension(double extension) {
        ASSET.extension = extension;
    }

    private static void loadSettings() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("./settings.txt"));
            extension = Double.parseDouble(reader.readLine());
            nick = reader.readLine();
            reader.close();
        }
        catch (NullPointerException | IOException ex) {
            System.out.println(ex.getMessage());
            extension = 1.0;
            nick = "Введите имя";
            saveSettings();
        }
    }

    public static void saveSettings() {
        try {
            File ipTxt = new File("./settings.txt");
            PrintWriter fileWriter = new PrintWriter(new FileWriter(ipTxt),true);
            fileWriter.println(extension);
            fileWriter.println(nick);
            fileWriter.close();
        }
        catch (IOException ex) {
            File f = new File("./settings.txt");
            try {
                f.createNewFile();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }



    //вне папок
    static public Image enemyShip, field, miss, shipHole, warning, enemyMiss, enemyField,eraser50;
    //победа или поражение
    static public JLabel win, lose, eraser;
    static public JButton exitBtn;
    //game
    static public Image playerBattlefield,enemyBattlefield;
    static public JLabel game_background, underFields, myCountLabel, enemyCountLabel, sideBar, moveLabel;
    static public JButton send;
    static public JComponent shipForMyCount, myCount, notMyCount, moveStatus, input, scroll ,shipForNotMyCount;
    //menu
    static public JLabel underField, authors, cbs, ipAddress, ipField, menuBack, menuBackground, menuMain, underMenuBack, underMenuMain, leftWall, rightWall;
    static public JButton startBtn, savedIp, changeIp, createServer;
    static public JComponent nameField, selectorField;
    //selector
    static public Image field_s_edit;
    static public JLabel selector_background, under2Selector, exShipBox, underExShipBox, underSelector;
    static public JButton start, randomBtn;
    //exampleShip
    static public JLabel[] exampleShips;
    static public JComponent[] toolsLight;

    public static int gameMulti;

    private static final String way;

    private static boolean eraser_o_fif;


    static {
        gameMulti = (int) (50*extension);

        way = "/seaBattle/game/assets/img/";
        exampleShips = new JLabel[4];
        toolsLight = new JComponent[6];
        nameField = iniComponent(0,114,236,41);
        //картинки

        field_s_edit = iniImage("selector/field_s_edit",600,600);
        eraser50 = iniImage("eraser50",50,50);
        enemyShip = iniImage("emenyShip", 50, 50);
        field = iniImage("field", 600, 600);
        miss = iniImage("miss", 50, 50);
        warning = iniImage("warning", 100, 100);
        enemyMiss = iniImage("emenyMiss", 50, 50);
        //game_field = iniImage("field", 600, 600);
        enemyField = iniImage("emenyField", 600, 600);
        shipHole = iniImage("shipHole", 50, 50);

        win = iniLabel("win", 450, 450, 0, 0);
        lose = iniLabel("lose", 450, 450, 0, 0);

        initMenuImages();
        initBattleImages();
        initSelectorImages();

    }

    private static JComponent iniComponent(int x,int y, int width, int height) {
        x *= ASSET.extension;
        y *= ASSET.extension;
        width *= ASSET.extension;
        height *= ASSET.extension;
        JComponent comp = new JLabel();
        comp.setBounds(x,y,width,height);
        return comp;
    }

    private static JLabel iniLabel(String name, int width, int height, int x, int y) {
        x *= ASSET.extension;
        y *= ASSET.extension;
        JLabel label = new JLabel(new ImageIcon(iniImage(name, width, height)));
        width *= ASSET.extension;
        height *= ASSET.extension;
        label.setBounds(x, y, width, height);
        return label;
    }

    private static JButton iniBtn(String name, int width, int height, int x, int y) {
        x *= ASSET.extension;
        y *= ASSET.extension;
        JButton btn = new JButton(new ImageIcon(iniImage(name, width, height)));
        width *= ASSET.extension;
        height *= ASSET.extension;
        btn.setBounds(x, y, width, height);
        return btn;
    }

    public static void switchEraserImage() {
        if (eraser_o_fif) {
            eraser.setIcon(new ImageIcon(iniImage("eraser50",50,50)));
        }
        else
            eraser.setIcon(new ImageIcon(iniImage("eraser",50,50)));
        eraser_o_fif = !eraser_o_fif;
    }

    public static void fifAllExampleShips() {
        for (int i = 1; i < 5; i++)
            exampleShips[i-1].setIcon(new ImageIcon(iniImage("ships/d" + i + "/ver" + "/k" + i + "50",50,50*i)));
    }

    public static void switchImageFromToolbar(int deck, String fif) {
        fifAllExampleShips();
        eraser50();
        exampleShips[deck-1].setIcon(new ImageIcon(iniImage("ships/d" + deck + "/ver" + "/k" + deck+fif,50,50*deck)));
    }

    public static void eraser50() {
        if (eraser_o_fif)
            switchEraserImage();
    }

    public static void setRedLight(int index) { //палубы корабля
        JLabel shipLabel = (JLabel) toolsLight[index-1];
        shipLabel.setIcon(new ImageIcon(iniImage("selector/lightR"+index,shipLabel.getWidth(),shipLabel.getHeight())));
    }

    public static void setGreenLight(int index) { //палубы корабля
        JLabel shipLabel = (JLabel) toolsLight[index-1];
        shipLabel.setIcon(new ImageIcon(iniImage("selector/light"+index,shipLabel.getWidth(),shipLabel.getHeight())));
    }

    public static void initSelectorImages() {
        gameMulti = (int) (50*extension);
        underField = iniLabel("selector/underField", 641,641, 5, 5);
        under2Selector = iniLabel("selector/under2Selector", 854,846, 0, 0);
        selectorField = iniComponent(21,21,600,600);
        selector_background = iniLabel("selector/background", 854, 860, 0, 0);
        exShipBox = iniLabel("selector/exShipBox", 50, 650, 35, 28);
        underExShipBox = iniLabel("selector/underExShipBox", 152, 738, 635, 16);
        underSelector = iniLabel("selector/underSelector", 800, 780, 26, 30);

        start = iniBtn("selector/start", 610, 68, 21, 646);
    }

    public static void initToolbarImages() {
        int defaultX = 35, defaultY = 28,light=16; //661,46, 696 74
        int deference = 0;
        for (int i = 1; i < 5; i++) {
            JLabel shipLabel = iniLabel("ships/d" + i + "/ver" + "/k" + i + "50",50,50*i,light,light);
            exampleShips[i-1] = shipLabel;
            JLabel lightLabel = iniLabel("selector/light"+i,82,(50*i)+32,defaultX,defaultY+deference);
            toolsLight[i-1] = lightLabel;
            lightLabel.add(shipLabel);
            deference += 50 * i + 10;
        }
        eraser = iniLabel("eraser50", 50, 50, light, light);
        JLabel lightLabel5 = iniLabel("selector/light1",82,82,defaultX,defaultY+deference);
        lightLabel5.add(eraser);
        toolsLight[4] = lightLabel5;
        eraser_o_fif = false;
        deference += 50 + 10;
        randomBtn = iniBtn("randomBtn", 50, 50, light, light);
        JLabel lightLabel6 = iniLabel("selector/light1",82,82,defaultX,defaultY+deference);
        lightLabel6.add(randomBtn);
        toolsLight[5] = lightLabel6;
    }

    public static void initMenuImages() {
        gameMulti = (int) (50*extension);
        //меню
        authors = iniLabel("menu/authors",206,43,13,153);
        cbs = iniLabel("menu/cbs", 182, 45, 26, 191); //74
        ipAddress = iniLabel("menu/ipAddress", 207, 45, 0, 0);
        ipField = iniLabel("menu/ipField", 183, 25, 12, 37);
        menuBack = iniLabel("menu/menuBack", 328, 231, 0, 329);
        menuBackground = iniLabel("menu/menuBackground", 475, 700, 0, 0);
        menuMain = iniLabel("menu/menuMain", 328, 560, 74, 62);
        underMenuBack = iniLabel("menu/underMenuBack", 206, 191, 61, 13);
        underMenuMain = iniLabel("menu/underMenuMain", 234, 247, 47, 43);
        leftWall = iniLabel("menu/leftWall",40,560,0,0);
        rightWall = iniLabel("menu/rightWall",40,560,288,0);

        startBtn = iniBtn("menu/startBtn", 235, 85, 0, 0);
        savedIp = iniBtn("menu/savedIp", 87, 21, 108, 68);
        changeIp = iniBtn("menu/changeIp", 87, 21, 12, 68);
        createServer = iniBtn("menu/createServer", 206, 46, 0, 116);
    }

    public static void initBattleImages() {
        //игровое окно
        game_background = iniLabel("game/background", 1920, 1080, 0, 0);

        underFields = iniLabel("game/underFields",1200,700,85,75);
            enemyBattlefield = iniImage("game/enemyBattlefield",500,500); //702 175
            playerBattlefield = iniImage("game/playerBattlefield",500,500); //169 175

        sideBar = iniLabel("game/sideBar", 535,1080, 1385,0);
                moveLabel = iniLabel("game/moveLabel", 370,85, 83, 108);
                    moveStatus = iniComponent(30,15,300,50);

            scroll = iniComponent(0,0,370,400);
            input = iniComponent(0,400,320,40);
            send = iniBtn("game/send", 50, 40, 320,400);

        myCountLabel = iniLabel("game/myCountLabel", 370,85, 83, 740);
            myCount = iniComponent(10,10,300,50);
                shipForMyCount = iniComponent(0,0,gameMulti,gameMulti);
        enemyCountLabel = iniLabel("game/enemyCountLabel", 370,85, 83, 854);
            notMyCount = iniComponent(10,10,300,50);
                shipForNotMyCount = iniComponent(0,0,gameMulti,gameMulti);
    }

    private static Image iniImage(String name, int width, int height) {
        //System.out.println(name + " картинка " + width + " " + height + " way  " + way + name + ".png");
        width *= ASSET.extension;
        height *= ASSET.extension;
        Image image = new ImageIcon(ASSET.class.getClass().getResource(way + name + ".png")).getImage();
        return image.getScaledInstance(width, height, Image.SCALE_DEFAULT);
    }
}
