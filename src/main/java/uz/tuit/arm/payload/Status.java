package uz.tuit.arm.payload;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Status {
    private String message;
    private long code;
//    private Object data;

    public Status(String message, long code) {
        this.message = message;
        this.code = code;
    }
}
