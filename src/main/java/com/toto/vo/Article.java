package com.toto.vo;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/3/19 0019.
 * 跟发表帖子有关的javabean
 * 唯一要注意的是这里边userid会与BBuser类有关  那么我们就使用BBuser类
 */
public class Article implements Serializable {
    private int id;
    private int rootid;
    private String title;
    private String content;
    private BBSUser user;
    private  String datetime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRootid() {
        return rootid;
    }

    public void setRootid(int rootid) {
        this.rootid = rootid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public BBSUser getUser() {
        return user;
    }

    public void setUser(BBSUser user) {
        this.user = user;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
}
