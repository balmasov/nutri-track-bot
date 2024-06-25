package com.nutritrack.bot.config.dagger_config.module;

import com.nutritrack.bot.repository.connectors.DatabaseConnector;
import com.nutritrack.bot.repository.connectors.DatabaseConnectorImpl;
import dagger.Module;
import dagger.Provides;

import javax.inject.Named;
import javax.inject.Singleton;

@Module
public class DatabaseModule {
    private static final String CCH_PASS = "CCH_PASS";
    private static final String CCH_URL = "CCH_URL";
    private static final String CCH_USER = "CCH_USER";

    @Provides
    @Singleton
    @Named("url")
    String provideDatabaseUrl() {
        return System.getenv(CCH_URL);
    }

    @Provides
    @Singleton
    @Named("user")
    String provideDatabaseUser() {
        return System.getenv(CCH_USER);
    }

    @Provides
    @Singleton
    @Named("pass")
    String provideDatabasePassword() {
        return System.getenv(CCH_PASS);
    }

    @Provides
    @Singleton
    DatabaseConnector provideDevProdDatabaseConnector(@Named("url") String dbUrl, @Named("user") String user, @Named("pass") String pass) {
        return new DatabaseConnectorImpl(dbUrl, user, pass);
    }
}
