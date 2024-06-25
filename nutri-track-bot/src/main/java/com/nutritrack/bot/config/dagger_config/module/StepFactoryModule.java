package com.nutritrack.bot.config.dagger_config.module;

import com.nutritrack.bot.repository.model.UserStep;
import com.nutritrack.bot.service.factory.StepFactory;
import com.nutritrack.bot.service.factory.impl.StepFactoryImpl;
import com.nutritrack.bot.service.step.StepService;
import dagger.Module;
import dagger.Provides;

import javax.inject.Provider;
import javax.inject.Singleton;
import java.util.Map;

@Module
public class StepFactoryModule {

    @Provides
    @Singleton
    StepFactory provideStepFactory(Map<UserStep, Provider<StepService>> serviceMap) {
        return new StepFactoryImpl(serviceMap);
    }
}
