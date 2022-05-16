package uz.tuit.arm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.tuit.arm.entity.Users;

import java.util.List;

public interface UserRepository extends JpaRepository<Users, Long> {
    boolean existsByPhoneNumber(String phoneNumber);

    Users findUsersByPhoneNumber(String phoneNumber);

    List<Users> findAllByActive(Boolean aBoolean);
}
