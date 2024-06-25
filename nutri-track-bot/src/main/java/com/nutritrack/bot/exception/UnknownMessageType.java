package com.nutritrack.bot.exception;

public class UnknownMessageType extends UserMistakeException {


    public UnknownMessageType(String message, Object... args) {
        super(message, args);
    }
}
