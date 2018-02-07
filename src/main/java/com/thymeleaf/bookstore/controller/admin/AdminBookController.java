package com.thymeleaf.bookstore.controller.admin;

import com.thymeleaf.bookstore.form.BookForm;
import com.thymeleaf.bookstore.model.Book;
import com.thymeleaf.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("admin")
public class AdminBookController {

    private final BookService bookService;

    @Autowired
    public AdminBookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("books")
    public String getBooks(Model model) {
        List<Book> books = bookService.findAllBooks();
        model.addAttribute("books", books);
        return "/admin/books";
    }

    @GetMapping("/books/add")
    public String addBookPage(Model model) {
        BookForm bookForm = new BookForm();
        model.addAttribute("bookForm", bookForm);
        return "/admin/addBook";
    }

    @PostMapping("/books/add")
    public String addBook(@ModelAttribute BookForm bookForm, Model model) {
        Book savedBook = bookService.saveBook(bookForm);
        model.addAttribute("successMessage", "Book created with ID " + savedBook.getBookId());
        return addBookPage(model);
    }

    @GetMapping("/books/edit/{bookId}")
    public String editBookPage(@PathVariable Long bookId, Model model) {
        Book book = bookService.findBookById(bookId);
        model.addAttribute("bookForm", new BookForm(book));
        return "/admin/editBook";
    }

    @PostMapping("/books/edit")
    public String editBook(@ModelAttribute BookForm bookForm, Model model) {
        Book savedBook = bookService.saveBook(bookForm);
        model.addAttribute("successMessage", "Book updated with ID " + savedBook.getBookId());
        return "admin/editBook";
    }

    @GetMapping("/books/delete/{bookId}")
    public String deleteBook(@PathVariable Long bookId, Model model) {
        Book book = bookService.findBookById(bookId);
        bookService.deleteBook(book);
        model.addAttribute("successMessage", "Book with id " + bookId + " deleted successfully");
        return getBooks(model);
    }

    @GetMapping("/books/view/{bookId}")
    public String viewBook(@PathVariable Long bookId, Model model) {
        Book book = bookService.findBookById(bookId);
        model.addAttribute("book", book);
        return "/admin/viewBook";
    }
}
