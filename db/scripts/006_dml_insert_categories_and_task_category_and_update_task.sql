/* Author: fourbarman */
/*
 Insert categories
 */
insert into categories(name)
values ('Home'),
       ('Work'),
       ('Study'),
       ('Entertainment'),
       ('Cooking'),
       ('Sport'),
       ('Movies'),
       ('Music');
/*
 Insert into task_category
 */
insert into
    task_category(task_id, category_id)
select t.id, c.id from tasks t, categories c where t.description = 'Feed a cat' and c.name = 'Home';
insert into
    task_category(task_id, category_id)
select t.id, c.id from tasks t, categories c where t.description = 'Feed a cat' and c.name = 'Entertainment';

insert into
    task_category(task_id, category_id)
select t.id, c.id from tasks t, categories c where t.description = 'Make cake' and c.name = 'Cooking';
insert into
    task_category(task_id, category_id)
select t.id, c.id from tasks t, categories c where t.description = 'Make cake' and c.name = 'Study';

insert into
    task_category(task_id, category_id)
select t.id, c.id from tasks t, categories c where t.description = 'My birthday' and c.name = 'Entertainment';