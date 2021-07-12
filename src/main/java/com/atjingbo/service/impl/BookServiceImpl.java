package com.atjingbo.service.impl;

import com.atjingbo.dao.BookDao;
import com.atjingbo.dao.impl.BookDaoImpl;
import com.atjingbo.pojo.Book;
import com.atjingbo.pojo.Page;
import com.atjingbo.service.BookService;

import java.util.List;
public class BookServiceImpl implements BookService {
    BookDao bookDao = new BookDaoImpl();
    @Override
    public int addBook(Book book) {
        return bookDao.addBook(book);
    }

    @Override
    public int deleteBookById(Integer id) {
        return bookDao.deleteBookById(id);
    }

    @Override
    public int updateBook(Book book) {
        return bookDao.updateBook(book);
    }

    @Override
    public Book queryBookById(Integer id) {
        return bookDao.queryBookById(id);
    }

    @Override
    public List<Book> queryBooks() {
        return bookDao.queryBooks();
    }

    @Override
    public Page page(int pageNo, int pageSize) {
        Page<Book> page = new Page<>();

        page.setPageSize(pageSize); // 当前页的显示数量

        int pageTotalCount = bookDao.queryForPageTotalCount(); // 总记录条数
        page.setPageTotalCount(pageTotalCount);

        int pageTotal = pageTotalCount / pageSize; // 总页码
        if(pageTotalCount % pageSize > 0){
            pageTotal++;
        }
        page.setPageTotal(pageTotal);

        page.setPageNo(pageNo); // 当前页码
        pageNo = page.getPageNo();
        int begin = (pageNo - 1) * pageSize;
        List<Book> items = bookDao.queryForPageItems(begin, pageSize); // 当前页的数据
        page.setItems(items);

        return page;
    }

    @Override
    public Page pageByPrice(int pageNo, int pageSize, int min, int max) {
        Page<Book> page = new Page<>();

        page.setPageSize(pageSize); // 当前页的显示数量

        int pageTotalCount = bookDao.queryForPageTotalCountByPrice(min, max); // 总记录条数
        page.setPageTotalCount(pageTotalCount);

        int pageTotal = pageTotalCount / pageSize; // 总页码
        if(pageTotalCount % pageSize > 0){
            pageTotal++;
        }
        page.setPageTotal(pageTotal);

        page.setPageNo(pageNo); // 当前页码
        pageNo = page.getPageNo();
        int begin = (pageNo - 1) * pageSize;
        List<Book> items = bookDao.queryForPageItemsByPrice(begin, pageSize, min, max); // 当前页的数据
        page.setItems(items);

        return page;
    }
}
