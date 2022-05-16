package uz.tuit.arm.payload;

import lombok.Data;

@Data
public class ReqBooks {
    private String name;
    private String author;
    private Boolean type;
    private Long categoryId;
    private Long count;
}
