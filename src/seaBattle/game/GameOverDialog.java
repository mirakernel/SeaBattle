package seaBattle.game;

import javax.swing.*;

public class GameOverDialog extends JDialog {

    private final JFrame frame;
    private final String result;

    public GameOverDialog(JFrame frame, String result) {
        this.frame = frame;
        this.result = result;

        initComponents();
        setSize(450, 550);
        setResizable(false);
    }

    public void start() {
        frame.setVisible(false);
        setVisible(true);
        repaint();
    }

    private void initComponents() {
        JLabel background = new JLabel();

        switch (result) {
            case "������!" : {
                background.setIcon(new ImageIcon(getClass().getResource("/seaBattle/game/assets/img/win.png")));
                //soundManager.playWin();
                break;
            }
            case "���������.": {
                background.setIcon(new ImageIcon(getClass().getResource("/seaBattle/game/assets/img/lose.png")));
                //soundManager.playLose();
                break;
            }
        }

        JButton closeBtn = new JButton("�����");
        closeBtn.addActionListener(e -> System.exit(0));
        background.setBounds(0,0,450,450);
        closeBtn.setBounds(0,450,450,100);
        add(background);
        add(closeBtn);
    }
}
