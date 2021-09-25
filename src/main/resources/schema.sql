create table if not exists todo_item (
    id identity primary key,
    title varchar(1000) not null,
    completed boolean not null,
    ord integer not null
);

create table if not exists user (
    id identity primary key,
    email varchar(1000) not null,
    password_hash varchar(1000) not null
);

insert into user(id, email, password_hash) values(1, 'user@example.com', '$2a$10$yiPwkbpJQSXFrYxlQibFKeUu/FTkZI//h3.8qQFCOoVTSOlXZ8uuW');