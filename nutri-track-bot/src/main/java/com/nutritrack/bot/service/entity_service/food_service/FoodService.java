package com.nutritrack.bot.service.entity_service.food_service;

import com.nutritrack.bot.repository.model.Food;
import com.nutritrack.bot.repository.model.FoodState;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface FoodService {

    void save(@NotNull Food food);

    void update(@NotNull Food food);

    Food getCookingFoodById(@NotNull BigInteger userId);

    Food getFoodByNameAndUserId(@NotNull String name, @NotNull BigInteger userId);

    Food getUserCalculationFood(@NotNull BigInteger id);

    void deleteCookingFoodByUserId(@NotNull BigInteger userId);

    Optional<List<Food>> getCookedFoodByUserId(@NotNull BigInteger userId);

    Food getFoodById(Long foodId);

    Food setState(Food food, FoodState foodState);

    Optional<Food> getProcessedFoodByUserId(@NotNull BigInteger id);

    Optional<List<Food>> getUsersFood(@NotNull BigInteger id);

    String deleteFood(Long foodId);

    void deleteUserFood(BigInteger userId);
}
