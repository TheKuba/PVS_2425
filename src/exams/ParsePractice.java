package exams;


import javax.sound.sampled.Line;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ParsePractice {
    public static void main(String[] args) throws IOException {
        List<InventoryItem> items = Files.lines(Paths.get("data/inventory.txt"))
                .map(line -> line.split(";"))
                .map(tokens -> tokens.length == 2 ? new InventoryItem(tokens[0], Integer.parseInt(tokens[1])) : new InventoryItem(tokens[0], Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2])))
                .toList();

        Map<String, InventoryItem> referMap = new HashMap<>();
        for (InventoryItem item : items){
            referMap.put(item.getName(), item);
        }

        List<String> orders  = Files.readAllLines(Paths.get("data/operations.txt"));

        for (String order : orders){
            //vezmi radek
            String[] lineData = order.split(";");
            String itemName = lineData[1];
            InventoryItem referItem = referMap.get(itemName);

            if (referItem != null){
                referItem.processOrder(lineData[0], Integer.parseInt(lineData[2]));
            } else {
                System.out.println("Item " + itemName + " not found");
            }
        }

        System.out.println("Score: " + orders.size() + " total");
        System.out.println((orders.size() - InventoryItem.invalidCounter) + " total");
        for (String itemName : referMap.keySet()){
            System.out.println(itemName + ": " + referMap.get(itemName).getQty());
        }
    }
}
class InventoryItem{
    String name;
    int qty;
    int maxQty;
    static int invalidCounter = 0;

    public InventoryItem(String name, int qty, int maxQty) {
        this.name = name;
        this.qty = qty;
        this.maxQty = maxQty;
    }

    public InventoryItem(String name, int qty) {
        this.name = name;
        this.qty = qty;
        this.maxQty = Integer.MAX_VALUE;
    }

    void processOrder(String type, int qty){
        if (qty < 0){
            System.out.println("Negative order number");
            invalidCounter++;
            return;
        }

        if (type.equals("ADD") && this.qty + qty > maxQty){
            System.out.println("Over the max qty");
            invalidCounter++;
            return;
        }

        if (type.equals("REMOVE") && this.qty - qty < 0){
            System.out.println("Cannot remove than stored");
            invalidCounter++;
            return;
        }

        if (type.equals("ADD")){
            this.qty += qty;
        }

        if (type.equals("REMOVE")){
            this.qty -= qty;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getMaxQty() {
        return maxQty;
    }

    public void setMaxQty(int maxQty) {
        this.maxQty = maxQty;
    }
}
