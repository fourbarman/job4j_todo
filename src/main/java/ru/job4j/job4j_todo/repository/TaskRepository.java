package ru.job4j.job4j_todo.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.job4j.job4j_todo.model.Task;

import java.util.List;

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
    private final SessionFactory sf;

    /**
     * Get all tasks.
     *
     * @return List.
     */
    public List<Task> getAllTasks() {
        List tasks;
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            tasks = session.createQuery("from Task").list();
            session.getTransaction().commit();
            session.close();
        }
        return tasks;
    }

    /**
     * Get all tasks by "done" field.
     *
     * @param completed Completed or not.
     * @return List.
     */
    public List<Task> getAllByComplete(boolean completed) {
        List tasks;
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            Query<Task> query = session.createQuery("from Task where done = :fDone", Task.class);
            query.setParameter("fDone", completed);
            tasks = query.list();
        }
        return tasks;
    }

    /**
     * Add task.
     *
     * @param newTask New task.
     * @return Added Task.
     */
    public Task addTask(Task newTask) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            session.save(newTask);
            session.getTransaction().commit();
        }
        return newTask;
    }

    /**
     * Find task by id.
     *
     * @param id Id.
     * @return Found task.
     */
    public Task findTaskById(int id) {
        Task task;
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            Query query = session.createQuery("from Task where id = :fId", Task.class);
            query.setParameter("fId", id);
            task = (Task) query.uniqueResult();
        }
        return task;
    }

    /**
     * Update task.
     *
     * @param task Updated task.
     */
    public void updateTask(Task task) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            session.update(task);
            session.getTransaction().commit();
        }
    }

    /**
     * Delete task.
     *
     * @param task Task.
     */
    public void deleteTask(Task task) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            session.delete(task);
            session.getTransaction().commit();
        }
    }
}
