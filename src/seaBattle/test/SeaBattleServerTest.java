package seaBattle.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

class SeaBattleServerTest {
    //Тупа тест
    Scanner scanner;
    PrintWriter printWriter;

    public static void main(String[] args) throws Exception {
        SeaBattleServerTest test = new SeaBattleServerTest();
        test.go();
    }

    void go() throws Exception {
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress("localhost", 1488));
        scanner = new Scanner(socket.getInputStream());
        printWriter = new PrintWriter(socket.getOutputStream(), true);

        Thread readerFromServer = new Thread(new ReaderFromServer());
        readerFromServer.start();

        while (true) {
            BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
            printWriter.println(inputReader.readLine());
            printWriter.flush();
        }
        //scanner.nextLine();
    }

    class ReaderFromServer implements Runnable {
        @Override
        public void run() {
            while (scanner.hasNextLine()) {
                String currentString = scanner.nextLine();
                System.out.println(currentString);
                printWriter.println("1488");
                printWriter.flush();
            }
        }
    }
}
