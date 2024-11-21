package com.innowise;

import com.innowise.utils.PropertiesUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MigrationFileReader {
    private static final String SQL_EXTENSION = ".sql";

    public static List<String> findMigrationFilesInResources() {
        try {
            Path migrationPath = Paths.get(MigrationFileReader.class
                    .getClassLoader()
                    .getResource(PropertiesUtils.getProperty("migration.path"))
                    .toURI());

            return Files.list(migrationPath)
                    .filter(Files::isRegularFile)
                    .filter(file -> file.toString().endsWith(SQL_EXTENSION))
                    .map(path -> path.getFileName().toString())
                    .sorted()
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Ошибка при поиске файлов миграции в ресурсах", e);
        }
    }

    public static String readSqlFileFromResources(String fileName) {
        try (InputStream inputStream = MigrationFileReader.class
                .getClassLoader()
                .getResourceAsStream( PropertiesUtils.getProperty("migration.path") + "/" + fileName)) {

            if (inputStream == null) {
                throw new IOException("Файл миграции не найден: " + fileName);
            }

            return new BufferedReader(new InputStreamReader(inputStream))
                    .lines()
                    .collect(Collectors.joining("\n"));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Ошибка при чтении SQL-файла: " + fileName, e);
        }
    }

    public static List<String> parseSqlFileContent(String sqlContent) {
        if (sqlContent == null || sqlContent.isEmpty()) {
            return Collections.emptyList();
        }

        return List.of(sqlContent.split(";"))
                .stream()
                .map(String::trim)
                .filter(query -> !query.isEmpty())
                .collect(Collectors.toList());
    }


}
