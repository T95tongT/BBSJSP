package com.toto.control;

import com.toto.service.AriSerImp;
import com.toto.service.AriSerIn;
import com.toto.vo.Article;
import com.toto.vo.BBSUser;
import com.toto.vo.MyPage;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by Administrator on 2017/3/21 0021.
 */
@WebServlet(name = "ArticleServlet",urlPatterns = "/article",initParams = {
        @WebInitParam(name = "show",value = "show.jsp"),
        @WebInitParam(name = "success",value = "article?action=queryall&rootid=0&curpage=1")
})
public class ArticleServlet extends HttpServlet {
    RequestDispatcher dispatcher=null;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action=request.getParameter("action");
        BBSUser user;
        AriSerIn service=new AriSerImp();
        switch (action){
            case "queryall":
                String rootid=request.getParameter("rootid");
                String curpage=request.getParameter("curpage");
                HttpSession session=request.getSession();
                user=(BBSUser) session.getAttribute("user");
                int userid=user==null?999:user.getId();

                MyPage mp=service.queryall(Integer.parseInt(rootid),Integer.parseInt(curpage),userid);
                request.setAttribute("mp",mp);
                dispatcher=request.getRequestDispatcher(map.get("show"));
                break;
            case "del":
                String id=request.getParameter("id");
                if(service.delArticle(Integer.parseInt(id))){
                    dispatcher=request.getRequestDispatcher(map.get("success"));
                }
                break;
            case "addz":
                Article article=new Article();
                String title=new String  (request.getParameter("title").getBytes("iso8859-1"),"utf-8");
                String content=new String  (request.getParameter("content").getBytes("iso8859-1"),"utf-8");
                article.setTitle(title);
                article.setContent(content);
                article.setRootid(0);
                user=(BBSUser)request.getSession().getAttribute("user");
                article.setUser(user);

                if(service.addArticle(article)){
                    dispatcher=request.getRequestDispatcher(map.get("success"));
                }
                break;

            //遗留问题  中文不好使    下次改
            case "querybyid":
                String result=service.queryArticleById(Integer.parseInt(request.getParameter("rootid")));
                response.setCharacterEncoding("utf-8");
                response.setContentType("text/html");
                PrintWriter pw=response.getWriter();
                pw.print(result);
                pw.flush();
                pw.close();
                break;
        }

        dispatcher.forward(request,response);
    }
    @Override
    public void destroy() {
        super.destroy();
    }
Map <String,String> map=new HashMap<>();
    @Override
    public void init(ServletConfig config) throws ServletException {
       map.put("show",config.getInitParameter("show"));
        map.put("success",config.getInitParameter("success"));
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            this.doPost(request,response);
    }
}
