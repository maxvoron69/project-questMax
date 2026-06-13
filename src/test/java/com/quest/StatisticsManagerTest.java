package com.quest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StatisticsManagerTest {

    @Mock
    private HttpSession mockSession;
    
    private Map<String, Object> sessionAttributes;

    @BeforeEach
    void setUp() {
        sessionAttributes = new HashMap<>();
        
        Mockito.lenient().when(mockSession.getAttribute(anyString())).thenAnswer(invocation -> {
            String key = invocation.getArgument(0);
            return sessionAttributes.get(key);
        });
        
        Mockito.lenient().doAnswer(invocation -> {
            String key = invocation.getArgument(0);
            Object value = invocation.getArgument(1);
            sessionAttributes.put(key, value);
            return null;
        }).when(mockSession).setAttribute(anyString(), any());
    }

    /**Тест 1: Инициализация статистики для новой сессии*/
    @Test
    void testInitSessionStats_NewSession() {
        StatisticsManager.initSessionStats(mockSession, "Иван");

        Map<String, Object> stats = (Map<String, Object>) sessionAttributes.get("game_stats");
        assertNotNull(stats);
        assertEquals("Иван", stats.get("playerName"));
        assertEquals(1, stats.get("totalGames"));
    }

    /**Тест 2: Инициализация статистики для существующей сессии*/
    @Test
    void testInitSessionStats_ExistingSession() {
        Map<String, Object> existingStats = new HashMap<>();
        existingStats.put("playerName", "Иван");
        existingStats.put("totalGames", 1);
        sessionAttributes.put("game_stats", existingStats);

        StatisticsManager.initSessionStats(mockSession, "Петр");

        Map<String, Object> stats = (Map<String, Object>) sessionAttributes.get("game_stats");
        assertEquals("Иван", stats.get("playerName"));
        assertEquals(1, stats.get("totalGames"));
    }

    /**Тест 3: Инкремент количества игр*/
    @Test
    void testIncrementGames() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("playerName", "Иван");
        stats.put("totalGames", 1);
        sessionAttributes.put("game_stats", stats);

        StatisticsManager.incrementGames(mockSession);

        Map<String, Object> updatedStats = (Map<String, Object>) sessionAttributes.get("game_stats");
        assertEquals(2, updatedStats.get("totalGames"));
    }

    /**Тест 4: Получение статистики из сессии*/
    @Test
    void testGetSessionStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("playerName", "Иван");
        stats.put("totalGames", 1);
        sessionAttributes.put("game_stats", stats);

        Map<String, Object> retrievedStats = StatisticsManager.getSessionStats(mockSession);

        assertNotNull(retrievedStats);
        assertEquals("Иван", retrievedStats.get("playerName"));
        assertEquals(1, retrievedStats.get("totalGames"));
    }

    /**Тест 5: Получение статистики, когда она не инициализирована*/
    @Test
    void testGetSessionStats_Null() {
        Map<String, Object> retrievedStats = StatisticsManager.getSessionStats(mockSession);

        assertNull(retrievedStats);
    }
}
