package ru.job4j.job4j_todo.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.job4j_todo.model.Task;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * TaskRepository.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version %I%, %G%.
 * @since 15.09.2022.
 */
@Repository
@AllArgsConstructor
public class TaskRepository {
    private final CrudRepository crudRepository;

    private final String GET_ALL = """
            select distinct t 
            from Task t 
            left join fetch 
            t.categories 
            join fetch 
            t.priority
            """;
    private final String GET_ALL_BY_COMPLETED = """
            select distinct t from Task t left join fetch t.categories join fetch t.priority where done = :fDone
            """;
    private final String FIND_TASK_BY_ID = """
            select distinct t from Task t left join fetch t.categories join fetch t.priority where t.id = :fId
            """;

    /**
     * Get all tasks.
     *
     * @return List.
     */
    public List<Task> getAllTasks() {
        return crudRepository.queryDistinct(GET_ALL, Task.class);
    }

    /**
     * Get all tasks by "done" field.
     *
     * @param completed Completed or not.
     * @return List.
     */
    public List<Task> getAllByComplete(boolean completed) {
        return crudRepository.query(GET_ALL_BY_COMPLETED, Task.class, Map.of("fDone", completed));
    }

    /**
     * Add task.
     *
     * @param newTask New task.
     * @return Added Task.
     */
    public Task addTask(Task newTask) {
        crudRepository.run(session -> session.persist(newTask));
        return newTask;
    }

    /**
     * Find task by id.
     *
     * @param id Id.
     * @return Found task.
     */
    public Optional<Task> findTaskById(int id) {
        return crudRepository.optional(FIND_TASK_BY_ID, Task.class, Map.of("fId", id));
    }

    /**
     * Update task.
     *
     * @param task Updated task.
     */
    public void updateTask(Task task) {
        crudRepository.run(session -> session.merge(task));
    }

    /**
     * Delete task.
     *
     * @param task Task.
     */
    public void deleteTask(Task task) {
        crudRepository.run(session -> session.delete(task));
    }
}
