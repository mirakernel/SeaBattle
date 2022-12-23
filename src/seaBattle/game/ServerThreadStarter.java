package seaBattle.game;

import seaBattle.game.chat.ChatServer;
import seaBattle.game.server.SBServer;

public class ServerThreadStarter extends Thread {
    private final String name;

    public ServerThreadStarter(String name){
        super(name);
        this.name = name;
    }

    public void run(){
        switch (name) {
            case "chat": {
                setupChatServer();
            }
            case "game": {
                setupGameServer();
            }

        }
    }

    private void setupChatServer() {
        new ChatServer().go();
    }

    private void setupGameServer() {
        try {
            new SBServer().go();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}