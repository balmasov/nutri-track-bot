package com.nutritrack.bot.config.dagger_config.module;

import com.nutritrack.bot.config.dagger_config.UserStepKey;
import com.nutritrack.bot.repository.model.UserStep;
import com.nutritrack.bot.service.step.StepService;
import com.nutritrack.bot.service.step.impl.CommandDefinerServiceImpl;
import com.nutritrack.bot.service.step.impl.SteplessServiceImpl;
import com.nutritrack.bot.service.step.impl.cooking.CookedFoodWeighingServiceImpl;
import com.nutritrack.bot.service.step.impl.cooking.CookingCalculationServiceImpl;
import com.nutritrack.bot.service.step.impl.cooking.CookingServiceImpl;
import com.nutritrack.bot.service.step.impl.cooking.RawFoodWeighingServiceImpl;
import com.nutritrack.bot.service.step.impl.deletion.DeletionServiceImpl;
import com.nutritrack.bot.service.step.impl.eating.EatingCalculationServiceImpl;
import com.nutritrack.bot.service.step.impl.eating.EatingNamingServiceImpl;
import com.nutritrack.bot.service.step.impl.eating.EatingServiceImpl;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class StepServiceModule {

    @Binds
    @IntoMap
    @UserStepKey(UserStep.STEEPLES)
    abstract StepService bindSteeplesService(SteplessServiceImpl impl);

    @Binds
    @IntoMap
    @UserStepKey(UserStep.COOKING)
    abstract StepService bindCookingService(CookingServiceImpl impl);

    @Binds
    @IntoMap
    @UserStepKey(UserStep.RAW_FOOD_WEIGHING)
    abstract StepService bindRawFoodWeighingService(RawFoodWeighingServiceImpl impl);

    @Binds
    @IntoMap
    @UserStepKey(UserStep.COOKED_FOOD_WEIGHING)
    abstract StepService bindCookedFoodWeighingService(CookedFoodWeighingServiceImpl impl);

    @Binds
    @IntoMap
    @UserStepKey(UserStep.COOKING_CALCULATION)
    abstract StepService bindCookingCalculationService(CookingCalculationServiceImpl impl);

    @Binds
    @IntoMap
    @UserStepKey(UserStep.EATING)
    abstract StepService bindEatingService(EatingServiceImpl impl);

    @Binds
    @IntoMap
    @UserStepKey(UserStep.EATING_NAMING)
    abstract StepService bindEatingNamingService(EatingNamingServiceImpl impl);

    @Binds
    @IntoMap
    @UserStepKey(UserStep.EATING_CALCULATION)
    abstract StepService bindEatingCalculationService(EatingCalculationServiceImpl impl);

    @Binds
    @IntoMap
    @UserStepKey(UserStep.COMMAND)
    abstract StepService bindCommandDefinerService(CommandDefinerServiceImpl impl);

    @Binds
    @IntoMap
    @UserStepKey(UserStep.DELETION)
    abstract StepService bindCommandDeletionService(DeletionServiceImpl impl);

}
