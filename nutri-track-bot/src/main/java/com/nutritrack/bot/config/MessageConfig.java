package com.nutritrack.bot.config;

import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Properties;

public class MessageConfig {
    private final Properties properties;

    @Inject
    public MessageConfig(String filename) {
        properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(filename)) {
            if (input == null) {
                throw new IOException("File not found: " + filename);
            }
            InputStreamReader reader = new InputStreamReader(input, StandardCharsets.UTF_8);
            properties.load(reader);
        } catch (IOException ex) {
            throw new RuntimeException("Error while loading properties file\n" + ex);
        }
    }

    public String getDefaultMessage(MessageKey key) {
        return properties.getProperty(key.getKey());
    }

    public String getLocalMessageOrDefault(MessageKey key, Locale locale) {
        if (locale == null) {
            return getDefaultMessage(key);
        }
        String localizedFilename = String.format("messages_%s.properties", locale.getLanguage());
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(localizedFilename)) {
            if (input != null) {
                InputStreamReader reader = new InputStreamReader(input, StandardCharsets.UTF_8);
                Properties localizedProperties = new Properties();
                localizedProperties.load(reader);
                return localizedProperties.getProperty(key.getKey(), properties.getProperty(key.getKey()));
            }
        } catch (IOException ex) {
            throw new RuntimeException("Error while loading properties file for language: " + localizedFilename + "\n" + ex);
        }
        return properties.getProperty(key.getKey());
    }
}
