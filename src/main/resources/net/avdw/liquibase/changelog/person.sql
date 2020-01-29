--liquibase formatted sql
--changeset andrew:2020-01-29
create table Person(
    "PK" INTEGER PRIMARY KEY AUTOINCREMENT,
    "Name" TEXT NOT NULL,
    "Active" INTEGER NOT NULL DEFAULT 'YES',
    "Role" TEXT NOT NULL,
    "Email" TEXT NOT NULL
);