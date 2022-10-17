/* Author: fourbarman */

/*
 Insert tasks
 */
insert into
    tasks(description, created, done)
VALUES
    ('Feed a cat', '2022-01-21T05:47:26.853Z', false),
    ('Make cake', '2022-02-21T05:47:26.853Z', false),
    ('My birthday', '2022-03-21T05:47:26.853Z', true);
/*
 Insert users
 */
insert into
    todo_user(username, login, password)
VALUES
    ('admin', 'admin', 'pass'),
    ('user', 'user', '1');