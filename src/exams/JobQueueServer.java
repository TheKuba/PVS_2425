package exams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JobQueueServer {
    public static List<String> jobList = new ArrayList<>();
    public static void main(String[] args) {
        int port = 11111;

        try(ServerSocket serverSocket = new ServerSocket(port)){

            while (true){
                Socket incomingClient = serverSocket.accept();
                System.out.println("Pripojil se novy klient: " + incomingClient.getInetAddress() + ":" + incomingClient.getPort());

                EchoClientHandler handler = new EchoClientHandler(incomingClient);
                handler.start();
            }

        } catch (IOException e ){
            System.out.println("Chyba na serveru: " + e.getMessage());
        }
    }

    public static void addToList(String toAdd){
        jobList.add(toAdd);
    }
    public static String printList() {
        return jobList.toString();
    }
}

class EchoClientHandler extends Thread{
    private Socket clientSocket;

    EchoClientHandler(Socket clientSocket){
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try(BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))){
            PrintWriter pw = new PrintWriter(clientSocket.getOutputStream(), true);

            String received;

            while ((received = br.readLine()) != null){
                received.trim();
                if (received.equalsIgnoreCase("quit")) {
                    System.out.println(clientSocket.getInetAddress() + ":" + clientSocket.getPort() + " se odpojuje.");
                    break;
                } else if (received.contains("push")) {
                    String[] message = received.split(" ", 2);
                    if (Arrays.stream(message).count() > 1) {
                        if (!message[1].isEmpty())
                            JobQueueServer.addToList(message[1]);
                        pw.println("Added: " + message[1]);
                        System.out.println("Added: " + message[1]);
                    } else {
                        pw.println("Chybi zprava");
                    }
                } else if (received.equalsIgnoreCase("list")) {
                    pw.println(JobQueueServer.printList());
                } else {
                    pw.println("Neznamy prikaz");
                }
            }

        } catch (IOException e){
            System.out.println("Client-side error: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.out.println("Client-side error: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
