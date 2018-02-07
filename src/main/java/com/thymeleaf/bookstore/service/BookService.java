package com.thymeleaf.bookstore.service;

import com.thymeleaf.bookstore.form.BookForm;
import com.thymeleaf.bookstore.model.Book;

import java.util.List;

public interface BookService {

    List<Book> findAllBooks();

    Book findBookById(Long bookId);

    Book updateBook(Book book);

    void deleteBook(Book book);

    Book saveBook(BookForm bookForm);
}
