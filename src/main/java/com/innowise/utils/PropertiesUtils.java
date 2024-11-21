package com.innowise.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtils {
    private static Properties properties = new Properties();

    static {
        try (InputStream inputStream = PropertiesUtils.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (inputStream == null) {
                throw new IOException("Не удалось найти файл: application.properties");
            }
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Ошибка при загрузке файла свойств", e);
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}
