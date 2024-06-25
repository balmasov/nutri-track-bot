package com.nutritrack.bot.service.step.impl.command;

import com.nutritrack.bot.repository.model.User;
import com.nutritrack.bot.service.keyboard.KeyboardService;
import com.nutritrack.bot.service.parser.dto.CustomUpdate;
import com.nutritrack.bot.service.step.CommandHandler;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import javax.inject.Inject;

import static com.nutritrack.bot.config.MessageKey.HELP_MESSAGE;

public class HelpCommandHandler implements CommandHandler {

    private final KeyboardService keyboardService;

    @Inject
    public HelpCommandHandler(KeyboardService keyboardService) {
        this.keyboardService = keyboardService;
    }

    @Override
    public SendMessage handle(User user, CustomUpdate update) {
        //TODO add help message
        return keyboardService.createCookingOrEatingKeyboard(update, HELP_MESSAGE);
    }
}
