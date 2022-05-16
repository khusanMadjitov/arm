package uz.tuit.arm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.tuit.arm.entity.Permission;
import uz.tuit.arm.entity.enums.Permissions;

import java.util.List;

public interface PermissionRepository extends JpaRepository<Permission,Long> {

    List<Permission> findAllByName(Permissions name);
}
