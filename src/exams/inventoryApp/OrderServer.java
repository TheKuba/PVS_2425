package exams.inventoryApp;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class OrderServer {

    private static final int PORT = 5001;
    private static Map<String, Integer> items = new HashMap<>();

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

            Object obj = in.readObject();
            System.out.println("Objekt prijat");

            if (obj instanceof Order order) {
                switch (order.type) {
                    case PUT: putItem(order.itemName, order.qty); break;
                    case TAKE: if (!takeItem(order.itemName, order.qty)) {
                        System.out.println("Nedostatek tohoto predmetu na sklade");
                    } break;
                }
            } else {
                StateRequest request = (StateRequest) obj;
                out.writeObject(new StateResponse(request.requestId, items));
            }

        } catch (Exception e) {
            System.out.println("Client error: " + e.getMessage());
        }
    }

    static private void putItem (String itemName, int qty) {
        items.put(itemName, qty);
    }

    static private boolean takeItem (String itemName, int qty) {
        if (items.containsKey(itemName)) {
            if (items.get(itemName) <= qty) {
                items.remove(itemName, qty);
                return true;
            } else {
                return false;
            }
        }
        else  {
            return false;
        }
    }
}
