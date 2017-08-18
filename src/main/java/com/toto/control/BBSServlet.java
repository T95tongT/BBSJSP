package com.toto.control;

import com.toto.service.BSerImp;
import com.toto.service.BSerIn;
import com.toto.vo.BBSUser;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Administrator on 2017/3/13 0013.
 */
@WebServlet(name = "BBSServlet",urlPatterns = "/user",initParams = {
        @WebInitParam(name = "show",value = "article?action=queryall&rootid=0&curpage=1")
})
public class BBSServlet extends HttpServlet {
    RequestDispatcher dispatcher=null;

    BSerIn service=new BSerImp();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        BBSUser user=new BBSUser();
       if(ServletFileUpload.isMultipartContent(request)) {
           Map <String,String> picType=new HashMap<>();
           picType.put("image/jpeg",".jpg");
           picType.put("image/gif",".gif");
           picType.put("image/x-ms-bmp",".bmp");
           picType.put("image/png",".png");
           String spath=request.getServletContext().getRealPath("/");
           String tmpPath=""+System.getProperty("file.separator")+"tmpDir";
           File tmpDir=new File(tmpPath);
           if(!tmpDir.isDirectory()){
               tmpDir.mkdir();
           }
           String savePath=""+System.getProperty("file.separator")+"upload";
           File saveDir=new File(savePath);
           if(!saveDir.isDirectory()){
               saveDir.mkdir();
           }
           DiskFileItemFactory disk=new DiskFileItemFactory();
           disk.setRepository(tmpDir);
           disk.setSizeThreshold(1024*1024*10);
           ServletFileUpload sfu=new ServletFileUpload(disk);
           sfu.setFileSizeMax(1024*1024);
           sfu.setSizeMax(1024*1024*10);

           try {
               FileItemIterator fii=sfu.getItemIterator(request);
               while(fii.hasNext()){
                   FileItemStream fis=fii.next();
                   String name=fis.getFieldName();
                   String fileName=fis.getName();
                   String filetype=fis.getContentType();
                   InputStream stream=fis.openStream();
                   if(!fis.isFormField()&&fileName.length()>0){
                       if(!picType.containsKey(filetype)){
                           System.out.println("不是我们想要的图片类型");
                            return ;
                       }
                       BufferedInputStream bis=new BufferedInputStream(stream);
                       UUID uuid=UUID.randomUUID();
                       String picName=spath+"\\upload\\"+uuid.toString()+picType.get(filetype);
                       BufferedOutputStream bos=new BufferedOutputStream(new FileOutputStream(new File(picName)));
                       Streams.copy(bis,bos,true);

                      user.setPic(picName);
                   }else{
                      switch(name){
                          case"reusername":
                              user.setUsername(Streams.asString(stream));
                              break;
                          case"repassword":
                              user.setPassword(Streams.asString(stream));
                              break;
                       }
                   }
               }
               if(service.register(user)){
                   dispatcher=request.getRequestDispatcher(map.get("show"));
                   dispatcher.forward(request,response);
               }
           } catch (FileUploadException e) {
               e.printStackTrace();
           }

       }else {
           String action=request.getParameter("action");
           if(action.equals("login")){
               login(request,response);
           }else if(action.equals("pic")){
                getPicById(request, response);
           }
       }
         /*    BSerIn service=new BSerImp();
            if(ServletFileUpload.isMultipartContent(request)){//如果是二进制格式的
                String tmpPath=""+System.getProperty("file.separator")+"tmpDir";//创建一个临时目录  最终都放在保存目录中
                File tmpDir=new File(tmpPath);//把字符串格式转换成文件格式
                String spath=request.getServletContext().getRealPath("/");//获取该项目的TOMCAT的目录
                if(!tmpDir.isDirectory()){
                    tmpDir.mkdir();//如果不存在这个临时目录的文件就创建一个
                }
                //在这之前只是创建一个文件以及目录  并没有在磁盘中有地方放他
                DiskFileItemFactory disk=new DiskFileItemFactory();//开个磁盘的位置
                disk.setRepository(tmpDir);//把这个目录放入磁盘中
                disk.setSizeThreshold(1024*1024*10);//开个磁盘的大小
                ServletFileUpload sfu=new ServletFileUpload();//开完磁盘空间  和目录都写好了   那就往上边传
                sfu.setFileSizeMax(1024*1024);//设置单个文件的大小
                sfu.setSizeMax(1024*1024*10);//设置所有文件的大小
                try {
                    //上传头像到磁盘里
                    FileItemIterator fii=sfu.getItemIterator(request);//获取前端传来的请求
                    // 上传头像到数据库
                    BBSUser user=service.upPicToCom(fii,spath);
                    if(service.register(user)){//如果注册成功
                        dispatcher=request.getRequestDispatcher(map.get("show"));
                        dispatcher.forward(request,response);
                    }
                } catch (FileUploadException e) {
                    e.printStackTrace();
                }
            }else {


                String action=request.getParameter("action");
                if(action.equals("login")){
                    login(request,response);
                }else  if(action.equals("pic")){
                    String id=request.getParameter("id");
                    byte [] buffer=service.getById(Integer.parseInt(id));
                    response.setContentType("image/jpeg");
                    //响应到前端
                    OutputStream os=response.getOutputStream();
                    os.write(buffer);
                    os.flush();
                    os.close();
                }

            }*/

}
    private  void getPicById(HttpServletRequest request, HttpServletResponse response){
        String id=request.getParameter("id");//从前端页中获取id
        //获取的ID为String 类型   所以要转换成int类型
        byte[] buffer=service.getById(Integer.parseInt(id));
        //响应格式为图片
        response.setContentType("image/jpeg");
        try {
            //转换成输出流   是为了显示在客户端
            OutputStream os=response.getOutputStream();
            os.write(buffer);
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username=request.getParameter("username");
        String password=request.getParameter("password");
        BBSUser user=new BBSUser();
        //把从前端获取来的值放进vo层  以便在dao层可以与数据库里的值进行比较
        user.setUsername(username);
        user.setPassword(password);
        BSerIn bsi=new BSerImp();
        RequestDispatcher dispatcher;
        HttpSession session=request.getSession();//获取session值
        BBSUser u=bsi.login(user);
        if(u!=null){//登陆成功

            session.setAttribute("user",u);

            if(request.getParameter("sun")!=null){//cookie值
                Cookie cookie=new Cookie("papaoku",username);
                Cookie cookie1=new Cookie("papaokp",password);
                cookie.setMaxAge(24*3600*3600);//开时长
                cookie1.setMaxAge(24*3600*3600);
                response.addCookie(cookie);
                response.addCookie(cookie1);
            }
            request.setAttribute("info","登陆成功");
        }else{//登录失败
            session.removeAttribute("user");//登录失败 清除session值
            request.setAttribute("info","登录失败");
        }
        //不管成功还是失败  都走这道这页
        dispatcher=request.getRequestDispatcher(map.get("show"));
        dispatcher.forward(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }


    Map<String,String> map=new HashMap<String, String>();//放入集合中
    @Override
    public void init(ServletConfig config) throws ServletException {
        map.put("show",config.getInitParameter("show"));
    }
}
