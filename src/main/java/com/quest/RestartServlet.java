package com.quest;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "RestartServlet", value = "/restart")
public class RestartServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Полная инвалидация сессии для сброса всей игрового состояния
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        // Перенаправление на index.jsp с новым session cookie
        resp.sendRedirect(req.getContextPath() + "/index.jsp");
    }
}
