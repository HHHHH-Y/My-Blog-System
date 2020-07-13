package com.huyue.servlet;

import com.huyue.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created with IntelliJ IDEA.
 * Description: 首页的 servlet
 * User: HHH.Y
 * Date: 2020-07-13
 */
// "" 代表只匹配首页
@WebServlet("")
public class IndexServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 通过 session 判断用户是否已经注册
        HttpSession session = req.getSession();
        User user = (User)session.getAttribute("user");
        String nav = "";
        // 如果未登录, 就显示未登录信息
        if(user == null) {
            nav = "             <li><a href=\"register.html\">注册</a> </li>\n" +
                    "           <li><a href=\"login.html\">登录</a> </li>\n";
        } else {
            // 如果登录了, 显示登录的信息
            nav = "             <li>" + user.username + "</li>\n" +
                    "           <li><a href=\"publish.html\">发表文章</a> </li>\n";
        }
        resp.setContentType("text/html; charset = utf-8");
        PrintWriter writer = resp.getWriter();
        String htmlContent = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>我的博客系统</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class = \"navigator\">\n" +
                "       <ol>\n" +
                nav +
                "       </ol>\n" +
                "    </div>\n" +
                "\n" +
                "    <div id=\"article_list\">\n" +
                "        <!-- 如果没有文章 -->\n" +
                "        没有任何文章, 请发表第一篇文章\n" +
                "        <!-- 如果有文章 -->\n" +
                "    </div>\n" +
                "\n" +
                "</body>\n" +
                "</html>";
        writer.println(htmlContent);
    }
}
