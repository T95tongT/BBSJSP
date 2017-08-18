package com.toto.db;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by Administrator on 2017/3/13 0013.
 */
public class DruidDB {
    static Connection  conn=null;
    static DataSource ds;
    static {

        try {
            Properties properties=new Properties();
            properties.load(DruidDB.class.getClassLoader().getResourceAsStream("druid.ini"));
            ds= DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public static Connection getConnection(){
        try {
            conn=ds.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}
