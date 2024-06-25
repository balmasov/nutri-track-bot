package com.nutritrack.bot.exception;

public class WrongInputException extends UserMistakeException {
    public WrongInputException(String message, Object... args) {
        super(message, args);
    }
}
