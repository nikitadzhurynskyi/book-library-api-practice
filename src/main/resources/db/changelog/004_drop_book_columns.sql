-- liquibase formatted sql

-- changeset kiyotaka:1735488667841-1
ALTER TABLE book_entity
    DROP COLUMN authors;
ALTER TABLE book_entity
    DROP COLUMN genres;

