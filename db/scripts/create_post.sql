-- Схема для проекта Агрегатор
CREATE TABLE post(
id serial PRIMARY KEY,
name text,
text text,
LINK text UNIQUE,
created date
);