package com.nutritrack.bot.service.step;

import com.nutritrack.bot.repository.model.User;
import com.nutritrack.bot.service.parser.dto.CustomUpdate;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface CommandHandler {

    SendMessage handle(User user, CustomUpdate update);
}
