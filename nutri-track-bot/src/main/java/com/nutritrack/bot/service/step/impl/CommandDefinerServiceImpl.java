package com.nutritrack.bot.service.step.impl;

import com.nutritrack.bot.exception.WrongInputException;
import com.nutritrack.bot.repository.model.Command;
import com.nutritrack.bot.repository.model.User;
import com.nutritrack.bot.repository.model.UserStep;
import com.nutritrack.bot.service.factory.CommandHandlerFactory;
import com.nutritrack.bot.service.parser.dto.CustomUpdate;
import com.nutritrack.bot.service.step.StepService;
import com.nutritrack.bot.service.step.StepState;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import javax.inject.Inject;

public class CommandDefinerServiceImpl implements StepService, StepState {

    private final CommandHandlerFactory factory;

    @Inject
    public CommandDefinerServiceImpl(CommandHandlerFactory factory) {
        this.factory = factory;
    }

    @Override
    public SendMessage doStep(User user, CustomUpdate update) {
        String userMsg = update.getUserMsg();
        try {
            Command command = Command.get(extractCommand(userMsg));

            return factory.getHandler(command)
                    .handle(user, update);

        } catch (IllegalArgumentException e) {
            throw new WrongInputException("Command %s does not exist", userMsg);
        }
    }

    private String extractCommand(String input) {
        if (input.startsWith("/")) {
            String[] parts = input.split(" ", 2);
            return parts[0].substring(1);
        }
        throw new RuntimeException("No command in string: " + input);
    }

    @Override
    public UserStep step() {
        return UserStep.COMMAND;
    }
}
