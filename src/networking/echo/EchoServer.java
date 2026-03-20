package networking.echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
    public static void main(String[] args) {
        int port = 11111;
        System.out.println("Spousti se server na portu " + port);

        try (ServerSocket serverSocket = new ServerSocket(port)){
            System.out.println("Server posloucha...");

            try(Socket client = serverSocket.accept()){
                System.out.println("Pripojil se " + client.getInetAddress());

                BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));

                PrintWriter pw = new PrintWriter(client.getOutputStream(), true);

                String line;
                while ((line = reader.readLine()) != null){
                    System.out.println("Od klienta prislo: " + line);
                    pw.println("ECHO:" + line);
                }
            }
        } catch (RuntimeException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
