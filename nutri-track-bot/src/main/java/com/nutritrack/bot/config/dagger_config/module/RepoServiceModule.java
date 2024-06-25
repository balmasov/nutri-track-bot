package com.nutritrack.bot.config.dagger_config.module;

import com.nutritrack.bot.repository.FoodRepo;
import com.nutritrack.bot.repository.UserRepo;
import com.nutritrack.bot.service.entity_service.food_service.FoodService;
import com.nutritrack.bot.service.entity_service.food_service.Impl.FoodServiceImpl;
import com.nutritrack.bot.service.entity_service.user_service.UserService;
import com.nutritrack.bot.service.entity_service.user_service.impl.UserServiceImpl;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class RepoServiceModule {

    @Provides
    @Singleton
    UserService provideUserService(UserRepo userRepository) {
        return new UserServiceImpl(userRepository);
    }

    @Provides
    @Singleton
    FoodService provideFoodService(FoodRepo foodRepository) {
        return new FoodServiceImpl(foodRepository);
    }
}
