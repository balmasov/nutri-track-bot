package com.nutritrack.bot.config.dagger_config.module;

import com.nutritrack.bot.repository.sql_service.ParamSetter;
import com.nutritrack.bot.repository.sql_service.impl.setter.*;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoSet;

import javax.inject.Singleton;

@Module
public class ParamSetterModule {
    @Singleton
    @Provides
    @IntoSet
    ParamSetter provideBigIntegerSetter() {
        return new BigIntegerSetter();
    }

    @Singleton
    @Provides
    @IntoSet
    ParamSetter provideNullSetter() {
        return new NullSetter();
    }

    @Singleton
    @Provides
    @IntoSet
    ParamSetter provideEnumSetter() {
        return new EnumSetter();
    }

    @Singleton
    @Provides
    @IntoSet
    ParamSetter provideIntegerSetter() {
        return new IntegerSetter();
    }

    @Singleton
    @Provides
    @IntoSet
    ParamSetter provideLongSetter() {
        return new LongSetter();
    }

    @Singleton
    @Provides
    @IntoSet
    ParamSetter provideStringSetter() {
        return new StringSetter();
    }

    @Singleton
    @Provides
    @IntoSet
    ParamSetter provideDoubleSetter() {
        return new DoubleSetters();
    }

}
