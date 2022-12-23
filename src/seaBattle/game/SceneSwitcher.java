package seaBattle.game;

import seaBattle.game.scenes.GameScene;

import java.util.HashMap;

public class SceneSwitcher { //�������� ��� �����

    private HashMap<String, GameScene> scenes;
    GameScene currentScene;

    public SceneSwitcher() {
        scenes = new HashMap<>();
    }

    public void addScene(GameScene scene, String sceneName) {
        scenes.put(sceneName, scene);
    }
    public void removeScene(String sceneName) {
        try {
            scenes.remove(sceneName);
        } catch (Exception exception) {
            System.out.println("����� ����� ���");
        }
    }

    public void showScene(String sceneName) {
        GameScene currentScene;
        try {
            currentScene = scenes.get(sceneName);
            currentScene.start();
        } catch (Exception exception) {
            System.out.println("����� ����� ��� (��� ��� �� �������, ������ � ����� �����)");
        }
    }

}
