package com.nutritrack.bot.service.telegram.impl;

import com.nutritrack.bot.service.telegram.CountCaloriesBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

import javax.inject.Inject;

public class CountCaloriesBotImpl implements CountCaloriesBot {

    private final AbsSender sender;

    @Inject
    public CountCaloriesBotImpl(AbsSender sender) {
        this.sender = sender;
    }

    @Override
    public Message execute(SendMessage message) {
        Message sentMsg;

        try {
            sentMsg = sender.execute(message);
        } catch (Exception e) {
            String msg = "Failed while sending message to bot chat:\n" + e;
            throw new RuntimeException(msg);
        }
        return sentMsg;
    }

    @Override
    public void execute(EditMessageText message) {
        try {
            sender.execute(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
