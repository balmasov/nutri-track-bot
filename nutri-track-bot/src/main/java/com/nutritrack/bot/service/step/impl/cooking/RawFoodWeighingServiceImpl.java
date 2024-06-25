package com.nutritrack.bot.service.step.impl.cooking;

import com.nutritrack.bot.repository.model.Food;
import com.nutritrack.bot.repository.model.FoodState;
import com.nutritrack.bot.repository.model.User;
import com.nutritrack.bot.repository.model.UserStep;
import com.nutritrack.bot.service.entity_service.food_service.FoodService;
import com.nutritrack.bot.service.entity_service.user_service.UserService;
import com.nutritrack.bot.service.message.MessageService;
import com.nutritrack.bot.service.parser.dto.CustomUpdate;
import com.nutritrack.bot.service.step.StepService;
import com.nutritrack.bot.service.step.StepState;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import javax.inject.Inject;

import static com.nutritrack.bot.config.MessageKey.HOW_MANY_GRAMS_OF_RAW_FOOD;

public class RawFoodWeighingServiceImpl implements StepService, StepState {

    private final UserService userService;
    private final FoodService foodService;
    private final MessageService msgService;

    @Inject
    public RawFoodWeighingServiceImpl(UserService userService, FoodService foodService, MessageService msgService) {
        this.userService = userService;
        this.foodService = foodService;
        this.msgService = msgService;
    }

    @Override
    public SendMessage doStep(User user, CustomUpdate update) {
        Food food = new Food.Builder()
                .name(update.getUserMsg())
                .state(FoodState.COOKING)
                .userId(user.getId())
                .build();

        foodService.save(food);

        userService.setStep(user, UserStep.COOKED_FOOD_WEIGHING);
        userService.update(user);

        return msgService.createSendMessage(update, HOW_MANY_GRAMS_OF_RAW_FOOD);
    }

    @Override
    public UserStep step() {
        return UserStep.RAW_FOOD_WEIGHING;
    }
}
