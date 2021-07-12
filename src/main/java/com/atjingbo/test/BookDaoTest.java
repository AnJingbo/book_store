package com.atjingbo.test;

import com.atjingbo.dao.BookDao;
import com.atjingbo.dao.impl.BookDaoImpl;
import com.atjingbo.pojo.Book;
import org.junit.Test;

import java.math.BigDecimal;

public class BookDaoTest {
    BookDao bookDao = new BookDaoImpl();
    @Test
    public void addBook() {
        Book book = new Book(null, "Head First", "Sierra & Bates", new BigDecimal(48), 9998, 998, null);
        bookDao.addBook(book);
    }

    @Test
    public void deleteBookById() {
        bookDao.deleteBookById(3);
    }

    @Test
    public void updateBook() {
        Book book = new Book(2, "java成神之路", "gzw", new BigDecimal(2000), 9999, 111, null);
        bookDao.updateBook(book);
    }

    @Test
    public void queryBookById() {
        System.out.println(bookDao.queryBookById(2));
    }

    @Test
    public void queryBooks() {
        for(Book book : bookDao.queryBooks()){
            System.out.println(book);
        }
    }

    @Test
    public void queryForPageTotalCount() {
        System.out.println(bookDao.queryForPageTotalCount());
    }

    @Test
    public void queryForPageItems() {
        for(Book book : bookDao.queryForPageItems(0, 4)){
            System.out.println(book);
        }
    }
    @Test
    public void queryForPageTotalCountByPrice() {
        System.out.println(bookDao.queryForPageTotalCountByPrice(10, 50));
    }

    @Test
    public void queryForPageItemsByPrice() {
        for(Book book : bookDao.queryForPageItemsByPrice(0, 4, 10, 50)){
            System.out.println(book);
        }
    }
}