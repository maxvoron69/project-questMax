package com.quest;

/**
 * Состояние текущей игры, хранится в HTTP-сессии.
 */
public class GameSession {
    private String playerName;
    private QuestStep currentStep;

    public GameSession(String playerName, QuestStep currentStep) {
        this.playerName = playerName;
        this.currentStep = currentStep;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public QuestStep getCurrentStep() {
        return currentStep;
    }

    public void setCurrentStep(QuestStep currentStep) {
        this.currentStep = currentStep;
    }

    /**
     * Проверяет, завершена ли игра (победа или поражение).
     */
    public boolean isComplete() {
        return currentStep.isTerminal();
    }
}
