# job4j_todo
Repository for "todo" project:

Web app with Spring boot, Thymeleaf, Bootstrap, Hibernate, PostgreSql .


This project is based on [JDK 17](https://www.oracle.com/java/technologies/javase-downloads.html#JDK17) and uses:
- [Maven](https://maven.apache.org/) (v. 3.6.3),
- [Springboot](https://spring.io/) (v. 2.7.3),
- [Bootstrap](https://getbootstrap.com/docs/4.4/getting-started/introduction/) (v. 4.4.1)
- [Thymeleaf](https://www.thymeleaf.org/)

The database layer is represented by:
- [Hibernate](https://hibernate.org/) (5.6.11.Final)
- [PostgreSQL](https://www.postgresql.org/) (v. 42.2),
- [Liquibase](https://www.liquibase.org/) (v. 4.15.0).

Tests use [JUnit5](https://junit.org/junit5/) and [Mockito](https://site.mockito.org/).

## articles
- Page with all tasks table. Table shows task name, task creation date, current task status (completed or not).
- All tasks table page has "create" button.
- Three links: All tasks, Completed tasks, New tasks.
- Detailed description available.
- On details page task can be set to "Complete", "Edit", "Delete":
  - "Complete" button sets "Completed" status to the task.
  - "Edit" button redirects to edit page.
  - "Delete" button deletes task from system and redirects to all tasks table.
## build and start
Steps to start a program from sources:
1. Create database with name "todo".
2. Database user should be **postgres** with **password** password.
3. Go through terminal\cmd to project sources and use maven command to generate .jar:
```mvn package```
4. If the project compiles successfully You will see folder "target" with generated _job4j_todo-1.0-SNAPSHOT.jar_.
5. Execute it from terminal\cmd with command:
```java -jar job4j_todo-1.0-SNAPSHOT.jar```
6. Final step is just open Your browser and head to project index page: **http://localhost:8080/index**.

### Contacts
Feel free for contacting me:
- **Skype**: pankovmv
