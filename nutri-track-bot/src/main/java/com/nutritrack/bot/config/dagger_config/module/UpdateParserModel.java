package com.nutritrack.bot.config.dagger_config.module;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nutritrack.bot.service.message.MessageService;
import com.nutritrack.bot.service.parser.UpdateParser;
import com.nutritrack.bot.service.parser.impl.UpdateParserImpl;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class UpdateParserModel {

    @Provides
    @Singleton
    UpdateParser provideStepFactory(ObjectMapper objectMapper, MessageService messageService) {
        return new UpdateParserImpl(objectMapper, messageService);
    }
}
