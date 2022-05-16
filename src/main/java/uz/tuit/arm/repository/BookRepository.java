package uz.tuit.arm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import uz.tuit.arm.entity.Books;

import java.awt.print.Book;
import java.util.List;

@Service
public interface BookRepository extends JpaRepository<Books, Long> {
    List<Books> findAllByCategoryId(Long id);


}
