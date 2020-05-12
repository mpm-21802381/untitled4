package test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class EchoClient2 {
    public static void main(String args[]) throws Exception {
        if (args.length != 1) {
            System.err.println("usage: java EchoClient2 <host>");
            System.exit(1);
        }
        String host = args[0];
        int port = 6500;
        String cmd, line;
        Socket socket = new Socket(host, port);
        BufferedReader input = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
        PrintStream output = new PrintStream(socket.getOutputStream(), true);
        while (true) {
            Scanner scan = new Scanner(System.in);
            System.out.print(host + ":" + port + "#>");
            cmd = scan.nextLine();
            output.println(cmd);
            line = input.readLine();
            if(cmd.equalsIgnoreCase("tchau")){
                System.out.println("a sair...");
                break;
            }
            System.out.println("Recebido : "+line);

        }
        input.close();
        output.close();
        socket.close();
    }
}
