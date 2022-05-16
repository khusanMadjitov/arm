package uz.tuit.arm.payload;

import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;

@Data
public class SignUp {
    private String firstName;
    private String lastName;
    private String middleName;
    private String password;
    private String phoneNumber;
    private boolean active = true;
    @Enumerated(EnumType.STRING)
    private List<Long> permissions;

}