package com.nutritrack.bot.service.factory;

import com.nutritrack.bot.repository.model.Command;
import com.nutritrack.bot.service.step.CommandHandler;

public interface CommandHandlerFactory {

    CommandHandler getHandler(Command command);
}
