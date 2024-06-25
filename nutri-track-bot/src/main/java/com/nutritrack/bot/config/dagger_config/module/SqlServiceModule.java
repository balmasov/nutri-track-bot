package com.nutritrack.bot.config.dagger_config.module;

import com.nutritrack.bot.repository.connectors.DatabaseConnector;
import com.nutritrack.bot.repository.model.mapper.ResultSetMapper;
import com.nutritrack.bot.repository.model.mapper.impl.FoodMapper;
import com.nutritrack.bot.repository.model.mapper.impl.UserMapper;
import com.nutritrack.bot.repository.sql_service.ParamSetter;
import com.nutritrack.bot.repository.sql_service.SQLService;
import com.nutritrack.bot.repository.sql_service.impl.SQLServiceImpl;
import dagger.Module;
import dagger.Provides;

import javax.inject.Named;
import javax.inject.Singleton;
import java.util.Set;

@Module
public class SqlServiceModule {
    @Provides
    @Singleton
    @Named("UserSQLService")
    SQLService provideUserSQLService(Set<ParamSetter> setters, @Named("UserMapper") ResultSetMapper<?> mapper, DatabaseConnector dbConnector) {
        return new SQLServiceImpl(setters, mapper, dbConnector);
    }

    @Provides
    @Singleton
    @Named("FoodSQLService")
    SQLService provideFoodSQLService(Set<ParamSetter> setters, @Named("FoodMapper") ResultSetMapper<?> mapper, DatabaseConnector dbConnector) {
        return new SQLServiceImpl(setters, mapper, dbConnector);
    }

    @Provides
    @Named("UserMapper")
    ResultSetMapper<?> provideUserMapper() {
        return new UserMapper();
    }

    @Provides
    @Named("FoodMapper")
    ResultSetMapper<?> provideFoodMapper() {
        return new FoodMapper();
    }
}
