create table if not exists todo_item (
    id identity primary key,
    title varchar(1000) not null,
    completed boolean not null
);