-- Схема для проекта Агрегатор
CREATE TABLE IF NOT EXISTS post(
id serial PRIMARY KEY,
name text,
text text,
link text UNIQUE,
created timestamp
);
