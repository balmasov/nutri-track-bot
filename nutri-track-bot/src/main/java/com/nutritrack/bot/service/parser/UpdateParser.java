package com.nutritrack.bot.service.parser;

import com.nutritrack.bot.service.parser.dto.CustomUpdate;

public interface UpdateParser {

    CustomUpdate parse(String input);
}
