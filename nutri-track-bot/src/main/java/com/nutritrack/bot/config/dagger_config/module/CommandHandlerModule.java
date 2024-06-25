package com.nutritrack.bot.config.dagger_config.module;

import com.nutritrack.bot.config.dagger_config.CommandKey;
import com.nutritrack.bot.repository.model.Command;
import com.nutritrack.bot.service.step.CommandHandler;
import com.nutritrack.bot.service.step.impl.command.CancelCommandHandler;
import com.nutritrack.bot.service.step.impl.command.DeleteCommandHandler;
import com.nutritrack.bot.service.step.impl.command.HelpCommandHandler;
import com.nutritrack.bot.service.step.impl.command.StartCommandHandler;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class CommandHandlerModule {

    @Binds
    @IntoMap
    @CommandKey(Command.START)
    abstract CommandHandler bindStartHandler(StartCommandHandler impl);

    @Binds
    @IntoMap
    @CommandKey(Command.CANCEL)
    abstract CommandHandler bindSCanselHandler(CancelCommandHandler impl);

    @Binds
    @IntoMap
    @CommandKey(Command.HELP)
    abstract CommandHandler bindSHelpHandler(HelpCommandHandler impl);

    @Binds
    @IntoMap
    @CommandKey(Command.DELETE)
    abstract CommandHandler bindDeleteHandler(DeleteCommandHandler impl);
}
