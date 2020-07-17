package com.huyue.servlet;

import com.huyue.model.Article;
import com.huyue.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * Description: 更新文章 servlet
 * User: HHH.Y
 * Date: 2020-07-13
 */

// 当前端提交 input[type = fail]时, 后端必须加这样的注解
@MultipartConfig
@WebServlet("/publish")
public class PublishServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if(user == null) {
            resp.sendRedirect("login.html");
            return;
        }
        req.setCharacterEncoding("utf-8");
        String title = req.getParameter("title");
        String content = req.getParameter("content");

        // 对于读取图片的格式
        Part imagePart = req.getPart("image");
        InputStream is = imagePart.getInputStream();
        // 将读到的信息先存入到 buffer 中
        byte[] buffer = new byte[1024];
        try(OutputStream os = new FileOutputStream("D:\\github\\My-Blog-System\\MyBlog\\保存上传过来的文件\\Hello.gif")) {
            while (true) {
                int len = is.read(buffer);
                if(len == -1) {
                    // 读到 EOS, 所有数据全部读完了
                    break;
                }
                os.write(buffer, 0, len);
                os.flush();
            }
        }

        // 进行文章的发表
        try {
            Article.publish(user.id, title, content);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
        // 返回首页
        resp.sendRedirect("/");
    }
}
