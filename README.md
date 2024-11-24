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
