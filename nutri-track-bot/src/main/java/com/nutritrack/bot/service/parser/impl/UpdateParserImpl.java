package com.nutritrack.bot.service.parser.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nutritrack.bot.config.MessageKey;
import com.nutritrack.bot.exception.UnknownMessageType;
import com.nutritrack.bot.exception.UpdateMessageException;
import com.nutritrack.bot.service.message.MessageService;
import com.nutritrack.bot.service.parser.UpdateParser;
import com.nutritrack.bot.service.parser.dto.CustomUpdate;
import com.nutritrack.bot.service.parser.dto.WebHookBodyRequest;
import org.apache.commons.lang3.LocaleUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import static com.nutritrack.bot.config.MessageKey.UPDATE_MSG;

public class UpdateParserImpl implements UpdateParser {
    private final static Logger logger = LoggerFactory.getLogger(UpdateParserImpl.class);

    private final ObjectMapper objectMapper;
    private final MessageService messageService;

    public UpdateParserImpl(ObjectMapper objectMapper, MessageService messageService) {
        this.objectMapper = objectMapper;
        this.messageService = messageService;
    }

    @Override
    public CustomUpdate parse(String input) {
        Update update = parsUpdate(input);

        return createCustomUpdate(update);
    }

    //TODO refactor needed
    private CustomUpdate createCustomUpdate(Update update) {

        if (update.hasMessage()) {
            return messageCustomUpdate(update);
        } else if (update.hasCallbackQuery()) {
            return callbackQueryCustomUpdate(update);
        } else if (update.hasEditedMessage()) {
            String message = messageService.getMessage(UPDATE_MSG, LocaleUtils.toLocale(update.getEditedMessage().getFrom().getLanguageCode()));
            CustomUpdate update1 = editedMessageCustomUpdate(update);
            throw new UpdateMessageException(update1, message);
        } else {
            logger.error("No Message Or CallbackQuery.\nUpdate: {}", update);
            throw new UnknownMessageType("Unknown Message Type");
        }
    }

    private CustomUpdate callbackQueryCustomUpdate(Update update) {
        return getCustomUpdate(
                update.getCallbackQuery().getFrom(),
                null,
                update.getCallbackQuery().getData(),
                update.getCallbackQuery().getMessage().getChatId(),
                update.getCallbackQuery().getFrom().getLanguageCode(),
                update);
    }

    private CustomUpdate messageCustomUpdate(Update update) {
        return getCustomUpdate(
                update.getMessage().getFrom(),
                update.getMessage().getText(),
                null,
                update.getMessage().getChatId(),
                update.getMessage().getFrom().getLanguageCode(),
                update);
    }

    private CustomUpdate editedMessageCustomUpdate(Update update) {
        return getCustomUpdate(
                update.getEditedMessage().getFrom(),
                update.getEditedMessage().getText(),
                null,
                update.getEditedMessage().getChatId(),
                update.getEditedMessage().getFrom().getLanguageCode(),
                update);
    }

    private static CustomUpdate getCustomUpdate(User user,
                                                String userMsg,
                                                String callbackQueryData,
                                                Long chatId,
                                                String locale,
                                                Update update) {
        return new CustomUpdate.Builder()
                .telegramUser(user)
                .userMsg(userMsg)
                .callbackQueryData(callbackQueryData)
                .chatId(chatId)
                .locale(locale)
                .build(update);
    }

    private Update parsUpdate(String input) {
        try {
            WebHookBodyRequest body = objectMapper.readValue(input, WebHookBodyRequest.class);

            Update update = objectMapper.readValue(body.getBody(), Update.class);

            logger.info("Parsed Update: {}", update);
            return update;
        } catch (Exception e) {
            logger.error("Exception while parsing update: " + e);
            throw new RuntimeException(e);
        }
    }


}
