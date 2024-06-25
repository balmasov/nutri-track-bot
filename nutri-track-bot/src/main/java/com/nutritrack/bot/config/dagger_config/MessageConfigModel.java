package com.nutritrack.bot.config.dagger_config;

import com.nutritrack.bot.config.MessageConfig;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class MessageConfigModel {

    @Provides
    @Singleton
    MessageConfig provideConfigFile() {
        // Load the default properties file
        return new MessageConfig("messages.properties");
    }
}

