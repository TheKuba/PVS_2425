package exams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class MathServer {
    public static void main(String[] args) {
        int port = 54321;
        System.out.println("Running server on port " + port);
        String[] opertaions = {"Addition", "Subtraction", "Multiplication"};
        int num1, num2, op, result;

        try (ServerSocket serverSocket = new ServerSocket(port)){
            System.out.println("Server is listening...");

            try(Socket client = serverSocket.accept()){
                System.out.println(client.getInetAddress() + " has connected");

                BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));

                PrintWriter pw = new PrintWriter(client.getOutputStream(), true);

                num1 = (int) (Math.random()*(-100)+100);
                num2 = (int) (Math.random()*(-100)+100);
                op = (int) (Math.random()*3);
                switch (op){
                    case 0 -> result = num1 + num2;
                    case 1 -> result = num1 - num2;
                    default -> result = num1 * num2;
                }

                pw.println("/" + opertaions[op] + ";" + num1 + ";" + num2);

                String line;
                while ((line = reader.readLine()) != null){
                    System.out.println(line);
                    if (line.startsWith("/result;")){
                        String[] params = line.split(";");
                        try {
                            if (Integer.parseInt(params[1]) == result){
                                pw.println("Correct");
                            } else {
                                pw.println("Incorrect");
                            }
                        } catch (NumberFormatException e){
                            System.out.println("kokot");
                        }
                    } else {
                        System.out.println("Wrong format");
                    }

                }
            }
        } catch (RuntimeException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
