package com.nutritrack.bot.service.exception;

import com.nutritrack.bot.service.parser.dto.CustomUpdate;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface ExceptionService {

    SendMessage handleException(Exception e, CustomUpdate update);
}
