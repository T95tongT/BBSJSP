package com.toto.service;

import com.alibaba.fastjson.JSON;
import com.sun.xml.internal.bind.v2.util.CollisionCheckStack;
import com.toto.dao.ADAOImp;
import com.toto.dao.AriDAOIn;
import com.toto.vo.Article;
import com.toto.vo.MyPage;

import java.nio.channels.AsynchronousByteChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/21 0021.
 */
public class AriSerImp implements AriSerIn {
    AriDAOIn service=new ADAOImp();
    @Override
    public MyPage queryall(int rootid, int curpage, int userid) {
        return service.queryall(rootid,curpage,userid);
    }

    @Override
    public boolean delArticle(int id) {
        return service.delArticle(id);
    }

    @Override
    public boolean addArticle(Article article) {
        return service.addArticle(article);
    }

    @Override
    public String queryArticleById(int id) {
        List<Article> list=service.queryArticleById(id);
        Map<String,Object> map=new HashMap<>();
        if(list!=null){
            map.put("title",list.get(0).getTitle());
            map.put("list",list.subList(1,list.size()));
        }
        System.out.print(JSON.toJSONString(map,true));
        return JSON.toJSONString(map,true);
    }

    @Override
    public boolean delReply(int id) {
        return service.delReply(id);
    }
}
