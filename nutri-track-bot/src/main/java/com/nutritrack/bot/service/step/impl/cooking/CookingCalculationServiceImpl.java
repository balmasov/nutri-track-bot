package com.nutritrack.bot.service.step.impl.cooking;

import com.nutritrack.bot.config.MessageKey;
import com.nutritrack.bot.exception.WrongInputException;
import com.nutritrack.bot.repository.model.Food;
import com.nutritrack.bot.repository.model.FoodState;
import com.nutritrack.bot.repository.model.User;
import com.nutritrack.bot.repository.model.UserStep;
import com.nutritrack.bot.service.entity.food_service.FoodService;
import com.nutritrack.bot.service.entity.user_service.UserService;
import com.nutritrack.bot.service.keyboard.KeyboardService;
import com.nutritrack.bot.service.message.MessageService;
import com.nutritrack.bot.service.parser.dto.CustomUpdate;
import com.nutritrack.bot.service.step.StepService;
import com.nutritrack.bot.service.step.StepState;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import javax.inject.Inject;

import static com.nutritrack.bot.config.MessageKey.COOKED_FOOD_INFO;

public class CookingCalculationServiceImpl implements StepState, StepService {

    private final FoodService foodService;
    private final UserService userService;
    private final KeyboardService keyboardService;
    private final MessageService msgService;

    @Inject
    public CookingCalculationServiceImpl(FoodService foodService,
                                         UserService userService,
                                         KeyboardService keyboardService,
                                         MessageService msgService) {
        this.foodService = foodService;
        this.userService = userService;
        this.keyboardService = keyboardService;
        this.msgService = msgService;
    }

    @Override
    public SendMessage doStep(User user, CustomUpdate update) {
        String userMsg = update.getUserMsg();
        try {
            Food food = foodService.getCookingFoodById(user.getId());
            food.setCookedWeight(Double.parseDouble(userMsg));
            food.setState(FoodState.COOKED);

            foodService.update(food);

            SendMessage massage = keyboardService.createCookingOrEatingKeyboard(update, COOKED_FOOD_INFO,
                    food.getName(), food.getRawWeight(), food.getCookedWeight()
            );

            userService.setStep(user, UserStep.STEEPLES);
            userService.update(user);

            return massage;
        } catch (NumberFormatException e) {
            throw new WrongInputException(msgService.wrongNumberFormatInputMsg(update));
        }
    }

    @Override
    public UserStep step() {
        return UserStep.COOKING_CALCULATION;
    }
}
