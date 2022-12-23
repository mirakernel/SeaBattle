package seaBattle.game;

import seaBattle.game.scenes.GameScene;

import java.util.HashMap;

public class SceneSwitcher { //СИНГЛТОН ТУТ НУЖЕН

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
            System.out.println("Такой сцены нет");
        }
    }

    public void showScene(String sceneName) {
        GameScene currentScene;
        try {
            currentScene = scenes.get(sceneName);
            currentScene.start();
        } catch (Exception exception) {
            System.out.println("Такой сцены нет (или чет со стартом, смотри в логах короч)");
        }
    }

}
