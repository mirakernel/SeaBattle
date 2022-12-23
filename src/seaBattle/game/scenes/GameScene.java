package seaBattle.game.scenes;

import javax.swing.*;

public abstract class GameScene {
    JFrame frame;


    public GameScene() {}

    public abstract void start();
    public abstract void close();
}
