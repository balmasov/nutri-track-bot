package com.nutritrack.bot.service.exception.impl;

import com.nutritrack.bot.exception.UpdateMessageException;
import com.nutritrack.bot.exception.UserMistakeException;
import com.nutritrack.bot.service.exception.ExceptionService;
import com.nutritrack.bot.service.message.MessageService;
import com.nutritrack.bot.service.parser.dto.CustomUpdate;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import javax.inject.Inject;

import static com.nutritrack.bot.config.MessageKey.SERVER_ERR_MSG;
import static com.nutritrack.bot.config.MessageKey.USER_ERR_MSG;

public class ExceptionServiceImpl implements ExceptionService {

    private final MessageService msgService;

    @Inject
    public ExceptionServiceImpl(MessageService msgService) {
        this.msgService = msgService;
    }

    @Override
    public SendMessage handleException(Exception e, CustomUpdate update) {

        if (e instanceof UpdateMessageException) {
            update = ((UpdateMessageException) e).getUpdate();
        }

        if (e instanceof UserMistakeException) {
            return userMistakeException(e, update);
        }

        return serverError(update);
    }

    private SendMessage userMistakeException(Exception e, CustomUpdate update) {
        return msgService.createSendMessage(update, USER_ERR_MSG, e.getMessage());
    }

    private SendMessage serverError(CustomUpdate update) {

        return msgService.createSendMessage(update, SERVER_ERR_MSG);
    }
}
