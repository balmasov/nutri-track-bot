package com.nutritrack.bot.service.step.impl.cooking;

import com.nutritrack.bot.repository.model.User;
import com.nutritrack.bot.repository.model.UserStep;
import com.nutritrack.bot.service.entity_service.user_service.UserService;
import com.nutritrack.bot.service.message.MessageService;
import com.nutritrack.bot.service.parser.dto.CustomUpdate;
import com.nutritrack.bot.service.step.StepService;
import com.nutritrack.bot.service.step.StepState;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import javax.inject.Inject;

import static com.nutritrack.bot.config.MessageKey.NAME_OF_FOOD_YOU_ARE_COOKING;

public class CookingServiceImpl implements StepService, StepState {

    private final UserService userService;
    private final MessageService msgService;

    @Inject
    public CookingServiceImpl(UserService userService, MessageService msgService) {
        this.userService = userService;
        this.msgService = msgService;
    }

    @Override
    public SendMessage doStep(User user, CustomUpdate update) {
        userService.setStep(user, UserStep.RAW_FOOD_WEIGHING);
        userService.update(user);

        return msgService.createSendMessage(update, NAME_OF_FOOD_YOU_ARE_COOKING);
    }

    @Override
    public UserStep step() {
        return UserStep.COOKING;
    }
}
