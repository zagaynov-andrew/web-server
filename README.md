## :information_source:About

- Основной упор был направлен на backend, а frontend написан для тестирования.
- Сервер позволяет регистрировать и аутентифицировать пользователя при помощи двух сервлетов, обрабатывающих POST запросы на /signup и на /signin соответственно.
- Все данные о пользователях хранятся в базе данных (H2 или MySql).
- На фронтенде отправляются HTTP-запросы при помощи Ajax для фоновой проверки корректности введенных данных.
- В качестве заглушки служит общий чат для всех пользователей. Сообщения нигде не сохраняются.
- Чтобы страница чата не обновлялась постоянно, используется вебсокет, который обрабатывает запросы на /chat.
- Страница chat.html является шаблоном, формирование которой происходит динамически при помощи FreeMarker.

## :hammer:Build

Чтобы собрать сервер из исходного кода, склонируйте этот репозиторий:

```
git clone https://github.com/zagaynov-andrew/web-server.git
```

Затем перейдите в директорию `web-server` и скомпилируйте сервер, используя Maven:

```
cd web-server && mvn compile assembly:single
```

:pushpin: Для успешной сборки вам понадобятся установленные Maven и Java 11.

## :rocket:Launch

Для запуска сервера исполните Java-архив:

```
java -jar server.jar
```

После чего, можно проверить работоспособность на

[localhost:8080](http://localhost:8080)


