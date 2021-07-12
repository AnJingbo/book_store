package com.atjingbo.test;

import com.atjingbo.pojo.Book;
import com.atjingbo.pojo.Page;
import com.atjingbo.service.BookService;
import com.atjingbo.service.impl.BookServiceImpl;
import org.junit.Test;

import java.math.BigDecimal;

public class BookServiceTest {
    BookService bookService = new BookServiceImpl();
    @Test
    public void addBook() {
        bookService.addBook(new Book(null, "算法4", "Robert & Bruce", new BigDecimal(78), 10000, 100, null));
    }

    @Test
    public void deleteBookById() {
        bookService.deleteBookById(8);
    }

    @Test
    public void updateBook() {
        bookService.updateBook(new Book(3, "什么是快乐星球？", "DY", new BigDecimal(98), 999, 1000, null));
    }

    @Test
    public void queryBookById() {
        System.out.println(bookService.queryBookById(3));
    }

    @Test
    public void queryBooks() {
        for(Book book : bookService.queryBooks()){
            System.out.println(book);
        }
    }
    @Test
    public void page(){
        System.out.println(bookService.page(1, 4));
    }

    @Test
    public void pageByPrice(){
        System.out.println(bookService.pageByPrice(1, 4, 10, 50));
    }
}