insert into roles (name)
values
    ('ROLE_USER'), ('ROLE_ADMIN');
insert into users (username,password,email)
values
    ( 'TaskAuthor', '$2a$10$dzl3bQHE/9TQButInq2Itu/iTq.20BcHnKn8EsM5QcRVqmT4RzERi','authorEmail@mail.ru'),
    ( 'TaskExecutor', '$2a$10$ncQyPSj9ff5f5cpRTGqvAOJNr/oD9hR6Y6qS718vGyf4PwyP.tn9G','executorEmail@mail.ru');

insert into users_roles (user_id, role_id)
values
    (1, 1),
    (1, 2);
