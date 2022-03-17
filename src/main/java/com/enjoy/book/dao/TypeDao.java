package com.enjoy.book.dao;

import com.enjoy.book.util.DBHelper;
import org.apache.commons.dbutils.QueryRunner;
import com.enjoy.book.bean.*;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class TypeDao {

    QueryRunner runner = new QueryRunner();


    public int add(String name,long parentId) throws SQLException {
        Connection conn = DBHelper.getConnection();
        String sql="insert into type values(null,?,?)";
        int count = runner.update(conn,sql,name,parentId);
        DBHelper.close(conn);
        return count;
    }


    public List<Type> getAll() throws SQLException {
        Connection conn = DBHelper.getConnection();
        String sql="select id,name,parentId from type ";
        List<Type> types = runner.query(conn,sql,new BeanListHandler<Type>(Type.class));
        DBHelper.close(conn);
        return types;
    }


    public Type getById(long typeId) throws SQLException {
        Connection conn = DBHelper.getConnection();
        String sql="select id,name,parentId from type where id=?";
        Type type=runner.query(conn,sql,new BeanHandler<Type>(Type.class),typeId);
        DBHelper.close(conn);
        return type;
    }

    public int modify(long id,String name,long parentId) throws SQLException {
        Connection conn = DBHelper.getConnection();
        String sql="update type set name=?,parentId=? where id=?";
        int count = runner.update(conn,sql,name,parentId,id);
        DBHelper.close(conn);
        return count;
    }

    public int remove(long id) throws SQLException {
        Connection conn = DBHelper.getConnection();
        String sql="delete from type where id=?";
        int count = runner.update(conn,sql,id);
        DBHelper.close(conn);
        return count;
    }
}
