package com.nutritrack.bot.config.dagger_config.module;

import com.nutritrack.bot.service.telegram.CountCaloriesBot;
import com.nutritrack.bot.service.telegram.impl.CountCaloriesBotImpl;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class BotModule {
    @Provides
    @Singleton
    CountCaloriesBot provideCountCaloriesBot() {
        return new CountCaloriesBotImpl();
    }
}
