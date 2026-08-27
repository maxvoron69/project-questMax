package com.quest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class QuestServletTest {

    private QuestServlet servlet;

    @Mock
    private HttpServletRequest mockRequest;

    @Mock
    private HttpServletResponse mockResponse;

    @Mock
    private HttpSession mockSession;

    @Mock
    private RequestDispatcher mockDispatcher;

    @BeforeEach
    void setUp() throws ServletException, IOException {
        servlet = new QuestServlet();
        when(mockRequest.getSession()).thenReturn(mockSession);
        when(mockRequest.getRequestDispatcher(anyString())).thenReturn(mockDispatcher);
        when(mockRequest.getContextPath()).thenReturn("");
        doNothing().when(mockDispatcher).forward(any(), any());
    }

    @Test
    void testDoGet_NoSession_RedirectsToIndex() throws ServletException, IOException {
        when(mockSession.getAttribute("gameSession")).thenReturn(null);

        servlet.doGet(mockRequest, mockResponse);

        verify(mockResponse).sendRedirect("/index.jsp");
        verify(mockDispatcher, never()).forward(any(), any());
    }

    @Test
    void testDoGet_PrologueStep_RedirectsToIndex() throws ServletException, IOException {
        GameSession gameSession = new GameSession("Иван", QuestStep.PROLOGUE);
        when(mockSession.getAttribute("gameSession")).thenReturn(gameSession);

        servlet.doGet(mockRequest, mockResponse);

        verify(mockResponse).sendRedirect("/index.jsp");
    }

    @Test
    void testDoGet_ValidStep_ForwardsToQuestJsp() throws ServletException, IOException {
        GameSession gameSession = new GameSession("Иван", QuestStep.ROUND_1);
        when(mockSession.getAttribute("gameSession")).thenReturn(gameSession);

        servlet.doGet(mockRequest, mockResponse);

        verify(mockDispatcher).forward(eq(mockRequest), eq(mockResponse));
        verify(mockRequest).setAttribute(eq("gameSession"), eq(gameSession));
    }

    @Test
    void testDoPost_Round1_Choice1_NextIsRound2() throws ServletException, IOException {
        GameSession gameSession = new GameSession("Иван", QuestStep.ROUND_1);
        when(mockSession.getAttribute("gameSession")).thenReturn(gameSession);
        when(mockRequest.getParameter("choice")).thenReturn("1");

        servlet.doPost(mockRequest, mockResponse);

        assertEquals(QuestStep.ROUND_2, gameSession.getCurrentStep());
        verify(mockDispatcher).forward(eq(mockRequest), eq(mockResponse));
    }

    @Test
    void testDoPost_Round1_Choice2_NextIsDefeat() throws ServletException, IOException {
        GameSession gameSession = new GameSession("Иван", QuestStep.ROUND_1);
        when(mockSession.getAttribute("gameSession")).thenReturn(gameSession);
        when(mockRequest.getParameter("choice")).thenReturn("2");

        servlet.doPost(mockRequest, mockResponse);

        assertEquals(QuestStep.DEFEAT, gameSession.getCurrentStep());
        verify(mockDispatcher).forward(eq(mockRequest), eq(mockResponse));
    }

    @Test
    void testDoPost_Round3_Choice1_NextIsVictory() throws ServletException, IOException {
        GameSession gameSession = new GameSession("Иван", QuestStep.ROUND_3);
        when(mockSession.getAttribute("gameSession")).thenReturn(gameSession);
        when(mockRequest.getParameter("choice")).thenReturn("1");

        servlet.doPost(mockRequest, mockResponse);

        assertEquals(QuestStep.VICTORY, gameSession.getCurrentStep());
        verify(mockDispatcher).forward(eq(mockRequest), eq(mockResponse));
    }

    @Test
    void testDoPost_MissingChoiceParameter_Returns400() throws ServletException, IOException {
        GameSession gameSession = new GameSession("Иван", QuestStep.ROUND_1);
        when(mockSession.getAttribute("gameSession")).thenReturn(gameSession);
        when(mockRequest.getParameter("choice")).thenReturn(null);

        servlet.doPost(mockRequest, mockResponse);

        verify(mockResponse).sendError(eq(HttpServletResponse.SC_BAD_REQUEST), anyString());
    }

    @Test
    void testDoPost_InvalidChoiceParameter_Returns400() throws ServletException, IOException {
        GameSession gameSession = new GameSession("Иван", QuestStep.ROUND_1);
        when(mockSession.getAttribute("gameSession")).thenReturn(gameSession);
        when(mockRequest.getParameter("choice")).thenReturn("abc");

        servlet.doPost(mockRequest, mockResponse);

        verify(mockResponse).sendError(eq(HttpServletResponse.SC_BAD_REQUEST), anyString());
    }

    @Test
    void testDoPost_CompleteGame_RedirectsToIndex() throws ServletException, IOException {
        GameSession gameSession = new GameSession("Иван", QuestStep.VICTORY);
        when(mockSession.getAttribute("gameSession")).thenReturn(gameSession);
        when(mockRequest.getParameter("choice")).thenReturn("1");

        servlet.doPost(mockRequest, mockResponse);

        verify(mockResponse).sendRedirect("/index.jsp");
    }

    @Test
    void testFullGameFlow() throws ServletException, IOException {
        GameSession gameSession = new GameSession("Иван", QuestStep.ROUND_1);
        when(mockSession.getAttribute("gameSession")).thenReturn(gameSession);

        // Round 1 -> choice 1 -> Round 2
        when(mockRequest.getParameter("choice")).thenReturn("1");
        servlet.doPost(mockRequest, mockResponse);
        assertEquals(QuestStep.ROUND_2, gameSession.getCurrentStep());

        // Round 2 -> choice 1 -> Round 3
        servlet.doPost(mockRequest, mockResponse);
        assertEquals(QuestStep.ROUND_3, gameSession.getCurrentStep());

        // Round 3 -> choice 1 -> Victory
        servlet.doPost(mockRequest, mockResponse);
        assertEquals(QuestStep.VICTORY, gameSession.getCurrentStep());
        assertTrue(gameSession.isComplete());
    }
}
