package com.toto.dao;

import com.toto.vo.Article;
import com.toto.vo.MyPage;

import java.util.List;

/**
 * Created by Administrator on 2017/3/21 0021.
 */
public interface AriDAOIn {
    public MyPage queryall(int rootid,int curpage,int userid);
    public boolean delArticle(int id);
    public boolean addArticle(Article article);
    public  List<Article> queryArticleById(int id);
    public boolean delReply(int id);
}
