package com.quest;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameSessionTest {

    @Test
    void testConstructor_InitializesCorrectly() {
        GameSession session = new GameSession("Иван", QuestStep.ROUND_1);

        assertEquals("Иван", session.getPlayerName());
        assertEquals(QuestStep.ROUND_1, session.getCurrentStep());
    }

    @Test
    void testIsComplete_Round1_ReturnsFalse() {
        GameSession session = new GameSession("Иван", QuestStep.ROUND_1);
        assertFalse(session.isComplete());
    }

    @Test
    void testIsComplete_Victory_ReturnsTrue() {
        GameSession session = new GameSession("Иван", QuestStep.VICTORY);
        assertTrue(session.isComplete());
    }

    @Test
    void testIsComplete_Defeat_ReturnsTrue() {
        GameSession session = new GameSession("Иван", QuestStep.DEFEAT);
        assertTrue(session.isComplete());
    }

    @Test
    void testIsComplete_Defeat1_ReturnsTrue() {
        GameSession session = new GameSession("Иван", QuestStep.DEFEAT_1);
        assertTrue(session.isComplete());
    }

    @Test
    void testIsComplete_Defeat2_ReturnsTrue() {
        GameSession session = new GameSession("Иван", QuestStep.DEFEAT_2);
        assertTrue(session.isComplete());
    }

    @Test
    void testSetPlayerName() {
        GameSession session = new GameSession("Иван", QuestStep.ROUND_1);
        session.setPlayerName("Петр");
        assertEquals("Петр", session.getPlayerName());
    }

    @Test
    void testSetCurrentStep() {
        GameSession session = new GameSession("Иван", QuestStep.ROUND_1);
        session.setCurrentStep(QuestStep.ROUND_2);
        assertEquals(QuestStep.ROUND_2, session.getCurrentStep());
    }

    @Test
    void testProgression_Round1ToRound2() {
        GameSession session = new GameSession("Иван", QuestStep.ROUND_1);
        session.setCurrentStep(QuestStep.ROUND_1.getNextStep(1));
        assertEquals(QuestStep.ROUND_2, session.getCurrentStep());
        assertFalse(session.isComplete());
    }

    @Test
    void testFullProgression_ToVictory() {
        GameSession session = new GameSession("Иван", QuestStep.ROUND_1);
        session.setCurrentStep(QuestStep.ROUND_1.getNextStep(1)); // ROUND_2
        session.setCurrentStep(QuestStep.ROUND_2.getNextStep(1)); // ROUND_3
        session.setCurrentStep(QuestStep.ROUND_3.getNextStep(1)); // VICTORY
        assertTrue(session.isComplete());
        assertEquals(QuestStep.VICTORY, session.getCurrentStep());
    }

    @Test
    void testDefeatPath_Round1() {
        GameSession session = new GameSession("Иван", QuestStep.ROUND_1);
        session.setCurrentStep(QuestStep.ROUND_1.getNextStep(2)); // DEFEAT
        assertTrue(session.isComplete());
    }

    @Test
    void testDefeatPath_Round2() {
        GameSession session = new GameSession("Иван", QuestStep.ROUND_2);
        session.setCurrentStep(QuestStep.ROUND_2.getNextStep(2)); // DEFEAT_1
        assertTrue(session.isComplete());
    }

    @Test
    void testDefeatPath_Round3() {
        GameSession session = new GameSession("Иван", QuestStep.ROUND_3);
        session.setCurrentStep(QuestStep.ROUND_3.getNextStep(2)); // DEFEAT_2
        assertTrue(session.isComplete());
    }
}
