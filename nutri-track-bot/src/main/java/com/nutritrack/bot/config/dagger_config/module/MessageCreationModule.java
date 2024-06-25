package com.nutritrack.bot.config.dagger_config.module;

import com.nutritrack.bot.config.MessageConfig;
import com.nutritrack.bot.service.message.MessageService;
import com.nutritrack.bot.service.message.impl.MessageServiceImpl;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class MessageCreationModule {

    @Provides
    @Singleton
    MessageService provideMsgCreationService(MessageConfig messageConfig) {
        return new MessageServiceImpl(messageConfig);
    }
}
