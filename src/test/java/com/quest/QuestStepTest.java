package com.quest;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuestStepTest {

    @Test
    void testRound1_Choice1_NextIsRound2() {
        assertEquals(QuestStep.ROUND_2, QuestStep.ROUND_1.getNextStep(1));
    }

    @Test
    void testRound1_Choice2_NextIsDefeat() {
        assertEquals(QuestStep.DEFEAT, QuestStep.ROUND_1.getNextStep(2));
    }

    @Test
    void testRound2_Choice1_NextIsRound3() {
        assertEquals(QuestStep.ROUND_3, QuestStep.ROUND_2.getNextStep(1));
    }

    @Test
    void testRound2_Choice2_NextIsDefeat1() {
        assertEquals(QuestStep.DEFEAT_1, QuestStep.ROUND_2.getNextStep(2));
    }

    @Test
    void testRound3_Choice1_NextIsVictory() {
        assertEquals(QuestStep.VICTORY, QuestStep.ROUND_3.getNextStep(1));
    }

    @Test
    void testRound3_Choice2_NextIsDefeat2() {
        assertEquals(QuestStep.DEFEAT_2, QuestStep.ROUND_3.getNextStep(2));
    }

    @Test
    void testVictory_IsTerminal() {
        assertTrue(QuestStep.VICTORY.isTerminal());
    }

    @Test
    void testDefeat_IsTerminal() {
        assertTrue(QuestStep.DEFEAT.isTerminal());
    }

    @Test
    void testDefeat1_IsTerminal() {
        assertTrue(QuestStep.DEFEAT_1.isTerminal());
    }

    @Test
    void testDefeat2_IsTerminal() {
        assertTrue(QuestStep.DEFEAT_2.isTerminal());
    }

    @Test
    void testRound1_IsNotTerminal() {
        assertFalse(QuestStep.ROUND_1.isTerminal());
    }

    @Test
    void testRound2_IsNotTerminal() {
        assertFalse(QuestStep.ROUND_2.isTerminal());
    }

    @Test
    void testRound3_IsNotTerminal() {
        assertFalse(QuestStep.ROUND_3.isTerminal());
    }

    @Test
    void testPrologue_IsTerminal() {
        assertTrue(QuestStep.PROLOGUE.isTerminal());
    }

    @Test
    void testInvalidChoice_ReturnsCurrentStep() {
        assertEquals(QuestStep.ROUND_1, QuestStep.ROUND_1.getNextStep(0));
        assertEquals(QuestStep.ROUND_1, QuestStep.ROUND_1.getNextStep(3));
    }

    @Test
    void testGetTitle_NotNull() {
        assertNotNull(QuestStep.ROUND_1.getTitle());
        assertFalse(QuestStep.ROUND_1.getTitle().isEmpty());
    }

    @Test
    void testGetMessage_NotNull() {
        assertNotNull(QuestStep.ROUND_1.getMessage());
    }

    @Test
    void testGetOptionTexts_NotNull() {
        assertNotNull(QuestStep.ROUND_1.getOption1Text());
        assertNotNull(QuestStep.ROUND_1.getOption2Text());
    }

    @Test
    void testTerminalStep_OptionTextsAreNull() {
        assertNull(QuestStep.VICTORY.getOption1Text());
        assertNull(QuestStep.VICTORY.getOption2Text());
    }
}
