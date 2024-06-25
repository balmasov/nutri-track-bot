package com.nutritrack.bot.service.entity.food_service.Impl;


import com.nutritrack.bot.exception.NoEntityFoundException;
import com.nutritrack.bot.repository.FoodRepo;
import com.nutritrack.bot.repository.model.Food;
import com.nutritrack.bot.repository.model.FoodState;
import com.nutritrack.bot.service.entity.food_service.FoodService;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class FoodServiceImpl implements FoodService {

    private final FoodRepo foodRepository;

    @Inject
    public FoodServiceImpl(FoodRepo foodRepository) {
        this.foodRepository = foodRepository;
    }

    @Override
    public void save(@NotNull Food food) {
        if (Objects.isNull(food.getUserId())) {
            throw new RuntimeException("User id can't be null");
        }
        foodRepository.save(food);
    }

    @Override
    public void update(@NotNull Food food) {
        if (Objects.isNull(food.getId()) || Objects.isNull(food.getUserId())) {
            throw new IllegalArgumentException("Food id and user id can't be null");
        }

        foodRepository.update(food);
    }

    @Override
    public Food getCookingFoodById(@NotNull BigInteger userId) {
        List<Food> foods = foodRepository.getCookingFood(userId)
                .orElseThrow(() -> new NoEntityFoundException("No Preparing food was found"));

        if (foods.size() > 1) {
            throw new RuntimeException("User can have only one Cooking Food");
        }

        return foods.get(0);
    }

    @Override
    public Food getFoodByNameAndUserId(@NotNull String name, @NotNull BigInteger userId) {
        return foodRepository.getFood(name, userId)
                .orElseThrow(() -> new NoEntityFoundException("No food was found by name: %s", name))
                .get(0);
    }

    @Override
    public Food getUserCalculationFood(@NotNull BigInteger userId) {
        return foodRepository.getCalculationFood(userId)
                .orElseThrow(() -> new NoEntityFoundException("No food was found"))
                .get(0);
    }

    @Override
    public void deleteCookingFoodByUserId(@NotNull BigInteger userId) {
        foodRepository.deleteCookingFood(userId);
    }

    @Override
    public Optional<List<Food>> getCookedFoodByUserId(@NotNull BigInteger userId) {

        return foodRepository.geCookedFood(userId);
    }

    @Override
    public Food getFoodById(Long foodId) {
        return foodRepository.getFood(foodId)
                .orElseThrow(() -> new NoEntityFoundException("No food was found by id: " + foodId));
    }

    @Override
    public Food setState(Food food, FoodState foodState) {
        if (food == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        if (foodState == null) {
            throw new IllegalArgumentException("UserStep cannot be null");
        }

        food.setState(foodState);
        return food;
    }

    @Override
    public Optional<Food> getProcessedFoodByUserId(@NotNull BigInteger id) {
        return foodRepository.getProcessedFoodByUserId(id);
    }

    @Override
    public Optional<List<Food>> getUsersFood(@NotNull BigInteger id) {
        return foodRepository.getUsersFood(id);
    }

    @Override
    public String deleteFood(Long foodId) {
        List<Food> foods = foodRepository.deleteFood(foodId);

        if (foods == null || foods.isEmpty()) {
            throw new RuntimeException("foods list is null after deletion");
        }

        return foods.get(0).getName();
    }

    @Override
    public void deleteUserFood(BigInteger userId) {
        foodRepository.deleteUserFood(userId);
    }
}
