package com.nutritrack.bot.config.dagger_config.module;

import com.nutritrack.bot.repository.FoodRepo;
import com.nutritrack.bot.repository.SqlQuery;
import com.nutritrack.bot.repository.UserRepo;
import com.nutritrack.bot.repository.impl.FoodRepoImpl;
import com.nutritrack.bot.repository.impl.UserRepoImpl;
import com.nutritrack.bot.repository.sql_service.SQLService;
import dagger.Module;
import dagger.Provides;

import javax.inject.Named;
import javax.inject.Singleton;
import java.util.Map;

@Module
public class RepoModule {

    @Provides
    @Singleton
    FoodRepo provideFoodRepo(@Named("FoodSQLService") SQLService sqlService, Map<SqlQuery, String> sqlQueries) {
        return new FoodRepoImpl(sqlService, sqlQueries);
    }

    @Provides
    @Singleton
    UserRepo provideUserRepo(@Named("UserSQLService")SQLService sqlService, Map<SqlQuery, String> sqlQueries) {
        return new UserRepoImpl(sqlService, sqlQueries);
    }
}
