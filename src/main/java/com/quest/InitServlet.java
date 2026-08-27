package com.quest;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "InitServlet", value = "/start")
public class InitServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Use POST to start a game");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Получение HTTP-сессии
        HttpSession session = req.getSession();
        // Получение имени игрока из POST-параметра
        String playerName = req.getParameter("name");
        if (playerName == null || playerName.trim().isEmpty()) {
            playerName = "неизвестно";
        }
        // Инициализация статистики сессии
        StatisticsManager.initSessionStats(session, playerName);
        // Инкремент количества игр
        StatisticsManager.incrementGames(session);
        // Создание сессии игры и инициализация первым шагом
        GameSession gameSession = new GameSession(playerName, QuestStep.ROUND_1);
        session.setAttribute("gameSession", gameSession);
        // Перенаправление на сервлет квеста
        resp.sendRedirect(req.getContextPath() + "/quest");
    }
}
