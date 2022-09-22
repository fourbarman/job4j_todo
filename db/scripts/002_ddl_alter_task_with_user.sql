/* Author: fourbarman */

/*
alter task for "one-to-many" user-task relation.
*/
alter table tasks
add user_id int references todo_user(id);