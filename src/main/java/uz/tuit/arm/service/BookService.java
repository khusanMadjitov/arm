package uz.tuit.arm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import uz.tuit.arm.entity.BookBand;
import uz.tuit.arm.entity.Books;
import uz.tuit.arm.entity.Category;
import uz.tuit.arm.entity.Users;
import uz.tuit.arm.payload.ApiResponseModel;
import uz.tuit.arm.payload.ReqBooks;
import uz.tuit.arm.payload.Status;
import uz.tuit.arm.repository.BookBandRepository;
import uz.tuit.arm.repository.BookRepository;
import uz.tuit.arm.repository.CategoryRepository;
import uz.tuit.arm.repository.UserRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BookService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    BookBandRepository bookBandRepository;
    @Autowired
    JdbcTemplate jdbcTemplate;

    public ApiResponseModel add(ReqBooks reqBooks) {
        ApiResponseModel apiResponseModel = new ApiResponseModel();
        Status status = new Status();
        try {
            Books books = new Books();
            books.setAuthor(reqBooks.getAuthor());
            books.setName(reqBooks.getName());
            books.setType(reqBooks.getType());
            books.setCount(reqBooks.getCount());
            Optional<Category> categoryopt = categoryRepository.findById(reqBooks.getCategoryId());
            categoryopt.ifPresent(books::setCategory);
            bookRepository.save(books);
            apiResponseModel.setData(books);
            status.setCode(200);
            status.setMessage("success");
        } catch (Exception e) {
            status.setCode(500);
            status.setMessage("error");
        }
        apiResponseModel.setStatus(status);
        return apiResponseModel;
    }

    public ApiResponseModel list(Long id) {
        ApiResponseModel apiResponseModel = new ApiResponseModel();
        Status status = new Status();
        try {
            List<Books> books = bookRepository.findAllByCategoryId(id);
            apiResponseModel.setData(books);
            status.setCode(200);
            status.setMessage("success");
        } catch (Exception e) {
            status.setCode(500);
            status.setMessage("error");
        }
        apiResponseModel.setStatus(status);
        return apiResponseModel;
    }

    public ApiResponseModel bookBand(Long id, Long userId) {
        ApiResponseModel apiResponseModel = new ApiResponseModel();
        Status status = new Status();
        try {
            Optional<Books> booksOptional = bookRepository.findById(id);
            if (booksOptional.isPresent()) {
                Optional<Users> users = userRepository.findById(userId);
                if (users.isPresent()) {
                    List<Map<String, Object>> maps =
                            jdbcTemplate.queryForList("select count(*) as band_count\n" +
                                    "from book_band bb\n" +
                                    "where books_id = ?", id);
                    Long countBooks = bookRepository.findById(id).get().getCount();
                    Long countBands = 0L;
                    if (maps.size() > 0) {
                        countBands = Long.valueOf(String.valueOf(maps.get(0).get("band_count")));
                    }

                    if (countBooks>countBands) {
                        BookBand bookBand = new BookBand();
                        bookBand.setBooks(booksOptional.get());
                        bookBand.setUsers(users.get());
                        status.setCode(200);
                        status.setMessage("success");
                    }else {
                        status.setCode(200);
                        status.setMessage("success");
                    }

                } else {
                    status.setCode(223);
                    status.setMessage("user is not found");
                }
            } else {
                status.setCode(222);
                status.setMessage("book is not found!");
            }
            status.setCode(200);
            status.setMessage("success");
        } catch (Exception e) {
            status.setCode(500);
            status.setMessage("error");
        }
        apiResponseModel.setStatus(status);
        return apiResponseModel;
    }

    public ApiResponseModel infoBand(Long bookId) {
        ApiResponseModel apiResponseModel = new ApiResponseModel();
        Status status = new Status();
        try {
            Optional<Books> optionalBooks = bookRepository.findById(bookId);
            if (optionalBooks.isPresent()) {
                List<Map<String, Object>> maps =
                        jdbcTemplate.queryForList("select count(*) as band_count\n" +
                                "from book_band bb\n" +
                                "where books_id = ?", bookId);
                Long countBooks = bookRepository.findById(bookId).get().getCount();
                Long countBands = 0L;
                if (maps.size() > 0) {
                    countBands = Long.valueOf(String.valueOf(maps.get(0).get("band_count")));
                }
                Map<String, Object> map = new HashMap<>();
                map.put("count_books", countBooks);
                map.put("count_bands", countBands);
                apiResponseModel.setData(map);
                status.setCode(200);
                status.setMessage("success");

            } else {
                status.setCode(222);
                status.setMessage("book is not found!");
            }
        } catch (Exception e) {
            status.setCode(500);
            status.setMessage("error");
        }
        apiResponseModel.setStatus(status);
        return apiResponseModel;
    }
}
