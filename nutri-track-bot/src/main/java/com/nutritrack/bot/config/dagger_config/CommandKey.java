package com.nutritrack.bot.config.dagger_config;

import com.nutritrack.bot.repository.model.Command;
import dagger.MapKey;

@MapKey
public @interface CommandKey {

    Command value();
}
