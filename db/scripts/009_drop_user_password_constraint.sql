/* Author: fourbarman */

/*
Drop unnecessary constraint on user password.
*/

ALTER TABLE todo_user DROP CONSTRAINT todo_user_password_key;