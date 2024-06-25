package com.nutritrack.bot.service.step.impl.cooking;

import com.nutritrack.bot.exception.WrongInputException;
import com.nutritrack.bot.repository.model.Food;
import com.nutritrack.bot.repository.model.User;
import com.nutritrack.bot.repository.model.UserStep;
import com.nutritrack.bot.service.entity.food_service.FoodService;
import com.nutritrack.bot.service.entity.user_service.UserService;
import com.nutritrack.bot.service.message.MessageService;
import com.nutritrack.bot.service.parser.dto.CustomUpdate;
import com.nutritrack.bot.service.step.StepService;
import com.nutritrack.bot.service.step.StepState;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import javax.inject.Inject;

import static com.nutritrack.bot.config.MessageKey.HOW_MANY_GRAMS_OF_COOKED_FOOD;

public class CookedFoodWeighingServiceImpl implements StepService, StepState {

    private final UserService userService;
    private final FoodService foodService;
    private final MessageService msgService;

    @Inject
    public CookedFoodWeighingServiceImpl(FoodService foodService,
                                         UserService userService,
                                         MessageService msgService) {
        this.foodService = foodService;
        this.userService = userService;
        this.msgService = msgService;
    }

    @Override
    public SendMessage doStep(User user, CustomUpdate update) {
        String userMsg = update.getUserMsg();

        try {
            Food food = foodService.getCookingFoodById(user.getId());
            food.setRawWeight(Double.parseDouble(userMsg));
            foodService.update(food);

            userService.setStep(user, UserStep.COOKING_CALCULATION);
            userService.update(user);

            return msgService.createSendMessage(update, HOW_MANY_GRAMS_OF_COOKED_FOOD);
        } catch (NumberFormatException e) {
            throw new WrongInputException(msgService.wrongNumberFormatInputMsg(update));
        }
    }

    @Override
    public UserStep step() {
        return UserStep.COOKED_FOOD_WEIGHING;
    }
}
