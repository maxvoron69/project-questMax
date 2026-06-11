package com.quest;

import org.junit.Test;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class StatisticsManagerTest {

    // Тест 1: Инициализация статистики для новой сессии
    @Test
    public void testInitSessionStats_NewSession() {
        Map<String, Object> realSessionAttributes = new HashMap<>();
        
        HttpSession mockSession = mock(HttpSession.class);
        doAnswer(invocation -> {
            String key = invocation.getArgument(0);
            return realSessionAttributes.get(key);
        }).when(mockSession).getAttribute(anyString());
        doAnswer(invocation -> {
            String key = invocation.getArgument(0);
            Object value = invocation.getArgument(1);
            realSessionAttributes.put(key, value);
            return null;
        }).when(mockSession).setAttribute(anyString(), any());

        StatisticsManager.initSessionStats(mockSession, "Иван");

        Map<String, Object> stats = (Map<String, Object>) realSessionAttributes.get("game_stats");
        assertNotNull(stats);
        assertEquals("Иван", stats.get("playerName"));
        assertEquals(1, stats.get("totalGames"));
    }

    // Тест 2: Инициализация статистики для существующей сессии
    @Test
    public void testInitSessionStats_ExistingSession() {
        Map<String, Object> realSessionAttributes = new HashMap<>();
        
        HttpSession mockSession = mock(HttpSession.class);
        doAnswer(invocation -> {
            String key = invocation.getArgument(0);
            return realSessionAttributes.get(key);
        }).when(mockSession).getAttribute(anyString());
        doAnswer(invocation -> {
            String key = invocation.getArgument(0);
            Object value = invocation.getArgument(1);
            realSessionAttributes.put(key, value);
            return null;
        }).when(mockSession).setAttribute(anyString(), any());

        Map<String, Object> existingStats = new HashMap<>();
        existingStats.put("playerName", "Иван");
        existingStats.put("totalGames", 1);
        realSessionAttributes.put("game_stats", existingStats);

        StatisticsManager.initSessionStats(mockSession, "Петр");

        Map<String, Object> stats = (Map<String, Object>) realSessionAttributes.get("game_stats");
        assertEquals("Иван", stats.get("playerName"));
        assertEquals(1, stats.get("totalGames"));
    }

    // Тест 3: Инкремент количества игр
    @Test
    public void testIncrementGames() {
        Map<String, Object> realSessionAttributes = new HashMap<>();
        
        HttpSession mockSession = mock(HttpSession.class);
        doAnswer(invocation -> {
            String key = invocation.getArgument(0);
            return realSessionAttributes.get(key);
        }).when(mockSession).getAttribute(anyString());
        doAnswer(invocation -> {
            String key = invocation.getArgument(0);
            Object value = invocation.getArgument(1);
            realSessionAttributes.put(key, value);
            return null;
        }).when(mockSession).setAttribute(anyString(), any());

        Map<String, Object> stats = new HashMap<>();
        stats.put("playerName", "Иван");
        stats.put("totalGames", 1);
        realSessionAttributes.put("game_stats", stats);

        StatisticsManager.incrementGames(mockSession);

        Map<String, Object> updatedStats = (Map<String, Object>) realSessionAttributes.get("game_stats");
        assertEquals(2, updatedStats.get("totalGames"));
    }

    // Тест 4: Получение статистики из сессии
    @Test
    public void testGetSessionStats() {
        Map<String, Object> realSessionAttributes = new HashMap<>();
        
        HttpSession mockSession = mock(HttpSession.class);
        doAnswer(invocation -> {
            String key = invocation.getArgument(0);
            return realSessionAttributes.get(key);
        }).when(mockSession).getAttribute(anyString());

        Map<String, Object> stats = new HashMap<>();
        stats.put("playerName", "Иван");
        stats.put("totalGames", 1);
        realSessionAttributes.put("game_stats", stats);

        Map<String, Object> retrievedStats = StatisticsManager.getSessionStats(mockSession);

        assertNotNull(retrievedStats);
        assertEquals("Иван", retrievedStats.get("playerName"));
        assertEquals(1, retrievedStats.get("totalGames"));
    }

    // Тест 5: Получение статистики, когда она не инициализирована
    @Test
    public void testGetSessionStats_Null() {
        Map<String, Object> realSessionAttributes = new HashMap<>();
        
        HttpSession mockSession = mock(HttpSession.class);
        doAnswer(invocation -> {
            String key = invocation.getArgument(0);
            return realSessionAttributes.get(key);
        }).when(mockSession).getAttribute(anyString());

        Map<String, Object> retrievedStats = StatisticsManager.getSessionStats(mockSession);

        assertNull(retrievedStats);
    }
}
