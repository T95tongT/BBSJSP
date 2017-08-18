package com.toto.dao;

import com.toto.db.DBCPDB;
import com.toto.vo.Article;
import com.toto.vo.BBSUser;
import com.toto.vo.MyPage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/21 0021.
 */
public class ADAOImp implements AriDAOIn {
    private Connection conn= DBCPDB.getConnection();
    @Override
    public MyPage queryall(int rootid, int curpage, int userid) {
        MyPage mp=new MyPage();
        List <Article> list=new ArrayList<>();
        CallableStatement cs=null;
        ResultSet rs=null;
        String sql="call mypage(?,?,?,?,?)";
        try {
            cs=conn.prepareCall(sql);
            mp.setCurpage(curpage);
            cs.setInt(1,curpage);
            cs.setInt(2,userid);
            cs.registerOutParameter(3, Types.INTEGER);
            cs.registerOutParameter(4, Types.INTEGER);
            cs.registerOutParameter(5, Types.INTEGER);
            boolean has=cs.execute();
            while(has){
                mp.setMaxpage(cs.getInt(3));
                mp.setMaxrows(cs.getInt(4));
                mp.setPerrows(cs.getInt(5));
                rs=cs.getResultSet();
                while(rs.next()){
                    Article article=new Article();
                    article.setId(rs.getInt("id"));
                    article.setRootid(rs.getInt("rootid"));
                    article.setTitle(rs.getString("title"));
                    //clob读
                    BufferedReader br=new BufferedReader(rs.getCharacterStream("content"));
                    StringBuffer sb=new StringBuffer();
                    String n="";
                    try {
                        while((n=br.readLine())!=null){
                            sb.append(n);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    article.setContent(sb.toString());
                    article.setDatetime(rs.getString("datetime"));
                    BBSUser user=new BBSUser();
                    user.setId(rs.getInt("userid"));
                    article.setUser(user);
                    list.add(article);
                }
                has=cs.getMoreResults();
            }
            mp.setData(list);
        } catch (SQLException e) {
            e.printStackTrace();

        }finally{
            if(rs!=null){
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(cs!=null){
                try {
                    cs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return mp;
    }

    @Override
    public boolean delArticle(int id) {
        boolean flag=false;
        PreparedStatement ps=null;

        String sql="delete  from article where id=? or rootid=?";
        //rootid   为当前帖子的主贴是谁   从铁
        //id为主贴
        try {
            ps=conn.prepareStatement(sql);
            ps.setInt(1,id);
            ps.setInt(2,id);
            flag=ps.executeUpdate()>0;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {

            if(ps!=null){
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }

    @Override
    public boolean addArticle(Article article) {
        PreparedStatement ps=null;
        BBSUser user=new BBSUser();
        String sql="insert into article (rootid,title,content,userid,datetime) VALUES (?,?,?,?,now())";
        boolean flag=false;
        try {
            ps=conn.prepareStatement(sql);
            ps.setInt(1, article.getRootid());
            ps.setString(2,article.getTitle());
            //这个是clob写
            StringReader reader=new StringReader(article.getContent());
            ps.setCharacterStream(3,reader,article.getContent().length());
            ps.setInt(4,article.getUser().getId());
            flag=ps.executeUpdate()>0;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if(ps!=null){
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }

    @Override
    public List<Article> queryArticleById(int id) {
        PreparedStatement ps=null;
        ResultSet rs=null;
        String sql="select * from article where rootid=? or id=?  order by id";
        List<Article> list=new ArrayList<>();
        try {
            ps=conn.prepareStatement(sql);
            ps.setInt(1,id);
            ps.setInt(2,id);
            rs=ps.executeQuery();

            while(rs.next()){
                Article article=new Article();
                article.setId(rs.getInt("id"));
                article.setRootid(rs.getInt("rootid"));
                article.setTitle(rs.getString("title"));
                BBSUser u=new BBSUser();
                u.setId(rs.getInt("userid"));
                article.setUser(u);
                BufferedReader br=new BufferedReader(rs.getCharacterStream("content"));
                StringBuffer sb=new StringBuffer();
                String n="";
                while((n=br.readLine())!=null){
                    sb.append(n);
                }
                article.setContent(sb.toString());
                list.add(article);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public boolean delReply(int id) {
        boolean flag=false;
        PreparedStatement ps=null;

        String sql="delete  from article where id=? ";
        //rootid   为当前帖子的主贴是谁   从铁
        //id为主贴
        try {
            ps=conn.prepareStatement(sql);
            ps.setInt(1,id);
            flag=ps.executeUpdate()>0;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {

            if(ps!=null){
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }
}
