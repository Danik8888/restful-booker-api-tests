# Restful-Booker API tests

Автотесты REST API учебного сервиса Restful-Booker (restful-booker.herokuapp.com).

## Стек

Java 17, Maven, JUnit 5, REST Assured, Jackson, AssertJ, Allure.

## Запуск

```
mvn clean test
```

Allure-отчёт:

```
mvn test
mvn allure:serve
```

## Структура

```
src/test/java/io/demo/restfulbooker/
  config/TestConfig.java       базовый URL и доступы
  models/                      модели данных
  support/BookingFactory.java  фабрика тестовых данных
  tests/                       тестовые классы
```

## Что покрыто

- healthcheck GET /ping
- авторизация POST /auth: валидные и неверные данные
- CRUD бронирований: create, get, put, patch, delete
- список и фильтры GET /booking, схема ответа
- негативные сценарии: 403, 404, 400, XML-ответ

## Особенности API

- POST /booking отвечает 200, DELETE /booking/{id} - 201
- для PUT/PATCH/DELETE нужен Basic Auth (admin/password123)
- cookie-токен из POST /auth сервер не принимает
- Accept и Content-Type должны быть строго application/json
- с неверным паролем POST /auth отвечает 200, но с полем reason "Bad credentials"