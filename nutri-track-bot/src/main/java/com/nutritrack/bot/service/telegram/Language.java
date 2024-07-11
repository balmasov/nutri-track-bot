package com.nutritrack.bot.service.telegram;

public enum Language {
    EN("en"),
    RU("ru");

    private final String code;

    Language(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
