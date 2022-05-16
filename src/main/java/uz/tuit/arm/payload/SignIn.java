package uz.tuit.arm.payload;

import lombok.Data;

@Data
public class SignIn {
    private String phoneNumber;
    private String password;
}
