package exams.severs.gui;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class TreasureServer {
    private static final int PORT = 5555;

    private final Set<PrintWriter> clientOutputs = Collections.synchronizedSet(new HashSet<>());
    private int tx;
    private int ty;
    private boolean gameStarted = false;
    private int[][] gameStatus = new int[7][7];

    public static void main(String[] args) throws IOException {
        new TreasureServer().start();
    }

    private void start() throws IOException {
        System.out.println("GridServer listening on port " + PORT);
        tx = generateTreasure();
        ty = generateTreasure();

        try (ServerSocket ss = new ServerSocket(PORT)) {
            while (true) {
                Socket s = ss.accept();
                System.out.println("Client connected: " + s.getRemoteSocketAddress());
                new Thread(() -> handleClient(s), "client-" + s.getPort()).start();
            }
        }
    }

    private void handleClient(Socket socket) {
        PrintWriter out = null;

        try (socket;
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true)) {

            out = pw;
            clientOutputs.add(out);



            String line;
            while ((line = in.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("/")) {
                    String[] parts = line.split("\\s+");
                    if (parts.length != 3) {
                        if (line.startsWith("/connect")){
                            out.println("/gamestarted " + gameStarted);
                            if (gameStarted) {
                                for (int i = 0; i < gameStatus.length; i++) {
                                    for (int j = 0; j < gameStatus[i].length; j++) {
                                        if (gameStatus[i][j] == 1) {
                                            out.println("/reveal empty " + i + " " + j);
                                        }
                                    }
                                }
                            }
                            continue;
                        }
                        out.println("ERROR BadFormat");
                        continue;
                    }
                    gameStarted = true;

                    int cx = Integer.parseInt(parts[1]);
                    int cy = Integer.parseInt(parts[2]);

                    if (line.startsWith("/dig")) {
                        if (cx == tx && cy == ty){
                            broadcast("/reveal treasure " + tx + " " + ty);
                            gameStatus = new int[7][7];
                            gameStarted = false;
                            tx = generateTreasure();
                            ty = generateTreasure();
                            broadcast("/restart");
                        } else {
                            broadcast("/reveal empty " + cx + " " + cy);
                            gameStatus[cx][cy] = 1;
                        }
                    }
                } else {
                    out.println("ERROR UnknownCommand");
                }
            }

        } catch (IOException e) {
            System.out.println("Client IO error: " + e.getMessage());
        } finally {
            if (out != null) clientOutputs.remove(out);
            System.out.println("Client disconnected: " + socket.getRemoteSocketAddress());
        }
    }

    private void broadcast(String msg) {
        synchronized (clientOutputs) {
            for (PrintWriter out : clientOutputs) {
                out.println(msg);
            }
        }
    }

    private int generateTreasure(){
        Random r = new Random();
        return r.nextInt(0,7);
    }
}
