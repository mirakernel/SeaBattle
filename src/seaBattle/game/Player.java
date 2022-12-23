package seaBattle.game;

import java.io.*;

public class Player {
    public double extension;
    public String nick;

    private static Player instance;

    public static synchronized Player getInstance() {
        if (instance == null) {
            instance = new Player();
        }
        return instance;
    }

    public void setNick(String nick) {
        ASSET.nick = nick;
    }

    public void setExtension(double extension) {
        ASSET.extension = extension;
    }

    public void loadSettings() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("./settings.txt"));
            extension = Double.parseDouble(reader.readLine());
            nick = reader.readLine();
            reader.close();
        }
        catch (NullPointerException | IOException ex) {
            System.out.println(ex.getMessage());
            extension = 1.0;
            nick = "¬ведите им€";
            saveSettings();
        }
    }

    public void saveSettings() {
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
}
