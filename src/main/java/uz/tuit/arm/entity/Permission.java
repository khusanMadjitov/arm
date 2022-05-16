package uz.tuit.arm.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import uz.tuit.arm.entity.enums.Permissions;

import javax.persistence.*;

@Entity
@Data
public class Permission implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Permissions name;

    @Override
    public String getAuthority() {
        return name.name();
    }

}
