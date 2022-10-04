/* Author: fourbarman */

/*
Add table priorities.
Add reference priorities to tasks table.
Insert priorities.
Insert ids.
*/
create table if not exists priorities
(
    id       serial primary key,
    name     text,
    position int
);

insert into priorities(name, position) values ('critical', 1);
insert into priorities(name, position) values ('high', 2);
insert into priorities(name, position) values ('medium', 3);
insert into priorities(name, position) values ('low', 4);

alter table tasks add column priority_id int references priorities(id);

update tasks set priority_id = (select id from priorities where name = 'medium' limit 1);