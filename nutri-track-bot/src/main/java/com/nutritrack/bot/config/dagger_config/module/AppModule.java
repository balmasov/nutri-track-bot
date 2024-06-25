package com.nutritrack.bot.config.dagger_config.module;

import com.fasterxml.jackson.databind.ObjectMapper;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class AppModule {

    @Provides
    @Singleton
    ObjectMapper provideObjectMapper() {
        return new ObjectMapper();
    }

}
