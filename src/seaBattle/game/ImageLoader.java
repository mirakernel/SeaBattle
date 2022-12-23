package seaBattle.game;

import javax.swing.*;
import java.awt.*;

public class ImageLoader {

    private final String way;
    private int oneCellWH;

    public ImageLoader() {
        way = "/seaBattle/game/assets/img/";
        oneCellWH = 50;
    }

    private Image iniImage(String name, int width, int height) {
        System.out.println(name + " картинка " + width + " " + height + " way  " + way + name + ".png");
        width *= ASSET.extension;
        height *= ASSET.extension;
        Image image = new ImageIcon(ASSET.class.getClass().getResource(way + name + ".png")).getImage();
        return image.getScaledInstance(width, height, Image.SCALE_DEFAULT);
    }

}
