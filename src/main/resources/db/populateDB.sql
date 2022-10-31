DELETE FROM tasks;

ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO tasks (name, section, description)
VALUES ('Гран-при России', 'Фигурное катание', '2-й этап'),
       ('Собрать тренеров без фото', 'Баскетбол', ''),
       ('Теги', 'Гимнастика', 'Массовая проработка');