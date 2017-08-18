package com.toto.service;

import com.toto.vo.Article;
import com.toto.vo.MyPage;

import java.util.List;

/**
 * Created by Administrator on 2017/3/21 0021.
 */
public interface AriSerIn {
    public MyPage queryall(int rootid, int curpage, int userid);
    public boolean delArticle(int id);
    public boolean addArticle(Article article);
    public String queryArticleById(int id);
    public boolean delReply(int id);
}
