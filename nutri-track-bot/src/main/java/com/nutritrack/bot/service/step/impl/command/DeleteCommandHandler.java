package com.nutritrack.bot.service.step.impl.command;

import com.nutritrack.bot.repository.model.Food;
import com.nutritrack.bot.repository.model.User;
import com.nutritrack.bot.repository.model.UserStep;
import com.nutritrack.bot.service.entity_service.food_service.FoodService;
import com.nutritrack.bot.service.entity_service.user_service.UserService;
import com.nutritrack.bot.service.keyboard.KeyboardService;
import com.nutritrack.bot.service.parser.dto.CustomUpdate;
import com.nutritrack.bot.service.step.CommandHandler;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import javax.inject.Inject;
import java.util.List;

import static com.nutritrack.bot.config.MessageKey.DELETE_ONE_OR_ALL_FOOD_MESSAGE;
import static com.nutritrack.bot.config.MessageKey.NO_FOOD_FOUND;

public class DeleteCommandHandler implements CommandHandler {

    private final UserService userService;
    private final KeyboardService keyboardService;
    private final FoodService foodService;

    @Inject
    public DeleteCommandHandler(UserService userService,
                                KeyboardService keyboardService,
                                FoodService foodService) {
        this.userService = userService;
        this.keyboardService = keyboardService;
        this.foodService = foodService;
    }

    @Override
    public SendMessage handle(User user, CustomUpdate update) {
        return userService.get(user.getId())
                .map(this::getUsersFoodAndUpdateStepOrNull)
                .map(foods -> keyboardService.createDeletionFoodKeyboard(update, foods, DELETE_ONE_OR_ALL_FOOD_MESSAGE))
                .orElse(keyboardService.createCookingOrEatingKeyboard(update, NO_FOOD_FOUND));
    }

    private List<Food> getUsersFoodAndUpdateStepOrNull(User user) {
        return foodService.getUsersFood(user.getId())
                .filter(optFoods -> !optFoods.isEmpty())
                .map(foods -> {
                    userService.setStep(user, UserStep.DELETION);
                    userService.update(user);
                    return foods;
                })
                .orElse(null);
    }
}
