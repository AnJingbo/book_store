package com.atjingbo.service;

import com.atjingbo.pojo.Book;
import com.atjingbo.pojo.Page;

import java.util.List;

public interface BookService {
    public int addBook(Book book);

    public int deleteBookById(Integer id);

    public int updateBook(Book book);

    public Book queryBookById(Integer id);

    public List<Book> queryBooks();

    public Page page(int pageNo, int pageSize);

    public Page pageByPrice(int pageNo, int pageSize, int min, int max);
}
