/* Author: fourbarman */

/*
Add column timezone to table users.
Update table todo_users initial data.
*/
ALTER TABLE todo_user
    ADD COLUMN timezone VARCHAR;

UPDATE todo_user
SET timezone = 'Europe/Ulyanovsk'
where login = 'user';
UPDATE todo_user
SET timezone = 'Asia/Novosibirsk'
where login = 'admin';