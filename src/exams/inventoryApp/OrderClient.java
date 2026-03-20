package exams.inventoryApp;

import networking.ser.Point;
import networking.ser.Result;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class OrderClient {
    public static void main(String[] args) {
        try (
                Socket socket = new Socket("localhost", 5001);
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                Scanner sc = new Scanner(System.in);
        ) {
            int idCounter = 1000;
            while (true) {

                OrderType orderType;
                String[] what;
                System.out.println("chcete zjistit state?");
                System.out.println("Ano: 1");
                System.out.println("Ne: 2");

                switch (sc.nextInt()){
                    case 1:
                        out.writeObject(new StateRequest(idCounter));
                        out.flush();
                        StateResponse res = (StateResponse) in.readObject();
                        System.out.println(res.items.toString());
                        break;
                    case 2:
                        System.out.println("chcete pridat nebo odebrat?");
                        System.out.println("pridat: 1");
                        System.out.println("odebrat: 2");
                        if (sc.nextInt() == 1){
                            orderType = OrderType.PUT;
                        } else{
                            orderType = OrderType.TAKE;
                        }
                        sc.nextLine();
//                    System.out.println("napis co chces");
//                    what = sc.nextLine();
//                    System.out.println("kolik chces");
//                    amount = sc.nextInt();
                        System.out.println("co a kolik");
                        String input = sc.nextLine();
                        what = input.split(",");
                        out.writeObject(new Order(idCounter, orderType, what[0], Integer.parseInt(what[1])));
                        out.flush();
                        break;
                }

                idCounter++;



            }
        } catch (IOException | ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
    }
}