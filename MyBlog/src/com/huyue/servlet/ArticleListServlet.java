package com.huyue.servlet;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huyue.model.Article;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description: 文章列表接口(json 格式)
 * User: HHH.Y
 * Date: 2020-07-13
 */
@WebServlet("/api/article_list.json")
public class ArticleListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1. 获取文章列表
        List<Article> articleList = null;
        try {
            articleList = Article.list();
        } catch (SQLException e) {
            throw new ServletException(e);
        }
        // 2. 将文章列表转换成 JSON 字符串
        String jsonText = translateToJSON(articleList);
        // 3. 按照 HTTP 协议写入到 response 中
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("Application/json");
        resp.getWriter().println(jsonText);
    }

    private String translateToJSON(List<Article> articleList) {
        // 得到一个 json 的数组
        JSONArray array = new JSONArray();

        // 遍历文章列表, 并将文章的信息放入 json 数组中
        for (Article article:articleList) {
            JSONObject articleObject = new JSONObject();
            JSONObject authorObject = new JSONObject();

            authorObject.put("id", article.authorId);
            authorObject.put("username", article.authorUsername);

            articleObject.put("id", article.id);
            articleObject.put("author", authorObject);
            articleObject.put("title", article.title);
            articleObject.put("published_at", article.publishedAt);

            array.add(articleObject);
        }
        return array.toJSONString();
    }
}
