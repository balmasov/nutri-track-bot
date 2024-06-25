package com.nutritrack.bot.service.step.impl.eating;

import com.nutritrack.bot.repository.model.Food;
import com.nutritrack.bot.repository.model.User;
import com.nutritrack.bot.repository.model.UserStep;
import com.nutritrack.bot.service.entity_service.food_service.FoodService;
import com.nutritrack.bot.service.entity_service.user_service.UserService;
import com.nutritrack.bot.service.keyboard.KeyboardService;
import com.nutritrack.bot.service.parser.dto.CustomUpdate;
import com.nutritrack.bot.service.step.StepService;
import com.nutritrack.bot.service.step.StepState;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import javax.inject.Inject;
import java.util.List;

import static com.nutritrack.bot.config.MessageKey.NO_FOOD_FOUND;

public class EatingServiceImpl implements StepService, StepState {

    private final UserService userService;
    private final FoodService foodService;

    private final KeyboardService keyboardService;

    @Inject
    public EatingServiceImpl(UserService userService,
                             FoodService foodService,
                             KeyboardService keyboardService) {
        this.userService = userService;
        this.foodService = foodService;
        this.keyboardService = keyboardService;
    }

    @Override
    public SendMessage doStep(User user, CustomUpdate update) {
        return foodService.getCookedFoodByUserId(user.getId())
                .map(foods -> foodKeyboard(foods, user, update))
                .orElseGet(() -> noFoodMessage(update));
    }

    private SendMessage noFoodMessage(CustomUpdate update) {
        return keyboardService.createCookingOrEatingKeyboard(update, NO_FOOD_FOUND);
    }

    private SendMessage foodKeyboard(List<Food> foods, User user, CustomUpdate update) {
        userService.setStep(user, UserStep.EATING_NAMING);
        userService.update(user);

        return keyboardService.createFoodListBoard(update, foods);
    }

    @Override
    public UserStep step() {
        return UserStep.EATING;
    }
}
