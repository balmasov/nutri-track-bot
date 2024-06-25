package com.nutritrack.bot.service.message;

import com.nutritrack.bot.config.MessageKey;
import com.nutritrack.bot.service.parser.dto.CustomUpdate;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;

import java.util.Locale;

public interface MessageService {

    String wrongNumberFormatInputMsg(CustomUpdate update);

    String getMessage(MessageKey key, Locale locale);

    SendMessage createSendMessage(CustomUpdate update, MessageKey messageKey, Object... args);

    EditMessageText createEditMessage(CustomUpdate update, MessageKey key, Object... args);
}
