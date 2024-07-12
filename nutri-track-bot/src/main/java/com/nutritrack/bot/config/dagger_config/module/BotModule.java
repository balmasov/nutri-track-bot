package com.nutritrack.bot.config.dagger_config.module;

import com.nutritrack.bot.service.telegram.CountCaloriesBot;
import com.nutritrack.bot.service.telegram.impl.CountCaloriesBotImpl;
import dagger.Module;
import dagger.Provides;
import org.telegram.telegrambots.meta.bots.AbsSender;

import javax.inject.Singleton;

@Module
public class BotModule {
    @Provides
    @Singleton
    CountCaloriesBot provideCountCaloriesBot(AbsSender sender) {
        return new CountCaloriesBotImpl(sender);
    }
}
