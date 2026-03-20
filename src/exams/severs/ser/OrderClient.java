package exams.severs.ser;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class OrderClient {
    private static final int PORT = 5001;
    private static final String HOST = "localhost";

    public static void main(String[] args) {
        try (Socket socket = new Socket(HOST, PORT);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
             Scanner sc = new Scanner(System.in);
        ) {
            System.out.println("Pripojeno " + HOST + ":" + PORT);

            System.out.println("Zadej produkt:");
            String item = sc.nextLine();
            System.out.println("Zadej mnozstvi:");
            int amount = sc.nextInt();

            out.writeObject(new Order(item, amount));
            out.flush();

            OrderResponse response = (OrderResponse) in.readObject();
            System.out.println(response);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
