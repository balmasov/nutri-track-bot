package com.nutritrack.bot.repository;

import com.nutritrack.bot.repository.model.Food;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface FoodRepo {

    void save(Food food);

    void update(Food food);

    Optional<List<Food>> getCookingFood(BigInteger userId);

    Optional<List<Food>> getFood(String name, BigInteger userId);

    Optional<List<Food>> getCalculationFood(BigInteger id);

    void deleteCookingFood(BigInteger userId);

    Optional<List<Food>> geCookedFood(BigInteger userId);

    Optional<Food> getFood(Long foodId);

    Optional<Food> getProcessedFoodByUserId(BigInteger userId);

    Optional<List<Food>> getUsersFood(BigInteger userId);

    List<Food> deleteFood(Long foodId);

    void deleteUserFood(BigInteger userId);
}
