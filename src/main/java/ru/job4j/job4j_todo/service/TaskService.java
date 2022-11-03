package ru.job4j.job4j_todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.job4j_todo.model.Category;
import ru.job4j.job4j_todo.model.Priority;
import ru.job4j.job4j_todo.model.Task;
import ru.job4j.job4j_todo.model.User;
import ru.job4j.job4j_todo.repository.TaskRepository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

/**
 * TaskService.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version %I%, %G%.
 * @since 19.09.2022.
 */
@Service
@AllArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;

    /**
     * Set task created date and time in user's timezone.
     * If user doesn't have timezone, then set default.
     * @param user User.
     * @return Task list with user's timezone.
     */
    public List<Task> getAllTasks(User user) {
        List<Task> tasks = taskRepository.getAllTasks();
        if (user.getTimezone().isEmpty()) {
            user.setTimezone(TimeZone.getDefault().getID());
        }
        taskRepository.getAllTasks().forEach(t -> t.setCreated(t.getCreated().atZone(ZoneId.of(user.getTimezone(), ZoneId.SHORT_IDS)).toLocalDateTime()));
        return tasks;
    }

    public List<Task> getAllByComplete(boolean completed) {
        return this.taskRepository.getAllByComplete(completed);
    }

    public Task addTask(String description, User user, Priority priority, List<Category> categories) {
        return taskRepository.addTask(new Task(0, description, LocalDateTime.now(), false, user, priority, categories));
    }

    public Optional<Task> findTaskById(int id) {
        return this.taskRepository.findTaskById(id);
    }

    public void updateTask(Task task) {
        this.taskRepository.updateTask(task);
    }

    public boolean completeTask(int id) {
        Optional<Task> task = this.taskRepository.findTaskById(id);
        if (task.isPresent()) {
            task.get().setDone(true);
            this.taskRepository.updateTask(task.get());
            return true;
        }
        return false;
    }

    public void deleteTask(int id) {
        Optional<Task> task = this.taskRepository.findTaskById(id);
        task.ifPresent(this.taskRepository::deleteTask);
    }
}
