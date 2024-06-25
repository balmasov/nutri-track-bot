package com.nutritrack.bot.module;

import com.nutritrack.bot.service.telegram.CountCaloriesBot;
import dagger.Module;
import dagger.Provides;
import org.mockito.Mockito;

import javax.inject.Singleton;

@Module
public class TestBotModule {

    public static CountCaloriesBot mockCountCaloriesBot = Mockito.mock(CountCaloriesBot.class);

    @Singleton
    @Provides
    CountCaloriesBot provideMockDatabaseConnector() {
        return mockCountCaloriesBot;
    }
}
