package com.nutritrack.bot.service.telegram.factory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nutritrack.bot.service.telegram.Language;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.List;
import java.util.Locale;
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

            @Override
            public BotApiMethod onWebhookUpdateReceived(Update update) {
                String languageCode = getLanguageCode(update);
                registerCommands(languageCode);
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
                    ObjectMapper objectMapper = new ObjectMapper();
                    List<BotCommand> commands;

                    if (Language.RU.getCode().equals(languageCode)) {
                        commands = objectMapper.readValue(
                                getClass().getClassLoader().getResourceAsStream("commands_ru.json"),
                                new TypeReference<>() {});
                    } else {
                        commands = objectMapper.readValue(
                                getClass().getClassLoader().getResourceAsStream("commands.json"),
                                new TypeReference<>() {});
                    }

                    SetMyCommands setMyCommands = new SetMyCommands();
                    setMyCommands.setCommands(commands);

                    execute(setMyCommands);
                } catch (Exception e) {
                    throw new RuntimeException("Couldn't execute registerCommands method. Exception:\n" + e);
                }
            }

            private String getLanguageCode(Update update) {
                // Пример получения языка из объекта Update
                if (update.getMessage() != null && update.getMessage().getFrom() != null) {
                    return update.getMessage().getFrom().getLanguageCode();
                } else if (update.getCallbackQuery() != null && update.getCallbackQuery().getFrom() != null) {
                    return update.getCallbackQuery().getFrom().getLanguageCode();
                }
                return Locale.getDefault().getLanguage(); // Возвращает язык по умолчанию
            }
        };
    }
}
