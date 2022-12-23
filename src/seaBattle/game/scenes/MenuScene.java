package seaBattle.game.scenes;

import seaBattle.game.ASSET;
import seaBattle.game.ServerThreadStarter;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class MenuScene {
    private String ip;

    public MenuScene(String ip) {
        this.ip = ip;
    }

    public void start() {
        JFrame menuFrame = new JFrame("Главное меню морского боя");
        menuFrame.setVisible(true);
        menuFrame.setResizable(false); //Установка неизменнности экрана
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension dimension = kit.getScreenSize();
        menuFrame.setBounds((dimension.width/2 - 202),(dimension.height/2 - 202), (int) (475* ASSET.extension),(int)(700*ASSET.extension));
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton startBtn = ASSET.startBtn;
        JLabel ipAddress = ASSET.ipAddress;
        JTextField ipField = new JTextField(this.ip);
        JButton changeIp = ASSET.changeIp;
        JButton savedIp = ASSET.savedIp;
        JButton createServer = ASSET.createServer;
        createServer.setName("Создать Сервер");
        JLabel cbs = ASSET.cbs;
        JCheckBox selectCheckBox = new JCheckBox("Эдитор",true);
        JCheckBox randomCheckBox = new JCheckBox("Рандом");

        JTextField nameField = new JTextField(ASSET.nick);

        JLabel panel = ASSET.menuBackground;
        panel.setLayout(null);
        menuFrame.add(panel);

        JLabel menuMain = ASSET.menuMain;
        panel.add(menuMain);
        menuMain.add(ASSET.rightWall);
        menuMain.add(ASSET.leftWall);
        JLabel underMenuMain = ASSET.underMenuMain;
        menuMain.add(underMenuMain);
        underMenuMain.add(startBtn);

        nameField.setBounds(ASSET.nameField.getBounds());
        underMenuMain.add(nameField);

        underMenuMain.add(ASSET.authors);
        underMenuMain.add(ASSET.cbs);

        JLabel menuBack = ASSET.menuBack;
        JLabel underMenuBack = ASSET.underMenuBack;
        menuMain.add(menuBack);
        menuBack.add(underMenuBack);
        underMenuBack.add(ipAddress);

        ipField.setBounds(ASSET.ipField.getBounds());
        underMenuBack.add(ipField);

        underMenuBack.add(changeIp);
        underMenuBack.add(savedIp);

        underMenuBack.add(createServer);
        menuFrame.revalidate();

        ipField.setEditable(false);
        ipField.addActionListener(e -> savedIp.doClick());
        savedIp.setEnabled(false);

        changeIp.addActionListener(e -> {
            ipField.setEditable(true);
            savedIp.setName("Сохранить");
            savedIp.setEnabled(true);
        });
        createServer.addActionListener(e -> {
            if (createServer.getName().equals("Создать Сервер")) {
                setupServers();
                savedIp.setEnabled(false);
                changeIp.setEnabled(false);
                createServer.setName("Сервер запущен");
                createServer.setEnabled(false);
            }
        });
        savedIp.addActionListener(e -> {
            try {
                File ipTxt = new File("./serverIp.txt");
                PrintWriter fileWriter = new PrintWriter(new FileWriter(ipTxt),true);
                fileWriter.println(ipField.getText());
                fileWriter.close();
            }
            catch (IOException ex) {
                File f = new File("./serverIp.txt");
                try {
                    f.createNewFile();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }

            ipField.setEditable(false);
            savedIp.setEnabled(false);
        });

        startBtn.addActionListener(e -> {
            ASSET.setNick(nameField.getText());
            ASSET.saveSettings();
            menuFrame.dispose();
                ShipSelectionScene selectionScene = new ShipSelectionScene();
                selectionScene.start();
        });

        selectCheckBox.addActionListener(e -> randomCheckBox.setSelected(!selectCheckBox.isSelected()));

        randomCheckBox.addActionListener(e -> selectCheckBox.setSelected(!randomCheckBox.isSelected()));

    }

    private void setupServers() {
        Thread serverGameThread = new ServerThreadStarter("game");
        Thread serverChatThread = new ServerThreadStarter("chat");
        serverGameThread.start();
        serverChatThread.start();
        ip = "localhost";
    }

}
