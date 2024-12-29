-- liquibase formatted sql

-- changeset kiyotaka:1735486882143-1
CREATE TABLE book_entity
(
    id      BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    title   VARCHAR(255)                            NOT NULL,
    authors VARCHAR(255)                            NOT NULL,
    genres  VARCHAR(255)                            NOT NULL,
    CONSTRAINT pk_book_entity PRIMARY KEY (id)
);

-- changeset kiyotaka:1735486882143-2
CREATE TABLE user_book_entity
(
    book_id BIGINT NOT NULL,
    user_id UUID   NOT NULL
);

-- changeset kiyotaka:1735486882143-3
ALTER TABLE user_book_entity
    ADD CONSTRAINT fk_usebooent_on_book_entity FOREIGN KEY (book_id) REFERENCES book_entity (id);

-- changeset kiyotaka:1735486882143-4
ALTER TABLE user_book_entity
    ADD CONSTRAINT fk_usebooent_on_user_entity FOREIGN KEY (user_id) REFERENCES user_entity (id);
