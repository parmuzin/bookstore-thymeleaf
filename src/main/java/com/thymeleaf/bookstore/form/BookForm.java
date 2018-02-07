package com.thymeleaf.bookstore.form;

import com.thymeleaf.bookstore.model.Book;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

public class BookForm {
    private Long bookId;

    private String bookTitle;

    private String bookAuthor;

    private String bookYear;

    private Integer bookPages;

    private String bookIsbn;

    private BigDecimal bookPrice;

    private String bookDescription;

    private MultipartFile bookImage;

    public Long getBookId() {
        return bookId;
    }

    public Book toBook(Book book) {
        book.setBookId(bookId);
        book.setBookTitle(bookTitle);
        book.setBookAuthor(bookAuthor);
        book.setBookPages(bookPages);
        book.setBookIsbn(bookIsbn);
        book.setBookPrice(bookPrice);
        book.setBookDescription(bookDescription);

        return book;
    }

    public BookForm(Book book) {
        this.bookId = book.getBookId();
        this.bookTitle = book.getBookTitle();
        this.bookAuthor = book.getBookAuthor();
        this.bookPages = book.getBookPages();
        this.bookIsbn = book.getBookIsbn();
        this.bookPrice = book.getBookPrice();
        this.bookDescription = book.getBookDescription();
    }

    public BookForm() {
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public String getBookYear() {
        return bookYear;
    }

    public void setBookYear(String bookYear) {
        this.bookYear = bookYear;
    }

    public Integer getBookPages() {
        return bookPages;
    }

    public void setBookPages(Integer bookPages) {
        this.bookPages = bookPages;
    }

    public String getBookIsbn() {
        return bookIsbn;
    }

    public void setBookIsbn(String bookIsbn) {
        this.bookIsbn = bookIsbn;
    }

    public BigDecimal getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(BigDecimal bookPrice) {
        this.bookPrice = bookPrice;
    }

    public String getBookDescription() {
        return bookDescription;
    }

    public void setBookDescription(String bookDescription) {
        this.bookDescription = bookDescription;
    }

    public MultipartFile getBookImage() {
        return bookImage;
    }

    public void setBookImage(MultipartFile bookImage) {
        this.bookImage = bookImage;
    }
}
