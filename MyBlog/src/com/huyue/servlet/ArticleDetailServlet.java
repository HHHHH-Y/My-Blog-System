package com.huyue.servlet;

import com.huyue.model.Article;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: HHH.Y
 * Date: 2020-07-14
 */
@WebServlet("/article")
public class ArticleDetailServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String idStr = req.getParameter("id");
        int id = Integer.parseInt(idStr);

        // 通过 id 获取到文章
        Article article = null;
        try {
            article = Article.get(id);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
        // 如果文章不存在, 手动返回 404
        if(article == null) {
            resp.sendError(404, "没有这篇文章");
            return;
        }
        // 如果文章存在, 就打印出文章的标题和内容
        resp.setContentType("text/html; charset = utf-8");
        PrintWriter writer = resp.getWriter();
        writer.println("<h1>" + article.title + "</h1>");
        writer.println("<p>" + article.content + "</p>");
    }
}
