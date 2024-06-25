package com.nutritrack.bot.exception;

import com.nutritrack.bot.service.parser.dto.CustomUpdate;

public class UpdateMessageException extends UserMistakeException {

    private CustomUpdate update;

    public UpdateMessageException(CustomUpdate update, String message, Object... args) {
        super(message, args);
        this.update = update;
    }

    public CustomUpdate getUpdate() {
        return update;
    }

    public void setUpdate(CustomUpdate update) {
        this.update = update;
    }
}
