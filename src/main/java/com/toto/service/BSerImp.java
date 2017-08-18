package com.toto.service;

import com.toto.dao.BDAOImp;
import com.toto.dao.BDAOIn;
import com.toto.vo.BBSUser;

/**
 * Created by Administrator on 2017/3/13 0013.
 */
public class BSerImp implements BSerIn{


   BDAOIn bi=new BDAOImp();
    public BBSUser login(BBSUser user) {
        return bi.login(user);
    }

    @Override
    public boolean register(BBSUser user) {
        return bi.register(user);
    }

    @Override
    public byte[] getById(int id) {
        return bi.getById(id);
    }

   /* @Override
    public boolean register(BBSUser user) {
        return bi.register(user);
    }


    Map<String,String> picType=new HashMap<>();//将单个文件放入集合中  方便查询
    public BSerImp(){
        //显示我们允许的图片的格式
        picType.put("image/jpeg",".jpg");
        picType.put("image/gif",".gif");
        picType.put("image/x-ms-bmp",".bmp");
        picType.put("image/png",".png");
        //创建一个最终保存路径  可以一起上传
        String savePath=""+System.getProperty("file.separator")+"upload";
        File saveDir=new File(savePath);//将字符串转换成文件格式
        if(!saveDir.isDirectory()){
           saveDir.mkdir();//如果不存在就创建他
        }
    }
    @Override
    public BBSUser upPicToCom(FileItemIterator fii, String spath) {
        BBSUser user=new BBSUser();//是为了让vo层可以获得pic属性的值
        try {
           while (fii.hasNext()){//如果真的遍历到了文件
               FileItemStream fis=fii.next();//把每个遍历到的文件转换成文件项目流的形式
               String name=fis.getFieldName();//这块是获取的前端我们做注册时输入的输入值
               String fileType=fis.getContentType();//获取上传的头像到底是什么类型的
               InputStream stream=fis.openStream();
               if(!fis.isFormField()&&fis.getName().length()>0){//是文本框可输入的吗
                    if(!picType.containsKey(fileType)){
                        return null;//如果不是我们规定好的图片类型 就返回空值
                    }
                   BufferedInputStream bis=new BufferedInputStream(stream);//转换成缓冲输入流
                   UUID uuid=UUID.randomUUID();//随机生成一个名字
                   String filaNmae=spath+"\\upload\\"+uuid.toString()+picType.get(fileType);
                   BufferedOutputStream bos=new BufferedOutputStream(new FileOutputStream(new File(filaNmae)));
                   Streams.copy(bis,bos,true);//拷贝成功
                   user.setPic(filaNmae);//放入数据库中

               }else {//是文本框可输入的
                   switch(name){
                       case "reusername":
                         //  user.setUsername(name);那么获取的就是reusername
                           user.setUsername(Streams.asString(stream));
                           break;
                       case "repassword":
                           user.setPassword(Streams.asString(stream));
                           break;
                   }
               }
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public byte[] getById(int id) {
        return  bi.getById(id);
    }*/

}
