package com.nutritrack.bot.config.dagger_config.module;

import com.nutritrack.bot.repository.model.Command;
import com.nutritrack.bot.service.factory.CommandHandlerFactory;
import com.nutritrack.bot.service.factory.impl.CommandHandlerFactoryImpl;
import com.nutritrack.bot.service.step.CommandHandler;
import dagger.Module;
import dagger.Provides;

import javax.inject.Provider;
import javax.inject.Singleton;
import java.util.Map;

@Module
public class CommandFactoryModule {

    @Provides
    @Singleton
    CommandHandlerFactory provideCommandFactory(Map<Command, Provider<CommandHandler>> serviceMap) {
        return new CommandHandlerFactoryImpl(serviceMap);
    }

}
