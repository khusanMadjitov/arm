package uz.tuit.arm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.tuit.arm.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
