package com.innowise.migration;

import com.innowise.utils.PropertiesUtils;
import lombok.extern.slf4j.Slf4j;

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


/**
 * Утилитарный класс для работы с файлами миграций.
 * Предоставляет методы для поиска файлов миграций, чтения их содержимого и разбора SQL-запросов.
 */
@Slf4j
public class MigrationFileReader {
    private static final String SQL_EXTENSION = ".sql";

    /**
     * Ищет файлы миграций в указанной директории ресурсов.
     * Директория указывается в файле свойств через ключ `migration.path`.
     *
     * @return отсортированный список имён файлов миграций с расширением ".sql".
     * @throws RuntimeException если возникают ошибки при поиске файлов.
     */
    public static List<String> findMigrationFilesInResources() {
        try {
            Path migrationPath = Paths.get(MigrationFileReader.class
                    .getClassLoader()
                    .getResource(PropertiesUtils.getProperty("migration.path"))
                    .toURI());

            return Files.list(migrationPath)
                    .filter(Files::isRegularFile)
                    .filter(file -> file.toString().endsWith(SQL_EXTENSION))
                    .map((path) -> {
                        log.info("Найдены файлы миграции: {}", path.getFileName().toString());
                        return path.getFileName().toString();
                    })
                    .sorted()
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Ошибка при поиске файлов миграции в ресурсах");
            throw new RuntimeException("Ошибка при поиске файлов миграции в ресурсах", e);
        }
    }

    /**
     * Считывает содержимое указанного SQL-файла из ресурсов.
     *
     * @param fileName имя SQL-файла, который нужно считать.
     * @return строка, содержащая полный текст SQL-файла.
     * @throws RuntimeException если файл не найден или произошла ошибка при его чтении.
     */
    public static String readSqlFileFromResources(String fileName) {
        try (InputStream inputStream = MigrationFileReader.class
                .getClassLoader()
                .getResourceAsStream( PropertiesUtils.getProperty("migration.path") + "/" + fileName)) {

            if (inputStream == null) {
                log.error("Файл миграции не найден: {}", fileName);
                throw new IOException("Файл миграции не найден: " + fileName);
            }

            return new BufferedReader(new InputStreamReader(inputStream))
                    .lines()
                    .collect(Collectors.joining("\n"));
        } catch (IOException e) {
            e.printStackTrace();
            log.error("Ошибка при чтении SQL-файла: {}", fileName);
            throw new RuntimeException("Ошибка при чтении SQL-файла: " + fileName, e);
        }
    }

    /**
     * Разбирает содержимое SQL-файла на отдельные запросы.
     * Запросы разделяются символом `;`.
     *
     * @param sqlContent строка, содержащая SQL-запросы.
     * @return список отдельных SQL-запросов.
     * @throws IllegalArgumentException если содержимое файла пустое или {@code null}.
     */
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
