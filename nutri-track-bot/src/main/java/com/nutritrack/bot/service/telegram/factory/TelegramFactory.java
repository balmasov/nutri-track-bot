package com.nutritrack.bot.service.telegram.factory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.List;
import java.util.function.Function;

public class TelegramFactory {
    public static AbsSender sender(String token, String username) {
        return webhookBot(token, username, x -> null);
    }

    public static TelegramWebhookBot webhookBot(
            String token, String username, Function<Update, BotApiMethod> onUpdate) {
        return webhookBot(token, username, username, onUpdate);
    }

    public static TelegramWebhookBot webhookBot(String token,
                                                String username,
                                                String path,
                                                Function<Update, BotApiMethod> onUpdate) {
        return new TelegramWebhookBot(token) {

            {
                registerCommands("en");
            }

            @Override
            public BotApiMethod onWebhookUpdateReceived(Update update) {
                if (update.hasMessage() && update.getMessage().getFrom() != null) {
                    String userLanguage = update.getMessage().getFrom().getLanguageCode();
                    registerCommands(userLanguage);
                }
                return onUpdate.apply(update);
            }

            @Override
            public String getBotUsername() {
                return username;
            }

            @Override
            public String getBotPath() {
                return path;
            }

            private void registerCommands(String languageCode) {
                try {
                    String commandsFile = "commands_en.json";

                    if ("ru".equals(languageCode)) {
                        commandsFile = "commands_ru.json";
                    }

                    ObjectMapper objectMapper = new ObjectMapper();
                    List<BotCommand> commands = objectMapper.readValue(
                            getClass().getClassLoader().getResourceAsStream(commandsFile),
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
