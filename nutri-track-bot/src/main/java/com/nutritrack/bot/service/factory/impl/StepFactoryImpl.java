package com.nutritrack.bot.service.factory.impl;

import com.nutritrack.bot.repository.model.UserStep;
import com.nutritrack.bot.service.factory.StepFactory;
import com.nutritrack.bot.service.step.StepService;

import javax.inject.Inject;
import javax.inject.Provider;
import java.util.Map;

public class StepFactoryImpl implements StepFactory {

    private final Map<UserStep, Provider<StepService>> serviceMap;

    @Inject
    public StepFactoryImpl(Map<UserStep, Provider<StepService>> serviceMap) {
        this.serviceMap = serviceMap;
    }

    @Override
    public StepService getService(UserStep userStep) {

        return serviceMap.get(userStep).get();
    }
}
