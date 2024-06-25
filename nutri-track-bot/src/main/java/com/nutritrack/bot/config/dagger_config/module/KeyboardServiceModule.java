package com.nutritrack.bot.config.dagger_config.module;

import com.nutritrack.bot.service.keyboard.KeyboardService;
import com.nutritrack.bot.service.keyboard.impl.KeyboardServiceImpl;
import com.nutritrack.bot.service.message.MessageService;
import com.nutritrack.bot.service.telegram.CountCaloriesBot;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class KeyboardServiceModule {

    @Provides
    @Singleton
    KeyboardService provideKeyboardService(MessageService msgService, CountCaloriesBot bot) {
        return new KeyboardServiceImpl(msgService, bot);
    }
}
