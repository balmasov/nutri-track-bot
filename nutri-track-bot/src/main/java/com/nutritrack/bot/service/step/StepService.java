package com.nutritrack.bot.service.step;

import com.nutritrack.bot.repository.model.User;
import com.nutritrack.bot.service.parser.dto.CustomUpdate;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface StepService {

    SendMessage doStep(User user, CustomUpdate update);
}
