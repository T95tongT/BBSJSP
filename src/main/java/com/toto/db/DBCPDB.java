package com.toto.db;

import org.apache.commons.dbcp.BasicDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by Administrator on 2017/3/19 0019.
 */
public class DBCPDB {
    static Properties properties=null;
    static DataSource ds=null;
    static {
        properties=new Properties();
        try {
            properties.load(DBCPDB.class.getClassLoader().getResourceAsStream("dbcp.ini"));
            ds= BasicDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    static Connection conn=null;
    public static Connection getConnection(){
        try {
            conn=ds.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return conn;
    }
}
