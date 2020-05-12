package test;
import java.net.*;
import java.io.*;
// Aguarda comunicação no porto 6500,
// recebe mensagens e devolve-as
public class EchoServer {
    public static void main(String args[]) throws Exception {
//criar client na porta 6500
        ServerSocket server = new ServerSocket(6500);
        System.out.println ("servidor iniciado no porto 6500");
        Socket client = null;
//aguarda mensagens
        while(true) {
            client = server.accept();
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(client.getInputStream()));
            PrintStream output = new PrintStream(client.getOutputStream());
            String linha = br.readLine();
			System.out.println("Mensagem recebida: " + linha.toUpperCase());
            output.println(linha); // Echo input para output
//termina socket
            client.close();
        }
    }
}
//