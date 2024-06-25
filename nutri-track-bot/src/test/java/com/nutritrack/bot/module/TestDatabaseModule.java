package com.nutritrack.bot.module;

import com.nutritrack.bot.repository.connectors.DatabaseConnector;
import dagger.Module;
import dagger.Provides;
import org.mockito.Mockito;

import javax.inject.Singleton;

@Module
public class TestDatabaseModule {

    public static DatabaseConnector mockDbConnector = Mockito.mock(DatabaseConnector.class);

    @Singleton
    @Provides
    DatabaseConnector provideMockDatabaseConnector() {
        return mockDbConnector;
    }
}
