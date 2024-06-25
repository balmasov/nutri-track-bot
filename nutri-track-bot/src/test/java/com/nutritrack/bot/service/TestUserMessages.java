package com.nutritrack.bot.service;

public enum TestUserMessages {
    COOKING("cooking"),
    NAME_OF_COOKING_FOOD("rice"),
    COOKING_RAW_WEIGHT("200"),
    COOKED_WEIGHT("430"),
    EATING("eating"),
    WHAT_FOOD_TO_EAT("rice"),
    EATING_RAW_WEIGHT("35");

    private final String message;
    TestUserMessages(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
}
