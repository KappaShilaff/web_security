--liquibase formatted sql

--changeset Plank:2
create table users (
    id   BIGSERIAL PRIMARY KEY,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(255) NOT NULL,
    username VARCHAR(255) UNIQUE NOT NULL,
    email VARCHAR(255) NOT NULL,
    not_blocked BOOLEAN NOT NULL
);

insert into users
values (0, '{bcrypt}$2a$10$FcfA1ecZW8wjatMhWDwjTew3bQYzm/dJsfDd2B2AFQ8hmqDBAzL.S', 'ADMIN', 'admin', 'admin@admin.ru', true);

--rollback drop table users;
