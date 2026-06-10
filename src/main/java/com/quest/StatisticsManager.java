package com.quest;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

public class StatisticsManager {
    private static final String SESSION_STATS_KEY = "game_stats";

    public static void initSessionStats(HttpSession session, String playerName) {
        if (session.getAttribute(SESSION_STATS_KEY) == null) {
            Map<String, Object> stats = new HashMap<>();
            stats.put("playerName", playerName);
            stats.put("totalGames", 1);
            session.setAttribute(SESSION_STATS_KEY, stats);
        }
    }

    public static void incrementGames(HttpSession session) {
        Map<String, Object> stats = (Map<String, Object>) session.getAttribute(SESSION_STATS_KEY);
        if (stats != null) {
            int games = (Integer) stats.getOrDefault("totalGames", 0);
            stats.put("totalGames", games + 1);
            session.setAttribute(SESSION_STATS_KEY, stats);
        }
    }

    public static Map<String, Object> getSessionStats(HttpSession session) {
        return (Map<String, Object>) session.getAttribute(SESSION_STATS_KEY);
    }
}
