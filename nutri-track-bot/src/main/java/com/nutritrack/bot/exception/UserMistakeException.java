package com.nutritrack.bot.exception;

public abstract class UserMistakeException extends RuntimeException {

    public UserMistakeException(String message, Object... args) {
        super(String.format(message, args));
    }
}
