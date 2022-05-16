package uz.tuit.arm.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponseModel {
    private Status status;
    private Object data;
    
    public ApiResponseModel(Status status) {
        this.status = status;
    }
}
