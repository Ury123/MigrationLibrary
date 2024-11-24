package com.innowise;

import com.innowise.db.ConnectionManager;
import com.innowise.migration.MigrationFileReader;
import com.innowise.migration.MigrationManager;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Slf4j
public class MigrationTool {

    public static void run() {
        log.info("Старт выполнения миграций");
        try (Connection connection = ConnectionManager.getConnection()) {
            MigrationManager migrationManager = new MigrationManager(connection);

            migrationManager.createMigrationHistory();

            List<String> migrationFiles = MigrationFileReader.findMigrationFilesInResources();

            for (String migrationFile : migrationFiles) {
                String sqlContent = MigrationFileReader.readSqlFileFromResources(migrationFile);
                List<String> sqlQueries = MigrationFileReader.parseSqlFileContent(sqlContent);

                String version = migrationFile.split("__")[0];

                if (!migrationManager.isMigrationApplied(version)) {
                    System.out.println("Применение миграции: " + version);
                    log.info("Применение миграции {}", version);
                    migrationManager.applyMigration(version, sqlQueries);
                } else {
                    log.info("Миграция {} уже применена", version);
                    System.out.println("Миграция " + version + " уже применена.");
                }
            }
        } catch (SQLException e) {
            log.error("Ошибка ");
            e.printStackTrace();
        }
    }
}