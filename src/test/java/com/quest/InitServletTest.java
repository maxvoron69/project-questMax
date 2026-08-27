package com.quest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import org.mockito.Mockito;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class InitServletTest {

    private InitServlet servlet;

    @Mock
    private HttpServletRequest mockRequest;

    @Mock
    private HttpServletResponse mockResponse;

    @Mock
    private HttpSession mockSession;

    @Mock
    private ServletContext mockServletContext;

    @Mock
    private RequestDispatcher mockDispatcher;

    private java.util.Map<String, Object> sessionAttributes;

    @BeforeEach
    void setUp() throws ServletException, IOException {
        servlet = new InitServlet();
        sessionAttributes = new java.util.HashMap<>();

        lenient().when(mockRequest.getSession()).thenReturn(mockSession);
        lenient().when(mockSession.getAttribute(anyString())).thenAnswer(invocation ->
                sessionAttributes.get(invocation.getArgument(0)));
        lenient().doAnswer(invocation -> {
            String key = invocation.getArgument(0);
            sessionAttributes.put(key, invocation.getArgument(1));
            return null;
        }).when(mockSession).setAttribute(anyString(), any());

        when(mockRequest.getServletContext()).thenReturn(mockServletContext);
        when(mockServletContext.getRequestDispatcher(anyString())).thenReturn(mockDispatcher);
        when(mockRequest.getContextPath()).thenReturn("");
        doNothing().when(mockDispatcher).forward(any(), any());
    }

    @Test
    void testDoGet_Returns405() throws ServletException, IOException {
        servlet.doGet(mockRequest, mockResponse);

        verify(mockResponse).sendError(eq(HttpServletResponse.SC_METHOD_NOT_ALLOWED), anyString());
    }

    @Test
    void testDoPost_WithValidName() throws ServletException, IOException {
        when(mockRequest.getParameter("name")).thenReturn("Иван");

        servlet.doPost(mockRequest, mockResponse);

        verify(mockSession).setAttribute(eq("gameSession"), any(GameSession.class));
        verify(mockResponse).sendRedirect("/quest");
    }

    @Test
    void testDoPost_EmptyName_UsesDefault() throws ServletException, IOException {
        when(mockRequest.getParameter("name")).thenReturn("");

        servlet.doPost(mockRequest, mockResponse);

        verify(mockSession).setAttribute(eq("gameSession"), argThat(gs ->
                ((GameSession) gs).getPlayerName().equals("неизвестно")
        ));
    }

    @Test
    void testDoPost_NullName_UsesDefault() throws ServletException, IOException {
        when(mockRequest.getParameter("name")).thenReturn(null);

        servlet.doPost(mockRequest, mockResponse);

        verify(mockSession).setAttribute(eq("gameSession"), argThat(gs ->
                ((GameSession) gs).getPlayerName().equals("неизвестно")
        ));
    }

    @Test
    void testDoPost_GameSessionStartsAtRound1() throws ServletException, IOException {
        when(mockRequest.getParameter("name")).thenReturn("Петр");

        servlet.doPost(mockRequest, mockResponse);

        verify(mockSession).setAttribute(eq("gameSession"), argThat(gs ->
                ((GameSession) gs).getCurrentStep() == QuestStep.ROUND_1
        ));
    }

    @Test
    void testDoPost_IncrementsTotalGames() throws ServletException, IOException {
        when(mockRequest.getParameter("name")).thenReturn("Иван");

        // Имитация существующей статистики
        java.util.Map<String, Object> stats = new java.util.HashMap<>();
        stats.put("playerName", "Иван");
        stats.put("totalGames", 3);
        when(mockSession.getAttribute("game_stats")).thenReturn(stats);

        servlet.doPost(mockRequest, mockResponse);

        assertEquals(4, stats.get("totalGames"));
    }

    @Test
    void testDoPost_GameSessionHasCorrectName() throws ServletException, IOException {
        when(mockRequest.getParameter("name")).thenReturn("Алексей");

        servlet.doPost(mockRequest, mockResponse);

        verify(mockSession).setAttribute(eq("gameSession"), argThat(gs ->
                ((GameSession) gs).getPlayerName().equals("Алексей")
        ));
    }
}
