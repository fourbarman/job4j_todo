/* Author: fourbarman */

/*
alter task for "one-to-many" user-task relation.
*/
alter table
    tasks
add
    user_id int references todo_user(id);
/*
 Update tasks set user_id as admin.
 */
 update tasks set user_id = (select u.id from todo_user u where login = 'admin');