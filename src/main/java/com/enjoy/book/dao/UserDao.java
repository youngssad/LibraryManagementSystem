package com.enjoy.book.dao;

import com.enjoy.book.bean.User;
import com.enjoy.book.util.DBHelper;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 用户表的数据操作对象
 */
public class UserDao {
    QueryRunner runner = new QueryRunner();
    public User getUser(String name, String pwd) throws SQLException {

        Connection  conn = DBHelper.getConnection();

        String sql="select * from user where name=? and pwd=? and state = 1";

        User user = runner.query(conn,sql,new BeanHandler<User>(User.class),name,pwd);

        DBHelper.close(conn);

        return user;
    }

    public int modifyPwd(long id,String pwd) throws SQLException {
        String sql="update  user set pwd = ? where id=?";
        Connection conn = DBHelper.getConnection();
        int count = runner.update(conn,sql,pwd,id);
        DBHelper.close(conn);
        return count;
    }
}
