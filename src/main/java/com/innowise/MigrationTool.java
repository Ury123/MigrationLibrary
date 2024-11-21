package com.innowise;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class MigrationTool {
    public static void main(String[] args) {
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
                    migrationManager.applyMigration(version, sqlQueries);
                } else {
                    System.out.println("Миграция " + version + " уже применена.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}