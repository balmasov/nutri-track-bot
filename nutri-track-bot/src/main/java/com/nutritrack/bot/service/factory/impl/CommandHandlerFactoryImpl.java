package com.nutritrack.bot.service.factory.impl;

import com.nutritrack.bot.repository.model.Command;
import com.nutritrack.bot.service.factory.CommandHandlerFactory;
import com.nutritrack.bot.service.step.CommandHandler;

import javax.inject.Inject;
import javax.inject.Provider;
import java.util.Map;

public class CommandHandlerFactoryImpl implements CommandHandlerFactory {
    private final Map<Command, Provider<CommandHandler>> commandsMap;

    @Inject
    public CommandHandlerFactoryImpl(Map<Command, Provider<CommandHandler>> commandsMap) {
        this.commandsMap = commandsMap;
    }

    @Override
    public CommandHandler getHandler(Command command) {
        return commandsMap.get(command).get();
    }
}
