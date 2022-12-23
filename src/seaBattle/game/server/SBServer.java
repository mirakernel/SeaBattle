package seaBattle.game.server;


import java.io.*;
import java.net.*;
import seaBattle.game.chat.Chat;

public class SBServer {
	//Сервер принимвет сокеты 2 игроков и создает 2 новых потока
	//Сервер а не потоки регулируют игру
	
	Player player1;
	Player player2;

	Chat chat;
	
	public static void main(String[] args) throws Exception {
		SBServer seaBattleServer = new SBServer();
		seaBattleServer.go();
		
	}
	
	public void go() throws Exception{
		ServerSocket serverSocket = new ServerSocket(1488);
		player1 = new Player(serverSocket.accept());
		Thread p1 = new Thread(player1);
		player2 = new Player(serverSocket.accept());
		Thread p2 = new Thread(player2);
		p1.start();
		p2.start();
		try {Thread.sleep(300);}
		catch (Exception e) {}
		
		player1.setEmenyPlayer(player2);
		player2.setEmenyPlayer(player1);

		//Отправить в чат сообщение о начале игры

		player1.send("start");
		player2.send("start");
		System.out.println("Все игроки подключились!");
	}
	
	
	//Игрок на сервере, задача этого класса отправлять и считывать данные игрока
	class Player implements Runnable{
		BufferedReader reader;
		PrintWriter printWriter;
		String currentString;
		
		Player emenyPlayer;
		
		boolean isI;
		
		public Player(Socket socket) throws IOException{
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			printWriter = new PrintWriter(socket.getOutputStream(),true);
		}
		
		void setEmenyPlayer(Player emenyPlayer) {
			this.emenyPlayer = emenyPlayer;
		}
		
		@Override
		public void run() {
			try {
				while ((currentString = reader.readLine())!=null||currentString.equals("")) {
						System.out.println("read "+currentString);
						emenyPlayer.send(currentString);
				}
			}
			catch(Exception ex) {}
		}
		
		//Отправляет игроку данные о выстреле (EmenyPoint) и попал, не попал
		public void send(String s) {
			printWriter.println(s);
			printWriter.flush();
		}
	}
}