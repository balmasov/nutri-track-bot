package com.nutritrack.bot.module;

import com.nutritrack.bot.repository.sql_service.SQLService;
import dagger.Module;
import dagger.Provides;
import org.mockito.Mockito;

import javax.inject.Named;
import javax.inject.Singleton;

@Module
public class TestSqlServiceModule {

    public static SQLService mockSqlService = Mockito.mock(SQLService.class);

    @Provides
    @Singleton
    @Named("UserSQLService")
    SQLService provideUserSQLService() {
        return mockSqlService;
    }

    @Provides
    @Singleton
    @Named("FoodSQLService")
    SQLService provideFoodSQLService() {
        return mockSqlService;
    }
}
