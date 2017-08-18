package com.toto.dao;

import com.mysql.jdbc.util.BaseBugReport;
import com.toto.vo.BBSUser;
import org.apache.commons.fileupload.FileItemIterator;

/**
 * Created by Administrator on 2017/3/13 0013.
 */
public interface BDAOIn {
    public BBSUser login(BBSUser user);
  /*  public boolean register(BBSUser user);//注册
    public byte[] getById(int id);//通过ID查询*/
    public boolean register(BBSUser user);
    public byte[] getById(int id);//通过ID查询
}
