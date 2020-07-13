package com.huyue.servlet;

import com.huyue.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * Description: 注册 Servlet
 * User: HHH.Y
 * Date: 2020-07-13
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 指定字符集编码为 utf-8
        req.setCharacterEncoding("utf-8");
        // 拿到对应的属性 username, password
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        // 进行注册
        User user = null;
        try {
            user = User.login(username, password);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
        // 如果注册失败, 则跳转至注册界面继续注册
        if (user == null) {
            resp.sendRedirect("login.html");
            return;
        }

        // 如果登录成功, 则将用户信息存入 session 中
        // 注册完成之后直接保持登录
        HttpSession session = req.getSession();
        session.setAttribute("user", user);
        // 跳转回主页面
        resp.sendRedirect("/");
    }
}
