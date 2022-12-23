package seaBattle.game;

import seaBattle.game.scenes.MenuScene;

class Launcher {
    public static void main(String[] args) {
        MenuScene menuScene = new MenuScene("localhost");
        menuScene.start();
    }

}
