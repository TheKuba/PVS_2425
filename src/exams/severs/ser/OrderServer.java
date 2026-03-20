package exams.severs.ser;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

public class OrderServer {

    private static final int PORT = 5001;
    private static final Map<String, Double> CATALOG = Map.of(
            "pen", 1.50,
            "notebook", 3.99,
            "stapler", 7.25,
            "mouse", 12.90
    );

    public static void main(String[] args) throws IOException {
        try (ServerSocket server = new ServerSocket(PORT)) {
            System.out.println("OrderServer listening on port " + PORT);

            while (true) {
                Socket socket = server.accept();
                System.out.println("Client connected: " + socket.getRemoteSocketAddress());
                new Thread(() -> handleClient(socket), "client-handler").start();
            }
        }
    }

    private static void handleClient(Socket socket) {
        try (socket;
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())
        ) {
            //bude Order
            Order order = (Order) in.readObject();
            OrderResponse response;

            if (!CATALOG.containsKey(order.itemName())) {
                response = new OrderResponse("ERROR: Produkt nenalezen!", 0);
            } else if (order.qty() < 1) {
                response = new OrderResponse("ERROR: Mnozstvi nesmi byt mensi nez 1!", 0);
            } else {
                response = new OrderResponse(order.itemName(), (int) (CATALOG.get(order.itemName()) * order.qty()));
            }

            out.writeObject(response);

        } catch (Exception e) {
            System.out.println("Client error: " + e.getMessage());
        }
    }
}
