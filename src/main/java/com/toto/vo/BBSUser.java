package com.toto.vo;

import java.io.Serializable;
import java.sql.Blob;
import java.util.List;

/**
 * Created by Administrator on 2017/3/13 0013.
 * 这里仅仅是登录与注册有关的地方
 * 唯一要注意的是userid会与帖子有关   一个用户可以发表多个帖子  所以我们要用List遍历
 */
public class BBSUser implements Serializable {


    private String username;
    private  String password;
    private String pic;
    private List<Article> alist;//一个BBSuser会有多个帖子

    public List<Article> getAlist() {
        return alist;
    }

    public void setAlist(List<Article> alist) {
        this.alist = alist;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    private int id;
    public BBSUser(){}
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
