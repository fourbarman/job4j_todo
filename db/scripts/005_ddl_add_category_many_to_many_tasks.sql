/* Author: fourbarman */

/*
 Create table categories
 */
create table if not exists categories
(
    id   serial primary key,
    name text
);

/*
 Table for many-to-many tasks-categories.
 */
create table if not exists task_category
(
    id          serial primary key,
    task_id     int not null references tasks (id),
    category_id int not null references categories (id)
);

/*
 Add category_id
 */