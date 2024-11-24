# Migration Library

A lightweight Java library for managing database migrations using JDBC. 
The library provides functionality for applying SQL migration files, 
maintaining a history of applied migrations, supporting rollbacks, and ensuring thread-safe migrations with table locking.

## Technology

- Java 17
- Maven
- Lombok
- Logback
- Docker

## Functionality

- **Managing database migrations**
  - The program applies migrations defined as SQL files.
  - Provides the ability to manage the sequence of migrations.
- **Version controll**
  - The program understands what is the current version of the database and what migrations need to be applied.
  - If something goes wrong, the program rollback all applied migrations.
- **Configuration**
  - All settings are loaded from the application.properties file.
- **Migration file sources**
  - Migrations SQL files are loaded from application resources.
- **Synchronizing migrations**
  - The possibility of simultaneous migration run by different users is excluded.
  - Implemented a locking mechanism to prevent conflicts
- **Logging**
  - The migration execution process is logged (start, successful execution, errors).
  - The migration history is saved in a special table in the database

## Setup

Clone the repository
```bash
git clone https://github.com/Ury123/MigrationLibrary
```

## Database configuration

Change db.url, db.username and db.password in a file named `application.properties`** in the `src/main/resources` directory.
  ```properties
  db.driver=org.postgresql.Driver
  db.url=jdbc:postgresql://localhost:5432/postgres
  db.username=postgres
  db.password=postgres
  migration.path=migrations
  ```

## Add migration files

Add SQl files with your migrations in the `src/main/resources/migrations` directory.
You should name your files like 'V1__Create_table.sql', where V1 is version of your migration.
