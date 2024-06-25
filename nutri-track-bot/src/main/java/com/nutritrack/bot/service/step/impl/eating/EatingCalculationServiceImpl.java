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
import com.nutritrack.bot.service.parser.dto.CustomUpdate;
import com.nutritrack.bot.service.step.StepService;
import com.nutritrack.bot.service.step.StepState;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import javax.inject.Inject;

import static com.nutritrack.bot.config.MessageKey.YOU_NEED_X_GRAM;

public class EatingCalculationServiceImpl implements StepService, StepState {

    private final FoodService foodService;
    private final UserService userService;
    private final KeyboardService keyboardService;
    private final MessageService msgService;

    @Inject
    public EatingCalculationServiceImpl(FoodService foodService,
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
        Food food = foodService.getUserCalculationFood(user.getId());

        Double preparedFood = calculateNeededWeight(update, food);

        SendMessage massage = keyboardService.createCookingOrEatingKeyboard(update, YOU_NEED_X_GRAM,
                preparedFood, food.getName()
        );

        userService.setStep(user, UserStep.STEEPLES);
        userService.update(user);
        foodService.setState(food, FoodState.COOKED);
        foodService.update(food);

        return massage;
    }

    private Double calculateNeededWeight(CustomUpdate update, Food food) {
        try {
            double desiredRawFoodPortion = Double.parseDouble(update.getUserMsg());
            double rawWeight = food.getRawWeight();
            double completedCookedWeight = food.getCookedWeight();

            return desiredRawFoodPortion / rawWeight * completedCookedWeight;

        } catch (NumberFormatException e) {
            throw new WrongInputException(msgService.wrongNumberFormatInputMsg(update));
        }
    }

    @Override
    public UserStep step() {
        return UserStep.EATING_CALCULATION;
    }
}
