package com.nutritrack.bot.service.cooking;

import com.nutritrack.bot.repository.model.UserStep;
import com.nutritrack.bot.service.AbstractTest;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static com.nutritrack.bot.repository.SqlQuery.GET_COOKING_FOOD;
import static com.nutritrack.bot.service.TestUserMessages.*;

public class CookingProcessTest extends AbstractTest {

    @Test
    public void cookingTest() throws IOException {
        mockSqlServiceGetUser(UserStep.STEEPLES);
        extracted(COOKING);
    }

    @Test
    public void rawFoodWeighingTest() throws IOException {
        mockSqlServiceGetUser(UserStep.RAW_FOOD_WEIGHING);
        extracted(NAME_OF_COOKING_FOOD);
    }

    @Test
    public void rawFoodWeighing() throws IOException {
        mockSqlServiceGetUser(UserStep.COOKED_FOOD_WEIGHING);
        mockSqlServiceGetFood(sqlQueries.get(GET_COOKING_FOOD));
        extracted(COOKING_RAW_WEIGHT);
    }

    @Test
    public void cookedFoodWeighing() throws IOException {
        mockSqlServiceGetUser(UserStep.COOKING_CALCULATION);
        mockSqlServiceGetFood(sqlQueries.get(GET_COOKING_FOOD));
        extracted(COOKED_WEIGHT);
    }
}

