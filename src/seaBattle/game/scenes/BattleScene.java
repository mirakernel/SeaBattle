package seaBattle.game.scenes;

import seaBattle.game.*;
import seaBattle.game.chat.Chat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

/*
��������� ������
���������� ������� ����, ������������ ��������:
	������� ����� ������, ���� �����, ������/������������,
	������� ������������ �������� � ���������/������� ������������ ��������,
	���� �����: ���
	������: �����
	������ �����
	������: ���������
		����������� ���� � �����������
		������� ���������� (���������� � �������� x1.5, x2, x3)
		������������ ��������
			-���� ���� �����, ���� ���������� ������
			-���� ���� ������, ���� ���������� �����
			-���� ������, ���������� �����
			-���� �����, ���������� ������
..
��������� ������, ��������������� ������, ����������� ����
//
��� ����������� � ������� � �������
..
����� ������ ������ ������������ ������ �����, ������������ �������, ������� �������� ���������� �� ������
����������� ����
���� ���������
����������� ���������:
����� 1 ���������� ����� �� ������ ����� 2 �������� �����
��������� ����� �� ����� 1, ��������� ��������
����� ��� ���������� ������� ������
� ��� ����������� �� ��� ��� ���� �� ����� ���������� ��� ������� ������ �� �������
��������� �����, ������� ������ ����������
���� ��������, ������������ ������ �����, ����� ����
 */

/*
	�����
	�������� ��������� ����� � Player ��� ������� ����� �����?

	���������� ���������
	��������� ��� ��������

	������ ������� + ���� ������ ���������

	������� ������

 */



public class BattleScene {

	private String ip; //
	private boolean isMyMove = true; //
	private AttackWriter attackWriter;
	private AttackReader attackReader;
	private Thread serverGameThread;
	private Thread serverChatThread;

	private boolean isAnswer;
	private boolean isStarted = false;

	private JFrame frame;
	private TextLabel myCount;
	private TextLabel notMyCount;
	private JLabel move;
	private TextLabel moveStatus;
	private PlayerBattlefield playerBattlefield;
	private EnemyBattlefield enemyBattlefield;
	private Chat client;


	public BattleScene(ArrayList<Ship> ships) {
		playerBattlefield = new PlayerBattlefield();
		playerBattlefield.setShips(ships);
		enemyBattlefield = new EnemyBattlefield();
		initIp();

	}

	public void start() {
		frame = new JFrame();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(((int) (1920 * ASSET.extension)), (int) (1080 * ASSET.extension));

		//������� ������ ������������� ������� � ��������� � ��� ��� ��� �����
		moveStatus = new TextLabel("��� ���");

		JLabel shipForMyCount = new JLabel(new ImageIcon(getClass().getResource("/seaBattle/game/assets/img/ships/d" + 1 + "/ver" + "/k" + 1 + ".png")));
		shipForMyCount.setBounds(ASSET.shipForMyCount.getBounds());
		myCount = new TextLabel("0  ���� " + ASSET.nick);
		myCount.setBounds(ASSET.myCount.getBounds());

		JLabel shipForNotMyCount = new JLabel(new ImageIcon(getClass().getResource("/seaBattle/game/assets/img/ships/d" + 1 + "/ver" + "/k" + 1 + ".png")));
		shipForNotMyCount.setBounds(ASSET.shipForNotMyCount.getBounds());
		notMyCount = new TextLabel("0  ���� ����������");

		notMyCount.setBounds(ASSET.notMyCount.getBounds());
		move = ASSET.moveLabel;
		moveStatus.setBounds(ASSET.moveStatus.getBounds());

		//�������� ����
		client = new Chat(ASSET.nick, this.ip);
		client.setupNetWorking();
		client.setupGUI();
		client.newTheard();
		client.setBounds(83,240,370,440);

		//���������
		frame.add(ASSET.game_background);

		ASSET.game_background.add(ASSET.underFields);
					playerBattlefield.setBounds(84,100,500,500); //169 175
						ASSET.underFields.add(playerBattlefield);
					enemyBattlefield.setBounds(617,100,500,500); //702 175
						ASSET.underFields.add(enemyBattlefield);

		ASSET.game_background.add(ASSET.sideBar);
				ASSET.sideBar.add(ASSET.myCountLabel);
					ASSET.myCountLabel.add(myCount);
					ASSET.myCountLabel.add(shipForMyCount);
				ASSET.sideBar.add(ASSET.enemyCountLabel);
					ASSET.enemyCountLabel.add(notMyCount);
					ASSET.enemyCountLabel.add(shipForNotMyCount);
				ASSET.sideBar.add(move);
						move.add(moveStatus);
					ASSET.sideBar.add(client);



		connect();
	}

