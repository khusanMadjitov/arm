package uz.tuit.arm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.tuit.arm.entity.Users;
import uz.tuit.arm.security.CurrentUser;
import uz.tuit.arm.service.BookService;

@RestController
@RequestMapping("/api/book")
public class BookController {
    @Autowired
    BookService bookService;

    @PostMapping("/add")
    public HttpEntity<?> add(@RequestParam Long userId, @RequestParam Long id) {
        return ResponseEntity.ok(bookService.bookBand(id, userId));
    }
}
