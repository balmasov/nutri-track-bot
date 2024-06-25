package com.nutritrack.bot.service.step.impl;

import com.nutritrack.bot.config.MessageKey;
import com.nutritrack.bot.repository.model.User;
import com.nutritrack.bot.repository.model.UserStep;
import com.nutritrack.bot.service.factory.StepFactory;
import com.nutritrack.bot.service.keyboard.KeyboardService;
import com.nutritrack.bot.service.message.MessageService;
import com.nutritrack.bot.service.parser.dto.CustomUpdate;
import com.nutritrack.bot.service.step.StepService;
import com.nutritrack.bot.service.step.StepState;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import javax.inject.Inject;

import static com.nutritrack.bot.config.MessageKey.*;

public class SteplessServiceImpl implements StepService, StepState {

    private final StepFactory factory;
    private final KeyboardService keyboardService;
    private final MessageService msgService;

    @Inject
    public SteplessServiceImpl(StepFactory factory, KeyboardService keyboardService, MessageService msgService) {
        this.factory = factory;
        this.keyboardService = keyboardService;
        this.msgService = msgService;
    }

    @Override
    public SendMessage doStep(User user, CustomUpdate update) {

        UserStep step = cookingOrEatingOrNull(update);

        return step == null
                ? keyboardService.createCookingOrEatingKeyboard(update, COOKING_OR_EATING)
                : factory.getService(step).doStep(user, update);
    }

    private UserStep cookingOrEatingOrNull(CustomUpdate update) {
        String userMsg = update.getUserMsg();

        String cooking = msgService.getMessage(COOKING, update.getLocale());
        String eating = msgService.getMessage(EATING, update.getLocale());

        if (cooking.equalsIgnoreCase(userMsg)) {
            return UserStep.COOKING;
        } else if (eating.equalsIgnoreCase(userMsg)) {
            return UserStep.EATING;
        }

        return null;
    }

    @Override
    public UserStep step() {
        return UserStep.STEEPLES;
    }

}
