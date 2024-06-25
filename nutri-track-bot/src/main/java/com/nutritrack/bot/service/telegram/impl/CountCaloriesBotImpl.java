package com.nutritrack.bot.service.telegram.impl;

import com.nutritrack.bot.service.telegram.CountCaloriesBot;
import com.nutritrack.bot.service.telegram.factory.TelegramFactory;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class CountCaloriesBotImpl implements CountCaloriesBot {
    private static final AbsSender SENDER = TelegramFactory.sender(System.getenv("BOT_TOKEN"), System.getenv("BOT_NAME"));

    @Override
    public Message execute(SendMessage message) {
        Message sentMsg;

        try {
            sentMsg = SENDER.execute(message);
        } catch (Exception e) {
            String msg = "Failed while sending message to bot chat:\n" + e;
            throw new RuntimeException(msg);
        }
        return sentMsg;
    }

    @Override
    public void execute(EditMessageText message) {
        try {
            SENDER.execute(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
