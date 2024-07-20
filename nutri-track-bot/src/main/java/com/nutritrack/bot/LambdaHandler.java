package com.nutritrack.bot;

import com.nutritrack.bot.config.dagger_config.DaggerAppComponent;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nutritrack.bot.exception.UnknownMessageType;
import com.nutritrack.bot.service.entity_service.user_service.UserService;
import com.nutritrack.bot.service.exception.ExceptionService;
import com.nutritrack.bot.service.factory.StepFactory;
import com.nutritrack.bot.service.parser.UpdateParser;
import com.nutritrack.bot.service.parser.dto.CustomUpdate;
import com.nutritrack.bot.service.telegram.CountCaloriesBot;
import com.nutritrack.bot.service.telegram.factory.TelegramFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static com.nutritrack.bot.config.LogbackConfig.configureLogbackDirectly;

public class LambdaHandler implements RequestStreamHandler {

    private static final Logger logger = LoggerFactory.getLogger(LambdaHandler.class);

    @Inject
    ObjectMapper objectMapper;
    @Inject
    CountCaloriesBot bot;
    @Inject
    StepFactory stepFactory;
    @Inject
    UserService userService;
    @Inject
    ExceptionService exceptionService;
    @Inject
    UpdateParser updateParser;

    @Inject
    public LambdaHandler(ObjectMapper objectMapper, CountCaloriesBot bot, StepFactory stepFactory,
                         UserService userService, ExceptionService exceptionService, UpdateParser updateParser) {
        this.objectMapper = objectMapper;
        this.bot = bot;
        this.stepFactory = stepFactory;
        this.userService = userService;
        this.exceptionService = exceptionService;
        this.updateParser = updateParser;
    }

    public LambdaHandler() {
        DaggerAppComponent.create().inject(this);
    }

    @Override
    public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {
        okToTelegram(output);
        configureLogbackDirectly();
        CustomUpdate update = null;

        try {
            update = updateParser.parse(new String(input.readAllBytes()));

            String languageCode = TelegramFactory.getLanguageCode(update);
            TelegramFactory.registerCommands(languageCode, bot.getSender());

            var user = userService.get(update);

            var sendMessage = stepFactory.getService(user.getStep())
                    .doStep(user, update);

            logger.info("Message to user: {}", sendMessage.getText());

            bot.execute(sendMessage);
        } catch (UnknownMessageType e) {
            logger.error("handleRequest exception: {}", e.getMessage());
        } catch (Exception e) {
            logger.error("handleRequest exception:\n{}", e);
            SendMessage sendMessage = exceptionService.handleException(e, update);
            bot.execute(sendMessage);
        }
    }

    private void okToTelegram(OutputStream output) throws IOException {
        Map<String, Object> response = new HashMap<>();
        response.put("statusCode", 200);
        response.put("headers", Map.of("Content-Type", "application/json"));
        response.put("body", "{\"status\":\"OK\"}");

        try (OutputStreamWriter writer = new OutputStreamWriter(output, StandardCharsets.UTF_8)) {
            writer.write(objectMapper.writeValueAsString(response));
            writer.flush();
        }

        logger.info("Message ok was written to output");
    }
}
