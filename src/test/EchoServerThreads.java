package test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.lang.reflect.Array;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Random;

public class EchoServerThreads {
    public static void main(String args[]) throws Exception {
//criar client na porta 6500
        ServerSocket server = new ServerSocket(6500);
        System.out.println("servidor iniciado no porto 6500");
        Socket client = null;
        while (true) {
            client = server.accept();
            System.out.println("nova conexao...");
            Thread t = new Thread(new EchoClientThread(client));
            t.start();
            System.out.println("oi");
        }
    }//fim main

    public static class EchoClientThread implements Runnable {
        private Socket s;

        String[] frases = {"ola!", "boas!", "viva", "então", "tudo fixe", "mequie"};
        Random rand = new Random();
        public EchoClientThread(Socket socket) {
            this.s = socket;
        }

        public void run() {
            String threadName = Thread.currentThread().getName();
            String ipCliente = s.getInetAddress().toString();//IP do cliente
            System.out.println("conectado com " + ipCliente);
            try {
                BufferedReader input = new BufferedReader(
                        new InputStreamReader(s.getInputStream()));
                PrintStream output = new PrintStream(s.getOutputStream(), true);
                String line;
                while ((line = input.readLine()) != null) {
                    System.out.println(ipCliente + ": " + threadName + ": " + line);
                    if(line.equalsIgnoreCase("horas")){
                        output.println(java.time.LocalTime.now());
                    }
                    if(line.equalsIgnoreCase("lista")){
                        output.println(Arrays.toString(frases));
                    }
                    if(line.equalsIgnoreCase("frase")){
                        int rand_int = rand.nextInt(6);
                        output.println(frases[rand_int]);
                    }
                    if (line.equalsIgnoreCase("tchau")) {
                        break;
                    }
                }
                output.println("até logo");
                input.close();
                output.close();
                s.close();
            } catch (Exception e) {
                System.err.println("Erro: " + e);
            }
            System.out.println("Cliente " + ipCliente + " descontectado!");
        }
    }
}
