package exams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class JobQueueClient {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 11111;

        try (Socket socket = new Socket(host, port);
             BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
             BufferedReader serverInput = new BufferedReader(
                     new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
        ) {
            System.out.println("Pripojeno " + host + ":" + port);
            System.out.println("Commands: push <desc>, list, quit");

            while (true) {
                System.out.print(">> ");
                String command = userInput.readLine();
                if (command == null) {
                    // Uzivatel ukonci konzoli
                    break;
                }
                command = command.trim();

                out.println(command); // posli prikaz na server

                // Pripad pro ukonceni
                if (command.equalsIgnoreCase("quit")) {
                    break;
                }

                // Odpoved serveru
                String response = serverInput.readLine();
                if (response == null) {
                    System.out.println("Server closed the connection.");
                    break;
                }
                System.out.println(response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Client stopped.");
    }
}
