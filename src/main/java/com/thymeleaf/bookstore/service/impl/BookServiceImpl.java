package com.thymeleaf.bookstore.service.impl;

import com.thymeleaf.bookstore.form.BookForm;
import com.thymeleaf.bookstore.model.Book;
import com.thymeleaf.bookstore.repository.BookRepository;
import com.thymeleaf.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book findBookById(Long bookId) {
        return bookRepository.findOne(bookId);
    }

    @Override
    public Book updateBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public void deleteBook(Book book) {
        bookRepository.delete(book);
    }

    @Override
    public Book saveBook(BookForm bookForm) {
        Book book = new Book();

        if (bookForm.getBookImage() != null) {
            byte[] image = null;
            try {
                image = bookForm.getBookImage().getBytes();
            } catch (IOException e) {}
            if (image != null && image.length >0) {
                book.setBookImage(image);
                book.setBookImageContentType("image/jpeg");
            }
        }

        book = bookForm.toBook(book);
        return bookRepository.save(book);
    }
}
