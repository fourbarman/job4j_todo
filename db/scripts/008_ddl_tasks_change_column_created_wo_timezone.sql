/* Author: fourbarman */

/*
Table users.
Change column created to timestamp without timezone.
*/
ALTER TABLE tasks DROP COLUMN created;
ALTER TABLE tasks ADD COLUMN created TIMESTAMP WITHOUT TIME ZONE DEFAULT now();