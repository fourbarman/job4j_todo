CREATE TABLE IF NOT EXISTS tasks
(
    id          SERIAL PRIMARY KEY,
    description TEXT,
    created     TIMESTAMP,
    done        BOOLEAN
);

CREATE TABLE IF NOT EXISTS todo_user
(
    id       SERIAL PRIMARY KEY,
    username     VARCHAR NOT NULL,
    login    VARCHAR NOT NULL UNIQUE,
    password VARCHAR NOT NULL UNIQUE
);