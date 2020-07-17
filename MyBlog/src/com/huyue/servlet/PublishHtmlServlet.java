package com.huyue.servlet;

import com.huyue.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * Description: 当 form 表单中要提交 input[type = file] 的输入时, 必须把 enctype 修改为 multipart/form-data
 *              目的是让浏览器发起请求时, 可以在网络中把文件内容传递给服务端!
 *
 * 要正确处理文件上传:
 * 1. 浏览器这边, 注意 form 表单的 enctype
 * 2. 服务端这边:
 *    1. 加注解 @MultipartConfig
 *    2. 通过 Part 中的 inputStream 进行数据的读取 (字节流)
 *
 * User: HHH.Y
 * Date: 2020-07-13
 */
@WebServlet("/publish.html")
public class PublishHtmlServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 在 session 中查看是否已经登录
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        // 如果用户没有进行登录, 跳转到登录界面
        if(user == null) {
            resp.sendRedirect("login.html");
            return;
        }

        String htmlContent = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>我的博客系统系统 | 发表文章</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <form method=\"post\" action=\"publish\" enctype = \"multipart/form-data\">\n" +
                "        <div>\n" +
                "            <label for = \"title\">标题</label>\n" +
                "            <input type=\"text\" id = \"title\" name = \"title\">\n" +
                "        </div>\n" +
                "\n" +
                "        <div>\n" +
                "            <label for = \"content\">正文</label>\n" +
                "            <textarea id = \"content\" name = \"content\"></textarea>\n" +
                "        </div>\n" +
                "        <div>\n" +
                "            <label for=\"image\">图片</label>\n" +
                "            <input type = \"file\" id=\"image\" name=\"image\">" +
                "        </div>\n" +
                "\n" +
                "        <div>\n" +
                "            <button type=\"submit\">发表</button>\n" +
                "        </div>\n" +
                "    </form>\n" +
                "</body>\n" +
                "</html>";
        resp.setContentType("text/html; charset = utf-8");
        resp.getWriter().println(htmlContent);
    }
}
