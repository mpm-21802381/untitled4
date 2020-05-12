package test;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class EchoClientUdp {
    private DatagramSocket socket;
    private InetAddress address;

    private byte[] buf = new byte[256];

    public EchoClientUdp(String address) throws SocketException, UnknownHostException {
        socket = new DatagramSocket();
        this.address = InetAddress.getByName(address);
    }

    public static void main(String[] args) {
        String received = "";
        while (!received.equals("tchau")) {
            Scanner myObj = new Scanner(System.in);
            String msg;
            System.out.print(args[0] + ">");
            msg = myObj.next();
            try {
                EchoClientUdp client = new EchoClientUdp(args[0]);
                received = client.sendEcho(msg);
                System.out.println("Recebido: " + received);

            } catch (SocketException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String sendEcho(String msg) throws IOException {
        buf = msg.getBytes();
        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 4445);
        socket.send(packet);
        buf = new byte[256];
        packet = new DatagramPacket(buf, buf.length);
        socket.receive(packet);
        String s = new String(
                packet.getData(),
                packet.getOffset(),
                packet.getLength(),
                StandardCharsets.UTF_8
        );
        return s;
    }

    public void close() {
        socket.close();
    }
}