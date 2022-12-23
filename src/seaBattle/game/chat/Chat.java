package seaBattle.game.chat;

import seaBattle.game.ASSET;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Chat extends JLabel{
	String clientName;
	PrintWriter writer;
	BufferedReader reader;
	public JTextArea textArea;
	boolean needName = false;

	public JTextField input;
	public JButton send;

	private boolean started = false;

	String ip;

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public Chat(String clientName, String ip) {
		this.clientName = clientName;
		this.ip = ip;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	
	public void newTheard() {
		Thread readerThread = new Thread(new IncomingReader());
		readerThread.start();
	}
	
	public void setupNetWorking() {
		try {
		Socket sk = new Socket(ip,5000);
		writer = new PrintWriter(sk.getOutputStream());
		reader = new BufferedReader(new InputStreamReader(sk.getInputStream()));
		}
		catch (Exception ex) {}
	}
	
	public void setupGUI() {
		send = ASSET.send;

		input = new JTextField(20);
		input.setBounds(ASSET.input.getBounds());

		textArea = new JTextArea(15,30);

		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setEditable(false);
		JScrollPane scroll = new JScrollPane(textArea);
		textArea.setLineWrap(true);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setBounds(ASSET.scroll.getBounds());
		
		this.add(scroll);
		this.add(input);
		this.add(send);
		
		input.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				send.doClick();
			}
		} );
		
		send.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
				writer.println(clientName+": "+input.getText());
				writer.flush();
				
				String[] ME = input.getText().split("-"); //-name-имя
				
				switch (ME[1]) {
					case "name": {
						clientName = ME[2];
						break;
					}
				}
				
				}
				catch (Exception ex) {}
				input.setText("");
				input.requestFocus();
			}
		} );
	}
	
	public class IncomingReader implements Runnable{
		public void run() {
			String message;
			try {
				while((message = reader.readLine())!=null||message.equals("")||message.equals("\n")) {
					textArea.append(message+"\n");
					if (message.equals("start"))
						started = true;
				}
			}
			catch(Exception en) {}
		}
	}
	
	
}