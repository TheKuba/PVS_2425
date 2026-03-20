package networking.echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiEchoServer {
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
} class EchoClientHandler extends Thread{
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
                System.out.println(clientSocket.getInetAddress()+":"
                        + clientSocket.getPort() + " posila: " + received);

                if ("quit".equalsIgnoreCase(received)){
                    System.out.println(clientSocket.getInetAddress()+":"
                            + clientSocket.getPort() + " se odpojuje.");
                    break;
                }
                pw.println("Echo: " + received);
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
