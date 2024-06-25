package com.nutritrack.bot.service.message.impl;

import com.nutritrack.bot.config.MessageConfig;
import com.nutritrack.bot.config.MessageKey;
import com.nutritrack.bot.service.message.MessageService;
import com.nutritrack.bot.service.parser.dto.CustomUpdate;
import com.nutritrack.bot.util.NumberExtractHelper;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;

import javax.inject.Inject;
import java.util.Locale;
import java.util.Random;

import static com.nutritrack.bot.config.MessageKey.NUMBER_FORMAT_WRONG_INPUT;
import static com.nutritrack.bot.config.MessageKey.NUMBER_FORMAT_WRONG_INPUT_GUESS;

public class MessageServiceImpl implements MessageService {

    private final MessageConfig messageConfig;

    @Inject
    public MessageServiceImpl(MessageConfig messageConfig) {
        this.messageConfig = messageConfig;
    }

    @Override
    public String wrongNumberFormatInputMsg(CustomUpdate update) {
        String number = NumberExtractHelper.extractDigitsOrEmptyStr(update.getUserMsg());

        String msg;
        if (NumberExtractHelper.onlyDigits(number)) {
            msg = getFormatMsg(update, NUMBER_FORMAT_WRONG_INPUT_GUESS, number);
        } else {
            Random r = new Random();
            msg = getFormatMsg(update, NUMBER_FORMAT_WRONG_INPUT, r.nextInt(100));
        }

        return msg;
    }

    @Override
    public SendMessage createSendMessage(CustomUpdate update, MessageKey messageKey, Object... args) {
        String message = messageKey == null
                ? ""
                : getFormatMsg(update, messageKey, args);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(message);
        sendMessage.setChatId(update.getChatId());

        return sendMessage;
    }

    @Override
    public String getMessage(MessageKey key, Locale locale) {
        return messageConfig.getLocalMessageOrDefault(key, locale);
    }

    @Override
    public EditMessageText createEditMessage(CustomUpdate update, MessageKey key, Object... args) {
        String updatedText = getFormatMsg(update, key, args);

        EditMessageText editMessage = new EditMessageText();
        editMessage.setChatId(update.getChatId());
        editMessage.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
        editMessage.setText(updatedText);

        return editMessage;
    }

    private String getFormatMsg(CustomUpdate update, MessageKey key, Object... args) {
        Locale locale = update == null
                ? null
                : update.getLocale();
        return String.format(messageConfig.getLocalMessageOrDefault(key, locale), args);
    }
}
