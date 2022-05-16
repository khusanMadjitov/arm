package uz.tuit.arm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.tuit.arm.entity.BookBand;

public interface BookBandRepository extends JpaRepository<BookBand, Long> {
}
