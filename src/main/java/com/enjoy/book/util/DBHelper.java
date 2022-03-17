package com.enjoy.book.util;

import com.mchange.v2.c3p0.ComboPooledDataSource;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;


public class DBHelper {

    static ComboPooledDataSource ds = new ComboPooledDataSource("mysql-book");

    private static ThreadLocal<Connection>  tl = new ThreadLocal<>();
  
    static{

        try {
            Class.forName("com.mysql.jdbc.Driver");
  
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
          
        }
    }


    public static Connection getConnection() throws SQLException {

        Connection conn = tl.get();

        if(conn!=null){
            return conn;
        }

        conn = ds.getConnection();
        return conn;
    }

    public static void close(Connection conn, Statement st, ResultSet rs)throws SQLException{

        Connection tlConn = tl.get();

        if(conn== tlConn){
            return;
        }

		if(rs!=null){
			rs.close();
		}
		if(st!=null){
			st.close();
		}
		if(conn!=null){
			DBHelper.close(conn);
		}
       
    }
    public static void close(Connection conn)throws SQLException{

        Connection tlConn = tl.get();

        if(tlConn == conn){
            return;
        }

		if(conn!=null){

            conn.close();
		}
    }

    public static void beginTransaction() throws SQLException {

        Connection conn = tl.get();
        if(conn !=null){
           throw new RuntimeException("Transaction start") ;
        }

        conn = getConnection();

        tl.set(conn);

        conn.setAutoCommit(false);
    }

    public static void commitTransaction() throws SQLException {

        Connection conn = tl.get();

        if(conn == null){
            throw new RuntimeException("No transaction,submit fail");
        }

        conn.commit();

        tl.remove();

        DBHelper.close(conn);
    }

    public static void rollbackTransaction() throws SQLException {

        Connection conn = tl.get();
        if(conn == null){
            throw new RuntimeException("No transaction，roll back fail");
        }

        conn.rollback();

        tl.remove();

        DBHelper.close(conn);
    }
}
