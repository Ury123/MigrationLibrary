package com.innowise.utils;

import com.innowise.exeptions.PropertiesException;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Slf4j
public class PropertiesUtils {
    private static Properties properties = new Properties();

    static {
        try (InputStream inputStream = PropertiesUtils.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (inputStream == null) {
                log.error("Не удалось найти файл: application.properties");
                throw new IOException("Не удалось найти файл: application.properties");
            }
            properties.load(inputStream);
            log.info("Свойства из файла application.properties успешно загружены");
        } catch (IOException e) {
            e.printStackTrace();
            log.error("Ошибка при загрузке файла application.properties");
            throw new PropertiesException("Ошибка при загрузке файла application.properties", e);
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}
