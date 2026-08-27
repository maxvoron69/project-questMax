package com.quest;

import java.util.HashMap;
import java.util.Map;

/**
 * Перечисление всех шагов квеста с логикой переходов.
 * Каждый шаг содержит заголовок, текст сцены и варианты выбора.
 */
public enum QuestStep {
    PROLOGUE("ПРОЛОГ",
            "Ты стоишь в космическом порту и готов подняться на борт своего корабля.<br>" +
                    "Разве ты не об этом мечтал?<br>" +
                    "Стать капитаном галактического судна с экипажем, который будет совершать подвиги под твоим командованием.<br>" +
                    "<b>Так что вперед!</b>"),

    ROUND_1("Ты потерял память. Принять вызов НЛО?",
            "Когда ты поднялся на борт корабля, тебя поприветствовала девушка с чёрной папкой в руках:<br>" +
                    "— Здравствуйте, командир! Я Звездаида — ваша помощница. Видите? Там в углу пьёт кофе<br>" +
                    "наш штурман — сержант Перегарный Шлейф, под штурвалом спит наш бортмеханик — Чёрный Штуцер,<br>" +
                    "а фотографирует его Полярный Меридиан — наш навигатор.",
            "Принять вызов НЛО", "Отклонить вызов"),

    ROUND_2("Ты принял вызов. Поднимаешься на мостик к капитану?",
            "Тебя вернули домой после того, как ты принял вызов. Но что дальше?",
            "Подняться на мостик", "Отказаться подниматься на мостик"),

    ROUND_3("Ты поднялся на мостик. Ты кто?",
            "Капитан НЛО смотрит на тебя с подозрением. Нужно объяснить, кто ты.",
            "Рассказать правду о себе", "Солгать о себе"),

    VICTORY("Тебя вернули домой. ПОБЕДА!",
            "Поздравляем! Ты прошёл все испытания и стал настоящим капитаном галактического судна!"),

    DEFEAT("ТЫ ОТКЛОНИЛ ВЫЗОВ. ПОРАЖЕНИЕ!",
            "Ты упустил свой шанс на великое приключение."),

    DEFEAT_1("ТЫ НЕ ПОШЁЛ НА ПЕРЕГОВОРЫ. ПОРАЖЕНИЕ!",
            "Ты упустил свой шанс на великое приключение."),

    DEFEAT_2("ТВОЮ ЛОЖЬ РАЗОБЛАЧИЛИ. ПОРАЖЕНИЕ!",
            "Ты упустил свой шанс на великое приключение.");

    private final String title;
    private final String message;
    private final String option1Text;
    private final String option2Text;

    QuestStep(String title, String message) {
        this(title, message, null, null);
    }

    QuestStep(String title, String message,
              String option1Text, String option2Text) {
        this.title = title;
        this.message = message;
        this.option1Text = option1Text;
        this.option2Text = option2Text;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public String getOption1Text() {
        return option1Text;
    }

    public String getOption2Text() {
        return option2Text;
    }

    /**
     * Определяет следующий шаг на основе текущего шага и выбора игрока.
     * @param choice 1 или 2
     * @return следующий шаг
     */
    public QuestStep getNextStep(int choice) {
        Map<QuestStep, int[]> transitions = getTransitions();
        int[] next = transitions.get(this);
        if (next == null || choice < 1 || choice > 2) {
            return this;
        }
        return values()[next[choice - 1]];
    }

    private static Map<QuestStep, int[]> getTransitions() {
        Map<QuestStep, int[]> transitions = new HashMap<>();
        // ROUND_1: [ROUND_2, DEFEAT]
        transitions.put(ROUND_1, new int[]{ROUND_2.ordinal(), DEFEAT.ordinal()});
        // ROUND_2: [ROUND_3, DEFEAT_1]
        transitions.put(ROUND_2, new int[]{ROUND_3.ordinal(), DEFEAT_1.ordinal()});
        // ROUND_3: [VICTORY, DEFEAT_2]
        transitions.put(ROUND_3, new int[]{VICTORY.ordinal(), DEFEAT_2.ordinal()});
        return transitions;
    }

    /**
     * Проверяет, является ли шаг завершающим (победа или поражение).
     */
    public boolean isTerminal() {
        return option1Text == null && option2Text == null;
    }
}
