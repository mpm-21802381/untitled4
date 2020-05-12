package test;
import java.net.*;
import java.io.*;
// Connectar ao porto 6500 de um servidor especifico,
// envia uma mensagem e imprime resultado,

public class EchoClient {
    // usage: java EchoClient <servidor> <mensagem>
    public static void main(String args[]) throws Exception {
        Socket client = new Socket(args[0], 6500);
        BufferedReader input = new BufferedReader(
                new InputStreamReader(client.getInputStream()));
        PrintStream output = new PrintStream(client.getOutputStream());
        output.println(args[1]); // escreve mensagem na socket
// imprime resposta do servidor
        System.out.println("Recebido : " + input.readLine().toUpperCase());
// termina socket
        client.close();
    }
}
