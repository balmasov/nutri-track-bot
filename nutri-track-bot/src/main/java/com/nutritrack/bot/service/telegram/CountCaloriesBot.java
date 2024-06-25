package com.nutritrack.bot.service.telegram;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;

import javax.validation.constraints.NotNull;

public interface CountCaloriesBot {

    Message execute(@NotNull SendMessage message);

    void execute(@NotNull EditMessageText message);
}
