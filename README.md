# GitHub Analytics

Spring Boot приложение для автоматического сбора и анализа данных с GitHub.

## Описание

Приложение регулярно собирает информацию о репозиториях и коммитах с GitHub API и сохраняет их в базу данных PostgreSQL. Пользователь вручную добавляет репозитории для отслеживания, а планировщик автоматически подтягивает новые коммиты.

## Технологии

- Java 17
- Spring Boot
- Spring Data JPA
- Spring Scheduler + `@Async`
- PostgreSQL
- Liquibase
- MapStruct
- Lombok
- RestTemplate

## Архитектура

```
GitHubRemote       — HTTP-запросы к GitHub API
GitHubService      — бизнес-логика, маппинг DTO → Entity
RepositoryService  — CRUD для репозиториев
CommitService      — CRUD для коммитов
ScheduledTasks     — планировщик, оркестрирует сбор коммитов
CommitMapper       — маппинг GitHubCommitDto → Commit (MapStruct)
RepositoryMapper   — маппинг GitHubRepositoryDto → Repository (MapStruct)
```

## API

### Репозитории

| Метод | URL | Описание |
|-------|-----|----------|
| `POST` | `/repository` | Добавить репозиторий для отслеживания |
| `GET` | `/repositories` | Получить все отслеживаемые репозитории |
| `GET` | `/repositories/{username}` | Получить репозитории пользователя с GitHub |

### Коммиты

| Метод | URL | Описание |
|-------|-----|----------|
| `GET` | `/commits/{name}` | Получить коммиты репозитория по имени |

## Как работает планировщик

1. Берёт все репозитории из БД
2. Для каждого репозитория проверяет — есть ли уже коммиты в БД
3. Если коммитов нет — тянет все коммиты с GitHub
4. Если есть — тянет только новые (через параметр `since` по дате последнего коммита)
5. Фильтрует последний коммит по `sha` чтобы не сохранить дубликат
6. Сохраняет новые коммиты в БД

## Запуск

### Требования

- Java 17+
- PostgreSQL
- Maven

### Настройка базы данных

Создайте базу данных:

```sql
CREATE DATABASE job4j_github_analysis;
```

### Конфигурация

`application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://127.0.0.1:5432/job4j_github_analysis
spring.datasource.username=postgres
spring.datasource.password=password
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.show-sql=true

spring.liquibase.change-log=classpath:db/db.changelog-master.xml

spring.task.scheduling.pool.size=10
scheduler.fixedRate=PT1M
```

### Сборка и запуск

```bash
mvn clean install
mvn spring-boot:run
```

## Пример использования

Добавить репозиторий для отслеживания:

```bash
curl -X POST http://localhost:8080/repository \
  -H "Content-Type: application/json" \
  -d '{"name": "job4j_design", "ownerLogin": "dmarakov"}'
```

Получить коммиты репозитория:

```bash
curl http://localhost:8080/commits/job4j_design
```

После добавления репозитория планировщик автоматически начнёт собирать коммиты с GitHub в соответствии с настроенным интервалом.