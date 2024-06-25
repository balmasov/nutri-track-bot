package com.nutritrack.bot.service.step.impl.command;

import com.nutritrack.bot.repository.model.Food;
import com.nutritrack.bot.repository.model.FoodState;
import com.nutritrack.bot.repository.model.User;
import com.nutritrack.bot.repository.model.UserStep;
import com.nutritrack.bot.service.entity.food_service.FoodService;
import com.nutritrack.bot.service.entity.user_service.UserService;
import com.nutritrack.bot.service.keyboard.KeyboardService;
import com.nutritrack.bot.service.parser.dto.CustomUpdate;
import com.nutritrack.bot.service.step.CommandHandler;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import javax.inject.Inject;

import static com.nutritrack.bot.config.MessageKey.CANSEL_COMMAND_MSG;

public class CancelCommandHandler implements CommandHandler {

    private final UserService userService;
    private final FoodService foodService;
    private final KeyboardService keyboardService;

    @Inject
    public CancelCommandHandler(UserService userService,
                                FoodService foodService,
                                KeyboardService keyboardService) {
        this.userService = userService;
        this.foodService = foodService;
        this.keyboardService = keyboardService;
    }

    @Override
    public SendMessage handle(User user, CustomUpdate update) {
        return userService.get(user.getId())
                .filter(u -> u.getStep() != UserStep.STEEPLES)
                .map(u -> cancelCurrentStep(u, update))
                .orElse(mainKeyboard(update));
    }

    private SendMessage cancelCurrentStep(User user, CustomUpdate update) {
        userService.setStep(user, UserStep.STEEPLES);
        userService.update(user);

        foodService.getProcessedFoodByUserId(user.getId())
                .map(this::canselFoodStep);

        return mainKeyboard(update);
    }

    private Food canselFoodStep(Food food) {
        if (food.getState() == FoodState.CALCULATING) {
            foodService.setState(food, FoodState.COOKED);
            foodService.update(food);
        } else if (food.getState() == FoodState.COOKING) {
            foodService.deleteCookingFoodByUserId(food.getUserId());
        }
        return food;
    }

    private SendMessage mainKeyboard(CustomUpdate update) {
        return keyboardService.createCookingOrEatingKeyboard(update, CANSEL_COMMAND_MSG);
    }
}
