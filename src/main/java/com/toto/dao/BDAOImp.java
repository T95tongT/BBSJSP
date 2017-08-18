package com.toto.dao;

import com.toto.db.DruidDB;
import com.toto.vo.BBSUser;

import java.io.File;
import java.io.FileInputStream;
import java.sql.*;

/**
 * Created by Administrator on 2017/3/21 0021.
 */
public class BDAOImp implements BDAOIn {
    private Connection conn= DruidDB.getConnection();
    @Override
    public BBSUser login(BBSUser user) {
        String sql="select * from bbs where username=? and password=?";
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        BBSUser u=null;
        try {
            pstmt=conn.prepareStatement(sql);
            pstmt.setString(1,user.getUsername());
            pstmt.setString(2,user.getPassword());

            rs=pstmt.executeQuery();
            if(rs.next()){
                u=new BBSUser();
                u.setUsername(rs.getString("username"));
                u.setPassword(rs.getString("password"));
                u.setId(rs.getInt("id"));
            }


        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                rs.close();
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }


        return u;
    }

    @Override
    public boolean register(BBSUser user) {
        String sql="insert into bbs (username,password,pic) values(?,?,?)";
        PreparedStatement pstmt=null;
        boolean flag=false;
        try {
            pstmt=conn.prepareStatement(sql);

            pstmt.setString(1,user.getUsername());
            pstmt.setString(2,user.getPassword());
            //blob

            File file= new File(user.getPic());//头像文件
            FileInputStream fis=new FileInputStream(file);

            pstmt.setBinaryStream(3,fis,fis.available());//存储blob
            flag=pstmt.executeUpdate()>0;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(pstmt!=null){
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        }
        return flag;

    }

    @Override
    public byte[] getById(int id) {
        PreparedStatement pstmt=null;
        ResultSet rs=null;

        String sql="select pic from bbs where id=?";
        byte[] buffer=null;

        try {
            pstmt=conn.prepareStatement(sql);
            pstmt.setInt(1,id);
            rs=pstmt.executeQuery();

            if(rs.next()){
                //blob 读
                Blob blob=rs.getBlob("pic");

                buffer=blob.getBytes(1,(int)blob.length());

            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return buffer;
    }
}
