package com.nutritrack.bot.config.dagger_config;

import com.nutritrack.bot.repository.model.UserStep;
import dagger.MapKey;

@MapKey
public @interface UserStepKey {
    UserStep value();
}
