CREATE TABLE IF NOT EXISTS users (
"id" SERIAL PRIMARY KEY NOT NULL,
"name" VARCHAR(60) NOT NULL,
"email" VARCHAR NOT NULL UNIQUE,
"password" VARCHAR NOT NULL,
"role" SMALLINT
);