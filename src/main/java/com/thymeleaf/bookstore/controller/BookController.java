package com.thymeleaf.bookstore.controller;

import com.thymeleaf.bookstore.errors.EntityNotFoundException;
import com.thymeleaf.bookstore.model.Book;
import com.thymeleaf.bookstore.repository.BookRepository;
import com.thymeleaf.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping(value = {"/home", "/"})
    public String listAllBooks(Model model) {
        List<Book> books = bookService.findAllBooks();
        model.addAttribute("books", books);
        return "home";
    }

    @GetMapping("/bookImage")
    public void bookImage(HttpServletResponse response,
                          @RequestParam("bookId") Long bookId) throws IOException {
        Book book = bookService.findBookById(bookId);
        if (book != null) {
            response.setContentType("image/jpg");
            response.getOutputStream().write(book.getBookImage());
        }
        response.getOutputStream().close();
    }

    @GetMapping("/books/{bookId:\\d+}")
    public String getBook(@PathVariable Long bookId, Model model) throws EntityNotFoundException{
        Book book = bookService.findBookById(bookId);
        if(book == null) {
            throw new EntityNotFoundException("Book coudn't be found");
        }
        model.addAttribute("book", book);
        return "book";
    }

}
