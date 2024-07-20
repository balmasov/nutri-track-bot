package com.nutritrack.bot.service.telegram;

import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.validation.constraints.NotNull;

public interface CountCaloriesBot {

    Message execute(@NotNull SendMessage message);

    void execute(@NotNull EditMessageText message);

    Boolean execute(@NotNull SetMyCommands setMyCommands) throws TelegramApiException;

    AbsSender getSender();
}
