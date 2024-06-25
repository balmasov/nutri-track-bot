package com.nutritrack.bot.config.dagger_config.module;

import com.nutritrack.bot.repository.SqlQuery;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.EnumMap;
import java.util.Map;

@Module
public class SqlQueriesModule {

    @Provides
    @Singleton
    public Map<SqlQuery, String> provideSqlQueries() {
        Map<SqlQuery, String> queries = new EnumMap<>(SqlQuery.class);
        try (InputStream is = getClass().getResourceAsStream("/sql/queries.sql");
             BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("--")) {
                    continue;
                }
                String[] parts = line.split("=", 2);
                if (parts.length == 2) {
                    String key = parts[0].trim();
                    String query = parts[1].trim();
                    if (query.endsWith(";")) {
                        query = query.substring(0, query.length() - 1);
                    }
                    queries.put(SqlQuery.valueOf(key), query);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load SQL queries", e);
        }
        return queries;
    }
}
