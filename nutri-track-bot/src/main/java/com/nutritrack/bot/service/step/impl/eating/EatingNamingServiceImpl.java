package com.nutritrack.bot.service.step.impl.eating;

import com.nutritrack.bot.exception.WrongInputException;
import com.nutritrack.bot.repository.model.Food;
import com.nutritrack.bot.repository.model.FoodState;
import com.nutritrack.bot.repository.model.User;
import com.nutritrack.bot.repository.model.UserStep;
import com.nutritrack.bot.service.entity_service.food_service.FoodService;
import com.nutritrack.bot.service.entity_service.user_service.UserService;
import com.nutritrack.bot.service.keyboard.KeyboardService;
import com.nutritrack.bot.service.message.MessageService;
import com.nutritrack.bot.service.message.impl.MessageServiceImpl;
import com.nutritrack.bot.service.parser.dto.CustomUpdate;
import com.nutritrack.bot.service.step.StepService;
import com.nutritrack.bot.service.step.StepState;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import javax.inject.Inject;

import static com.nutritrack.bot.config.MessageKey.*;

public class EatingNamingServiceImpl implements StepService, StepState {

    private final FoodService foodService;
    private final UserService userService;
    private final MessageService messageService;
    private final KeyboardService keyboardService;

    @Inject
    public EatingNamingServiceImpl(FoodService foodService,
                                   UserService userService,
                                   MessageServiceImpl messageService,
                                   KeyboardService keyboardService) {
        this.foodService = foodService;
        this.userService = userService;
        this.messageService = messageService;
        this.keyboardService = keyboardService;
    }

    @Override
    public SendMessage doStep(User user, CustomUpdate update) {

        if (!update.hasCallbackQuery()) {
            throw new WrongInputException(messageService.getMessage(CHOSE_KEYBOARD_OPTION, update.getLocale()));
        }

        Food food = foodService.getFoodById(Long.parseLong(update.getCallbackQueryData()));

        keyboardService.deleteReplyMarkupKeyBoard(update, YOUR_CHOICE_WAS, food.getName());

        userService.setStep(user, UserStep.EATING_CALCULATION);
        userService.update(user);
        foodService.setState(food, FoodState.CALCULATING);
        foodService.update(food);

        return messageService.createSendMessage(update, HOW_MANY_YOU_EAT, food.getName());
    }

    @Override
    public UserStep step() {
        return UserStep.EATING_NAMING;
    }
}
