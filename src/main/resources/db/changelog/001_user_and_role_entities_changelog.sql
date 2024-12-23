-- liquibase formatted sql

-- changeset kiyotaka:1734887862784-1
CREATE TABLE role_entity
(
    name VARCHAR(255) NOT NULL,
    CONSTRAINT pk_role_entity PRIMARY KEY (name)
);

-- changeset kiyotaka:1734887862784-2
CREATE TABLE user_entity
(
    id       UUID         NOT NULL,
    email    VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    CONSTRAINT pk_user_entity PRIMARY KEY (id)
);

-- changeset kiyotaka:1734887862784-3
CREATE TABLE user_role_entity
(
    role_id VARCHAR(255) NOT NULL,
    user_id UUID         NOT NULL,
    CONSTRAINT pk_user_role_entity PRIMARY KEY (role_id, user_id)
);

-- changeset kiyotaka:1734887862784-4
ALTER TABLE user_entity
    ADD CONSTRAINT uc_user_entity_email UNIQUE (email);

-- changeset kiyotaka:1734887862784-5
ALTER TABLE user_role_entity
    ADD CONSTRAINT fk_userolent_on_role_entity FOREIGN KEY (role_id) REFERENCES role_entity (name);

-- changeset kiyotaka:1734887862784-6
ALTER TABLE user_role_entity
    ADD CONSTRAINT fk_userolent_on_user_entity FOREIGN KEY (user_id) REFERENCES user_entity (id);

