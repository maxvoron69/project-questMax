package com.quest;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Единый сервлет для управления квестом.
 * GET /quest — отображает текущий шаг
 * POST /quest — обрабатывает выбор игрока
 */
@WebServlet("/quest")
public class QuestServlet extends HttpServlet {

    private static final String GAME_SESSION_ATTR = "gameSession";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        GameSession gameSession = getGameSession(req);

        // Если сессия пуста или шаг PROLOGUE — редирект на index.jsp
        if (gameSession == null || gameSession.getCurrentStep() == QuestStep.PROLOGUE) {
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
            return;
        }

        req.setAttribute("gameSession", gameSession);
        req.getRequestDispatcher("/quest.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String choiceParam = req.getParameter("choice");
        if (choiceParam == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing choice parameter");
            return;
        }

        int choice;
        try {
            choice = Integer.parseInt(choiceParam);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid choice parameter");
            return;
        }

        if (choice != 1 && choice != 2) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Choice must be 1 or 2");
            return;
        }

        GameSession gameSession = getGameSession(req);
        if (gameSession == null || gameSession.isComplete()) {
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
            return;
        }

        QuestStep currentStep = gameSession.getCurrentStep();
        QuestStep nextStep = currentStep.getNextStep(choice);
        gameSession.setCurrentStep(nextStep);

        req.setAttribute("gameSession", gameSession);

        // Если достигли терминального шага — редирект на defeat.jsp или quest.jsp
        if (nextStep == QuestStep.VICTORY || nextStep.name().startsWith("DEFEAT")) {
            req.getRequestDispatcher("/defeat.jsp").forward(req, resp);
        } else {
            req.getRequestDispatcher("/quest.jsp").forward(req, resp);
        }
    }

    /**
     * Получает GameSession из сессии, создаёт новую если нет.
     */
    private GameSession getGameSession(HttpServletRequest req) {
        HttpSession httpSession = req.getSession();
        GameSession gameSession = (GameSession) httpSession.getAttribute(GAME_SESSION_ATTR);
        if (gameSession == null) {
            gameSession = new GameSession("неизвестно", QuestStep.PROLOGUE);
            httpSession.setAttribute(GAME_SESSION_ATTR, gameSession);
        }
        return gameSession;
    }
}
