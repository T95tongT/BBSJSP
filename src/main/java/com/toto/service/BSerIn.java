package com.toto.service;

import com.toto.vo.BBSUser;
import org.apache.commons.fileupload.FileItemIterator;

/**
 * Created by Administrator on 2017/3/13 0013.
 */
public interface BSerIn {

    public BBSUser login(BBSUser user);
    public boolean register(BBSUser user);//注册
    public byte[] getById(int id);
   /* public boolean register(BBSUser user);//注册

    public BBSUser upPicToCom(FileItemIterator fii, String spath);
    public byte[] getById(int id);//通过ID查询*/

}
