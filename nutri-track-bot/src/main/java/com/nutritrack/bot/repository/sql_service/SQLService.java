package com.nutritrack.bot.repository.sql_service;

import java.util.List;

public interface SQLService {

    List<?> select(String query, List<?> args);
    List<?> select(String query, Object param);

    void execute(String query, List<?> args);
}
