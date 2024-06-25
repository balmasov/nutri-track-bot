package com.nutritrack.bot.config.dagger_config.module;

import com.nutritrack.bot.service.exception.ExceptionService;
import com.nutritrack.bot.service.exception.impl.ExceptionServiceImpl;
import com.nutritrack.bot.service.message.MessageService;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class ExceptionServiceModule {

    @Provides
    @Singleton
    ExceptionService bindExceptionService(MessageService msgService) {
        return new ExceptionServiceImpl(msgService);
    }
}
