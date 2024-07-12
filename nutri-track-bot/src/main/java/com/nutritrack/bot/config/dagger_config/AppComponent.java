package com.nutritrack.bot.config.dagger_config;

import com.nutritrack.bot.LambdaHandler;
import com.nutritrack.bot.config.dagger_config.module.*;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = {
        AppModule.class,
        BotModule.class,
        DatabaseModule.class,
        KeyboardServiceModule.class,
        ParamSetterModule.class,
        RepoModule.class,
        RepoServiceModule.class,
        SqlServiceModule.class,
        StepFactoryModule.class,
        StepServiceModule.class,
        ExceptionServiceModule.class,
        MessageCreationModule.class,
        CommandFactoryModule.class,
        CommandHandlerModule.class,
        UpdateParserModel.class,
        MessageConfigModel.class,
        SqlQueriesModule.class,
        AbsSenderModule.class
})
public interface AppComponent {
    void inject(LambdaHandler handler);
}
