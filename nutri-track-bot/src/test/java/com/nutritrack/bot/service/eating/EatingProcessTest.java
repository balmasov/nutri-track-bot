package com.nutritrack.bot.service.eating;

import com.nutritrack.bot.repository.model.UserStep;
import com.nutritrack.bot.service.AbstractTest;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static com.nutritrack.bot.repository.SqlQuery.*;
import static com.nutritrack.bot.service.TestUserMessages.*;

public class EatingProcessTest extends AbstractTest {

    @Test
    public void eatingServiceTest() throws IOException {
        mockSqlServiceGetUser(UserStep.STEEPLES);
        mockSqlServiceGetFood(sqlQueries.get(GET_USER_COOKED_FOOD));
        extracted(EATING);
    }

    @Test
    public void eatingNamingServiceTest() throws IOException {
        mockSqlServiceGetUser(UserStep.EATING_NAMING);
        mockSqlServiceGetFood(sqlQueries.get(GET_FOOD_BY_ID_AND_NAME), food().getName());
        extracted(WHAT_FOOD_TO_EAT);
    }

    @Test
    public void eatingCalculationServiceTest() throws IOException {
        mockSqlServiceGetUser(UserStep.EATING_CALCULATION);
        mockSqlServiceGetFood(sqlQueries.get(GET_CALCULATION_FOOD));
        extracted(EATING_RAW_WEIGHT);
    }
}
