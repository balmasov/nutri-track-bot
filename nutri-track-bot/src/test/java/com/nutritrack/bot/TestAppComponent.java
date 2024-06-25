package com.nutritrack.bot;

import com.nutritrack.bot.config.dagger_config.AppComponent;
import com.nutritrack.bot.config.dagger_config.MessageConfigModel;
import com.nutritrack.bot.config.dagger_config.module.*;
import com.nutritrack.bot.module.TestBotModule;
import com.nutritrack.bot.module.TestDatabaseModule;
import com.nutritrack.bot.module.TestSqlServiceModule;
import com.nutritrack.bot.service.AbstractTest;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = {
        AppModule.class,
        TestBotModule.class, //test module
        TestDatabaseModule.class, //test module
        KeyboardServiceModule.class,
        ParamSetterModule.class,
        RepoModule.class,
        RepoServiceModule.class,
        TestSqlServiceModule.class, //testModule
        StepFactoryModule.class,
        StepServiceModule.class,
        ExceptionServiceModule.class,
        MessageCreationModule.class,
        CommandFactoryModule.class,
        CommandHandlerModule.class,
        UpdateParserModel.class,
        MessageConfigModel.class,
        SqlQueriesModule.class
})
public interface TestAppComponent extends AppComponent {
    void inject(AbstractTest test);

}
