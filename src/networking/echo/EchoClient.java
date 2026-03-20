package networking.echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class EchoClient {
    public static void main(String[] args) {
        int port = 11111;
        String host = "127.0.0.1";

        System.out.println("Pripojuji se k " + host + ":" + port);

        try (Socket socket = new Socket(host, port)){
            PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            System.out.println("Pripojeno k serveru");
            Scanner sc = new Scanner(System.in);
            while (true){
                System.out.println("Zadej zpravu, kterou chces poslat (quit pro ukonceni)");

                String message = sc.nextLine();
                if (message.equalsIgnoreCase("quit")) {
                    break;
                }

                //zpravu ze scanneru (klavesnice/konzole) nacti a posli do outputu (tj smerem server)
                pw.println(message);

                String response = input.readLine();

                if (response == null) {
                    System.out.println("Server ukoncil pripojeni");
                    break;
                }
                System.out.println("Echo od serveru: " + response);
            }


        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
