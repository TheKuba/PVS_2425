package networking.ser;

import java.io.Serializable;

public class ResultResponse implements Serializable {
    String text;
    ResultType type;

    void logic() {
        if (type == ResultType.OK) {
            text = new Result(0, "B").toString();
        } else {
            text = "Error: Bad numbers";
        }
    }

}
