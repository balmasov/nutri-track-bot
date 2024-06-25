package com.nutritrack.bot.service.factory;

import com.nutritrack.bot.repository.model.UserStep;
import com.nutritrack.bot.service.step.StepService;

public interface StepFactory {

    StepService getService(UserStep userStep);
}
