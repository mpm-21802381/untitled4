package test;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Random;

public class EchoServerUdp extends Thread {
    private DatagramSocket socket;
    private boolean running;
    private byte[] buf = new byte[256];
    String[] frases = {"ola!", "boas!", "viva", "então", "tudo fixe", "mequie"};
    Random rand = new Random();
    int randomNumber;

    public EchoServerUdp() throws SocketException {
        socket = new DatagramSocket(4445);
    }

    public void run() {
        running = true;

        while (running) {
            try {
                buf = new byte[256];
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                InetAddress address = packet.getAddress();
                int port = packet.getPort();
                String s = new String(
                        packet.getData(),
                        packet.getOffset(),
                        packet.getLength(),
                        StandardCharsets.UTF_8
                );
                System.out.println("Recebido [ " + packet.getAddress().getHostAddress() + " ] : " + s);
                buf = new byte[256];
                switch (s){
                    case "horas":
                        String time = java.time.LocalTime.now().toString();
                        buf = time.getBytes();
                        break;
                    case "lista":
                        String all = Arrays.toString(frases);
                        buf = all.getBytes();
                        break;
                    case "frase":
                        int rand_int = rand.nextInt(6);
                        buf = frases[rand_int].getBytes();
                        break;
                    case "tchau":
                        buf = "tchau".getBytes();
                        break;
                    default:
                        buf = "invalido".getBytes();
                }
                packet = new DatagramPacket(buf, buf.length, address, port);
                socket.send(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        socket.close();
    }

    public static void main(String[] args) throws SocketException {
        EchoServerUdp serverUdp = new EchoServerUdp();
        serverUdp.run();
    }
}