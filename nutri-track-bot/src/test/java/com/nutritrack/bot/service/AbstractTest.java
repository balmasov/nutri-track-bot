package com.nutritrack.bot.service;

import com.nutritrack.bot.DaggerTestAppComponent;
import com.amazonaws.services.lambda.runtime.Context;
import com.nutritrack.bot.LambdaHandler;
import com.nutritrack.bot.repository.SqlQuery;
import com.nutritrack.bot.repository.model.Food;
import com.nutritrack.bot.repository.model.FoodState;
import com.nutritrack.bot.repository.model.User;
import com.nutritrack.bot.repository.model.UserStep;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.inject.Inject;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.nutritrack.bot.module.TestSqlServiceModule.mockSqlService;
import static com.nutritrack.bot.repository.SqlQuery.GET_USER;
import static com.nutritrack.bot.service.TestUserMessages.NAME_OF_COOKING_FOOD;

public abstract class AbstractTest {
    protected final String FILE_PATH = "C:\\Users\\ivanb\\IdeaProjects\\SAM\\CountCaloryHelper\\events\\event.json";
    private final BigInteger USER_ID = BigInteger.valueOf(1984L);
    private static final String USER_NAME = "Pushkin";

    @Inject
    protected Map<SqlQuery, String> sqlQueries;

    @Mock
    private Context context;
    @Inject
    LambdaHandler lambdaHandler;

    @BeforeEach
    public void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);

        DaggerTestAppComponent.builder()
                .build()
                .inject(this);

    }

    protected void extracted(TestUserMessages userMsg) throws RemoteException {
        try (InputStream inputStream = TestUtils.replacePlaceholderInJsonFile(FILE_PATH, userMsg.getMessage())) {

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            lambdaHandler.handleRequest(inputStream, outputStream, context);

            String output = outputStream.toString(StandardCharsets.UTF_8);

            Assertions.assertNotNull(output);

        } catch (Exception exception) {
            throw new RemoteException("Error while testing: ", exception);
        }
    }

    protected void mockSqlServiceGetUser(UserStep step) {
        Mockito.when(mockSqlService.select(sqlQueries.get(GET_USER), Collections.singletonList(USER_ID)))
                .thenReturn(getUsers(step));
    }

    protected void mockSqlServiceGetFood(String sql) {
        Mockito.when(mockSqlService.select(sql, USER_ID))
                .thenReturn(getFoods());
    }

    protected void mockSqlServiceGetFood(String sql, String name) {
        Mockito.when(mockSqlService.select(sql, Arrays.asList(USER_ID, name)))
                .thenReturn(getFoods());
    }

    private <T> List<T> getUsers(UserStep step) {
        return List.of((T) user(step));
    }

    protected User user(UserStep step) {
        return new User.Builder()
                .id(USER_ID)
                .userName(USER_NAME)
                .step(step)
                .build();
    }

    protected <T> List<T> getFoods() {
        return List.of((T) food());
    }

    protected Food food() {
        return new Food.Builder()
                .id(1L)
                .name(NAME_OF_COOKING_FOOD.getMessage())
                .state(FoodState.COOKING)
                .userId(USER_ID)
                .cookedWeight(430.0)
                .rawWeight(200.0)
                .build();
    }

}
