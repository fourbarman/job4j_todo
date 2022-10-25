package ru.job4j.job4j_todo.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.Test;
import ru.job4j.job4j_todo.model.Category;
import ru.job4j.job4j_todo.model.Priority;
import ru.job4j.job4j_todo.model.Task;
import ru.job4j.job4j_todo.model.User;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

/**
 * PriorityRepositoryTest.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version %I%, %G%.
 * @since 21.10.2022.
 */
public class TaskRepositoryTest {
    private static final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
    private static final SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    private static final CrudRepository crudRepository = new CrudRepository(sessionFactory);
    private static final TaskRepository taskRepository = new TaskRepository(crudRepository);

    /**
     * Test getAll when get all tasks than return task list and success.
     */
    @Test
    public void whenGelAll() {
        Task stored = taskRepository.getAllTasks().get(0);
        Instant created = stored.getCreated();
        User user = stored.getUser();
        Priority priority = stored.getPriority();
        List<Category> categories = stored.getCategories();
        Task task1 = taskRepository.addTask(new Task(0, "description1", created, true, user, priority, categories));
        Task task2 = taskRepository.addTask(new Task(0, "description2", created, true, user, priority, categories));
        Task task3 = taskRepository.addTask(new Task(0, "description3", created, false, user, priority, categories));
        Task task4 = taskRepository.addTask(new Task(0, "description4", created, false, user, priority, categories));
        List<Task> tasks = taskRepository.getAllTasks();
        assertThat(tasks).containsAll(List.of(task1, task2, task3, task4));
    }

    /**
     * Test getAllByComplete when get all tasks by completed than return tasks and success.
     */
    @Test
    public void whenGetAllByCompleteThanSuccess() {
        int size = taskRepository.getAllTasks().size();
        List<Task> completed = taskRepository.getAllByComplete(true);
        List<Task> uncompleted = taskRepository.getAllByComplete(false);
        assertThat(completed.size()).isGreaterThan(0);
        assertThat(uncompleted.size()).isGreaterThan(0);
        assertThat(completed.size() + uncompleted.size()).isEqualTo(size);
    }

    /**
     * Test addTask when add task than success and return task with id.
     */
    @Test
    public void whenAddNewTaskThanSuccess() {
        String description = "description";
        Task stored = taskRepository.getAllTasks().get(0);
        Instant created = stored.getCreated();
        User user = stored.getUser();
        Priority priority = stored.getPriority();
        List<Category> categories = stored.getCategories();
        Task task = taskRepository.addTask(new Task(0, description, created, false, user, priority, categories));
        assertThat(task.getDescription()).isEqualTo(description);
        assertThat(task.getUser()).isEqualTo(user);
        assertThat(task.getPriority()).isEqualTo(priority);
        assertThat(task.getCategories()).containsAll(categories);
    }

    /**
     * Test findTaskById when find task by id than return found task and success.
     */
    @Test
    public void whenFindTaskById() {
        Task storedTask = taskRepository.getAllTasks().get(0);
        Optional<Task> foundTask = taskRepository.findTaskById(storedTask.getId());
        assertThat(foundTask).isPresent();
        assertThat(foundTask.get()).isEqualTo(storedTask);
    }

    /**
     * Test updateTask when update task than return updated task and success.
     */
    @Test
    public void whenUpdateTask() {
        Task storedTask = taskRepository.getAllTasks().get(0);
        int id = storedTask.getId();
        User user = storedTask.getUser();
        Priority priority = storedTask.getPriority();
        List<Category> categories = storedTask.getCategories();
        String newDescription = "new description";
        storedTask.setDescription(newDescription);
        taskRepository.updateTask(storedTask);
        Optional<Task> updatedTask = taskRepository.findTaskById(id);
        assertThat(updatedTask).isPresent();
        assertThat(updatedTask.get().getUser()).isEqualTo(user);
        assertThat(updatedTask.get().getPriority()).isEqualTo(priority);
        assertThat(updatedTask.get().getDescription()).isEqualTo(newDescription);
        assertThat(updatedTask.get().getCategories()).containsAll(categories);
    }

    /**
     * Test deleteTask when delete task than task deleted from database.
     */
    @Test
    public void whenDeleteTask() {
        String description = "description";
        Task stored = taskRepository.getAllTasks().get(0);
        Instant created = stored.getCreated();
        User user = stored.getUser();
        Priority priority = stored.getPriority();
        List<Category> categories = stored.getCategories();
        Task task = taskRepository.addTask(new Task(0, description, created, false, user, priority, categories));
        taskRepository.deleteTask(task);
        assertThat(taskRepository.getAllTasks()).doesNotContain(task);
    }
}