	//���������
	private void initIp() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader("./serverIp.txt"));
			ip = reader.readLine();
			reader.close();
		} catch (NullPointerException | IOException ex) {
			ip = "localhost";
		}
	}

	private EnemyCell convertStringToEnemyPoint(String s, boolean isShip, String name, String result, int deck, Cell sp, int hp, String position) {
		int x = 0;
		int y = 0;
		char[] abc = "����������".toCharArray();
		char[] charPoint = s.toCharArray();

		for (int i = 0; i < abc.length; i++) {
			if (abc[i] == (charPoint[0])) {
				x = i;
			}
		}
		try {
			y = Integer.parseInt("" + charPoint[1] + charPoint[2]) - 1;
		} catch (Exception e) {
			y = (Integer.parseInt("" + charPoint[1])) - 1;
		}
		return new EnemyCell(x, y, isShip, name, result, deck, sp, hp, position);
	}

	//���������� ����� ���������
	public EnemyCell attack(String line) {
		String result = null;
		EnemyCell enemyCell = convertStringToEnemyPoint(line, false, "null", "null", 0, new Cell(0, 0), 0, "null");
		for (Ship ship : playerBattlefield.getShips()) {
			result = ship.getDamage(line);
			if (result.equals("�����!") || result.equals("�������!")) {
				enemyCell.setIsShip(true);
				enemyCell.setDeck(ship.deck);
				enemyCell.setName(ship.name);
				enemyCell.position = ship.position;
				enemyCell.hp = ship.hp;
				enemyCell.difference = ship.getDifferenceDeStartPoint(enemyCell);
				if (result.equals("�����!"))
					SoundManager.getInstance().playHit();
				else
					SoundManager.getInstance().playSunk();
				break;
			}
		}
		enemyCell.setResult(result);
		return enemyCell;
	}

	public void connect() {
		try {
			attackWriter = new AttackWriter();
			attackReader = new AttackReader();
			Socket socket = new Socket();
			socket.connect(new InetSocketAddress(ip, 1488));
			attackReader.connect(new Scanner(socket.getInputStream()));
			attackWriter.connect(new PrintWriter(socket.getOutputStream(), true));
		} catch (IOException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(frame,
					"�� ������� ������������ � �������",
					"������", JOptionPane.ERROR_MESSAGE,
					new ImageIcon(getClass().getResource("/seaBattle/game/assets/img/warning.png")));
			frame.dispose();
		}

		Thread readerFromServer = new Thread(attackReader);
		readerFromServer.start();
		isAnswer = false;
	}

	private class AttackWriter {
		private PrintWriter printWriter;
		private String lastedWritten;
		private final String[][] allPossibleCoordinates;

		public AttackWriter() {
			lastedWritten = "";

			char[] abc = "����������".toCharArray();
			allPossibleCoordinates = new String[10][10];
			for (int x = 0; x < 10; x++) {
				for (int y = 0; y < 10; y++) {
					allPossibleCoordinates[x][y] = abc[x] + "" + (y + 1);
				}
			}

			enemyBattlefield.addMouseListener(new MouseListener() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (isMyMove) {
						writePlayerAttack(enemyBattlefield.getCurrentCell().toString());
					}
				}

				@Override
				public void mousePressed(MouseEvent e) {

				}

				@Override
				public void mouseReleased(MouseEvent e) {

				}

				@Override
				public void mouseEntered(MouseEvent e) {

				}

				@Override
				public void mouseExited(MouseEvent e) {

				}
			});
		}

		public void connect(PrintWriter printWriter) {
			this.printWriter = printWriter;
		}


		private boolean isCoordinates(String s) {
			boolean isC = false;
			for (int x = 0; x < 10; x++) {
				for (int y = 0; y < 10; y++) {
					if (s.equals(allPossibleCoordinates[x][y])) {
						isC = true;
						break;
					}
				}
				if (isC)
					break;
			}
			return isC;
		}

		public void writePlayerAttack(String s) {
			if (isCoordinates(s) && checkVoidCell(s) && !s.equals(lastedWritten) && isStarted) {
				lastedWritten = s;
				System.out.println("��� ������:" + s);
				SoundManager.getInstance().playMiss();
				printWriter.println(s);
				printWriter.flush();
				isAnswer = true;
			}
		}

		public void writeEnemyAttackAnswer(EnemyCell enemyCell) {
			printWriter.println(enemyCell.send());
			printWriter.flush();
			playerBattlefield.repaint();
			enemyBattlefield.repaint();
		}

		private boolean checkVoidCell(String input) {
			for (Cell sp : enemyBattlefield.get()) {
				if (input.equals(sp.toString()))
					return false;
			}
			for (Cell sp : enemyBattlefield.getEnemyCells()) {
				if (input.equals(sp.toString()))
					return false;
			}
			return true;
		}
	}

	private class AttackReader implements Runnable {

		private int winCount = 0; //20
		private int loseCount = 0; //20

		private Scanner scanner;
		private String currentString;

		private String lastedStr = "";
		private String chatLog = "";

		public void connect(Scanner scanner) {
			this.scanner = scanner;
		}

		@Override
		public void run() {
			readAttack();
		}

		private void readAttack() {
			while (scanner.hasNextLine()) {
				currentString = scanner.nextLine();

				System.out.println(winCount + " <-win/ " + loseCount + " <-lose");
				updateMoveLabelText();
				checkIsStarted();

				if (currentString.equals("start")) {
					isStarted = true;
					continue;
				}

				if (isStarted) {
					if (currentString.equals(lastedStr))
						continue;

					playerBattlefield.requestFocus(); //!!!!!!!!!!
					//������ ���������, ���� ��� ����� �� �������� ����� enemyPoint, ����� - ����� ���������� � ��������� ���������
					if (isAnswer) {
						readPlayerAttack();
					} else {
						readEnemyAttack();
					}

					lastedStr = currentString;
					sendMoveLogToChat();
					updateMoveLabelText();

					playerBattlefield.repaint(); //!!!!!!!!!!!!!!
					playerBattlefield.grabFocus(); //!!!!!!!!!!!!!!

					if (winCount == 20) {
						winOrLoseDialog("������!");
					}
					if (loseCount == 20) {
						winOrLoseDialog("���������.");
					}

				}
			}
		}

		private void sendMoveLogToChat() {
			client.textArea.append(chatLog + "\n");
			chatLog = "";
		}

		private void appendOutLog(String plus) {
			chatLog += plus;
		}

		private void readPlayerAttack() {
			isAnswer = false;
			String[] results = currentString.split("/");
			EnemyCell enemyCell = null;
			try {
				enemyCell = convertStringToEnemyPoint((results[0]), Boolean.parseBoolean(results[1]), results[2], results[3], Integer.parseInt(results[4]), new Cell(Integer.parseInt(results[5]), Integer.parseInt(results[6])), Integer.parseInt(results[7]), results[8]);
			} catch (ArrayIndexOutOfBoundsException e) {
				System.out.println("�������������� ������");
			}
			if (enemyCell.getResult().equals("�����!") || enemyCell.getResult().equals("�������!")) {
				SoundManager.getInstance().playHit();
				winCount++;
				myCount.setText(winCount + " ���� " + ASSET.nick);
				myCount.repaint();
			}
			enemyBattlefield.addEnemyCell(enemyCell);
			isMyMove = enemyCell.getIsShip();
			if (enemyCell.getResult().equals("�������!")) {
				enemyBattlefield.makeEnemyShipBarriers(enemyCell.getName(), enemyCell.getDeck());
			}
			appendOutLog("��� ������: " + enemyCell + " " + enemyCell.getResult());
		}

		private void readEnemyAttack() {
			if (isMyMove) {
				isMyMove = false;
				moveStatus.repaint();
			}
			EnemyCell enemyCell = attack(currentString);
			attackWriter.writeEnemyAttackAnswer(enemyCell);
			//���� ������ - �������� � ������ ��������
			if (!enemyCell.getIsShip()) {
				enemyBattlefield.addMiss(enemyCell);
				isAnswer = true;
				isMyMove = true;
			} else {
				enemyBattlefield.addEnemyHit(enemyCell);
				loseCount++;
				notMyCount.setText(loseCount + " ���� ����������");
				notMyCount.repaint();
			}
			chatLog += "��� ����������: " + enemyCell + " " + enemyCell.getResult();
		}

		private void winOrLoseDialog(String result) {
			GameOverDialog gameOver = new GameOverDialog(frame, result);
			gameOver.start();
		}

		private void updateMoveLabelText() {
			if (isMyMove) {
				move.setText("��� ���");
				moveStatus.setText("��� ���");
			} else {
				move.setText("��� ����������");
				moveStatus.setText("��� ����������");
			}
		}

		private boolean checkIsStarted() {
			return currentString.equals("start");
		}
	}

	private class TextLabel extends JLabel {
		Font font;
		public TextLabel(String text) {
			super(text);
			try {
				font = Font.createFont(Font.TRUETYPE_FONT, new BufferedInputStream(getClass().getResourceAsStream("/seaBattle/game/assets/fonts/19246.ttf"))).deriveFont((float) (29*ASSET.extension));
				GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
				ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new BufferedInputStream(getClass().getResourceAsStream("/seaBattle/game/assets/fonts/19246.ttf"))));
			} catch (IOException | FontFormatException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void paintComponent(Graphics g) {
			g.setFont(font);
			g.drawString(this.getText(),60,17);

		}
	}
}