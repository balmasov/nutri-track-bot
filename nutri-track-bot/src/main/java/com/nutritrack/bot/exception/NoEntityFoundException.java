package com.nutritrack.bot.exception;

public class NoEntityFoundException extends UserMistakeException {
    public NoEntityFoundException(String message, Object... args) {
        super(message, args);
    }
}
