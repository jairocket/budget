CREATE TABLE IF NOT EXISTS "USERS" (
"ID" INT NOT NULL GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
"NAME" VARCHAR(60) NOT NULL,
"EMAIL" VARCHAR NOT NULL UNIQUE,
"PASSWORD" VARCHAR NOT NULL,
"ROLE" SMALLINT
);

CREATE TABLE IF NOT EXISTS "CATEGORIES" (
    "ID" INT NOT NULL GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    "NAME" VARCHAR(60) NOT NULL
);

CREATE TABLE IF NOT EXISTS "BOARDS" (
    "ID" INT NOT NULL GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    "USER_ID" INT NOT NULL
);

CREATE TABLE IF NOT EXISTS "FINANCIAL_RECORDS" (
    "ID" INT NOT NULL GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    "TITLE" VARCHAR NOT NULL,
    "PREDICTED_VALUE" FLOAT,
    "ACTUAL_VALUE" FLOAT,
    "DUE_DATE" DATE,
    "STATUS" SMALLINT,
    "TYPE" SMALLINT NOT NULL,
    "BOARD_ID" INT NOT NULL,
    CONSTRAINT "fk_boards_financial_records" FOREIGN KEY (BOARD_ID)
    REFERENCES BOARDS(ID)
);

CREATE TABLE IF NOT EXISTS "FINANCIAL_RECORDS_CATEGORIES" (
    "CATEGORY_ID" INT NOT NULL,
    "FINANCIAL_RECORD_ID" INT NOT NULL,
    CONSTRAINT fk_categories_financial_records FOREIGN KEY(CATEGORY_ID) REFERENCES CATEGORIES(ID),
    CONSTRAINT fk_financial_records_categories FOREIGN KEY(FINANCIAL_RECORD_ID) REFERENCES FINANCIAL_RECORDS(ID)
);