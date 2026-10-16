# Java Backend Course 2026

Лабораторные работы по курсу Java Backend.

## Лабораторная 1

Вариант 23: чтение `type` из CSV, поиск уникальных значений через `HashSet` и запись результата в файл.

Maven удалит старую сборку (`clean`) и соберёт JAR (`package`):

```bash
mvn clean package
java -jar lab-1/target/lab-1-1.0.0-SNAPSHOT.jar
```

Результат: `lab-1/types.txt`.

## Лабораторная 2

Вариант 23: сущность `Item` (`id`, `name`, `quantity`), PostgreSQL и JDBC CRUD. Используются HikariCP и Flyway.

Создай `.env` по образцу `example.env`, укажи пароль и URL `jdbc:postgresql://localhost:5433/items_db`. Затем запусти базу из её папки:

```bash
cd lab-2/infra/database
docker compose up -d
```

Ключ `-d` запускает базу в фоне. Затем запусти `ru.kolosov.lab2.Main` из IDE. Приложение применит миграции и покажет работу CRUD-операций.
