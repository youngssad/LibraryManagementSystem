package com.enjoy.book.dao;

import com.enjoy.book.bean.Book;
import com.enjoy.book.util.DBHelper;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class BookDao {
    QueryRunner runner = new QueryRunner();


    public List<Book> getBooksByTypeId(long typeId) throws SQLException {
        Connection conn = DBHelper.getConnection();
        String sql = "select * from book where typeId = ?";
        List<Book> books = runner.query(conn,sql,new BeanListHandler<Book>(Book.class),typeId);
        DBHelper.close(conn);
        return books;

    }

    public int add(long typeId,String name,double price,String desc,String pic,
                   String publish,String author,long stock,String address) throws SQLException {
        Connection conn  = DBHelper.getConnection();
        String sql="insert into book(typeId,`name`,price,`desc`,pic,publish,author,stock,address) " +
                "values(?,?,?,?,?,?,?,?,?)";
        int count = runner.update(conn,sql,typeId,name,price,desc,pic,publish,author,stock,address);
        DBHelper.close(conn);
        return count;

    }


    public int modify(long id,long typeId,String name,double price,String desc,String pic,
                      String publish,String author,long stock,String address) throws SQLException {
        Connection conn  = DBHelper.getConnection();
        String sql="update book set typeId= ?,`name` = ?,price =?,`desc`=?,pic = ?,publish = ?,author =?,stock=?,address = ? where id= ? ";
        int count = runner.update(conn,sql,typeId,name,price,desc,pic,publish,author,stock,address,id);
        DBHelper.close(conn);
        return count;

    }


    public int modify(long id,int amount) throws SQLException {
        Connection conn  = DBHelper.getConnection();
        String sql="update book set stock=stock + ? where id= ? ";
        int count = runner.update(conn,sql,amount,id);
        DBHelper.close(conn);
        return count;

    }
    public int remove(long id) throws SQLException {
        Connection conn  = DBHelper.getConnection();
        String sql="delete from book where id=? ";
        int count = runner.update(conn,sql,id);
        DBHelper.close(conn);
        return count;
    }


    public List<Book>  getByPage(int pageIndex,int pageSize) throws SQLException {
        Connection conn  = DBHelper.getConnection();
        String sql = "select * from book limit ?,?";
        int offset = (pageIndex-1)*pageSize;
        List<Book> books = runner.query(conn,sql,new BeanListHandler<Book>(Book.class),offset,pageSize);
        DBHelper.close(conn);
        return  books;
    }

    public Book getById(long id) throws SQLException {
        Connection conn  = DBHelper.getConnection();
        String sql="select * from book where id=?";
        Book book = runner.query(conn,sql,new BeanHandler<Book>(Book.class),id);
        DBHelper.close(conn);
        return book;
    }


    public int  getCount() throws SQLException {
        Connection conn  = DBHelper.getConnection();
        String sql = "select count(id)from book";
        Number data = runner.query(conn,sql,new ScalarHandler<>());
        int count = data.intValue();
        DBHelper.close(conn);
        return count;
    }

    public Book getByName(String bookName) throws SQLException {
        Connection conn  = DBHelper.getConnection();
        String sql="select * from book where name=?";
        Book book = runner.query(conn,sql,new BeanHandler<Book>(Book.class),bookName);
        DBHelper.close(conn);
        return book;
    }
}
