package exams.severs.ser;
import java.io.Serializable;

public record OrderResponse(String itemName, int price) implements Serializable {

}
