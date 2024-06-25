package com.nutritrack.bot.repository.impl;

import com.nutritrack.bot.repository.FoodRepo;
import com.nutritrack.bot.repository.SqlQuery;
import com.nutritrack.bot.repository.model.Food;
import com.nutritrack.bot.repository.sql_service.SQLService;

import javax.inject.Inject;
import javax.inject.Named;
import java.math.BigInteger;
import java.util.*;

import static com.nutritrack.bot.repository.SqlQuery.*;

public class FoodRepoImpl implements FoodRepo {

    @Named("FoodSQLService")
    private final SQLService sqlService;
    private final Map<SqlQuery, String> sqlQueries;

    @Inject
    public FoodRepoImpl(SQLService sqlService, Map<SqlQuery, String> sqlQueries) {
        this.sqlService = sqlService;
        this.sqlQueries = sqlQueries;
    }

    @Override
    public void save(Food food) {
        sqlService.execute(sqlQueries.get(SAVE_FOOD),
                Arrays.asList(
                        food.getCookedWeight(),
                        food.getName(),
                        food.getRawWeight(),
                        food.getState(),
                        food.getUserId()
                )
        );
    }

    @Override
    public void update(Food food) {
        sqlService.execute(sqlQueries.get(UPDATE_FOOD),
                Arrays.asList(
                        food.getCookedWeight(),
                        food.getName(),
                        food.getRawWeight(),
                        food.getState(),
                        food.getUserId(),
                        food.getId())
        );
    }

    @Override
    public Optional<List<Food>> getCookingFood(BigInteger userId) {
        List<Food> foods = (List<Food>) sqlService.select(sqlQueries.get(GET_COOKING_FOOD), userId);

        return getFoods(foods);
    }

    @Override
    public Optional<List<Food>> getFood(String name, BigInteger userId) {
        List<Food> foods = (List<Food>) sqlService.select(sqlQueries.get(GET_FOOD_BY_ID_AND_NAME), Arrays.asList(userId, name));

        return getFoods(foods);
    }

    @Override
    public Optional<List<Food>> getCalculationFood(BigInteger userId) {
        List<Food> foods = (List<Food>) sqlService.select(sqlQueries.get(GET_CALCULATION_FOOD), userId);

        return getFoods(foods);
    }

    @Override
    public void deleteCookingFood(BigInteger userId) {
        sqlService.execute(sqlQueries.get(DELETE_PROCESSED_FOOD), Collections.singletonList(userId));
    }

    @Override
    public Optional<List<Food>> geCookedFood(BigInteger userId) {
        List<Food> foods = (List<Food>) sqlService.select(sqlQueries.get(GET_USER_COOKED_FOOD), userId);

        return getFoods(foods);
    }

    @Override
    public Optional<Food> getFood(Long foodId) {
        List<Food> foods = (List<Food>) sqlService.select(sqlQueries.get(GET_FOOD_BY_ID), foodId);

        return getFood(foods);
    }

    @Override
    public Optional<Food> getProcessedFoodByUserId(BigInteger userId) {
        List<Food> foods = (List<Food>) sqlService.select(sqlQueries.get(GET_PROCESSED_FOOD_BY_USER_ID), userId);

        return getFood(foods);
    }

    @Override
    public Optional<List<Food>> getUsersFood(BigInteger userId) {
        List<Food> foods = (List<Food>) sqlService.select(sqlQueries.get(GET_ALL_USERS_FOOD), userId);

        return getFoods(foods);
    }

    @Override
    public List<Food> deleteFood(Long foodId) {
        List<Food> foods = (List<Food>) sqlService.select(sqlQueries.get(DELETE_FOOD_BY_ID), foodId);

        return foods;
    }

    @Override
    public void deleteUserFood(BigInteger userId) {
        sqlService.execute(sqlQueries.get(DELETE_USER_FOOD), Collections.singletonList(userId));
    }

    private Optional<List<Food>> getFoods(List<Food> foods) {
        if (foods.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(foods);
    }

    private Optional<Food> getFood(List<Food> foods) {
        if (foods.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(foods.get(0));
    }
}
