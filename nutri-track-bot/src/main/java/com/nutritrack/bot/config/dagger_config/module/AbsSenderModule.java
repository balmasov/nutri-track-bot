package com.nutritrack.bot.config.dagger_config.module;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dagger.Module;
import dagger.Provides;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.bots.AbsSender;

import javax.inject.Singleton;
import java.util.List;
import java.util.function.Function;

@Module
public class AbsSenderModule {

    private static final String BOT_TOKEN = System.getenv("BOT_TOKEN");
    private static final String BOT_NAME = System.getenv("BOT_NAME");
    private static final Function<Update, BotApiMethod> onUpdate = x -> null;

    @Provides
    @Singleton
    AbsSender provideCountCaloriesBot() {
        return new TelegramWebhookBot(BOT_TOKEN) {

            {
                registerCommands();
            }

            @Override
            public BotApiMethod onWebhookUpdateReceived(Update update) {
                return onUpdate.apply(update);
            }

            @Override
            public String getBotUsername() {
                return BOT_NAME;
            }

            @Override
            public String getBotPath() {
                return BOT_NAME;
            }

            private void registerCommands() {
                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    List<BotCommand> commands = objectMapper.readValue(
                            getClass().getClassLoader().getResourceAsStream("commands.json"),
                            new TypeReference<>() {});

                    SetMyCommands setMyCommands = new SetMyCommands();
                    setMyCommands.setCommands(commands);

                    execute(setMyCommands);
                } catch (Exception e) {
                    throw new RuntimeException("Couldn't execute registerCommands method. Exception:\n" + e);
                }
            }
        };
    }
}
