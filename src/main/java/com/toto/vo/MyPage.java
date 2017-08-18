package com.toto.vo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/3/20 0020.
 *  in in_curpage  int ,
 *  in in_userid  int,
    out out_maxpage  int,
    out out_maxrows  int,
    out out_perrows  int
 */
public class MyPage implements Serializable {
    private int curpage;//当前页
    private int maxpage;//最大页
    private int maxrows;//最大行数  就是一共有多少行
    private int perrows;//当前行数
    private List<Article> data;//每页的数据

    public int getCurpage() {
        return curpage;
    }

    public void setCurpage(int curpage) {
        this.curpage = curpage;
    }

    public int getMaxpage() {
        return maxpage;
    }

    public void setMaxpage(int maxpage) {
        this.maxpage = maxpage;
    }

    public int getMaxrows() {
        return maxrows;
    }

    public void setMaxrows(int maxrows) {
        this.maxrows = maxrows;
    }

    public int getPerrows() {
        return perrows;
    }

    public void setPerrows(int perrows) {
        this.perrows = perrows;
    }

    public List<Article> getData() {
        return data;
    }

    public void setData(List<Article> data) {
        this.data = data;
    }
}
